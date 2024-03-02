/**
 * For more information: https://github.com/Shashank02051997/FancyToast-Android
 * How to Use: MyToast.<type>(Context context, String info)
 */
package fpt.edu.fumic.utils;
import android.content.Context;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;

public class MyToast {
    public static void defaultToast(Context context, String info) {
        FancyToast.makeText(context,info,FancyToast.LENGTH_LONG,FancyToast.DEFAULT,false).show();
    }
    public static void successfulToast(Context context, String info){
        FancyToast.makeText(context,info,FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
    }
    public static void infoToast(Context context, String info){
        FancyToast.makeText(context,info,FancyToast.LENGTH_LONG,FancyToast.INFO,false).show();
    }
    public static void warningToast(Context context, String info){
        FancyToast.makeText(context,info,FancyToast.LENGTH_LONG,FancyToast.WARNING,false).show();
    }
    public static void errorToast(Context context, String info){
        FancyToast.makeText(context, info,FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
    }
    public static void confusingToast(Context context, String info) {
        FancyToast.makeText(context,info,FancyToast.LENGTH_LONG,FancyToast.CONFUSING,false).show();
    }

}
