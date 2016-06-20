package com.arsalan.garage.utils;

import android.net.Uri;

/**
 * <p/>
 * Created by: Noor  Alam on 10/02/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class ImageFileProvider extends android.support.v4.content.FileProvider {
    @Override
    public String getType(Uri uri) { return "image/jpeg"; }
}