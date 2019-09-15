package com.example.findinshop.by.parsing.model.logic.addElementsToHashMap;

import android.util.ArrayMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

import com.example.findinshop.by.parsing.model.entity.shops.ShopList;
import com.example.findinshop.by.parsing.model.stringCorrect.StringCorrect;

public class HashMapElementsAddition {
    public static ArrayMap<String, ArrayList<ArrayMap<String, String>>> addElementsToMainResultHashMap(
            ArrayList<ArrayList<String>> sourceArray, ArrayMap<String, ArrayList<ArrayMap<String, String>>> resultHashMap,
            ShopList list, int indName, int indPrice) {


        for (int i = 0; i < sourceArray.size(); i ++) {

                ArrayMap<String, String> innerMap = new ArrayMap<String, String>();

                String inerKey = list.getShopList().get(0).getShopName();
                String innerValue = sourceArray.get(i).get(indPrice);
                innerMap.put(inerKey, innerValue);

                ArrayList<ArrayMap<String, String>> valueArray = new ArrayList<ArrayMap<String, String>>();
                valueArray.add(innerMap);
                String innerRequest = StringCorrect.stringCorrectForParsing(sourceArray.get(i).get(indName));
                resultHashMap.put(innerRequest, valueArray);
            }

            return resultHashMap;

        }

    public static ArrayMap<String, String> addElementsToImageHashMap(
            ArrayList<ArrayList<String>> sourceArray, ArrayMap<String, String> imageHashMap,
            int indName, int indImage) {


        for (int i = 0; i < sourceArray.size(); i ++) {

            String prodName = StringCorrect.stringCorrectForParsing(sourceArray.get(i).get(indName));
            String image = sourceArray.get(i).get(indImage);
            imageHashMap.put(prodName, image);
        }

        return imageHashMap;

    }

    public static ArrayMap<String, ArrayList<ArrayMap<String, String>>> addElementsToLinkHashMap(
            ArrayList<ArrayList<String>> sourceArray, ArrayMap<String, ArrayList<ArrayMap<String, String>>> linkHashMap,
            ShopList list, int indName, int indLink) {


        for (int i = 0; i < sourceArray.size(); i ++) {

            ArrayMap<String, String> innerMap = new ArrayMap<String, String>();

            String inerKey = list.getShopList().get(0).getShopName();
            String innerValue = sourceArray.get(i).get(indLink);
            innerMap.put(inerKey, innerValue);

            ArrayList<ArrayMap<String, String>> valueArray = new ArrayList<ArrayMap<String, String>>();
            valueArray.add(innerMap);
            String innerRequest = StringCorrect.stringCorrectForParsing(sourceArray.get(i).get(indName));
            linkHashMap.put(innerRequest, valueArray);
        }

        return linkHashMap;

    }



    }
