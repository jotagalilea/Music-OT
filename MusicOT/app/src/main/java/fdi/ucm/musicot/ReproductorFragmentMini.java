package fdi.ucm.musicot;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.example.usuario_local.music_ot.R;

import fdi.ucm.musicot.Modelo.Reproductor;

/**
 * Created by DeekinIII on 18/05/2017.
 */

public class ReproductorFragmentMini extends Fragment {

    public static boolean deployed;
    Reproductor reproductor;
    public static ProgressBar progressBar;
    public static ImageButton butonPlay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmen_reproduccion_mini, container, false);

        reproductor = MenuActivity.reproductor;

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        butonPlay = (ImageButton) view.findViewById(R.id.mini_P);

        butonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reproductor.botonReproducirCancion();
            }
        });

        return view;
    }

    public void setRepFrag(Reproductor repFrag){
        this.reproductor = repFrag;
    }
}

