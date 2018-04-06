package test;

import com.mtl.pojo.Item;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseHtml {

    private boolean isFind = false;     //用来判断是否解析到了上次执行的最后一个标题, 结束条件

    /**
     * 获取当前html页面的所有item对象
     * @param html  当前页面的html字符串
     * @param lastTitle 停止解析的帖子标题
     * @return  item集合
     * @throws IOException  okhttp抛出的异常
     */
    public List<Item> getCurrentPageItems(String html, String lastTitle) throws IOException {

        ArrayList<Item> items = new ArrayList<>();

        //Jsoup解析html文本获取Document对象
        Document parse = Jsoup.parse(html);
        Element body = parse.body();

        //通过选择器获取到标志的div然后赋值给item
        Element element = body.selectFirst("div#forumnew");
//        System.out.println("element = " + element);
        Element table = element.nextElementSibling();
        Elements tbodys = table.select("tbody");
        for (int j = 0; j < tbodys.size(); j++) {
            element = tbodys.get(j);
            String title = element.selectFirst("a.xst").html();
            if (title.equals(lastTitle)){   //如果查找到上次的最后的话题就直接结束并通知前台找到了标记
                isFind = true;
                break;
            }
            Item item = new Item();
            item.setTitle(title);
            Element tbody = element.selectFirst("tbody");
            Elements tds = tbody.select("td");
            for (int i = 0; i < tds.size(); i++) {
                Element td = tds.get(i);
                switch (i){
                    case 0:
                        item.setUrl("https://www.52pojie.cn/" + td.selectFirst("a").attr("href"));
                        Element span = td.selectFirst("span");
                        if (span != null)
                            item.setAuthorityLevel(span.html());
                        break;
                    case 1:
                        item.setPartition(td.selectFirst("a").html());
                        break;
                    case 2:
                        item.setAuther(td.selectFirst("a").html());
                        item.setPublishTime(td.selectFirst("span").html());
                        break;
                    case 3:
                        item.setReplyNum(td.selectFirst("a").html());
                        item.setViewNum(td.selectFirst("em").html());
                        break;
                    case 4:
                        item.setLastReplyName(td.selectFirst("a").html());
                        item.setLastReplyTime(td.selectFirst("em").selectFirst("a").html());
                        item.setLastReplyUrl("https://www.52pojie.cn/" + td.selectFirst("a").attr("href"));
                        break;
                }
            }
            try {
                parseLink(item);
            }catch (Exception e){
                e.printStackTrace();
            }
            items.add(item);
        }
        return items;
    }

    /**
     * 解析item内部的百度云链接
     * @param item  item对象
     */
    private void parseLink(Item item) throws IOException {
        if (item.getAuthorityLevel() == null) {
            OkHttpClient okHttpClient = new OkHttpClient();
            String url = item.getUrl();
            Request build = new Request.Builder()
                    .url(url)
                    .build();
            Response response = okHttpClient.newCall(build).execute();
            if (response.isSuccessful()){
                String string = response.body().string();
                //            System.out.println(string);
                Matcher matcher = Pattern.compile("[^\"](https://pan.baidu.com/s/[\\w\\-0-9_]+[a-zA-Z_0-9])((?!https).)+密码: ?([a-zA-Z0-9]{4})[^a-zA-Z0-9]").matcher(string);
                StringBuilder links = new StringBuilder();
                StringBuilder pwds = new StringBuilder();
                while (matcher.find()){
                    if (links.indexOf(matcher.group(1)) == -1){
                        links.append(matcher.group(1)).append(";");
                        pwds.append(matcher.group(3)).append(";");
                    }
//                    System.out.println("match = " + matcher.group(0));
                }
                if (!links.toString().equals("")){
                    item.setLinksAndPwdsStr(links.toString() + "#;#" + pwds.toString());
                }
            }
        }
    }

    /**
     * 测试需要阅读权限的链接返回的报文体    为以后自动登录获取链接做准备
     * @throws IOException
     */
    @Test
    public void testLink() throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request build = new Request.Builder()
                .url("https://www.52pojie.cn/thread-719615-1-1.html")
                .build();
        Response response = okHttpClient.newCall(build).execute();
        if (response.isSuccessful()){
            String string = response.body().string();
            Matcher authLevel = Pattern.compile("抱歉，本帖要求阅读权限高于 \\d+ 才能浏览").matcher(string);
            System.out.println(string);
            if (authLevel.find()) {
                System.out.println("需要权限");
            }else {
                Matcher matcher = Pattern.compile("[^\"](https://pan.baidu.com/s/[\\w\\-0-9_]+[a-zA-Z_0-9])((?!https).)+密码: ?([a-zA-Z0-9]{4})[^a-zA-Z0-9]").matcher(string);
                while (matcher.find()){
                    System.out.println("match = " + matcher.group(1) + "--" + matcher.group(3));
                }
            }
        }
    }

    public boolean isFind() {
        return isFind;
    }

    public void setFind(boolean find) {
        isFind = find;
    }
}
