package com.mtl.ThreadMy;

import com.mtl.pojo.Item;
import com.mtl.service.ItemParseService;
import com.mtl.service.ItemService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ReptileThread implements Runnable {

    @Autowired
    private ItemParseService itemParseService;

    @Autowired
    private ItemService itemService;
    private String thisTopic;
    private String lastTopic;

    @Override
    public void run() {
        try {
            OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象

            for (int i = 1; i <= 8; i++) {
                Request request = new Request.Builder()
                        .url("https://www.52pojie.cn/forum.php?mod=guide&view=newthread&page=" + i)//请求接口。如果需要传参拼接到接口后面。
                        .build();                                           //创建Request 对象
                Response response = null;
                response = client.newCall(request).execute();               //得到Response 对象
                if (response.isSuccessful()) {
                    String string = response.body().string();
                    System.out.println(string);
                    List<Item> items = itemParseService.parseHtml(string, lastTopic);
                    itemService.addItems(items);
                    if (i == 1){
                        thisTopic = items.get(0).getTitle();
                    }
                    if (itemParseService.isFind()){
                        break;
                    }
                }
            }
            lastTopic = thisTopic;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
