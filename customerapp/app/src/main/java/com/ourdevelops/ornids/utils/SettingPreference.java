package com.ourdevelops.ornids.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.ourdevelops.ornids.constants.Constant;


public class SettingPreference {

    private static String CURRENCY = "$";
    private static String ABOUTUS = "ABOUTUS";
    private static String EMAIL = "EMAIL";
    private static String PHONE = "PHONE";
    private static String WEBSITE = "WEBSITE";
    private static String PAYPALKEY = "PAYPAL";
    private static String STRIPEACTIVE = "STRIPEACTIVE";
    private static String PAYPALMODE = "PAYPALMODE";
    private static String PAYPALACTIVE = "PAYPALACTIVE";
    private static String CURRENCYTEXT = "CURRENCYTEXT";

    private static String PAYUDEBUG = "PAYUDEBUG";
    private static String PAYUMERCHANTKEY = "PAYUMERCHANTKEY";
    private static String PAYUMERCHANTID = "PAYUMERCHANTID";
    private static String PAYUSALT = "PAYUSALT";
    private static String STRIPEPUBLISH = "STRIPEPUBLISH";
    private static String PAYUACTIVE = "0";
    private static String LATITUDE = "LATITUDE";
    private static String LONGITUDE = "LONGITUDE";
    private static String ADDRESS = "ADDRESS";

    private SharedPreferences pref;

    private SharedPreferences.Editor editor;

    public SettingPreference(Context context) {
        pref = context.getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE);
    }

    public void updatelatitude(String string) {
        editor = pref.edit();
        editor.putString(LATITUDE, string);
        editor.commit();
    }

    public void updatelongitude(String string) {
        editor = pref.edit();
        editor.putString(LONGITUDE, string);
        editor.commit();
    }

    public void updateaddress(String string) {
        editor = pref.edit();
        editor.putString(ADDRESS, string);
        editor.commit();
    }

    public void updatePayuActive(String string) {
        editor = pref.edit();
        editor.putString(PAYUACTIVE, string);
        editor.commit();
    }

    public void updateStripepublish(String string) {
        editor = pref.edit();
        editor.putString(STRIPEPUBLISH, string);
        editor.commit();
    }

    public void updatePayudebug(String string) {
        editor = pref.edit();
        editor.putString(PAYUDEBUG, string);
        editor.commit();
    }

    public void updatePayusalt(String string) {
        editor = pref.edit();
        editor.putString(PAYUSALT, string);
        editor.commit();
    }

    public void updatePayumerchantkey(String string) {
        editor = pref.edit();
        editor.putString(PAYUMERCHANTKEY, string);
        editor.commit();
    }

    public void updatePayumerchantid(String string) {
        editor = pref.edit();
        editor.putString(PAYUMERCHANTID, string);
        editor.commit();
    }

    public void updateCurrency(String string) {
        editor = pref.edit();
        editor.putString(CURRENCY, string);
        editor.commit();
    }

    public void updatePaypal(String string) {
        editor = pref.edit();
        editor.putString(PAYPALKEY, string);
        editor.commit();
    }

    public void updateabout(String string) {
        editor = pref.edit();
        editor.putString(ABOUTUS, string);
        editor.commit();
    }

    public void updateemail(String string) {
        editor = pref.edit();
        editor.putString(EMAIL, string);
        editor.commit();
    }

    public void updatephone(String string) {
        editor = pref.edit();
        editor.putString(PHONE, string);
        editor.commit();
    }

    public void updateweb(String string) {
        editor = pref.edit();
        editor.putString(WEBSITE, string);
        editor.commit();
    }

    public void updatepaypalactive(String string) {
        editor = pref.edit();
        editor.putString(PAYPALACTIVE, string);
        editor.commit();
    }

    public void updatepaypalmode(String string) {
        editor = pref.edit();
        editor.putString(PAYPALMODE, string);
        editor.commit();
    }

    public void updatestripeactive(String string) {
        editor = pref.edit();
        editor.putString(STRIPEACTIVE, string);
        editor.commit();
    }

    public void updatecurrencytext(String string) {
        editor = pref.edit();
        editor.putString(CURRENCYTEXT, string);
        editor.commit();
    }

    public String[] getSetting() {

        String[] settingan = new String[19];
        settingan[0] = pref.getString(CURRENCY, "$");
        settingan[1] = pref.getString(ABOUTUS, "");
        settingan[2] = pref.getString(EMAIL, "");
        settingan[3] = pref.getString(PHONE, "");
        settingan[4] = pref.getString(WEBSITE, "");
        settingan[5] = pref.getString(PAYPALKEY, "");
        settingan[6] = pref.getString(PAYPALACTIVE, "0");
        settingan[7] = pref.getString(STRIPEACTIVE, "0");
        settingan[8] = pref.getString(PAYPALMODE, "1");
        settingan[9] = pref.getString(CURRENCYTEXT, "USD");
        settingan[10] = pref.getString(PAYUDEBUG, "0");
        settingan[11] = pref.getString(PAYUMERCHANTKEY, "1234");
        settingan[12] = pref.getString(PAYUMERCHANTID, "1234");
        settingan[13] = pref.getString(PAYUSALT, "1234");
        settingan[14] = pref.getString(PAYUACTIVE, "0");
        settingan[15] = pref.getString(STRIPEPUBLISH, "pk_test");
        settingan[16] = pref.getString(LATITUDE, "51.8493256");
        settingan[17] = pref.getString(LONGITUDE, "4.5510763");
        settingan[18] = pref.getString(ADDRESS, "Beste Vriendjes, Irenestraat 13, Barendrecht, South Holland 2991 BG, Netherlands");

        return settingan;
    }
}