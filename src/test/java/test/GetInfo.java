package test;

import com.mtl.pojo.Item;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class GetInfo {

    private static String lastTopic;            //上一次查询最终的帖子的标题, 用来判断是否解析到上次解析的位置
    private static String thisTopic;            // 暂时保存这次解析的第一个标题, 最后加到lastTopic中去

    @Test
    public void getInfo(){
        try {
            OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象

            for (int i = 1; i <= 2; i++) {

                //构建请求对象    通过内部类Request.Builder构建
                Request request = new Request.Builder()
                        .url("https://www.52pojie.cn/forum.php?mod=guide&view=newthread&page=" + i)//请求接口。如果需要传参拼接到接口后面。
                        .build();
                Response response = null;

                //发送请求得到response对象
                response = client.newCall(request).execute();

                //判断返回状态码
                if (response.isSuccessful()) {
                    String string = response.body().string();

//                    查看返回的response头信息, 实际上用来设置返回的cookie的, 还没有完成
//                    Headers header = response.headers();
//                    for (int j = 0; j < header.size(); j++) {
//                    System.out.println(header.name(i) + "-----" + header.value(i));
//                    }
//                    System.out.println(string);

                    //调用方法解析html文本
                    ParseHtml parseHtml = new ParseHtml();
                    List<Item> items = parseHtml.getCurrentPageItems(string, lastTopic);

                    testInsert(items);
                    if (i == 1){
                        thisTopic = items.get(0).getTitle();
                    }
                    if (parseHtml.isFind()){
                        break;
                    }
                }
            }
            lastTopic = thisTopic;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testInsert(List<Item> items){
        try {
            InputStream resourceAsStream= Resources.getResourceAsStream("mybatis.xml");
            SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsStream);
            SqlSession sqlSession = build.openSession();
            int insert = sqlSession.insert("com.mtl.mapper.ItemMapper.insertItems", items);
            System.out.println("insert = " + insert);
            sqlSession.commit();
            sqlSession.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
