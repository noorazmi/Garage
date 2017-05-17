package com.arsalan.garage.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
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
        menuItemsArrayList.add(new HomeMenuItem(R.drawable.longroad, context.getString(R.string.alsalmi_road, AppConstants.SCREEN_ROAD_HELP)));
        menuItemsArrayList.add(new HomeMenuItem(R.drawable.longroad, context.getString(R.string.alsubiya_road, AppConstants.SCREEN_ROAD_HELP)));
        menuItemsArrayList.add(new HomeMenuItem(R.drawable.longroad, context.getString(R.string.alabdali_road)));
        menuItemsArrayList.add(new HomeMenuItem(R.drawable.longroad, context.getString(R.string.kabd_road)));
        menuItemsArrayList.add(new HomeMenuItem(R.drawable.longroad, context.getString(R.string.alwafara_nuwaiseeb)));
        menuItemsArrayList.add(new HomeMenuItem(R.drawable.menroad, context.getString(R.string.help_on_road)));
        menuItemsArrayList.add(new HomeMenuItem(R.drawable.helproad, context.getString(R.string.alasimah)));
        menuItemsArrayList.add(new HomeMenuItem(R.drawable.helproad, context.getString(R.string.al_jahra)));
        menuItemsArrayList.add(new HomeMenuItem(R.drawable.helproad, context.getString(R.string.farwania)));
        menuItemsArrayList.add(new HomeMenuItem(R.drawable.helproad, context.getString(R.string.al_ahmadi)));
        menuItemsArrayList.add(new HomeMenuItem(R.drawable.helproad, context.getString(R.string.hawalli)));
        menuItemsArrayList.add(new HomeMenuItem(R.drawable.helproad, context.getString(R.string.mubarak_alkabir)));
        return menuItemsArrayList;
    }

    public static ArrayList<HomeMenuItem> getScrapScreenMenuItems(Context context) {
        ArrayList<HomeMenuItem> menuItemsArrayLis = new ArrayList<>();

        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.image_home1, context.getString(R.string.american)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.car_logos, context.getString(R.string.european)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.image_home, context.getString(R.string.asian)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.delivery, context.getString(R.string.connecting_pieces)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.car_chrashed, context.getString(R.string.buy_and_sale)));
        return menuItemsArrayLis;
    }

    public static ArrayList<HomeMenuItem> getBuyAndSaleScreenMenuItems(Context context) {
        ArrayList<HomeMenuItem> menuItemsArrayLis = new ArrayList<>();

        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.image_home1, context.getString(R.string.american)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.car_logos, context.getString(R.string.european)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.image_home, context.getString(R.string.asian)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.accessories, context.getString(R.string.parts_and_accessories)));
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

    public static ArrayList<HomeMenuItem> getKhidmatShamlaMenuItems(Context context) {
        ArrayList<HomeMenuItem> menuItemsArrayLis = new ArrayList<>();

        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.autoser1, context.getString(R.string.blacksmith_and_umbrellas)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.autoser2, context.getString(R.string.denying_water)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.autoser3, context.getString(R.string.more_intuitive)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.autoser4, context.getString(R.string.crane)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.autoser5, context.getString(R.string.nsav)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.autoser6, context.getString(R.string.transfer_and_relocation)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.autoser7, context.getString(R.string.driving_school)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.autoser8, context.getString(R.string.open_cars)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.autoser9, context.getString(R.string.land_shipping)));
        return menuItemsArrayLis;
    }

    public static ArrayList<HomeMenuItem> getScrapAmericaMenuItems(Context context) {

        ArrayList<HomeMenuItem> menuItemsArrayLis = new ArrayList<>();

        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.cadillac1, context.getString(R.string.cadillac)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.dodge, context.getString(R.string.dodge)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.chevrolet3, context.getString(R.string.chevrolet)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.gmc4, context.getString(R.string.gmc)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.fordlinc5, context.getString(R.string.ford_lincoln)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.hummerlogo6, context.getString(R.string.hummer)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.jeep7, context.getString(R.string.pocket)));
        //menuItemsArrayLis.add(new HomeMenuItem(R.drawable.car_chrashed, "آخر"));
        return menuItemsArrayLis;
    }

    public static ArrayList<HomeMenuItem> geBuyAndSaleAmericaMenuItems(Context context) {

        ArrayList<HomeMenuItem> menuItemsArrayLis = new ArrayList<>();

        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.cadillac1, context.getString(R.string.cadillac)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.dodge, context.getString(R.string.dodge)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.chevrolet3,context.getString(R.string.chevrolet) ));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.gmc4, context.getString(R.string.gmc)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.mercury, context.getString(R.string.mercury)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.ram, context.getString(R.string.ram)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.ford, context.getString(R.string.ford)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.lincoln, context.getString(R.string.lincoln)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.hummerlogo6,context.getString(R.string.hummer) ));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.jeep7, context.getString(R.string.pocket)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.chrysler,context.getString(R.string.chrysler) ));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.american,context.getString(R.string.others)));
        //menuItemsArrayLis.add(new HomeMenuItem(R.drawable.american, "آخر"));
        return menuItemsArrayLis;
    }


    public static ArrayList<HomeMenuItem> getScrapEuropeanMenuItems(Context context) {

        ArrayList<HomeMenuItem> menuItemsArrayLis = new ArrayList<>();

        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.mercedesbenz1,context.getString(R.string.mercedes) ));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.bmw2, context.getString(R.string.bmw)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.land_rover3,context.getString(R.string.land_rover) ));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.volkswagen4, context.getString(R.string.volkswagen)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.jaguarlogo5, context.getString(R.string.jaguar)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.porsche6, context.getString(R.string.porsche)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.oodcar7, context.getString(R.string.audi)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.peugeot8, context.getString(R.string.peugeot)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.skoda9, context.getString(R.string.skoda)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.mini10, context.getString(R.string.mini)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.renault, context.getString(R.string.renault)));
        return menuItemsArrayLis;
    }

    public static ArrayList<HomeMenuItem> getBuyAndSaleEuropeanMenuItems(Context context) {

        ArrayList<HomeMenuItem> menuItemsArrayLis = new ArrayList<>();

        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.mercedesbenz1, context.getString(R.string.mercedes)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.bmw2, context.getString(R.string.bmw)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.land_rover3, context.getString(R.string.land_rover)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.volkswagen4, context.getString(R.string.volkswagen)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.jaguarlogo5, context.getString(R.string.jaguar)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.porsche6, context.getString(R.string.porsche)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.oodcar7, context.getString(R.string.audi)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.peugeot8, context.getString(R.string.peugeot)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.skoda9, context.getString(R.string.skoda)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.mini10, context.getString(R.string.mini)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.renault,context.getString(R.string.renault) ));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.europian, context.getString(R.string.others)));
        //menuItemsArrayLis.add(new HomeMenuItem(R.drawable.europian, "آخر"));
        return menuItemsArrayLis;
    }


    public static ArrayList<HomeMenuItem> getScrapAsianMenuItems(Context context) {

        ArrayList<HomeMenuItem> menuItemsArrayLis = new ArrayList<>();

        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.toyota, context.getString(R.string.toyota)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.nissan,context.getString(R.string.nissan) ));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.lexus_name,context.getString(R.string.lexus) ));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.infinit, context.getString(R.string.infiniti)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.honda, context.getString(R.string.honda)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.hyundai, context.getString(R.string.hydraulic)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.kia_logo, context.getString(R.string.kia)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.mitsubishi,context.getString(R.string.mitsubishi)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.suzuki, context.getString(R.string.suzuki)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.mazda, context.getString(R.string.mazda)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.isuzu, context.getString(R.string.isuzu)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.sbaru12, context.getString(R.string.subaru)));
        //menuItemsArrayLis.add(new HomeMenuItem(R.drawable.asian, "آخر"));
        //menuItemsArrayLis.add(new HomeMenuItem(R.drawable.cherylogo, "شيري"));
        //menuItemsArrayLis.add(new HomeMenuItem(R.drawable.tata, "تاتا"));
        //menuItemsArrayLis.add(new HomeMenuItem(R.drawable.great, "جريت ويل"));
        //menuItemsArrayLis.add(new HomeMenuItem(R.drawable.ssangyong, "سانج يونج"));
        return menuItemsArrayLis;
    }

    public static ArrayList<HomeMenuItem> getBuyAndSaleAsianMenuItems(Context context) {

        ArrayList<HomeMenuItem> menuItemsArrayLis = new ArrayList<>();

        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.toyota, context.getString(R.string.toyota)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.nissan, context.getString(R.string.nissan)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.lexus_name,context.getString(R.string.lexus) ));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.infinit, context.getString(R.string.infiniti)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.honda, context.getString(R.string.honda)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.hyundai,context.getString(R.string.hyundai) ));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.kia_logo,context.getString(R.string.kia) ));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.mitsubishi,context.getString(R.string.mitsubishi) ));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.suzuki, context.getString(R.string.suzuki)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.mazda, context.getString(R.string.mazda)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.isuzu, context.getString(R.string.isuzu)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.sbaru12, context.getString(R.string.subaru)));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.asian, context.getString(R.string.others)));
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
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
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
