package com.ourdevelops.ornidsdriver.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.ourdevelops.ornidsdriver.constants.Constant;


public class SettingPreference {

    private static String KEY_AUTO_BID = "AUTO_BID";
    private static String KEY_MAKSIMAL_BELANJA = "MAKSIMAL_BELANJA";
    private static String KEY_KERJA = "KERJA";
    private static String KEY_NOTIF = "NOTIF";
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
    private static String LATITUDE = "LATITUDE";
    private static String LONGITUDE = "LONGITUDE";

    private static String PAYUDEBUG = "PAYUDEBUG";
    private static String PAYUMERCHANTKEY = "PAYUMERCHANTKEY";
    private static String PAYUMERCHANTID = "PAYUMERCHANTID";
    private static String PAYUSALT = "PAYUSALT";
    private static String PAYUACTIVE = "0";

    private static String STATUSDRIVER = "statusdriver";
    private static String STRIPEPUBLISH = "STRIPEPUBLISH";

    private SharedPreferences pref;

    private SharedPreferences.Editor editor;

    public SettingPreference(Context context) {
        pref = context.getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE);
    }

    public void updateStatusdriver(String string) {
        editor = pref.edit();
        editor.putString(STATUSDRIVER, string);
        editor.commit();
    }

    public void updateLatitude(String string) {
        editor = pref.edit();
        editor.putString(LATITUDE, string);
        editor.commit();
    }

    public void updateLongitude(String string) {
        editor = pref.edit();
        editor.putString(LONGITUDE, string);
        editor.commit();
    }

    public void updateStripepublish(String string) {
        editor = pref.edit();
        editor.putString(STRIPEPUBLISH, string);
        editor.commit();
    }

    public void updatePayuActive(String string) {
        editor = pref.edit();
        editor.putString(PAYUACTIVE, string);
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

    public void updateAutoBid(String autoBid) {
        editor = pref.edit();
        editor.putString(KEY_AUTO_BID, autoBid);
        editor.commit();
    }

    public void updateMaksimalBelanja(String max) {
        editor = pref.edit();
        editor.putString(KEY_MAKSIMAL_BELANJA, max);
        editor.commit();
    }

    public void updateKerja(String kerja) {
        editor = pref.edit();
        editor.putString(KEY_KERJA, kerja);
        editor.commit();
    }

    public void updateCurrency(String kerja) {
        editor = pref.edit();
        editor.putString(CURRENCY, kerja);
        editor.commit();
    }

    public void updateNotif(String version) {
        editor = pref.edit();
        editor.putString(KEY_NOTIF, version);
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

    public void updatePaypal(String string) {
        editor = pref.edit();
        editor.putString(PAYPALKEY, string);
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

        String[] settingan = new String[22];
        settingan[0] = pref.getString(KEY_AUTO_BID, "OFF");
        settingan[1] = pref.getString(KEY_MAKSIMAL_BELANJA, "1000");
        settingan[2] = pref.getString(KEY_KERJA, "OFF");
        settingan[3] = pref.getString(KEY_NOTIF, "OFF");
        settingan[4] = pref.getString(CURRENCY, "$");
        settingan[5] = pref.getString(ABOUTUS, "");
        settingan[6] = pref.getString(EMAIL, "");
        settingan[7] = pref.getString(PHONE, "");
        settingan[8] = pref.getString(WEBSITE, "");
        settingan[9] = pref.getString(PAYPALKEY, "1234");
        settingan[10] = pref.getString(PAYPALACTIVE, "0");
        settingan[11] = pref.getString(STRIPEACTIVE, "0");
        settingan[12] = pref.getString(PAYPALMODE, "0");
        settingan[13] = pref.getString(CURRENCYTEXT, "USD");
        settingan[14] = pref.getString(PAYUDEBUG, "0");
        settingan[15] = pref.getString(PAYUMERCHANTKEY, "1234");
        settingan[16] = pref.getString(PAYUMERCHANTID, "1234");
        settingan[17] = pref.getString(PAYUSALT, "1234");
        settingan[18] = pref.getString(PAYUACTIVE, "0");
        settingan[19] = pref.getString(STRIPEPUBLISH, "PK_test");
        settingan[20] = pref.getString(LATITUDE, "0");
        settingan[21] = pref.getString(LONGITUDE, "0");
        return settingan;
    }

    public void logout() {
        editor = pref.edit();
        editor.putString(KEY_AUTO_BID, "");
        editor.putString(KEY_MAKSIMAL_BELANJA, "");
        editor.putString(KEY_KERJA, "");
        editor.commit();
    }
}