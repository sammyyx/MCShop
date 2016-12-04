package com.weex.sammy.mcshop;

import com.taobao.weex.bridge.WXJSObject;
import com.taobao.weex.common.IWXObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sammy on 2016/12/3.
 */

public class MCBalance implements IWXObject {
    public List<MCItemForBalance> needItems = new ArrayList<MCItemForBalance>();
    public List<MCItemForBalance> wasteItems = new ArrayList<MCItemForBalance>();
}
