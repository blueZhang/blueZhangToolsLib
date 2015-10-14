package com.bluezhang.bitmapdisplay_1012_class.utils;

/**
 * Author: blueZhang
 * DATE:2015/10/12
 * Time: 17:25
 * AppName:BitmapDisplay_1012_Class
 * PckageName:com.bluezhang.bitmapdisplay_1012_class.tasks
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密解密工具类
 */
public final class EncryptUtil  {
    private EncryptUtil(){

    }


    /**
     * 将字节数组转换成16进制的字符串
     * @param data
     * @return
     */
    public static String toHex(byte[] data){
        String ret = null;

        if (data != null&&data.length>0) {
            StringBuilder sb = new StringBuilder();
            for (byte b:data){

                int v=b &0x0FF;
                String hexString = Integer.toHexString(v);
                if(v > 0x0F){
                    sb.append(hexString);
                }else{
                    sb.append('0').append(hexString);
                }
            }
            ret = sb.toString();
        }

        return ret;
    }

    /**
     * 将网址映射成文件名称
     * @param stringContent
     * @return
     */
    public static String md5(String stringContent){
        //TODO 使用MD5映射成文件名称
        String ret = null;

        if (stringContent != null) {
            try {
                //创建消息摘要 使用MD5算法
                MessageDigest digest = MessageDigest.getInstance("MD5");
                //或者是使用 MessageDigest.getInstance("SHA1");

                //计算URL对用的MD5数据，生成的数据是字节数组，内部包含了不可显示的字节，需要进行编码转换成字符串
                //不要使用new String(byte[])!!! 需要转换成16进制
                byte[] data = digest.digest(stringContent.getBytes());
                //byte[]每一个字节转换成16进制并且拼接成一个字符串

                ret = toHex(data);

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }


        return ret;
    }
}
