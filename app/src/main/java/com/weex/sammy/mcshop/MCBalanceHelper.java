package com.weex.sammy.mcshop;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by sammy on 2016/12/3.
 */

public class MCBalanceHelper {
    private Map<String, String> mNeedItemMap = new HashMap<String, String>();
    private Map<String, String> mWasteItemMap = new HashMap<String, String>();
    private void recursiveCalculate(String targetItemId, int needNumber) {
        if(needNumber <= 0) return;
        if(mWasteItemMap.containsKey(targetItemId)) {
            int remainNumber = Integer.parseInt(mWasteItemMap.get(targetItemId));
            if(remainNumber < needNumber) {
                mWasteItemMap.remove(targetItemId);
                recursiveCalculate(targetItemId, needNumber - remainNumber);
            } else if(remainNumber > needNumber) {
                remainNumber = remainNumber - needNumber;
                mWasteItemMap.put(targetItemId, Integer.toString(remainNumber));
            } else {
                mWasteItemMap.remove(targetItemId);
            }
        } else {
            MCItemFormula formula = MainActivity.sqliteHelper.getItemFormulaFromId(targetItemId);
            int outputNumber = Integer.parseInt(formula.outputNumber);
            int needNumberInt = needNumber;
            if(outputNumber > needNumber) {
                int remainNumber = outputNumber - needNumber;
                mWasteItemMap.put(targetItemId, Integer.toString(remainNumber));
                needNumberInt = 1;
            } else {
                double newNeedNumber = ((double)needNumber)/outputNumber;
                needNumberInt = (int)Math.ceil(newNeedNumber);
                int wasteNumber = needNumberInt*outputNumber - needNumber;
                if(wasteNumber > 0) {
                    mWasteItemMap.put(targetItemId, Integer.toString(wasteNumber));
                }
            }
            if(formula.items.isEmpty()) {
                if(mNeedItemMap.containsKey(targetItemId)) {
                    needNumberInt += Integer.parseInt(mNeedItemMap.get(targetItemId));
                }
                mNeedItemMap.put(targetItemId, Integer.toString(needNumberInt));
            } else {
                for(int i = 0; i < formula.items.size(); i++) {
                    recursiveCalculate(formula.items.get(i).id, needNumberInt);
                }
            }
        }
    }

    public MCBalance calculateFromShoppingList() {
        Map<String, String> shoppingMap = MCShoppingListHelper.mShoppingMap;
        Iterator<Map.Entry<String, String>> entries = shoppingMap.entrySet().iterator();
        while(entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            recursiveCalculate(entry.getKey(), Integer.parseInt(entry.getValue()));
        }
        return returnBalance();
    }

    private MCBalance returnBalance() {
        Iterator<Map.Entry<String, String>> needMapEntries = mNeedItemMap.entrySet().iterator();
        MCBalance mcBalance = new MCBalance();
        while(needMapEntries.hasNext()) {
            Map.Entry<String, String> entry = needMapEntries.next();
            MCItem itemInSql = MainActivity.sqliteHelper.getItemFromId(entry.getKey());
            MCItemForBalance item = new MCItemForBalance();
            item.name = itemInSql.name;
            item.icon = itemInSql.icon;
            item.id = itemInSql.id;
            item.category = itemInSql.category;
            item.number = entry.getValue();
            mcBalance.needItems.add(item);
        }
        Iterator<Map.Entry<String, String>> wasteMapEntries = mWasteItemMap.entrySet().iterator();
        while(wasteMapEntries.hasNext()) {
            Map.Entry<String, String> entry = wasteMapEntries.next();
            MCItem itemInSql = MainActivity.sqliteHelper.getItemFromId(entry.getKey());
            MCItemForBalance item = new MCItemForBalance();
            item.name = itemInSql.name;
            item.icon = itemInSql.icon;
            item.id = itemInSql.id;
            item.category = itemInSql.category;
            item.number = entry.getValue();
            mcBalance.wasteItems.add(item);
        }
        return mcBalance;
    }
}
