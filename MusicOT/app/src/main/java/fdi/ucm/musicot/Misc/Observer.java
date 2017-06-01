package fdi.ucm.musicot.Misc;

import android.view.KeyEvent;

import java.util.ArrayList;

/**
 * Created by Javier on 01/06/2017.
 */

public class Observer {

    ArrayList<OnKeyDownEventHandler> keyDownEventHandlers;
    ArrayList<DatosCancionEventHandler> datosCancionEventHandlers;

    public Observer(){

        keyDownEventHandlers = new ArrayList<OnKeyDownEventHandler>();
        datosCancionEventHandlers = new ArrayList<DatosCancionEventHandler>();
    }

    public void addToList(OnKeyDownEventHandler onKeyDownEventHandler){

        keyDownEventHandlers.add(onKeyDownEventHandler);
    }

    public void addToList(DatosCancionEventHandler datosCancionEventHandler){

        datosCancionEventHandlers.add(datosCancionEventHandler);
    }

    ////////////////////////////
    /////// OBSERVADORES ///////
    ////////////////////////////

    public void onKeyDown(int keyCode) {

        for (OnKeyDownEventHandler instance: keyDownEventHandlers) {

            instance.keyPressed(keyCode);
        }
    }

    public void actualizaDatosCancion(){

        for (DatosCancionEventHandler datos: datosCancionEventHandlers) {

            datos.actualizaDatosCancion();
            datos.updatePlayButton();
        }
    }

    public void updatePlayButton(){

        for (DatosCancionEventHandler datos: datosCancionEventHandlers) {

            datos.updatePlayButton();
        }
    }
}
