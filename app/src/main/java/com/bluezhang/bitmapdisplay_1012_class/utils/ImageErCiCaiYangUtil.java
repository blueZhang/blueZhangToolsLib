package com.bluezhang.bitmapdisplay_1012_class.utils;

/**
 * Author: blueZhang
 * DATE:2015/10/12
 * Time: 20:31
 * AppName:BitmapDisplay_1012_Class
 * PckageName:com.bluezhang.bitmapdisplay_1012_class.utils
 */

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import com.bluezhang.bitmapdisplay_1012_class.BuildConfig;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 对Image进行二次采样 缩小到用户需要的尺寸<br>
 *     如果没有Context或者是URL那么将会打印：<a href="">ImageErCiCaiYangUtil 的Log信息 </a>
 * </br>
 *
 */
public class ImageErCiCaiYangUtil {
    private ImageErCiCaiYangUtil() {

    }

    /**
     * <h4>返回图片进行二次采样之后内存卡中图片</h4>
     * <ul>
     *     <li>参数1：Image的URL地址</li>
     *     <li>参数：上下文对象</li>
     * </ul>
     * @param url 图片的url地址
     * @param context 上下文对象
     * @return 返回更改尺寸后图片的byte数组
     */
    public static byte[] changeImageSize(String url, Context context) {
        byte[] ret = null;
        // 1.准备目录

        //最终获取出来的文件的缓存的目录就是他
        if (url != null&& context!= null) {
            File cacheDir = null;

            String state = Environment.getExternalStorageState();

            if (Environment.MEDIA_MOUNTED.equals(state)) {

                //获取存储卡上面应用程序的缓存目录
                cacheDir = context.getExternalCacheDir();

            } else {

                //获取手机内部缓存
                cacheDir = context.getCacheDir();

            }


            //映射文件名
            String fileName = EncryptUtil.md5(url);

            File targetFile = new File(cacheDir, fileName);

            if (targetFile.exists()) {

                ByteArrayOutputStream bout = null;

                FileInputStream fin = null;

                try {

                    fin = new FileInputStream(targetFile);
                    ret = StreamUtil.readStream(fin);

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    StreamUtil.close(fin);
                    StreamUtil.close(bout);

                }
            }
        }else {
            if (BuildConfig.DEBUG) Log.d("ImageErCiCaiYangUtil", "URL IS NULL ,PLEASE INPUT URL OR CONTEXT IS NULL");
        }

        return ret;
    }


}
