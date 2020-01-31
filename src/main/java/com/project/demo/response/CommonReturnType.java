package com.project.demo.response;


import java.util.HashMap;
import java.util.Map;

public class CommonReturnType {
    private String status;
    private Object data;

    public static CommonReturnType create(String key, Object data) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, data);
        return CommonReturnType.create(map);
    }

    public static CommonReturnType create(Object data) {
        return CommonReturnType.create(data, "success");
    }

    public static CommonReturnType create(Object data, String status) {
        CommonReturnType type = new CommonReturnType();
        type.setStatus(status);
        type.setData(data);
        return type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

