package fdi.ucm.musicot.Observers;

import java.util.ArrayList;

/**
 * Created by Javier on 01/06/2017.
 */

public class Observer {

    ArrayList<OnKeyUpEventHandler> keyUpEventHandlers;
    ArrayList<DatosCancionEventHandler> datosCancionEventHandlers;

    public Observer(){

        keyUpEventHandlers = new ArrayList<OnKeyUpEventHandler>();
        datosCancionEventHandlers = new ArrayList<DatosCancionEventHandler>();
    }

    public void addToList(OnKeyUpEventHandler onKeyUpEventHandler){

        keyUpEventHandlers.add(onKeyUpEventHandler);
    }

    public void addToList(DatosCancionEventHandler datosCancionEventHandler){

        datosCancionEventHandlers.add(datosCancionEventHandler);
    }

    ////////////////////////////
    /////// OBSERVADORES ///////
    ////////////////////////////

    public void onKeyUp(int keyCode) {

        for (OnKeyUpEventHandler instance: keyUpEventHandlers) {

            instance.keyPressed(keyCode);
        }
    }

    public void onTextModified() {

        for (OnKeyUpEventHandler instance: keyUpEventHandlers) {

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
