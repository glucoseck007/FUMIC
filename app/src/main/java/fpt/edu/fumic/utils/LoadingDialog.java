package fpt.edu.fumic.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import java.util.Objects;

import fpt.edu.fumic.R;

public class LoadingDialog {
    private Activity activity;
    private Dialog dialog;

    public LoadingDialog(Activity myActivity){
        this.activity = myActivity;
    }
    public void startLoadingDialog(){
        LayoutInflater inflater = this.activity.getLayoutInflater();
        dialog = new Dialog(this.activity);
        dialog.setContentView(inflater.inflate(R.layout.layout_loading, null));
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void stopLoadingDialog(){
        dialog.dismiss();
    }
}
