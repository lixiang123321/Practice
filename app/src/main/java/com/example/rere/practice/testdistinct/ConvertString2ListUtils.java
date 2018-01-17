package com.example.rere.practice.testdistinct;

import com.example.rere.practice.base.utils.TagLog;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by rere on 18-1-2.
 */

public class ConvertString2ListUtils {

    private static final String TAG = ConvertString2ListUtils.class.getSimpleName();


    public static List<String> convert(String listStr, String splitStr) {
        TagLog.i(TAG, "convert() : " + " listStr = " + listStr + ","
                + " splitStr = " + splitStr + ","
        );

        List<String> list = new ArrayList<>();

        if (TextUtils.isEmpty(listStr)) {
            TagLog.w(TAG, "convert() : " + "listStr is empty.");
            return list;
        }

        if (TextUtils.isEmpty(splitStr)) {
            splitStr = ",";
            TagLog.w(TAG, "convert() : " + "use default splitStr");
        }

        String[] split = listStr.split(splitStr);
        for (String s : split) {
            String trim = s.trim();
            if (!TextUtils.isEmpty(trim)) {
                list.add(trim);
            }
        }

        TagLog.i(TAG, "convert() : " + " list = " + list + ",");
        return list;
    }

    public static <T> void getDuplicateAndUniqueBetweenTwoList(List<T> targetList, List<T> baseList, LinkedHashSet<T> duplicatesResult, LinkedHashSet<T> uniquesResult) {
        TagLog.i(TAG, "getDuplicateAndUniqueBetweenTwoList() : " + " targetList = " + targetList + ","
                + " baseList = " + baseList + ","
                + " duplicatesResult = " + duplicatesResult + ","
                + " uniquesResult = " + uniquesResult + ","
        );

        if (null == targetList || null == baseList) {
            TagLog.w(TAG, "getDuplicateAndUniqueBetweenTwoList() : " + "one list is null.");
            return;
        }

        if (null == duplicatesResult || null == uniquesResult) {
            TagLog.i(TAG, "getDuplicateAndUniqueBetweenTwoList() : " + "one result is null");
            return;
        }


        for (T s : baseList) {
            if (targetList.contains(s)) {
                duplicatesResult.add(s);
            } else {
                uniquesResult.add(s);
            }
        }

        TagLog.i(TAG, "getDuplicateAndUniqueBetweenTwoList() : " + " duplicatesResult = " + duplicatesResult + ",");
        TagLog.i(TAG, "getDuplicateAndUniqueBetweenTwoList() : " + " uniquesResult = " + uniquesResult + ",");

    }

}
