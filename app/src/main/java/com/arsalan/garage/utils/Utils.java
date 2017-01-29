package com.arsalan.garage.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v13.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arsalan.garage.GarageApp;
import com.arsalan.garage.R;
import com.arsalan.garage.models.HomeMenuItem;
import com.arsalan.garage.models.ShareOptionItem;
import com.arsalan.garage.models.StatusMessage;
import com.arsalan.garage.models.UserInfo;
import com.arsalan.garage.uicomponents.CustomButton;
import com.arsalan.garage.uicomponents.CustomEditText;
import com.arsalan.garage.uicomponents.CustomProgressDialog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import networking.HttpConstants;
import networking.listeners.OnLoadCompleteListener;
import networking.loader.LoaderHandler;
import networking.models.HTTPModel;
import networking.models.HTTPRequest;
import networking.models.HTTPResponse;

/**
 * <p/>
 * Created by: Noor  Alam on 17/08/15.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class Utils {

    public static ArrayList<HomeMenuItem> getRoadHelpScreenMenuItems(Context context) {

        ArrayList<HomeMenuItem> menuItemsArrayList = new ArrayList<>();
        menuItemsArrayList.add(new HomeMenuItem(R.drawable.longroad, context.getString(R.string.tariqal_salmi, AppConstants.SCREEN_ROAD_HELP)));
        menuItemsArrayList.add(new HomeMenuItem(R.drawable.longroad, context.getString(R.string.tariqal_sabih, AppConstants.SCREEN_ROAD_HELP)));
        menuItemsArrayList.add(new HomeMenuItem(R.drawable.longroad, context.getString(R.string.tariqal_abdali)));
        menuItemsArrayList.add(new HomeMenuItem(R.drawable.longroad, context.getString(R.string.tariq_kabad)));
        menuItemsArrayList.add(new HomeMenuItem(R.drawable.longroad, context.getString(R.string.al_wafrah_wal_naviseeb)));
        menuItemsArrayList.add(new HomeMenuItem(R.drawable.menroad, context.getString(R.string.fani_mutnaqal)));
        menuItemsArrayList.add(new HomeMenuItem(R.drawable.helproad, context.getString(R.string.al_asma)));
        menuItemsArrayList.add(new HomeMenuItem(R.drawable.helproad, context.getString(R.string.al_jahra)));
        menuItemsArrayList.add(new HomeMenuItem(R.drawable.helproad, context.getString(R.string.al_farwania)));
        menuItemsArrayList.add(new HomeMenuItem(R.drawable.helproad, context.getString(R.string.al_ahmadi)));
        menuItemsArrayList.add(new HomeMenuItem(R.drawable.helproad, context.getString(R.string.hauli)));
        menuItemsArrayList.add(new HomeMenuItem(R.drawable.helproad, context.getString(R.string.mubarakal_kabir)));
        return menuItemsArrayList;
    }

    public static ArrayList<HomeMenuItem> getKarajatScreenMenuItems() {

        ArrayList<HomeMenuItem> menuItemsArrayLis = new ArrayList<>();

        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.subgrag_icon1, "الشويخ/الري"));
        // menuItemsArrayLis.add(new HomeMenuItem(R.drawable.subgrag_icon2, "الري"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.subgrag_icon2, "شرق"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.subgrag_icon1, "الجهراء"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.subgrag_icon2, "صليبيه"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.subgrag_icon1, "الفحيحيل"));
        return menuItemsArrayLis;
    }

    public static ArrayList<HomeMenuItem> getScrapScreenMenuItems() {
        ArrayList<HomeMenuItem> menuItemsArrayLis = new ArrayList<>();

        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.image_home1, "امريكي"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.car_logos, "اوروبي"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.image_home, "اسيوي"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.delivery, "توصيل قطع"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.car_chrashed, "يع و شرا  "));
        return menuItemsArrayLis;
    }

    public static ArrayList<HomeMenuItem> getBuyAndSaleScreenMenuItems() {
        ArrayList<HomeMenuItem> menuItemsArrayLis = new ArrayList<>();

        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.image_home1, "امريكي"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.car_logos, "اوروبي"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.image_home, "اسيوي"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.accessories, "قطع غيار واكسسوارات"));
        return menuItemsArrayLis;
    }

    public static ArrayList<HomeMenuItem> getTaxiMenuItems() {
        ArrayList<HomeMenuItem> menuItemsArrayLis = new ArrayList<>();

        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.tax1, ""));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.tax2, ""));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.tax3, ""));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.tax4, ""));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.tax5, ""));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.tax6, ""));
        return menuItemsArrayLis;
    }

    public static ArrayList<HomeMenuItem> getKhidmatShamlaMenuItems() {
        ArrayList<HomeMenuItem> menuItemsArrayLis = new ArrayList<>();

        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.autoser1, "مظلات"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.autoser2, "تنكر مياه"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.autoser3, "دركتر"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.autoser4, "كرين"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.autoser5, "نساف"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.autoser6, "نقل عفش"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.autoser7, "تعليم قياده"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.autoser8, "فتح سيارات"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.autoser9, "الشحن البري"));
        return menuItemsArrayLis;
    }

    public static ArrayList<HomeMenuItem> getScrapAmericaMenuItems() {

        ArrayList<HomeMenuItem> menuItemsArrayLis = new ArrayList<>();

        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.cadillac1, "كاديلاك"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.dodge, "دودج"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.chevrolet3, "شيفروليه"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.gmc4, "جمس"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.fordlinc5, "فورد /لنكون"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.hummerlogo6, "همر"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.jeep7, "جيب"));
        //menuItemsArrayLis.add(new HomeMenuItem(R.drawable.car_chrashed, "آخر"));
        return menuItemsArrayLis;
    }

    public static ArrayList<HomeMenuItem> geBuyAndSaleAmericaMenuItems() {

        ArrayList<HomeMenuItem> menuItemsArrayLis = new ArrayList<>();

        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.cadillac1, "كاديلاك"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.dodge, "دودج"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.chevrolet3, "شيفروليه"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.gmc4, "جمس"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.mercury, "ميركوري"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.ram, "رام"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.ford, " فورد "));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.lincoln, " لنكون "));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.hummerlogo6, "همر"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.jeep7, "جيب"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.chrysler, "كلايزلر"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.american, "آخرون"));
        //menuItemsArrayLis.add(new HomeMenuItem(R.drawable.american, "آخر"));
        return menuItemsArrayLis;
    }


    public static ArrayList<HomeMenuItem> getScrapEuropeanMenuItems() {

        ArrayList<HomeMenuItem> menuItemsArrayLis = new ArrayList<>();

        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.mercedesbenz1, "مرسيدس"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.bmw2, "بي ام دبليو"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.land_rover3, "رنج روفر"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.volkswagen4, "فولكس"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.jaguarlogo5, "جاغوار"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.porsche6, "بورش"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.oodcar7, "اودي"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.peugeot8, "بيجو / ستروين"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.skoda9, "سكودا"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.mini10, "ميني"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.renault, "رينو"));
        return menuItemsArrayLis;
    }

    public static ArrayList<HomeMenuItem> getBuyAndSaleEuropeanMenuItems() {

        ArrayList<HomeMenuItem> menuItemsArrayLis = new ArrayList<>();

        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.mercedesbenz1, "مرسيدس"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.bmw2, "بي ام دبليو"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.land_rover3, "رنج روفر"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.volkswagen4, "فولكس"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.jaguarlogo5, "جاغوار"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.porsche6, "بورش"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.oodcar7, "اودي"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.peugeot8, "بيجو / ستروين"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.skoda9, "سكودا"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.mini10, "ميني"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.renault, "رينو"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.europian, "آخرون"));
        //menuItemsArrayLis.add(new HomeMenuItem(R.drawable.europian, "آخر"));
        return menuItemsArrayLis;
    }


    public static ArrayList<HomeMenuItem> getScrapAsianMenuItems() {

        ArrayList<HomeMenuItem> menuItemsArrayLis = new ArrayList<>();

        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.toyota, "تويوتا"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.nissan, "نيسان"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.lexus_name, "لكزس"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.infinit, "انفينتي"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.honda, "هوندا"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.hyundai, "هيونداي"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.kia_logo, "كيا"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.mitsubishi, "ميتسوبيشي"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.suzuki, "سوزوكي"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.mazda, "مازدا"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.isuzu, "ايسوزو"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.sbaru12, "سوبارو"));
        //menuItemsArrayLis.add(new HomeMenuItem(R.drawable.asian, "آخر"));
        //menuItemsArrayLis.add(new HomeMenuItem(R.drawable.cherylogo, "شيري"));
        //menuItemsArrayLis.add(new HomeMenuItem(R.drawable.tata, "تاتا"));
        //menuItemsArrayLis.add(new HomeMenuItem(R.drawable.great, "جريت ويل"));
        //menuItemsArrayLis.add(new HomeMenuItem(R.drawable.ssangyong, "سانج يونج"));
        return menuItemsArrayLis;
    }

    public static ArrayList<HomeMenuItem> getBuyAndSaleAsianMenuItems() {

        ArrayList<HomeMenuItem> menuItemsArrayLis = new ArrayList<>();

        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.toyota, "تويوتا"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.nissan, "نيسان"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.lexus_name, "لكزس"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.infinit, "انفينتي"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.honda, "هوندا"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.hyundai, "هيونداي"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.kia_logo, "كيا"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.mitsubishi, "ميتسوبيشي"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.suzuki, "سوزوكي"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.mazda, "مازدا"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.isuzu, "ايسوزو"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.sbaru12, "سوبارو"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.asian, "آخرون"));
        //menuItemsArrayLis.add(new HomeMenuItem(R.drawable.asian, "آخر"));
        //menuItemsArrayLis.add(new HomeMenuItem(R.drawable.cherylogo, "شيري"));
        //menuItemsArrayLis.add(new HomeMenuItem(R.drawable.tata, "تاتا"));
        //menuItemsArrayLis.add(new HomeMenuItem(R.drawable.great, "جريت ويل"));
        //menuItemsArrayLis.add(new HomeMenuItem(R.drawable.ssangyong, "سانج يونج"));
        return menuItemsArrayLis;
    }


    public static ArrayList<HomeMenuItem> getScrapTawsilKataMenuItems() {

        ArrayList<HomeMenuItem> menuItemsArrayLis = new ArrayList<>();


        return menuItemsArrayLis;
    }


    public static void showToastMessage(Context context, String msg) {
        Toast.makeText(context, msg + "", Toast.LENGTH_SHORT).show();
    }

    public static void setImageFromUrl(String imageUrl, ImageView imageView, int placeholderResId, ImageLoadingListener imageLoadingListener) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(placeholderResId)
                .showImageOnFail(placeholderResId)
                .showImageOnLoading(placeholderResId)
                .resetViewBeforeLoading(true).cacheInMemory(true).build();
        ImageLoader.getInstance().displayImage(imageUrl, imageView, options, imageLoadingListener);
    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager connMgr = (ConnectivityManager) GarageApp.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            if (connMgr != null) {
                if (connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED
                        || connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTING) {
                    return true;
                } else if (connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                        || connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTING) {
                    return true;
                } else
                    return false;
            } else {
                return false;
            }
        } catch (Exception e) {

        }
        return false;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) GarageApp.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            if (connMgr != null) {
                if (connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED
                        || connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTING) {
                    return true;
                } else if (connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                        || connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTING) {
                    return true;
                } else
                    return false;
            } else {
                return false;
            }
        } catch (Exception e) {

        }
        return false;
    }

    public static DisplayImageOptions gerDisplayImageOptions() {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(20))
                .build();
    }


    public static void initCall(String phoneNumber, Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CALL_PHONE)) {
                // Show an explanation to the user *asynchronously* -- don't block this thread waiting for the user's response! After the user sees the explanation, try again to request the permission.
                Toast.makeText(context, "Please grant the Phone permission by going in the App info", Toast.LENGTH_SHORT).show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, 232);
            }
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            context.startActivity(intent);
        }
    }

    public static Bitmap getBitmapFromPath(String path) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);
        return bitmap;
    }

    public static Bitmap getSampledBitmapFromFilePath(String path, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap getBitmapFromFile(File file) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), bmOptions);
        return bitmap;
    }

    public static int convertDpToPixels(Context activity, int dp) {
        float density = activity.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    /**
     * Non Swipable snackbar.
     *
     * @param activity Activity reference.
     * @param message
     */

    public static void showSnackBar(final Activity activity, String message) {
        View rootLayout = activity.findViewById(android.R.id.content);
        showSnackBar(rootLayout, message);
    }

    /**
     * Display Snack bar
     *
     * @param holderView If holderView is CoordinatorLayout the snackBar will be swipable otherwise will be non swipable
     * @param message
     */
    public static void showSnackBar(View holderView, String message) {
        final Snackbar snackbar = Snackbar.make(holderView, message, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(GarageApp.getInstance().getResources().getColor(R.color.app_bright_blue));
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.setAction("Close", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    public static String getUDID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID) + 6;
    }

    public static Point getDisplayPoint(Context context) {
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    public static ArrayList<ShareOptionItem> getShareOptions() {
        ArrayList<ShareOptionItem> shareOptions = new ArrayList<>(3);
        shareOptions.add(new ShareOptionItem(R.string.facebook, R.drawable.facebook));
        shareOptions.add(new ShareOptionItem(R.string.twitter, R.drawable.twitter));
        shareOptions.add(new ShareOptionItem(R.string.whatsapp, R.drawable.whatsapp));

        return shareOptions;
    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    /**
     * This function used to display indicator on pager
     *
     * @param circleCount int
     */
    public static ArrayList<ImageView> getCircleIndicator(Context context, int circleCount, LinearLayout layout) {
        if (context == null && circleCount > 0) {
            return null;
        }
        ArrayList<ImageView> indicatorImage = new ArrayList<ImageView>();
        indicatorImage.clear();

        layout.removeAllViews();
        for (int i = 0; i < circleCount; i++) {
            ImageView img = new ImageView(context);
            img.setId(i);
            img.setImageResource(R.drawable.ic_pagination);
            img.setPadding(2, 2, 2, 2);
            indicatorImage.add(img);
            layout.addView(img);
        }
        return indicatorImage;
    }

    /**
     * This function used to switch indicator or  dots on pager
     */
    public static void setIndicator(int pagerIndex, List<ImageView> indicatorImage) {
        if (pagerIndex < indicatorImage.size()) {
            for (int i = 0; i < indicatorImage.size(); i++) {
                ImageView circle = indicatorImage.get(i);
                if (i == pagerIndex) {
                    circle.setImageResource(R.drawable.ic_pagination_selected);
                } else {
                    circle.setImageResource(R.drawable.ic_pagination);
                }
            }
        }
    }

    /**
     * Verify email Id
     *
     * @param email
     * @return
     */
    public static boolean isValidEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        }
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static int getScreenHeight(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        return height;
    }

    public static void createForgotPasswordDialog(final Activity activity) {

        final ProgressDialog progressDialog = new CustomProgressDialog(activity);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);

        final Dialog forgotPasswordDialog = new Dialog(activity);
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_forgot_password, null, false);
        final TextInputLayout inputLayoutEmailForgotPassword = (TextInputLayout) view.findViewById(R.id.input_layout_email_forgot_password);
        forgotPasswordDialog.setContentView(view);
        forgotPasswordDialog.setCanceledOnTouchOutside(true);
        forgotPasswordDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        CustomButton resetPassword = (CustomButton) forgotPasswordDialog.findViewById(R.id.resetPassword);
        final TextInputLayout emailError = (TextInputLayout) forgotPasswordDialog.findViewById(R.id.input_layout_email_forgot_password);
        final CustomEditText emailForgotPassword = (CustomEditText) forgotPasswordDialog.findViewById(R.id.edittext_email_forgot_password);
        //forgotPasswordResult = (SwanTextView) forgotPasswordDialog.findViewById(R.id.forgotPasswordResult);
        ImageButton cancelDialog = (ImageButton) forgotPasswordDialog.findViewById(R.id.cancel_dialog);
        forgotPasswordDialog.show();
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(emailForgotPassword.getText().toString())) {
                    inputLayoutEmailForgotPassword.setError(activity.getString(R.string.blank_email));
                } else if (!Utils.isValidEmail(emailForgotPassword.getText().toString())) {
                    inputLayoutEmailForgotPassword.setError(activity.getString(R.string.invalid_email));
                } else {
                    JSONObject forgotPasswordJSON = new JSONObject();
                    try {
                        forgotPasswordJSON.put(AppConstants.EMAIL, emailForgotPassword.getText().toString());
                    } catch (JSONException e) {
                        Log.e("Exception:", Log.getStackTraceString(e));
                    }
                    progressDialog.show();
                    HTTPRequest httpRequest = new HTTPRequest();
                    httpRequest.setShowProgressDialog(true);
                    String fullUrl = Urls.RESET_PASSWORD;
                    httpRequest.setUrl(fullUrl);
                    httpRequest.setRequestType(HttpConstants.HTTP_REQUEST_TYPE_POST);
                    httpRequest.setValueObjectFullyQualifiedName(UserInfo.class.getName());
                    httpRequest.setJSONPayload(forgotPasswordJSON.toString());
                    LoaderHandler loaderHandler = LoaderHandler.newInstance(activity, httpRequest);
                    loaderHandler.setOnLoadCompleteListener(new OnLoadCompleteListener() {
                        @Override
                        public void onLoadComplete(HTTPModel httpModel) {
                            HTTPResponse httpResponse = (HTTPResponse) httpModel;
                            if (progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                            StatusMessage statusMessage = (StatusMessage)  httpResponse.getValueObject();
                            if (statusMessage.getStatus().equals(AppConstants.SUCCESS)) {
                                Utils.showSnackBar(activity, activity.getString(R.string.msg_password_sent));
                                forgotPasswordDialog.dismiss();
                            } else {
                                Utils.showSnackBar(activity, statusMessage.getMessage());
                                inputLayoutEmailForgotPassword.setError(statusMessage.getMessage());
                            }
                        }
                    });
                    loaderHandler.loadData();
                }
            }
        });
        cancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPasswordDialog.dismiss();
            }
        });
    }
}
