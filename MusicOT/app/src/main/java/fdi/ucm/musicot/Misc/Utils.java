package fdi.ucm.musicot.Misc;

/**
 * Created by Javier on 27/04/2017.
 */

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;

import java.io.File;


/**
 * Clase pensada para almacenar todas las funciones que no tienen lugar en otro sitio
 */
public class Utils {

    public static Fragment currentFragment;
    public static Fragment currentMiniFragment;

    public static boolean standNightmode;
    public static boolean automaticNightmode;

    /**
     * Convierte el numero introducido a DP.
     * num = numero que se quiere pasar a DP
     * item = objeto AppCompatActivity desde el que se invoca la funcion ("this" la mayoria de las veces)
     * @param num
     * @param item
     * @return
     */
    public static int convertirAdp(int num, AppCompatActivity item){

        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, num, item.getResources().getDisplayMetrics());
    }

    /**
     * Obtiene la dirección del sdcard en el dispositivo que se encuentra abierto en ese momento.
     * @return
     */
    public static File parseMountDirectory() {
        File dir_00 = new File("/storage/extSdCard");
        File dir_01 = new File("/storage/sdcard1");
        File dir_1 = new File("/storage/usbcard1");
        File dir_2 = new File("/storage/sdcard0");
        File dir_3 = new File("/mnt/sdcard");
        return dir_01.exists() ? dir_01 : dir_00.exists() ? dir_00 :
                dir_1.exists() ? dir_1 : dir_2.exists() ? dir_2 : dir_3.exists() ? dir_3 :
                        null;
    }


    // Métodos para la decodficación de las carátulas:

    public static Bitmap decodeSampledBitmapFromByteArray(byte[] res,
                                                         int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(res, 0, res.length, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(res, 0, res.length, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}

