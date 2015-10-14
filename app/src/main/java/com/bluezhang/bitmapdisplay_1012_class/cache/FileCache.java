package com.bluezhang.bitmapdisplay_1012_class.cache;

import android.content.Context;
import android.os.Environment;
import com.bluezhang.bitmapdisplay_1012_class.utils.EncryptUtil;
import com.bluezhang.bitmapdisplay_1012_class.utils.StreamUtil;

import java.io.*;

/**
 * Author: blueZhang
 * DATE:2015/10/12
 * Time: 10:11
 * AppName:BitmapDisplay_1012_Class
 * PckageName:com.bluezhang.bitmapdisplay_1012_class.cache
 */
public class FileCache {

    private static FileCache ourInstance;


    public static FileCache newInstance(Context context) {
        if (context != null) {
            if (ourInstance == null) {
                ourInstance = new FileCache(context);
            }
        } else {
            throw new IllegalArgumentException("Context must be set");
        }
        return ourInstance;
    }


    public static FileCache getInstance() {

        if (ourInstance == null) {
            throw new IllegalStateException("newInstance invoke");
        }
        return ourInstance;
    }



    private Context context;

    private FileCache(Context context) {
        this.context = context;
    }


    /**
     * 从相应的网址加载相应的内容
     * @param url
     * @return
     */
    public byte[] load(String url){
        //TODO 通过网址找到文件

        byte [] ret = null;

        if (url != null) {
            // 1.准备目录



                //最终获取出来的文件的缓存的目录就是他
                File cacheDir = null;

                String state = Environment.getExternalStorageState();

                if(Environment.MEDIA_MOUNTED.equals(state)){

                    //获取存储卡上面应用程序的缓存目录
                    cacheDir = context.getExternalCacheDir();

                }else{

                    //获取手机内部缓存
                    cacheDir = context.getCacheDir();

                }


                //映射文件名
                String fileName = EncryptUtil.md5(url);

                File targetFile = new File(cacheDir,fileName);

                if (targetFile.exists()){

                    ByteArrayOutputStream bout = null;

                    FileInputStream fin = null;

                    try {

                        fin = new FileInputStream(targetFile);

                        ret = StreamUtil.readStream(fin);


                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        StreamUtil.close(bout);
                       StreamUtil.close(fin);

                    }
                }
        }


        return ret;
    }


    /**
     * 保存对应的网址的数据到文件中
     * @param url
     * @param data
     */
    public void save(String url,byte[] data){
        //TODO 通过网址存文件

        // 1.准备目录
        if (url != null&&data!=null) {


            //最终获取出来的文件的缓存的目录就是他
            File cacheDir = null;

            String state = Environment.getExternalStorageState();

            if(Environment.MEDIA_MOUNTED.equals(state)){

                //获取存储卡上面应用程序的缓存目录
                cacheDir = context.getExternalCacheDir();

            }else{

                //获取手机内部缓存
                cacheDir = context.getCacheDir();

            }


            //映射文件名
            String fileName = EncryptUtil.md5(url);

            File targetFile = new File(cacheDir,fileName);

            FileOutputStream fout = null;

            //IO操作
            try {

                fout = new FileOutputStream(targetFile);

                fout.write(data);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {

                StreamUtil.close(fout);

            }

        }


    }


}
