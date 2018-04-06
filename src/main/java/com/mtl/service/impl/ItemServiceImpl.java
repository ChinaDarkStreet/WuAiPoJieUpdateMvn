package com.mtl.service.impl;

import com.mtl.mapper.ItemMapper;
import com.mtl.pojo.Item;
import com.mtl.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public void addItems(List<Item> list) {
        itemMapper.insertItems(list);
    }
}
