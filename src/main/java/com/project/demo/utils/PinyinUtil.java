package com.project.demo.utils;

import com.project.demo.error.BusinessException;
import com.project.demo.error.EmBusinessErr;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Ck
 * #date 2019/11/02 14:00
 */
public class PinyinUtil {
    /**
     * @author Ck
     * 传入中文获取首字母大写+全拼
     */
    public static String getPinYinUnionHead(String str) throws BusinessException {
        try {
            return getPinYinHeadChar(str).concat(" ").concat(getPinYin(str));
        } catch (Exception e) {
            throw new BusinessException(EmBusinessErr.UNKNOWN_ERROR, "拼音转换出错");
        }
    }

    /**
     * @author Ck
     * 传入中文获取首字母大写
     * 陈行 -> cxh  ( 行 -> xing hang)
     */
    public static String getPinYinHeadChar(String str) throws BusinessException {
        try {
            StringBuilder convert = new StringBuilder();
            for (int j = 0; j < str.length(); j++) {
                char word = str.charAt(j);
                //获取每个字的所有拼音 行 - > {"xing","hang"}
                String[] pinyinArray = PinyinUtil.getPinYinWithoutTone(word);
                if (pinyinArray != null) {
                    StringBuilder existedWord = new StringBuilder();
                    //获取每个读音的各个首字母
                    for (String item : pinyinArray) {
                        char nowWord = item.charAt(0);
                        //过滤同一个字的多个读音的相同首字母 "的" -> DI,DE -> D
                        if (existedWord.indexOf(String.valueOf(nowWord)) == -1) {
                            existedWord.append(nowWord);
                            convert.append(nowWord);
                        }
                    }
                } else {
                    convert.append(word);
                }
            }
            return convert.toString().toUpperCase();
        } catch (Exception e) {
            throw new BusinessException(EmBusinessErr.UNKNOWN_ERROR, "拼音转换出错");
        }
    }

    /**
     * @author Ck
     * 获取中文全拼
     * 陈 -> chen
     * 行 -> xing,hang
     * 陈行 -> chen_xing,hang
     */
    public static String getPinYin(String str) throws BusinessException {
        try {
            StringBuilder convert = new StringBuilder();
            for (int j = 0; j < str.length(); j++) {
                char word = str.charAt(j);
                String[] pinyinArray = PinyinUtil.getPinYinWithoutTone(word);
                if (pinyinArray != null) {
                    for (int k = 0; k < pinyinArray.length; k++) {
                        convert.append(pinyinArray[k]);
                        if (k + 1 < pinyinArray.length) {
                            convert.append('_');
                        }
                    }
                    if (j + 1 < str.length()) {
                        convert.append(',');
                    }
                } else {
                    convert.append(word);
                }
            }
            return convert.toString().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e, EmBusinessErr.UNKNOWN_ERROR, "拼音转换出错");
        }
    }


    /**
     * 获取中文字的拼音，返回结果为数组（多音字）
     * 如：空 -> kong
     */
    private static String[] getPinYinWithoutTone(char word) throws BadHanyuPinyinOutputFormatCombination {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        //不设置声调
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        //用v表示女n ü 中的 ü
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        String[] arr = PinyinHelper.toHanyuPinyinStringArray(word, format);

        if (arr != null) {
            return StringArrUnique(arr);
        } else {
            return null;
        }
    }

    /**
     * @author Ck
     * 字符串数组去重
     */
    private static String[] StringArrUnique(String[] ss) {
        Set<String> set = new HashSet<>(Arrays.asList(ss));
        return set.toArray(new String[set.size()]);
    }

    public static void main(String[] args) throws BusinessException {
        String[] testStrs = {
                "小超人",
                "就的",
                "陈一",
                "陈依二",
                "陈已",
                "二三四五六, 我",
                "陈星行",
                "陈行",
                "my name is mx, chinese name is 毛线",
                "123",
                "陈2",
                "丁凌"
        };
        for (String item : testStrs) {
            System.out.println(String.format("%s:\t拼音:%s\t首字母:%s\t首+全:%s\t",
                    item,
                    PinyinUtil.getPinYin(item),
                    PinyinUtil.getPinYinHeadChar(item),
                    PinyinUtil.getPinYinUnionHead(item)));
        }
    }
}
