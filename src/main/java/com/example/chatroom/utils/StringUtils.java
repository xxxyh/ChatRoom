package com.example.chatroom.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;

public abstract class StringUtils extends org.apache.commons.lang3.StringUtils {

    private static final Logger logger = LoggerFactory.getLogger(StringUtils.class);
    private static SecureRandom random = new SecureRandom();

    public static final String EMPTY_STR = "";
    public static final String ALPHA_NUM = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String STARS = "*****";
    private static final TextEncryptor textEncryptor =
        Encryptors.queryableText("bSAQ36DK8Ndbc6rsY4hsGmz5DQ6k5NmY", "5ffc865b106f69a8");

    private static String generateRandomStr(int length, int range) {
        Assert.state(length > 0 && range > 0 && range <= ALPHA_NUM.length());
        char[] str = new char[length];
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(range);
            str[i] = ALPHA_NUM.charAt(index);
        }
        return String.valueOf(str);
    }

    public static String generateRandomNum(int length) {
        return generateRandomStr(length, 10);
    }


    // 收藏夹图片随机数
    public static String getPictureRandom(int min,int max) {

        int randomNum = random.nextInt();
        if (randomNum < 0){
            randomNum = - randomNum;
        }
        // 范围缩小在1-12之间
        return String.valueOf(randomNum % (max - min + 1) + min);
    }

    public static String generateRandomStr(int length) {
        return generateRandomStr(length, ALPHA_NUM.length());
    }

    public static String generateItemOid(int length) {
        Assert.state(length > 0 && length < 10);
        return "q" + generateRandomStr(length - 1);
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String replace(String str, int index, char replace) {
        if (str == null || index < 0 || index >= str.length()) {
            return str;
        }
        char[] chars = str.toCharArray();
        chars[index] = replace;
        return String.valueOf(chars);
    }

    /**
     * 将逗号分隔的ids转化为List<Integer> eg. "1,2,3" -> [1,2,3]
     *
     * @param ids
     *            逗号分隔的ids，为null则返回null
     * @return List<Integer>
     */
    public static List<Integer> parseIds(String ids) {
        if (ids == null){
            return new ArrayList<>();
        }
        String[] strIdArray = ids.split(",");
        List<Integer> intIdList = new ArrayList<>(strIdArray.length);
        for (String strId : strIdArray) {
            Integer intId = Integer.valueOf(strId);
            intIdList.add(intId);
        }
        return intIdList;
    }

    /**
     * 转置List<Integer> parseIds(String ids)方法
     * 
     * @param ids
     * @return
     */
    public static String parseIds(List<Integer> ids) {
        if (ids == null) {
            return null;
        }
        StringJoiner sj = new StringJoiner(",");
        ids.forEach(id -> sj.add(id + ""));
        return sj.toString();
    }

    public static String getItemDataValue(String str) {
        if (isBlank(str) || "NULL".equalsIgnoreCase(str)) {
            str = null;
        } else {
            str = str.trim();
        }
        return str;
    }

    public static String encryptAES(String raw) {
        try {
            return textEncryptor.encrypt(raw);
        } catch (Exception e) {
            logger.error("encryptAES exception {}",e);
            return null;
        }
    }


    public static String decryptAES(String code) {
        try {
            return textEncryptor.decrypt(code);
        } catch (Exception e) {
            return null;
        }
    }
}
