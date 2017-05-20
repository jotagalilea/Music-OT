package fdi.ucm.musicot.Misc;

/**
 * Created by Javier on 27/04/2017.
 */

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;

import java.io.File;


/**
 * Clase pensada para almacenar todas las funciones que no tienen lugar en otro sitio
 */
public class Utils {

    public static Fragment currentFragment = null;
    public static Fragment currentMiniFragment;

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



}

