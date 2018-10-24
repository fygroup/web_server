package com.jeeplus.common.utils;

import java.util.List;

/**
 * Created by Le on 2017/10/31.
 */
public class CheckObject {
    public static boolean checkList(List list){
        if(list!=null&&list.size()>0){
            return true;
        }

        return false;
    }
}
