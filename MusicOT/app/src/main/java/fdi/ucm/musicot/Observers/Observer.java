package fdi.ucm.musicot.Observers;

import java.util.ArrayList;

/**
 * Created by Javier on 01/06/2017.
 */


public class Observer {

    ArrayList<OnKeyEventHandler> keyEventHandlers;
    ArrayList<DatosCancionEventHandler> datosCancionEventHandlers;
    ArrayList<OnNightModeEvent> onNightModeEvents;

    public Observer(){

        keyEventHandlers = new ArrayList<OnKeyEventHandler>();
        datosCancionEventHandlers = new ArrayList<DatosCancionEventHandler>();
        onNightModeEvents = new ArrayList<OnNightModeEvent>();
    }

    public void addOnKeyEventHandler(OnKeyEventHandler onKeyEventHandler){

        keyEventHandlers.add(onKeyEventHandler);
    }

    public void addDatosCancionEventHandler(DatosCancionEventHandler datosCancionEventHandler){
        datosCancionEventHandlers.add(datosCancionEventHandler);
    }

    public void addOnNightModeEvent(OnNightModeEvent onNightModeEvent){
        onNightModeEvents.add(onNightModeEvent);
    }
    ////////////////////////////
    /////// OBSERVADORES ///////
    ////////////////////////////

    //// OnKeyEventHandler
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

    //// DatosCancionEventHandler
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

    ////OnNightModeEvent

    // DAO.configuracion.onNightMode
    public boolean onNightMode = false;

    public boolean getNightMode(){
        return onNightMode;
    }

    public void setNightMode(boolean nightMode){
        onNightMode = nightMode;
    }

    public void toNightMode(){
        onNightMode = true;
        for (OnNightModeEvent onNight: onNightModeEvents) {
            onNight.toNightMode();
        }
    }

    public void toDayMode(){
        onNightMode = false;
        for (OnNightModeEvent onNight: onNightModeEvents) {
            onNight.toDayMode();
        }
    }

    public void switchNightmode(){
        if(onNightMode){
            toDayMode();
        }else{
            toNightMode();
        }
    }
}
