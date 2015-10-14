package com.bluezhang.bitmapdisplay_1012_class.tasks;

/**
 * Author: blueZhang
 * DATE:2015/10/12
 * Time: 16:23
 * AppName:BitmapDisplay_1012_Class
 * PckageName:com.bluezhang.bitmapdisplay_1012_class.tasks
 */

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import java.lang.ref.WeakReference;

/**
 * 解决图片错位的问题的
 * 自身不需要设置图片
 */
public class AsyncDrawable  extends BitmapDrawable{
    private final WeakReference<ImageLoadTask> taskWeakReference;

    /**
     * 模拟一个Bitmapdriable这个Drawable对象就可以直接给ImageView设置
     * @param res
     * @param bitmap
     * @param task
     */
    public AsyncDrawable(Resources res, Bitmap bitmap,ImageLoadTask task) {
        super(res, bitmap);
        taskWeakReference = new WeakReference<ImageLoadTask>(task);

    }

    /**
     * 获取当前Drawable包含的异步任务
     * @return
     */
    public ImageLoadTask getImageLoadTask(){
        ImageLoadTask ret = null;

        ret = taskWeakReference.get();

        return ret;


    }


}
