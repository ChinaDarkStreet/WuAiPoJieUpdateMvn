package com.mtl.mapper;

import com.mtl.pojo.Item;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;

import java.util.List;

public interface ItemMapper {

    int insertItem(Item item);

    int insertItems(List<Item> list);
}
