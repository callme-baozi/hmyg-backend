package com.baozi.hmygbackend.utils;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class HuToolHttp {
    public static void main(String[] args) throws IOException {

        long startTime = System.currentTimeMillis();
        getGoodsDetail();
        System.out.println("setLengthTest()耗时:" + (System.currentTimeMillis() - startTime) / 1000 + "s");

    }

    public static void getGoodsDetail() throws IOException {
        int i = 44518;
//        String t_url = "https://www.uinav.com/api/public/v1/goods/detail?goods_id=";  // 旧地址
        String t_url = "https://api-hmugo-web.itheima.net/api/public/v1/goods/detail?goods_id=";

        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("C:\\Users\\11493\\Desktop\\生活优购\\goodsDetail.txt",true));
        BufferedWriter bw = new BufferedWriter(osw);

//        OutputStreamWriter osw2 = new OutputStreamWriter(new FileOutputStream("C:\\Users\\11493\\Desktop\\生活优购\\attrs.txt", true));
//        BufferedWriter bw2 = new BufferedWriter(osw2);
//
//        OutputStreamWriter osw3 = new OutputStreamWriter(new FileOutputStream("C:\\Users\\11493\\Desktop\\生活优购\\pics.txt", true));
//        BufferedWriter bw3 = new BufferedWriter(osw3);

        while (i < 57446) {
            String url = t_url + i; // 拼接网址

            String result1 = HttpUtil.get(url); // hutool工具，发送get请求
            JSONObject jsonObject = JSON.parseObject(result1); // 将字符串转为json
            String message = jsonObject.getString("message"); // 获取message字段的内容
            if (message == null) { // 没有数据就跳过
                i++;
                continue;
            }
//        System.out.println(JSON.parseObject(message));

            JSONObject detail = JSON.parseObject(message); // 将message的内容转为JSON
            String content = JSON.toJSONString(detail, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                    SerializerFeature.WriteDateUseDateFormat); // 将JSON格式转为字符串
//        System.out.println(content);

            bw.write(content + "\n"); // 写入文件并换行

//            JSONArray attrs = detail.getJSONArray("attrs"); // 获取列表字段的内容 写入文件
//            for (Object roleId : attrs) {
//                JSONObject attr = JSON.parseObject(roleId.toString());
//                String s = JSON.toJSONString(attr, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
//                        SerializerFeature.WriteDateUseDateFormat);
//                bw2.write(s + "\n");
//            }
//
//            JSONArray pics = detail.getJSONArray("pics");
//            for (Object roleId : pics) {
//                JSONObject pic = JSON.parseObject(roleId.toString());
//                String s = JSON.toJSONString(pic, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
//                        SerializerFeature.WriteDateUseDateFormat);
//                bw3.write(s + "\n");
//            }

            System.out.println("第" + i + "条写入成功");
            i++;
        }
        bw.close();
//        bw2.close();
//        bw3.close();
    }

}
