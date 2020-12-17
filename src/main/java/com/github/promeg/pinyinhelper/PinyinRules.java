package com.github.promeg.pinyinhelper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author guyacong
 */
public final class PinyinRules {
    private final Map<String, String[]> mOverrides = new HashMap<>();

    public PinyinRules add(char c, String pinyin) {
        mOverrides.put(String.valueOf(c), new String[]{pinyin});
        return this;
    }

    public PinyinRules add(String str, String pinyin) {
        mOverrides.put(str, new String[]{pinyin});
        return this;
    }

    String toPinyin(char c) {
        return mOverrides.get(String.valueOf(c))[0];
    }

    BasePinyinMapDict toPinyinMapDict() {
        return new BasePinyinMapDict() {
            @Override
            public Map<String, String[]> mapping() {
                return mOverrides;
            }
        };
    }
}
