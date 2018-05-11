package com.chebao.utils;

import java.util.UUID;

/**
 * Created by Administrator on 2017/1/12.
 */

public class UUIDs {

    public static String uuid(){
        String getid= UUID.randomUUID().toString().replace("-", "");
        return getid;
    }
}
