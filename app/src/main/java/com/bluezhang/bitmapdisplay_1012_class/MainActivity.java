package com.bluezhang.bitmapdisplay_1012_class;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;
import com.bluezhang.bitmapdisplay_1012_class.cache.FileCache;
import com.bluezhang.bitmapdisplay_1012_class.tasks.AsyncDrawable;
import com.bluezhang.bitmapdisplay_1012_class.tasks.ImageLoadTask;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private ImageLoadTask task;

    @Override
    protected void onStart() {
        super.onStart();
        FileCache.newInstance(getApplicationContext());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String url = "http://pic.miercn.com/uploads/allimg/151012/85-151012135101.jpg";
        imageView = (ImageView) findViewById(R.id.imageView);
        loadImageFromUrl(url);
    }


    public void loadImageFromUrl(String url){
        task = new ImageLoadTask(imageView,256,128);

        /**
         * 参考：@see:file:///E:/SDK/adt-bundle-windows-x86_64-20140321/sdk/docs/training/displaying-bitmaps/process-bitmap.html
         *
         */
        AsyncDrawable drawable = new AsyncDrawable(
                getResources(),
                null,
                task
        );
        imageView.setImageDrawable(drawable);
        task.execute(url);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
