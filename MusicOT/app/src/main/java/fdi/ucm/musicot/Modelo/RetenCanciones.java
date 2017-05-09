package fdi.ucm.musicot.Modelo;

import android.app.Fragment;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by Julio on 09/05/2017.
 */

public class RetenCanciones extends Fragment {

    private Cancion[] cancionesRetenidas;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    public void setCancionesRetenidas(Cancion[] data) {
        this.cancionesRetenidas = data;
    }

    public Cancion[] getCancionesRetenidas() {
        return this.cancionesRetenidas;
    }


}
