package com.dziadkouskaya.findinshop.by.parsing.model.logic.structuresForComparison;

import android.util.ArrayMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class CompareHashMap {
	public static void divideStringIntoPiecesFromMap(ArrayMap<String, ArrayList<ArrayMap<String, String>>> sourceHash,
											  ArrayMap<String, ArrayList<String>> resultHash) {

		for (String key : sourceHash.keySet()) {
			String changedKey = key;
			String[] mass = changedKey.split(" ");
			ArrayList<String> arr1 = new ArrayList<>(Arrays.asList(mass));
			Collections.sort(arr1);
			resultHash.put(changedKey, arr1);

		}
	}

	public static void divideStringIntoPiecesFromList(ArrayList<ArrayList<String>> resultArray
			, ArrayMap<String, ArrayList<String>> arrayForEquals) {
		for (int i = 0; i < resultArray.size(); i ++) {
			String[] mass = resultArray.get(i).get(0).split(" ");
			ArrayList<String> arr = new ArrayList<>(Arrays.asList(mass));
			Collections.sort(arr);
			arrayForEquals.put(resultArray.get(i).get(0), arr);

		}
	}

}
