package com.arsalan.garage.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
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

    public static final String WHATSAPP = "WhatsApp";
    private static final String FACEBOOK = "Facebook";
    private static final String TWITTER = "Twitter";
    private static final String GMAIL = "Gmail";

    public static void shareOnFacebook(Context context, String text, String imageUrl) {
        shareImageWithText(context, text, imageUrl, FACEBOOK);
    }

    public static void shareOnTwitter(Context context, String text, String imageUrl) {
        shareImageWithText(context, text, imageUrl, TWITTER);
    }

    public static void shareOnWhatsApp(Context context, String text, String imageUrl) {
        shareImageWithText(context, text, imageUrl, WHATSAPP);
    }

    public static void shareOnGmail(Context context, String text, String imageUrl) {
        shareImageWithText(context, text, imageUrl, GMAIL);
    }


    public static void shareImageWithText(final Context context, final String shareContent, String shareUri, final String targetApp) {
        new ShareTask(context, new ShareTask.OnImageUriFoundListener() {
            @Override
            public void onImageURIFound(Uri uri) {
                if (uri == null) {
                    ShareUtil.shareContentUsingAppChoserDialog(context, shareContent);
                } else {
                    Intent shareIntent = getShareIntent(uri, context, shareContent);
                    shareUsingTargetApp(context, shareIntent, targetApp);
                }
            }
        }).execute(shareUri);
    }

    public static void shareImageWithText(final Context context, final String shareContent, String shareUri) {
        new ShareTask(context, new ShareTask.OnImageUriFoundListener() {
            @Override
            public void onImageURIFound(Uri uri) {
                if (uri == null) {
                    ShareUtil.shareContentUsingAppChoserDialog(context, shareContent);
                } else {
                    Intent shareIntent = getShareIntent(uri, context, shareContent);
                    shareContentUsingAppChoserDialog(context, shareIntent);
                }
            }
        }).execute(shareUri);
    }

    public static class ShareTask extends AsyncTask<String, Void, File> {
        private final Context context;
        private OnImageUriFoundListener onImageUriFoundListener;

        public ShareTask(Context context, OnImageUriFoundListener onImageUriFoundListener) {
            this.context = context;
            this.onImageUriFoundListener = onImageUriFoundListener;
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
            Uri uri = null;
            if (result != null) {
                uri = FileProvider.getUriForFile(context, context.getPackageName(), result);
            }
            onImageUriFoundListener.onImageURIFound(uri);
        }

        public interface OnImageUriFoundListener {
            void onImageURIFound(Uri uri);
        }

    }

    public static void shareUsingTargetApp(Context context, Intent intent, String targetApp) {

        if (targetApp.equals(WHATSAPP)) {
            intent.setPackage("com.whatsapp");
        } else if (targetApp.equals(FACEBOOK)) {
            intent.setPackage("com.facebook.katana");
        } else if (targetApp.equals(TWITTER)) {
            intent.setPackage("com.twitter.android");
        } else if (targetApp.equals(GMAIL)) {
            setGmailClassNameToIntent(context, intent);
            intent.putExtra(Intent.EXTRA_SUBJECT, "Garage: Check It Out!!!");
        }
        try {
            context.startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, targetApp + " is not installed on your device. Please install it and try again.", Toast.LENGTH_SHORT).show();
        }
    }

    public static Intent getShareIntent(Uri uri, Context context, String shareContent) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            context.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_TEXT, shareContent);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        return intent;
    }

    public static Intent getWhatsAppTextShareIntent(String message) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        return intent;
    }

    public static void openGmail(Context context, String recipient) {
        openGmail(context, recipient, "", "");
    }

    public static void openGmail(Context context, String recipient, String subject) {
        openGmail(context, recipient, subject, "");
    }

    public static void openGmail(Context context, String recipient, String subject, String message) {
        Intent gmailIntent = new Intent(Intent.ACTION_SENDTO);
        gmailIntent.setType("text/plain");
        setGmailClassNameToIntent(context, gmailIntent);
        gmailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipient});
        gmailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        gmailIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
        gmailIntent.setData(Uri.parse("mailto:")); // only email apps should handle this
        try {
            context.startActivity(gmailIntent);
        } catch (ActivityNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private static void setGmailClassNameToIntent(Context context, Intent intent) {
        final PackageManager pm = context.getPackageManager();
        final List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
        ResolveInfo best = null;
        for (final ResolveInfo info : matches) {
            if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail")) {
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
    public static void shareContentUsingAppChoserDialog(Context context, String text) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, text);
        shareContentUsingAppChoserDialog(context, sharingIntent);
    }

    public static void shareContentUsingAppChoserDialog(Context context, Intent shareIntent) {
        context.startActivity(Intent.createChooser(shareIntent, "Share using"));
    }


    public static void sendSMS(Activity activity, String phoneNo, String msg) {
//        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
//        sendIntent.setData(Uri.parse("sms:" + phoneNo));
//        sendIntent.putExtra("address", phoneNo);
//        sendIntent.putExtra("sms_body", msg);
//        activity.startActivity(sendIntent);


        Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", phoneNo);
        smsIntent.setData(Uri.parse("sms:" + phoneNo));
        smsIntent.putExtra("sms_body", msg);
        activity.startActivity(Intent.createChooser(smsIntent, "Send SMS:"));
    }
}
