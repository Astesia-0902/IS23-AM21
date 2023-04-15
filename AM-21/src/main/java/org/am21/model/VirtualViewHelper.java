package org.am21.model;

import com.alibaba.fastjson2.JSON;

/**
 * We use this class to build the virtual view
 */
public class VirtualViewHelper{
    public static VirtualView virtualViewBuilder(Match match) {
        VirtualView res = new VirtualView();
        //TODO:build the virtual view
        return res;
    }

    public static String getVirtualViewJSON(VirtualView virtualView) {
        return JSON.toJSONString(virtualView);
    }
}