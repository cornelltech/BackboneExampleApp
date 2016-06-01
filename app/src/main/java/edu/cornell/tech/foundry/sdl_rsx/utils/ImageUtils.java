package edu.cornell.tech.foundry.sdl_rsx.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

/**
 * Created by jk on 5/31/16.
 */

//public class ImageUtils {
//
//    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
//    {
//        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
//        image.compress(compressFormat, quality, byteArrayOS);
//        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
//    }
//
//    public static Bitmap decodeBase64(String input)
//    {
//        byte[] decodedBytes = Base64.decode(input, 0);
//        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
//    }
//
//    public static int getDrawableResourceId(Context context, String name)
//    {
//        return getDrawableResourceId(context, name, 0);
//    }
//
//    public static int getDrawableResourceId(Resources resources, String name, int defaultResId)
//    {
//        if (name == null || name.length() == 0 )
//        {
//            return defaultResId;
//        }
//        else
//        {
//            int resId = resources.getIdentifier(name, "drawable",);
//            return resId != 0 ? resId : defaultResId;
//        }
//    }
//}
