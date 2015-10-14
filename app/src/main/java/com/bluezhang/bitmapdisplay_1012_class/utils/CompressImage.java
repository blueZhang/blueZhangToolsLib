package com.bluezhang.bitmapdisplay_1012_class.utils;

/**
 * Author: blueZhang
 * DATE:2015/10/12
 * Time: 21:26
 * AppName:BitmapDisplay_1012_Class
 * PckageName:com.bluezhang.bitmapdisplay_1012_class.utils
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.util.Log;
import com.bluezhang.bitmapdisplay_1012_class.BuildConfig;
import com.bluezhang.bitmapdisplay_1012_class.cache.FileCache;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * <h3>对图片进行压缩</h3>
 * <ul>
 * <li>根据输入的宽度和高度对图片进行压缩</li>
 * <li>最后返回压缩之后的bitmap</li>
 * </ul>
 */
public class CompressImage {
    // 强引用缓存，一级缓存，特点：使用最近最少使用算法，将旧数据移除，为新数据提供空间
    private static LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(2 * 1024 * 1024) {// 缓存的内存空间为2M
        protected int sizeOf(String key, Bitmap value) {
            // 计算存放成员的大小，返回字节大小

            return value.getRowBytes() * value.getHeight();// 图片大小

        };

        @Override
        protected void entryRemoved(boolean evicted, String key,Bitmap oldValue, Bitmap newValue) {
            // 移除旧成员
            if (evicted)
            {
                // 将移除的成员存放到二级缓存中
                softCache.put(key, new SoftReference<Bitmap>(oldValue));// 将移除的成员存放到二级缓存中
            }

            super.entryRemoved(evicted, key, oldValue, newValue);
        }

    };
    private static Map<String, SoftReference<Bitmap>> softCache = new HashMap<String, SoftReference<Bitmap>>();;
    private static byte[] data;

    private CompressImage() {
    }





    /**
     * 官方计算代码
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        //当请求的宽度高度大域名的时候进行缩放
        if (reqWidth > 0 && reqHeight > 0) {
            if (height > reqHeight || width > reqWidth) {

                final int halfHeight = height / 2;
                final int halfWidth = width / 2;

                // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                // height and width larger than the requested height and width.
                while ((halfHeight / inSampleSize) > reqHeight
                        && (halfWidth / inSampleSize) > reqWidth) {
                    inSampleSize *= 2;
                }
            }
        }

        return inSampleSize;
    }


    public static Bitmap compressImg(String url, int requestWidth, int requestHeight) {
        Bitmap ret = null;
        //获取URL中对应的缓存

        // 从缓存中读取图片的方法
        // 先从一级缓存中获取

        ret = lruCache.get(url);
        if (ret == null) {
            if (BuildConfig.DEBUG) Log.d("CompressImage", "ddddddddddddddddd");
            // 从二级缓存中读取
            SoftReference<Bitmap> reference = softCache
                    .get(url);
            if (reference != null) {// 二级缓存中如果存在

                ret = reference.get();
                if (ret != null) {
                    // 将图片对象存放到一级缓存中
                    lruCache.put(url, ret);
                    // 从二级缓存中移除
                    softCache.remove(reference);
                } }else {// 从三级缓存中读取--扩展卡
                    data = FileCache.getInstance().load(url);

                    if (data != null) {
                        //TODO 有文件不需要联网

                    } else {
                        //TODO 没有文件需要下载
                        data = HttpTools.doGet(url);
                    }

                    if (data != null) {
                        //按照原始的图片的尺寸 进行Bitmap的生成
                        //bitmap生成的时候是按照图片的原始的宽高进行生成，并且每一个像素占用四个字节也就是ARGB
                        //ret = BitmapFactory.decodeByteArray(data,0,data.length);

                        //采用二次采样也就是缩小图片尺寸的方法


                        //步骤1：获取原始图片的宽、高的信息只用于采样的计算

                        //1.1: 创建Options给BitMapFactiry内部解码器传递参数
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        //1.2设置inJuetDecodeBounds 来控制解码器。只进行图片高度和宽度的获取不会加载Bitmap
                        //不占用内存，当时用这个参数，代表BitmapFactory.decodeXxx类似的方法，不会返回Bitmap

                        options.inJustDecodeBounds = true;
                        //解码的时候使用Options参数设置解码的方式
                        BitmapFactory.decodeByteArray(data, 0, data.length, options);

                        //------------------------------------------------

                        //2：根据图片的真实的尺寸，和当前需要显示的尺寸，进行计算，生成图片的采样率。
                        //2.2 准备显示在手机上的尺寸
                        //尺寸是根据程序需要设置的。

                        int size = calculateInSampleSize(options, requestWidth, requestHeight);
                        //2.3 计算和设置采样率
                        options.inSampleSize = size;//宽度的1/32 高度的1/32

                        //2.4开放 解码，实际生成Bitmap
                        options.inJustDecodeBounds = false;

                        //2.4.1
                        //要求对每一个采样的像素使用RGB_565存储方式
                        //一个像素占用两个字节比 ARGB_8888小了一半
                        //如果解码器不能使用指定的配置那么自动使用 ARGB_8888
                        options.inPreferredConfig = Bitmap.Config.RGB_565;


                        //2.5使用设置的采样参数进行解码
                        ret = BitmapFactory.decodeByteArray(data, 0, data.length, options);
                        // 将图片存放在缓存中的方法
                        // 将图片存放在一级缓存中
                        lruCache.put(url, ret);
                        FileCache.getInstance().save(url, data);

                        //data需要显式的释放
                        data = null;

                    }

                }


        }




        return ret;
    }


}
