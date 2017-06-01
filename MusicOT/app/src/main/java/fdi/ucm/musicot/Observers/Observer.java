package fdi.ucm.musicot.Observers;

import java.util.ArrayList;

/**
 * Created by Javier on 01/06/2017.
 */


public class Observer {

    ArrayList<OnKeyEventHandler> keyEventHandlers;
    ArrayList<DatosCancionEventHandler> datosCancionEventHandlers;

    public Observer(){

        keyEventHandlers = new ArrayList<OnKeyEventHandler>();
        datosCancionEventHandlers = new ArrayList<DatosCancionEventHandler>();
    }

    public void addOnKeyEventHandler(OnKeyEventHandler onKeyEventHandler){

        keyEventHandlers.add(onKeyEventHandler);
    }

    public void addDatosCancionEventHandler(DatosCancionEventHandler datosCancionEventHandler){

        datosCancionEventHandlers.add(datosCancionEventHandler);
    }

    ////////////////////////////
    /////// OBSERVADORES ///////
    ////////////////////////////

    public void onKeyUp(int keyCode) {

        for (OnKeyEventHandler instance: keyEventHandlers) {

            instance.keyPressed(keyCode);
        }
    }

    public void onTextModified() {

        for (OnKeyEventHandler instance: keyEventHandlers) {

            instance.textModified();
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
