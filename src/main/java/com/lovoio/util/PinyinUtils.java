package com.lovoio.util;

import com.github.promeg.pinyinhelper.Pinyin;

/**
 * @author <a href="https://github.com/bytebeats">bytebeats</a>
 * @since 2020/8/19 16:48
 */
public class PinyinUtils {
    public static String toPinyin(String input) {
        StringBuilder pyb = new StringBuilder();
        for (char ch : input.toCharArray()) {
            char[] pys = Pinyin.toPinyin(ch).toLowerCase().toCharArray();
            if (pys.length > 0) {
                pys[0] = Character.toUpperCase(pys[0]);
                pyb.append(pys);
            }
        }
        return pyb.toString();
    }

}
