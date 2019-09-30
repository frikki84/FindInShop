package com.dziadkouskaya.findinshop.by.parsing.model.logic.addElementsToHashMap;

import android.util.ArrayMap;

import com.dziadkouskaya.findinshop.by.parsing.model.entity.request.Request;
import com.dziadkouskaya.findinshop.by.parsing.model.entity.shops.ShopList;
import com.dziadkouskaya.findinshop.by.parsing.model.logic.structuresForComparison.CompareHashMap;
import com.dziadkouskaya.findinshop.by.parsing.model.stringCorrect.StringCorrect;

import java.util.ArrayList;
import java.util.Map;

public class ActionWithHashMaps {

    public static ArrayMap<String, ArrayList<ArrayMap<String
            , String>>> fillResultHashMap
            (ArrayMap<String
            , ArrayList<ArrayMap<String, String>>> resultHashMap, ShopList list, Request request
            ,ArrayMap<String, ArrayList<String>> hashmapForEquals, ArrayMap<String
            , String> middleHashMap, int indPrice) {

        ArrayList<ArrayList <String>> innerResult = list.getShopList().get(0).searchProduct(request);
        ArrayMap<String, ArrayList<String>> hashForEqualsLittle = new ArrayMap<String, ArrayList<String>>();
        CompareHashMap.divideStringIntoPiecesFromList(innerResult, hashForEqualsLittle);

        int counter = 0;
        int n = 0;
        for (Map.Entry<String, ArrayList<String>> itemLittle : hashForEqualsLittle.entrySet()) {

            for (Map.Entry<String, ArrayList<String>> itemBig : hashmapForEquals.entrySet()) {

                if (itemLittle.getValue().equals(itemBig.getValue())) {
                    ArrayMap<String, String> innerMap = new ArrayMap<String, String>();
                    String inerKey = list.getShopList().get(0).getShopName();
                    String innerValue = innerResult.get(n).get(indPrice);
                    innerMap.put(inerKey, innerValue);
                    resultHashMap.get(itemBig.getKey()).add(innerMap);
                } else {
                    counter += 1;
                }
                n += 1;

            }


            if (counter == hashmapForEquals.size()) {
                middleHashMap.put(itemLittle.getKey(), innerResult.get(n).get(indPrice));
                counter = 0;
            }
        }


        for (Map.Entry<String, String> itemMid : middleHashMap.entrySet()) {
            ArrayMap<String, String> innerMap = new ArrayMap<String, String>();
            String inerKey = list.getShopList().get(0).getShopName();
            String innerValue = itemMid.getValue().toString();
            innerMap.put(inerKey, innerValue);
            ArrayList<ArrayMap<String, String>> valueArray = new ArrayList<ArrayMap<String, String>>();
            valueArray.add(innerMap);
            String innerRequest = StringCorrect.stringCorrectForParsing(itemMid.getKey().toString());
            resultHashMap.put(innerRequest, valueArray);

        }

        CompareHashMap.divideStringIntoPiecesFromMap(resultHashMap, hashmapForEquals);
        middleHashMap.clear();




        return  resultHashMap;
    }
}
