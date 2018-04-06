package com.mtl.service;

import com.mtl.pojo.Item;

import java.io.IOException;
import java.util.List;

public interface ItemParseService {
    List<Item> parseHtml(String html, String lastTitle) throws IOException;

    boolean isFind();
}
