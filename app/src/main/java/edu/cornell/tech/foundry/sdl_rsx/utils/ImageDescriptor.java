package edu.cornell.tech.foundry.sdl_rsx.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Serializable;

import org.researchstack.backbone.utils.ResUtils;

/**
 * Created by jk on 5/31/16.
 */

public class ImageDescriptor implements Serializable {
    public String resourceName;
    public String resourceType;
    public String packageName;

    public Bitmap getBitmap(Context context) {


        try {
            int imageResId = ResUtils.getDrawableResourceId(context, this.resourceName);
            if (imageResId != 0) {
                return BitmapFactory.decodeResource(context.getResources(), imageResId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;


    }
}
