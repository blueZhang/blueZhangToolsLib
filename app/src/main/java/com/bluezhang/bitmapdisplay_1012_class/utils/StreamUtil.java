package com.bluezhang.bitmapdisplay_1012_class.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.Objects;

/**
 * Author: blueZhang
 * DATE:2015/10/12
 * Time: 16:51
 * AppName:BitmapDisplay_1012_Class
 * PckageName:com.bluezhang.bitmapdisplay_1012_class.utils
 */
public final class StreamUtil {
    private StreamUtil(){

    }



    public static  void close(Object stream){
        if (stream != null) {
            try{
                if (stream instanceof InputStream){
                    ((InputStream)stream).close();
                }
                else if (stream instanceof OutputStream){
                    ((OutputStream)stream).close();
                }
                else if(stream instanceof Reader){
                    ((Reader)stream).close();
                }
                else if(stream instanceof Writer){
                    ((Writer)stream).close();
                }
                else if (stream instanceof FileInputStream){
                    ((FileInputStream)stream).close();
                }
                else if (stream instanceof FileOutputStream){
                    ((FileOutputStream)stream).close();
                }else if(stream instanceof HttpURLConnection){
                    ((HttpURLConnection)stream).disconnect();
                }

            }catch (Exception e){

            }
        }

    }
    public static byte[] readStream(InputStream in) throws IOException {
        byte[] ret = null;
        if (in != null) {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte[] buf = new byte[128];
            int len;
            while (true) {
                len = in.read(buf);
                if (len == -1) {
                    break;
                }
                bout.write(buf, 0, len);
            }
            buf = null;
            ret = bout.toByteArray();
        }
        return ret;
    }

}
