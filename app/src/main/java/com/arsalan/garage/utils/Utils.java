package com.arsalan.garage.utils;

import android.content.Context;

import com.arsalan.garage.R;
import com.arsalan.garage.models.HomeMenuItem;

import java.util.ArrayList;

/**
 * <p/>
 * Created by: Noor  Alam on 17/08/15.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class Utils {

    public static  ArrayList<HomeMenuItem> getRoadHelpScreenMenuItems(Context context){

        ArrayList<HomeMenuItem> menuItemsArrayList = new ArrayList<>();
        menuItemsArrayList.add(new HomeMenuItem(R.drawable.longroad, context.getString(R.string.tariqal_salmi, AppConstants.SCREEN_ROAD_HELP)));
        menuItemsArrayList.add(new HomeMenuItem(R.drawable.longroad, context.getString(R.string.tariqal_sabih, AppConstants.SCREEN_ROAD_HELP)));
        menuItemsArrayList.add(new HomeMenuItem(R.drawable.longroad, context.getString(R.string.tariqal_abdali), AppConstants.SCREEN_ROAD_HELP));
        menuItemsArrayList.add(new HomeMenuItem(R.drawable.longroad, context.getString(R.string.tariq_kabad), AppConstants.SCREEN_ROAD_HELP));
        menuItemsArrayList.add(new HomeMenuItem(R.drawable.longroad, context.getString(R.string.al_wafrah_wal_naviseeb), AppConstants.SCREEN_ROAD_HELP));
        menuItemsArrayList.add(new HomeMenuItem(R.drawable.helproad, context.getString(R.string.al_asma), AppConstants.SCREEN_ROAD_HELP));
        menuItemsArrayList.add(new HomeMenuItem(R.drawable.helproad, context.getString(R.string.al_jahra), AppConstants.SCREEN_ROAD_HELP));
        menuItemsArrayList.add(new HomeMenuItem(R.drawable.helproad, context.getString(R.string.al_farwania), AppConstants.SCREEN_ROAD_HELP));
        menuItemsArrayList.add(new HomeMenuItem(R.drawable.helproad, context.getString(R.string.al_ahmadi), AppConstants.SCREEN_ROAD_HELP));
        menuItemsArrayList.add(new HomeMenuItem(R.drawable.helproad, context.getString(R.string.hauli), AppConstants.SCREEN_ROAD_HELP));
        menuItemsArrayList.add(new HomeMenuItem(R.drawable.helproad, context.getString(R.string.mubarakal_kabir), AppConstants.SCREEN_ROAD_HELP));
        return menuItemsArrayList;
    }

    public static ArrayList<HomeMenuItem> getKarajatScreenMenuItems(){

        ArrayList<HomeMenuItem> menuItemsArrayLis = new ArrayList<>();

        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.subgrag_icon1, "الشويخ"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.subgrag_icon2, "الري"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.subgrag_icon1, "شرق"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.subgrag_icon2, "الجهراء"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.subgrag_icon1, "صليبيه"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.subgrag_icon2, "الفحيحيل"));
        return menuItemsArrayLis;
    }

    public static ArrayList<HomeMenuItem> getScrapScreenMenuItems(){
        ArrayList<HomeMenuItem> menuItemsArrayLis = new ArrayList<>();

        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.image_home1, "امريكي"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.car_logos, "دودج / كلايزلر"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.image_home, "اسيوي"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.delivery, "توصيل قطع"));
        return menuItemsArrayLis;
    }

    public static ArrayList<HomeMenuItem> getTaxiMenuItems(){
        ArrayList<HomeMenuItem> menuItemsArrayLis = new ArrayList<>();

        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.tax1, ""));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.tax2, ""));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.tax3, ""));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.tax4, ""));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.tax5, ""));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.tax6, ""));
        return menuItemsArrayLis;
    }

    public static ArrayList<HomeMenuItem> getKhidmatShamlaMenuItems(){
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

    public static ArrayList<HomeMenuItem> getScrapAmericaMenuItems(){

        ArrayList<HomeMenuItem> menuItemsArrayLis = new ArrayList<>();

        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.cadillac1, "كاديلاك"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.chryslerdodge2, "اوربي"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.chevrolet3, "شيفروليه"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.gmc4, "جمس"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.fordlinc5, "فورد /لنكون"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.hummerlogo6, "همر"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.jeep7, "جيب"));
        return menuItemsArrayLis;
    }

    public static ArrayList<HomeMenuItem> getScrapAwarbiMenuItems(){

        ArrayList<HomeMenuItem> menuItemsArrayLis = new ArrayList<>();

        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.mercedesbenz1, "مرسيدس"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.bmw2, "بي ام دبليو"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.land_rover3, "رنج روفر"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.volkswagen4, "فولكس"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.jaguarlogo5, "جاغوار"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.porsche6, "بورش"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.oodcar7, "اودي"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.peugeot8, "بيجو / ستروين"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.skoda9, "سكودا\""));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.mini10, "ميني"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.car11, "رينو"));
        return menuItemsArrayLis;
    }

    public static ArrayList<HomeMenuItem> getScrapAsibiMenuItems(){

        ArrayList<HomeMenuItem> menuItemsArrayLis = new ArrayList<>();

        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.toyota, "تويوتا"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.nissan, "نيسان"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.lexus_name, "لكزس"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.infinit, "انفينتي"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.honda, "هوندا"));

        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.hyundai, "هيونداي"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.kia_logo, "كيا"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.skoda9, "ميتسوبيشي"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.mitsubishi, "سوزوكي"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.suzuki, "مازدا"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.mazda, "ايسوزو"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.sbaru12, "سوبارو"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.cherylogo, "شيري"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.tata, "تاتا"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.great, "جريت ويل"));
        menuItemsArrayLis.add(new HomeMenuItem(R.drawable.ssangyong, "سانج يونج"));
        return menuItemsArrayLis;
    }


    public static ArrayList<HomeMenuItem> getScrapTawsilKataMenuItems(){

        ArrayList<HomeMenuItem> menuItemsArrayLis = new ArrayList<>();


        return menuItemsArrayLis;
    }
}
