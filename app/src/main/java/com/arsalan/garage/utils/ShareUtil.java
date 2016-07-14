package com.arsalan.garage.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

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

    private static final String WHATSAPP = "WhatsApp";
    private static final String FACEBOOK = "Facebook";
    private static final String TWITTER = "Twitter";
    private static final String GMAIL = "Gmail";

    public static void shareOnFacebook(Context context, String text ,String imageUrl) {
        shareImageWithText(context, text , imageUrl, FACEBOOK);
    }

    public static void shareOnTwitter(Context context, String text ,String imageUrl) {
        shareImageWithText(context, text , imageUrl, TWITTER);
    }

    public static void shareOnWhatsApp(Context context, String text ,String imageUrl) {
        shareImageWithText(context, text , imageUrl, WHATSAPP);
    }

    public static void shareOnGmail(Context context, String text ,String imageUrl){
        shareImageWithText(context, text , imageUrl, GMAIL);
    }



    public static void shareImageWithText(Context context, final String shareContent, String shareUri, final String shareMedia) {

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
                if(shareMedia.equals(WHATSAPP)){
                    intent.setPackage("com.whatsapp");
                }else if(shareMedia.equals(FACEBOOK)){
                    intent.setPackage("com.facebook.katana");
                }else if(shareMedia.equals(TWITTER)){
                    intent.setPackage("com.twitter.android");
                }else if(shareMedia.equals(GMAIL)){
                    setGmailClassNameToIntent(context, intent);
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Garage: Check It Out!!!");
                }
                try {
                    context.startActivity(intent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(context, shareMedia + " is not installed on your device. Please install it and try again.", Toast.LENGTH_SHORT).show();
                }

            }
        }
        new ShareTask(context).execute(shareUri);
    }

    private static void setGmailClassNameToIntent(Context context, Intent intent){
        final PackageManager pm = context.getPackageManager();
        final List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
        ResolveInfo best = null;
        for(final ResolveInfo info : matches){
            if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail")){
                best = info;
            }
        }
        if (best != null) {
            intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
        }
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
