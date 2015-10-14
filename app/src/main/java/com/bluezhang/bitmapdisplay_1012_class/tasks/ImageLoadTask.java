package com.bluezhang.bitmapdisplay_1012_class.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;
import com.bluezhang.bitmapdisplay_1012_class.cache.FileCache;
import com.bluezhang.bitmapdisplay_1012_class.utils.CompressImage;
import com.bluezhang.bitmapdisplay_1012_class.utils.HttpTools;

import java.lang.ref.WeakReference;

/**
 * Author: blueZhang
 * DATE:2015/10/12
 * Time: 11:32
 * AppName:BitmapDisplay_1012_Class
 * PckageName:com.bluezhang.bitmapdisplay_1012_class.tasks
 *
 */

/**
 * <h2><a href = "">用于加载图片的异步加载类</a></h2>
 */
public class ImageLoadTask extends AsyncTask<String,Integer,Bitmap> {

    /**
     * 使用弱引用来进行ImageView对象的引用当UI销毁这个任务不再使用ImageView了
     */
    private final WeakReference<ImageView> imageViewWeakReference ;
    /**
     * 加载图片使用的宽度
     */
    private int requestWidth;
    /**
     * 加载图片使用的高度
     */
    private int requestHeight;

    /**
     * 异步任务的构造
     * @param imageView ImageView 需要显示的ImageView
     * @param requestWidth 请求的宽度 0 代表显示原始图像 ，>0将图像缩小
     * @param requestHeight 请求的宽度 0 代表显示原始图像 ，>0将图像缩小
     */
    public ImageLoadTask(ImageView imageView,int requestWidth,int requestHeight){

        this.requestHeight = requestHeight;
        this.requestWidth = requestWidth;
        imageViewWeakReference = new WeakReference<ImageView>(imageView);

    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap ret = null;

        if(params!=null&&params.length>0){

          ret = CompressImage.compressImg(params[0],requestWidth,requestHeight);


        }

        return ret;
    }


    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null) {
            //获取若引用包含的对象可能是空
            ImageView imageView = imageViewWeakReference.get();

            if (imageView != null) {
                //每一个图片都可包含AsyncDriable对象
                //这个对象用于处理图片错位
                Drawable drawable = imageView.getDrawable();

                if (drawable != null&&drawable instanceof  AsyncDrawable) {
                    //用于检测图片错位的情况
                    AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;

                    ImageLoadTask task = asyncDrawable.getImageLoadTask();
                    //当前的ImageView内部包含的AsyncDrawable 和当前的任务是一致的代表当前的任务是可以设置图片的
                    if (this == task ) {
                        imageView.setImageBitmap(bitmap);

                    }

                }else {//不用检测图片错位的情况

                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }
}
