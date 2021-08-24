package com.ssm.utils;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Msg {
    //状态码 返回 200 成功, 返回 500 失败
    private int status;

    private String msg;

    private Map<String, Object> extend = new HashMap<String, Object>();

    /**
     * 处理成功方法
     * @return
     */
    public static Msg success() {
        Msg result = new Msg();
        result.setStatus(200);
        result.setMsg("处理成功!");
        return result;
    }

    /**
     * 处理失败方法
     * @return
     */
    public static Msg fail() {
        Msg result = new Msg();
        result.setStatus(500);
        result.setMsg("处理失败!");
        return result;
    }

    /**
     * 封装信息方法
     * @param key
     * @param value
     * @return
     */
   public Msg add(String key, Object value) {
       this.getExtend().put(key, value);
       return this;
   }
}
