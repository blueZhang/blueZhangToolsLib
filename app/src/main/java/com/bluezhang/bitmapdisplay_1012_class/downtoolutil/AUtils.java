package com.bluezhang.bitmapdisplay_1012_class.downtoolutil;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.os.Handler;

/**
 * 网络工具
 * @author blueZhang
 *
 */
public class AUtils {
	
	public interface Callback{
		public void response(String url, byte[] bytes);
	}
	
	private static Handler mHandler=new Handler();
	private static ExecutorService executor=Executors.newFixedThreadPool(3);
	
	/**
	 * 下载网络资源的方法?
	 * @param url
	 * @param callback
	 * @throws Exception
	 */
	public static void get(final String url,
			final Callback callback) {
		
		executor.execute(new Runnable() {
			public void run() {
				// TODO 实现网络下载资源
				try{
					HttpURLConnection conn=(HttpURLConnection) new URL(url).openConnection();
					InputStream is=conn.getInputStream();
					byte[] buffer=new byte[20*1024];
					int len=-1;
					
					ByteArrayOutputStream baos=new ByteArrayOutputStream();
					
					if(conn.getResponseCode()==200){ //判断是否连接成功
						
						while((len=is.read(buffer))!=-1){
							baos.write(buffer, 0, len);
						}
						
						final byte[] bytes=baos.toByteArray(); //将下载完的流转成字节数组
						mHandler.post(new Runnable() {
							public void run() {
								//通过接口回调将数据回传给主线
								callback.response(url, bytes);
							}
						});
						
					}
					
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
	}
	
	
}
