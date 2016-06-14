package com.arsalan.garage.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.util.List;

/**
 * <p/>
 * Created by: Noor  Alam on 11/06/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class ShareUtil {


    public static void shareOnFacebook() {

    }

    public static void shareOnTwitter() {

    }

    public static void shareOnWhatsApp(Context context, String text ,String imageUrl) {
        shareImageWithText(context, text , imageUrl);
    }



    public static void shareImageWithText(Context context, final String shareContent, String shareUri) {

        class ShareTask extends AsyncTask<String, Void, File> {
            private final Context context;

            public ShareTask(Context context) {
                this.context = context;
            }

            @Override
            protected File doInBackground(String... params) {
                String url = params[0];
                try {
                    return Glide
                            .with(context)
                            .load(url)
                            .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            .get() // needs to be called on background thread
                            ;
                } catch (Exception ex) {
                    Log.w("SHARE", "Sharing " + url + " failed", ex);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(File result) {
                if (result == null) {
                    ShareUtil.shareContent(context,shareContent);
                    return;
                }
                Uri uri = FileProvider.getUriForFile(context, context.getPackageName(), result);
                share(uri);
            }

            private void share(Uri result) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo resolveInfo : resInfoList) {
                    String packageName = resolveInfo.activityInfo.packageName;
                    context.grantUriPermission(packageName, result, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_TEXT, shareContent);
                intent.putExtra(Intent.EXTRA_STREAM, result);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                context.startActivity(Intent.createChooser(intent, "Share Image"));
            }
        }
        new ShareTask(context).execute(shareUri);
    }



    /**
     * Sharing text
     *
     * @param context Context
     * @param text    String
     */
    public static void shareContent(Context context, String text) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
        context.startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }


}
