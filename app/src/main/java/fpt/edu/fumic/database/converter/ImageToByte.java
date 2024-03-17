package fpt.edu.fumic.database.converter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.widget.ImageView;

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import java.io.ByteArrayOutputStream;

public class ImageToByte {

    /*public static byte[] imageViewToByte(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }*/

    public static byte[] imageViewToByte(ImageView imageView, int maxWidth, int maxHeight) {
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        if (drawable == null) {
            return null; // or handle this case according to your requirements
        }

        Bitmap bitmap = drawable.getBitmap();
        if (bitmap == null) {
            return null; // or handle this case according to your requirements
        }

        // Calculate aspect ratio
        float aspectRatio = (float) bitmap.getWidth() / (float) bitmap.getHeight();

        // Calculate target dimensions
        int targetWidth, targetHeight;
        if (bitmap.getWidth() > bitmap.getHeight()) {
            targetWidth = maxWidth;
            targetHeight = (int) (targetWidth / aspectRatio);
        } else {
            targetHeight = maxHeight;
            targetWidth = (int) (targetHeight * aspectRatio);
        }

        // Resize the bitmap
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, true);

        // Convert the resized bitmap to a byte array
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        resizedBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }


    public static Bitmap getBitmapFromByteArray(byte[] imageData) {
        if (imageData != null && imageData.length > 0) {
            return BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
        } else {
            return null; // or handle error appropriately
        }
    }

    public static Bitmap resizeBitmap(Bitmap originalBitmap, int newWidth, int newHeight) {
        int width = originalBitmap.getWidth();
        int height = originalBitmap.getHeight();

        // Calculate the scale factor
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // Create a matrix for the manipulation
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        // Resize the bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(originalBitmap, 0, 0, width, height, matrix, false);

        // Recycle the original bitmap if necessary
        if (resizedBitmap != originalBitmap) {
            originalBitmap.recycle();
        }

        return resizedBitmap;
    }

}
