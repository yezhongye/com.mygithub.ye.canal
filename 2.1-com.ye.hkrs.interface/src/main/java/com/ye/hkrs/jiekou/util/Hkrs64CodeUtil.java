package com.ye.hkrs.jiekou.util;

import com.alibaba.fastjson.TypeReference;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

/**
 * Created by zjx on 2017/12/27.
 */
public class Hkrs64CodeUtil {
    /**
     * 编码
     * @param content
     * @param encryptKey
     * @param encodeName
     * @return
     */
    public static String hkBase64Encrypt(String content, String encodeName){
        try {
            return isEmpty(content) ? null : base64Encode(content.getBytes(encodeName));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 根据所得数据计算签名
     * @param jsonData
     * @param time
     * @param key
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String hkGetSign(String jsonData,long time,String key) throws UnsupportedEncodingException {
        List<Map<String,Object>> list =  FastJsonUtil.deserializeAny(jsonData, new TypeReference<List<Map<String, Object>>>(){});
        String sign =getSignArray(list,time,key);
        return sign;
    }
    /**
     * 解码
     * @param content
     * @param encodeName
     * @return
     */
    public static String hkBase64Decode(String content, String encodeName){
        try {
            return isEmpty(content) ? null : new String(base64Decode(content),encodeName);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private static String base64Encode(byte[] bytes) {
        return new BASE64Encoder().encode(bytes);
    }
    /**
     * base 64 decode
     * @param base64Code 待解码的base 64 code
     * @return 解码后的byte[]
     * @throws Exception
     */
    private static byte[] base64Decode(String base64Code) throws Exception{
        return isEmpty(base64Code) ? null : new BASE64Decoder().decodeBuffer(base64Code);
    }
    private static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * 弘康签名规则
     * @param list
     * @param time
     * @param key
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String getSignArray(List<Map<String,Object>> list,long time,String key) throws UnsupportedEncodingException {
        //编码
        final Base64.Encoder encoder = Base64.getEncoder();

        if(list == null || list.size() <= 0){
            return null;
        }
        int startIndex = Integer.valueOf(time % list.size()+"");
        String str = key.substring(startIndex,startIndex+8) +time;
        String sign = encoder.encodeToString(str.getBytes("utf-8"));
        return sign;
    }
}
