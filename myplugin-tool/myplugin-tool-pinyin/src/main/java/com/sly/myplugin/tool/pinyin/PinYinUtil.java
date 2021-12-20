package com.sly.myplugin.tool.pinyin;

import net.duguying.pinyin.Pinyin;

/**
 * 拼音工具类
 *
 * @author SLY
 * @date 2021/12/7
 */
public class PinYinUtil {

    private static volatile Pinyin pinyin = null;

    private static final String SEPARATOR = ",";

    /**
     * 获取拼音首字母
     *
     * @param text 文本
     * @return {@link String}
     * @author SLY
     * @date 2021/11/9
     */
    public static String getFirstLetter(String text) {
        if (text != null && !"".equals(text)) {
            initPinYin();
            String[] arrayNoMark = pinyin.translateInArrayNoMark(text);
            StringBuilder pinyinStr = new StringBuilder();
            for (String s : arrayNoMark) {
                pinyinStr.append(s.charAt(0));
            }
            return pinyinStr.toString();
        }
        return "";
    }

    /**
     * 获取拼音
     *
     * @param text 文本
     * @return {@link String}
     * @author SLY
     * @date 2021/12/7
     */
    public static String getFullLetter(String text) {
        if (text != null && !"".equals(text)) {
            initPinYin();
            return pinyin.translateNoMark(text);
        }
        return "";
    }

    /**
     * 获取拼音带分隔符
     *
     * @param text      文本
     * @param separator 分隔符
     * @return {@link String}
     * @author SLY
     * @date 2021/12/7
     */
    public static String getFullLetterWithSeparatorNoMark(String text, String separator) {
        if (text != null && !"".equals(text)) {
            initPinYin();
            return pinyin.translateWithSepNoMark(text, separator);
        }
        return "";
    }

    /**
     * 获取拼音带分隔符（默认分隔符","）
     *
     * @param text 文本
     * @return {@link String}
     * @author SLY
     * @date 2021/12/7
     */
    public static String getFullLetterWithSeparatorNoMark(String text) {
        if (text != null && !"".equals(text)) {
            initPinYin();
            return pinyin.translateWithSepNoMark(text, SEPARATOR);
        }
        return "";
    }

    /**
     * 获取拼音带分隔符
     *
     * @param text      文本
     * @param separator 分隔符
     * @return {@link String}
     * @author SLY
     * @date 2021/12/7
     */
    public static String getFullLetterWithSeparator(String text, String separator) {
        if (text != null && !"".equals(text)) {
            initPinYin();
            return pinyin.translateWithSep(text, separator);
        }
        return "";
    }

    /**
     * 获取拼音带分隔符（默认分隔符","）
     *
     * @param text 文本
     * @return {@link String}
     * @author SLY
     * @date 2021/12/7
     */
    public static String getFullLetterWithSeparator(String text) {
        if (text != null && !"".equals(text)) {
            initPinYin();
            return pinyin.translateWithSep(text, SEPARATOR);
        }
        return "";
    }

    public static void main(String[] args) {
        System.out.println(PinYinUtil.getFullLetterWithSeparatorNoMark("我是长春长城市长", ",") + "end");
    }


    private PinYinUtil() {

    }

    /**
     * 初始化拼音对象
     */
    private static void initPinYin() {
        if (pinyin == null) {
            synchronized (PinYinUtil.class) {
                if (pinyin == null) {
                    try {
                        pinyin = new Pinyin();
                    } catch (Exception e) {
                        throw new RuntimeException("初始化拼音工具对象异常", e);
                    }
                }
            }
        }
    }

}
