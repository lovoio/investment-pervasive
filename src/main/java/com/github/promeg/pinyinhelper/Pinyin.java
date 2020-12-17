package com.github.promeg.pinyinhelper;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author guyacong
 * @date 2015/9/28
 */
public final class Pinyin {

    static List<PinyinDict> mPinyinDicts = null;

    private Pinyin() {
    }

    /**
     * 使用 {@link Pinyin.Config} 初始化Pinyin。
     *
     * @param config 相应的设置，传入null则会清空所有的词典
     */
    public static void init(Config config) {
        if (config == null) {
            // 清空设置
            mPinyinDicts = null;
            return;
        }
        mPinyinDicts = Collections.unmodifiableList(config.getPinyinDicts());
    }

    /**
     * 向Pinyin中追加词典。
     *
     * 注意: 若有多个词典，推荐使用性能更优的 {@link Pinyin#init(Config)} 初始化Pinyin。
     *
     * @param dict 输入的词典
     */
    public static void add(PinyinDict dict) {
        if (dict == null || dict.words() == null || dict.words().size() == 0) {
            // 无效字典
            return;
        }
        init(new Config(mPinyinDicts).with(dict));
    }

    /**
     * 返回新的{@link Pinyin.Config} 对象
     *
     * @return 新的Config对象
     */
    public static Config newConfig() {
        return new Config(null);
    }



    /**
     * 将输入字符转为拼音
     *
     * @param c 输入字符
     * @return return pinyin if c is chinese in uppercase, String.valueOf(c) otherwise.
     */
    public static String toPinyin(char c) {
        if (isChinese(c)) {
            if (c == PinyinData.CHAR_12295) {
                return PinyinData.PINYIN_12295;
            } else {
                return PinyinData.PINYIN_TABLE[getPinyinCode(c)];
            }
        } else {
            return String.valueOf(c);
        }
    }

    /**
     * 将输入字符转为拼音
     *
     * @param c 输入字符
     * @param rules 自定义规则，具有最高优先级
     * @return return pinyin if c is chinese in uppercase, String.valueOf(c) otherwise.
     */
    public static String toPinyin(char c, PinyinRules rules) {
        if (rules != null && rules.toPinyin(c) != null) {
            return rules.toPinyin(c);
        } else {
            return toPinyin(c);
        }
    }

    /**
     * 判断输入字符是否为汉字
     *
     * @param c 输入字符
     * @return return whether c is chinese
     */
    public static boolean isChinese(char c) {
        return (PinyinData.MIN_VALUE <= c && c <= PinyinData.MAX_VALUE
                && getPinyinCode(c) > 0)
                || PinyinData.CHAR_12295 == c;
    }

    private static int getPinyinCode(char c) {
        int offset = c - PinyinData.MIN_VALUE;
        if (0 <= offset && offset < PinyinData.PINYIN_CODE_1_OFFSET) {
            return decodeIndex(PinyinCode1.PINYIN_CODE_PADDING, PinyinCode1.PINYIN_CODE, offset);
        } else if (PinyinData.PINYIN_CODE_1_OFFSET <= offset
                && offset < PinyinData.PINYIN_CODE_2_OFFSET) {
            return decodeIndex(PinyinCode2.PINYIN_CODE_PADDING, PinyinCode2.PINYIN_CODE,
                    offset - PinyinData.PINYIN_CODE_1_OFFSET);
        } else {
            return decodeIndex(PinyinCode3.PINYIN_CODE_PADDING, PinyinCode3.PINYIN_CODE,
                    offset - PinyinData.PINYIN_CODE_2_OFFSET);
        }
    }

    private static short decodeIndex(byte[] paddings, byte[] indexes, int offset) {
        //CHECKSTYLE:OFF
        int index1 = offset / 8;
        int index2 = offset % 8;
        short realIndex;
        realIndex = (short) (indexes[offset] & 0xff);
        //CHECKSTYLE:ON
        if ((paddings[index1] & PinyinData.BIT_MASKS[index2]) != 0) {
            realIndex = (short) (realIndex | PinyinData.PADDING_MASK);
        }
        return realIndex;
    }

    public static final class Config {

        List<PinyinDict> mPinyinDicts;
        private Config(List<PinyinDict> dicts) {
            if (dicts != null) {
                mPinyinDicts = new ArrayList<>(dicts);
            }

        }

        /**
         * 添加字典
         *
         * @param dict 字典
         * @return 返回Config对象，支持继续添加字典
         */
        public Config with(PinyinDict dict) {
            if (dict != null) {
                if (mPinyinDicts == null) {
                    mPinyinDicts = new ArrayList<>();
                    mPinyinDicts.add(dict);
                } else if (!mPinyinDicts.contains(dict)) {
                    mPinyinDicts.add(dict);
                }
            }
            return this;
        }
        List<PinyinDict> getPinyinDicts() {
            return mPinyinDicts;
        }
    }
}
