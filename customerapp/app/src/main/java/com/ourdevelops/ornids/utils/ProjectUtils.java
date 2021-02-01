package com.ourdevelops.ornids.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;


import com.ourdevelops.ornids.R;

import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by otacodes on 27/12/2018.
 */
public class ProjectUtils {

    public static final String TAG = "ProjectUtility";
    private static AlertDialog dialog;
    private static Toast toast;
    private static ProgressDialog mProgressDialog;
    private static final String VERSION_UNAVAILABLE = "N/A";
    private static Dialog dialog_gif;

    public static Bitmap bmp;

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    //For Changing Status Bar Color if Device is above 5.0(Lollipop)
    public static void changeStatusBarColor(Activity activity) {

        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(activity.getResources().getColor(R.color.black));
        }
    }

    public static void changeStatusBarColorNew(Activity activity, int color) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(activity.getResources().getColor(color));
        }
    }

    public static void Fullscreen(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    public static void statusbarBackgroundTrans(Activity activity, int drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(drawable);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            // window.setNavigationBarColor(activity.getResources().getColor(R.color.transparent));
            window.setBackgroundDrawable(background);
        }
    }

    public static void statusbarBackgroundTransformURL(Activity activity, String imageurl) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            URL url = null;
            try {
                url = new URL(imageurl);
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

            } catch (Exception e) {
                e.printStackTrace();
            }

            Window window = activity.getWindow();
            Drawable background = new BitmapDrawable(activity.getResources(), bmp);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            // window.setNavigationBarColor(activity.getResources().getColor(R.color.transparent));
            window.setBackgroundDrawable(background);
        }
    }

    public static boolean hasPermissionInManifest(Activity activity, int requestCode, String permissionName) {
        if (ContextCompat.checkSelfPermission(activity,
                permissionName)
                != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(activity,
                    new String[]{permissionName},
                    requestCode);
        } else {
            return true;
        }
        return false;
    }



    //For Long Period Toast Message
    public static void showLong(Context context, String message) {
        if (message == null) {
            return;
        }
        if (message.length() > 0) {
            if (toast == null && context != null) {
                toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            }
            if (toast != null) {
                toast.setText(message);
                toast.show();
            }
        }

    }

    public static boolean containsOnlyNumbers(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i)))
                return false;
        }
        return true;
    }

    // For Alert Dialog in App
    public static Dialog createDialog(Context context, int titleId, int messageId,
                                      DialogInterface.OnClickListener positiveButtonListener,
                                      DialogInterface.OnClickListener negativeButtonListener) {
        Builder builder = new Builder(context);
        builder.setTitle(titleId);
        builder.setMessage(messageId);
        builder.setPositiveButton("Ok", positiveButtonListener);
        builder.setNegativeButton("Cancel", negativeButtonListener);

        return builder.create();
    }

    // For Alert Dialog on Custom View in App
    public static Dialog createDialog(Context context, int titleId, int messageId, View view,
                                      DialogInterface.OnClickListener positiveClickListener,
                                      DialogInterface.OnClickListener negativeClickListener) {

        Builder builder = new Builder(context);
        builder.setTitle(titleId);
        builder.setMessage(messageId);
        builder.setView(view);
        builder.setPositiveButton("Ok", positiveClickListener);
        builder.setNegativeButton("Cancel", negativeClickListener);

        return builder.create();
    }


    public static void showDialog(Context context, String title, String msg,
                                  DialogInterface.OnClickListener OK, boolean isCancelable) {

        if (title == null)
            title = context.getResources().getString(R.string.app_name);

        if (OK == null)
            OK = new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    hideDialog();
                }
            };

        if (dialog == null) {
            Builder builder = new Builder(context);
            builder.setTitle(title);
            builder.setMessage(msg);
            builder.setPositiveButton("OK", OK);
            dialog = builder.create();
            dialog.setCancelable(isCancelable);
        }

        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Static method to show the dialog with custom message on it
     *
     * @param context      Context of the activity where to show the dialog
     * @param title        Title to be shown either custom or application name
     * @param msg          Custom message to be shown on dialog
     * @param OK           Overridden click listener for OK button in dialog
     * @param cancel       Overridden click listener for cancel button in dialog
     * @param isCancelable : Sets whether this dialog is cancelable with the BACK key.
     */
    public static void showDialog(Context context, String title, String msg,
                                  DialogInterface.OnClickListener OK,
                                  DialogInterface.OnClickListener cancel, boolean isCancelable) {

        if (title == null)
            title = context.getResources().getString(R.string.app_name);

        if (OK == null)
            OK = new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface paramDialogInterface,
                                    int paramInt) {
                    hideDialog();
                }
            };

        if (cancel == null)
            cancel = new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface paramDialogInterface,
                                    int paramInt) {
                    hideDialog();
                }
            };

        if (dialog == null) {
            Builder builder = new Builder(context);
            builder.setTitle(title);
            builder.setMessage(msg);
            builder.setPositiveButton("OK", OK);
            builder.setNegativeButton("Cancel", cancel);
            dialog = builder.create();
            dialog.setCancelable(isCancelable);
        }
        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Static method to show the progress dialog.
     *
     * @param context      : Context of the activity where to show the dialog
     * @param isCancelable : Sets whether this dialog is cancelable with the BACK key.
     * @param message      : Message to be shwon on the progress dialog.
     * @return Object of progress dialog.
     */
    public static Dialog showProgressDialog(Context context,
                                            boolean isCancelable, String message) {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
        mProgressDialog.setCancelable(isCancelable);
        //mProgressDialog.setIndeterminate(true);
        //mProgressDialog.setIndeterminateDrawable(context.getResources().getDrawable(R.drawable.my_animation));
        return mProgressDialog;
    }

    /**
     * Static method to pause the progress dialog.
     */
    public static void pauseProgressDialog() {
        try {
            if (mProgressDialog != null) {
                mProgressDialog.cancel();
                mProgressDialog.dismiss();
                mProgressDialog = null;
            }
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
    }


    /**
     * show Toast
     */

    public static void showToast(Context context, String message) {
        if (message == null) {
            return;
        }
        if (toast == null && context != null) {
            toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        }
        if (toast != null) {
            toast.setText(message);
            toast.show();
        }
    }


    /**
     * Static method to cancel the Dialog.
     */
    public static void cancelDialog() {

        try {
            if (dialog != null) {
                dialog.cancel();
                dialog.dismiss();
                dialog = null;
            }
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Static method to hide the dialog if visible
     */
    public static void hideDialog() {

        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog.cancel();
            dialog = null;
        }
    }


    /**
     * Checks the validation of email address.
     * Takes pattern and checks the text entered is valid email address or not.
     *
     * @param email : email in string format
     * @return True if email address is correct.
     */
    public static boolean isEmailValid(String email) {
        String expression = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            return true;
        } else if (email.equals("")) {
            return false;
        }
        return false;
    }


    public static boolean urlValidate(String url) {


        String expression = ".*(youtube|youtu.be).*";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(url);
        if (matcher.matches()) {
            return true;
        } else if (url.equals("")) {
            return false;
        }
        return false;

    }


    /**
     * Method checks if the given phone number is valid or not.
     *
     * @param number : Phone number is to be checked.
     * @return True if the number is valid.
     * False if number is not valid.
     */
    public static boolean isPhoneNumberValid(String number) {


        if (number.length() < 10 || number.length() > 11) {
            //	Log.d("tag", "Number is not valid");
            return false;
        }

        return true;
    }


    public static boolean isPasswordValid(String number) {

        //String regexStr = "^([0-9\\(\\)\\/\\+ \\-]*)$";
        String regexStr = " (?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{8,20})$";

        if (number.length() < 6 || number.length() > 13 /*|| number.matches(regexStr) == false*/) {
            //	Log.d("tag", "Number is not valid");
            return false;
        }

        return true;
    }

    public static long currentTimeInMillis() {
        Time time = new Time();
        time.setToNow();
        return time.toMillis(false);
    }

    /**
     * Checks if any text box is null or not.
     *
     * @param text : Text view for which validation is to be checked.
     * @return True if not null.
     */
    public static String getEditTextValue(EditText text) {
        return text.getText().toString().trim();
    }

    /**
     * Checks if any text box is null or not.
     *
     * @param text : Text view for which validation is to be checked.
     * @return True if not null.
     */
    public static boolean isEditTextFilled(EditText text) {
        if (text.getText() != null && text.getText().toString().trim().length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isPasswordLengthCorrect(EditText text) {
        if (text.getText() != null && text.getText().toString().trim().length() >= 8) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNetworkConnected(Context mContext) {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        } else
            return true;
    }

    public static void InternetAlertDialog(Context mContext) {
        Builder alertDialog = new Builder(mContext);

        //Setting Dialog Title
        alertDialog.setTitle("Error Connecting");

        //Setting Dialog Message
        alertDialog.setMessage("No Internet Connection");

        //On Pressing Setting button
        alertDialog.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
        alertDialog.show();
    }


    public static int getAppVersion(Context ctx) {
        try {
            PackageInfo packageInfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }


    public static String capWordFirstLetter(String fullname) {
        String fname = "";
        String s2;
        StringTokenizer tokenizer = new StringTokenizer(fullname);
        while (tokenizer.hasMoreTokens()) {
            s2 = tokenizer.nextToken().toLowerCase();
            if (fname.length() == 0)
                fname += s2.substring(0, 1).toUpperCase() + s2.substring(1);
            else
                fname += " " + s2.substring(0, 1).toUpperCase() + s2.substring(1);
        }

        return fname;
    }

    public static String getDisplayableTime(long delta) {
        long difference = 0;
        Long mDate = System.currentTimeMillis();

        if (mDate > delta) {
            difference = mDate - delta;
            final long seconds = difference / 1000;
            final long minutes = seconds / 60;
            final long hours = minutes / 60;
            final long days = hours / 24;
            final long months = days / 31;
            final long years = days / 365;

            if (seconds < 0) {
                return "not yet";
            } else if (seconds < 60) {
                return seconds == 1 ? "one second ago" : seconds + " seconds ago";
            } else if (seconds < 120) {
                return "a minute ago";
            } else if (seconds < 2700) // 45 * 60
            {
                return minutes + " minutes ago";
            } else if (seconds < 5400) // 90 * 60
            {
                return "an hour ago";
            } else if (seconds < 86400) // 24 * 60 * 60
            {
                return hours + " hours ago";
            } else if (seconds < 172800) // 48 * 60 * 60
            {
                return "yesterday";
            } else if (seconds < 2592000) // 30 * 24 * 60 * 60
            {
                return days + " days ago";
            } else if (seconds < 31104000) // 12 * 30 * 24 * 60 * 60
            {

                return months <= 1 ? "one month ago" : days + " months ago";
            } else {

                return years <= 1 ? "one year ago" : years + " years ago";
            }
        }
        return null;
    }
    public static String getDisplayableDay(long delta) {
        long difference = 0;
        Long mDate = System.currentTimeMillis();

        if (mDate > delta) {
            difference = mDate - delta;
            final long seconds = difference / 1000;
            final long minutes = seconds / 60;
            final long hours = minutes / 60;
            final long days = hours / 24;
            final long months = days / 31;
            final long years = days / 365;

            if (seconds < 0) {
                return "not yet";
            } else if (DateUtils.isToday(delta)) {
                return "TODAY";
            }/* else if (seconds < 120) {
                return "TODAY";
            } else if (seconds < 2700) // 45 * 60
            {
                return "TODAY";
            } else if (seconds < 5400) // 90 * 60
            {
                return "TODAY";
            } else if (seconds < 86400) // 24 * 60 * 60
            {
                return "TODAY";
            }*/ else if (seconds < 172800) // 48 * 60 * 60
            {
                return "yesterday";
            } else if (seconds < 2592000) // 30 * 24 * 60 * 60
            {
                return days + " days ago";
            } else if (seconds < 31104000) // 12 * 30 * 24 * 60 * 60
            {

                return months <= 1 ? "one month ago" : days + " months ago";
            } else {

                return years <= 1 ? "one year ago" : years + " years ago";
            }
        }
        return null;
    }


    public static long correctTimestamp(long timestampInMessage) {
        long correctedTimestamp = timestampInMessage;

        if (String.valueOf(timestampInMessage).length() < 13) {
            int difference = 13 - String.valueOf(timestampInMessage).length(), i;
            String differenceValue = "1";
            for (i = 0; i < difference; i++) {
                differenceValue += "0";
            }
            correctedTimestamp = (timestampInMessage * Integer.parseInt(differenceValue))
                    + (System.currentTimeMillis() % (Integer.parseInt(differenceValue)));
        }
        return correctedTimestamp;
    }

   /* public static double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d));
    }*/

    public static String convertTimestampToTime(long timestamp) {
        Timestamp tStamp = new Timestamp(timestamp);
        SimpleDateFormat simpleDateFormat;
        if (DateUtils.isToday(timestamp)) {
            simpleDateFormat = new SimpleDateFormat("hh:mm a");
            return simpleDateFormat.format(tStamp);
        } else {
            simpleDateFormat = new SimpleDateFormat("hh:mm a");
            return simpleDateFormat.format(tStamp);
        }
    }

    public static String convertTimestampDateToTime(long timestamp) {
        Timestamp tStamp = new Timestamp(timestamp);
        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat("dd MMM, yyyy hh:mm a");
        return simpleDateFormat.format(tStamp);
    }

    public static String getFirstLetterCapital(String input) {
        String val = "";
        try {
            val = Character.toUpperCase(input.charAt(0)) + input.substring(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return val;
    }

    public static String changeDateFormate(String time) {//2018-07-18 09:48:39
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd MMM yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }



    public static String extractYoutubeVideoId(String ytUrl) {
        String vId = "";
        Pattern pattern = Pattern.compile(
                "^https?://.*(?:youtu.be/|v/|u/\\w/|embed/|watch?v=)([^#&?]*).*$",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(ytUrl);
        if (matcher.matches()){
            vId = matcher.group(1);
        }
        return vId;
    }

}