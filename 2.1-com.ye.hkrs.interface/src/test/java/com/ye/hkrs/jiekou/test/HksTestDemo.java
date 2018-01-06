package com.ye.hkrs.jiekou.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.ye.hkrs.jiekou.demo.HKSysResquestData;
import com.ye.hkrs.jiekou.util.FastJsonUtil;
import com.ye.hkrs.jiekou.util.FileUtil;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by zjx on 2017/12/26.
 */
public class HksTestDemo {
    //解码
    final Base64.Decoder decoder = Base64.getDecoder();
    //编码
    final Base64.Encoder encoder = Base64.getEncoder();
    @Test
    public void encodeData() throws IOException {
        String requestData = FileUtil.read("/toubaoInfoJson.json", "utf-8");
//        HkrsResponseDemo hkrsResponseDemo =  FastJsonUtil.deserialize(requestData, HkrsResponseDemo.class);
//        System.out.println(hkrsResponseDemo);

        HKSysResquestData hkrsResponseDemo =  FastJsonUtil.deserialize(requestData, HKSysResquestData.class);
        List<HKSysResquestData> hkrsResponseDemoList=new ArrayList<>();
        hkrsResponseDemoList.add(hkrsResponseDemo);
        String ss=FastJsonUtil.serialize(hkrsResponseDemoList);
        //JSONArray dataArray = new JSONArray();
        //String newData = requestData.replace("\n","").replace("\r","");
        //Object obj = newData;
        //dataArray.add(obj);
        //String ss = JSON.toJSONString(dataArray).replace("\\","");
       String data = encoder.encodeToString(ss.getBytes("utf-8"));
        System.out.println(data);
        byte[] reData = decoder.decode(data);
        System.out.println(new String(reData));
    }
    @Test
    public void decoderData() throws IOException {
        String requestData = FileUtil.read("/toubaoSignJson.json", "utf-8");
//        System.out.println(requestData);
        String newData = requestData.replace("\n","").replace("\r","");
        byte[] reData = decoder.decode(newData.getBytes("utf-8"));
        String policyJson = new String(reData);
        System.out.println(policyJson);
        List<Map<String,Object>> list =  FastJsonUtil.deserializeAny(policyJson, new TypeReference<List<Map<String, Object>>>(){});
        List<Map> listH = FastJsonUtil.deserializeList(policyJson, Map.class);
        System.out.println(list.get(0).get("prod_name"));
    }

    @Test
    public void signEncoder() throws IOException {
//        String key = "3c0f2072f1f67c56d0ee78f3621ef69e";
        String key = "b9d8b9eb519dc11ff0e2a975d66b33b8";
        Long time = 1514967762523L;
        String resiveSign="YjlkOGI5ZWIxNTE0OTY3NzYyNTIz";
        String requestData = FileUtil.read("/toubaoSignJson.json", "utf-8");
//        System.out.println(requestData);
        String newData = requestData.replace("\n","").replace("\r","");
        byte[] reData = decoder.decode(newData.getBytes("utf-8"));
        String strData = new String(reData);
        System.out.println("解码后的数据："+strData);
        System.out.println("接受的sign："+resiveSign);
        Map<String,Object> mapObject = new HashMap<>();
//        JSONArray ja = JSON.parseArray(strData);
//        String getSign = getSign(ja, time, key);
        List<Map<String,Object>> list =  FastJsonUtil.deserializeAny(strData, new TypeReference<List<Map<String, Object>>>(){});
        String getSign = getSignArray(list, time, key);
        System.out.println("生成的sign:"+getSign);
        System.out.println("验证是否通过:"+resiveSign.equals(getSign));
        System.out.println(list.get(0));

    }

    private String getSign(JSONArray list,long time,String key) throws UnsupportedEncodingException {
        if(list == null || list.size() <= 0){
            return null;
        }
        int startIndex = Integer.valueOf(time % list.size()+"");
        String str = key.substring(startIndex,startIndex+8) +time;
        String sign = encoder.encodeToString(str.getBytes("utf-8"));
        return sign;
    }
    private String getSignArray(List<Map<String,Object>> list,long time,String key) throws UnsupportedEncodingException {
        if(list == null || list.size() <= 0){
            return null;
        }
        int startIndex = Integer.valueOf(time % list.size()+"");
        String str = key.substring(startIndex,startIndex+8) +time;
        String sign = encoder.encodeToString(str.getBytes("utf-8"));
        return sign;
    }


    @Test
    public void myUtilTest(){
        List<String> a = new ArrayList<>();
        a.add("1");
        a.add("2");
        //移除集合元素的正例
        Iterator<String> it = a.iterator();
        while (it.hasNext()) {
            String temp = it.next();
            if ("2".equals(temp)) {
                it.remove();
            }
        }
        //移除集合元素的反例
//        for (String temp : a) {
//            if ("2".equals(temp)) {
//                a.remove(temp);
//            }
//        }
        System.out.println(a);
    }
    @Test
    public void myYichangTest(){
        try {

            List<Integer> list = new ArrayList<>();
            list.add(1);
            list.add(2);
            list.add(3);
            list.add(4);
            int count=0;
            for(int i=0;i<list.size();i++){
                if(list.get(i) == 2){
                    continue;
                }
                System.out.println(list.get(i));
                count++;
            }
            System.out.println(count);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("异常了");
        }
    }
}
