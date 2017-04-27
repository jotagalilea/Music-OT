package fdi.ucm.musicot.Misc;

/**
 * Created by Javier on 27/04/2017.
 */

import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;

/**
 * Clase pensada para almacenar todas las funciones que no tienen lugar en otro sitio
 */
public class Utils {

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
}
