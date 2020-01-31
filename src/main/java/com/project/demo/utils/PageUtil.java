package com.project.demo.utils;

import com.github.pagehelper.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ck
 * #date 2019/10/13 13:37
 */
public class PageUtil {
    /**
     * @author Ck
     * 生成 {list:[],pageSize:int,pageRows:long} 格式的map，统一返回列表带分页信息的内容
     */
    public static <T> Map<String, Object> getListWithPageInfo(List<T> list, Page page) {
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("pageCount", page.getPages());
        map.put("pageRows", page.getTotal());
        return map;
    }
}
