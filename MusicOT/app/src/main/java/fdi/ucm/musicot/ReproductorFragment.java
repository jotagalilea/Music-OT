package fdi.ucm.musicot;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.usuario_local.music_ot.R;

import java.io.IOException;

import fdi.ucm.musicot.Misc.PaqueteCancionMedia;
import fdi.ucm.musicot.Modelo.Cancion;
import fdi.ucm.musicot.Modelo.Reproductor;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class ReproductorFragment extends Fragment {

    ImageButton botonPlay;
    ImageButton botonNextSong;
    ImageButton botonPrevSong;
    TextView textviewTituloCancion;
    Reproductor reproductor;
    public static ProgressBar progressBar;

    public ReproductorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reproductor, container, false);

        reproductor = MenuActivity.reproductor;

        progressBar = (ProgressBar)view.findViewById(R.id.reproductorBar);
        progressBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    System.out.println("Touch coordinates : " +
                            String.valueOf(motionEvent.getX()) + "x" + String.valueOf(motionEvent.getY()));
                }
                return true;
            }
        });

        botonPlay = (ImageButton)view.findViewById(R.id.reproductorButtonPlay);
        botonNextSong = (ImageButton)view.findViewById(R.id.reproductorButtonNextSong);
        botonPrevSong = (ImageButton)view.findViewById(R.id.reproductorButtonPrevSong);
        textviewTituloCancion = (TextView) view.findViewById(R.id.reproductorTituloCancionRep);

        botonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reproductor.botonReproducirCancion();
            }
        });
        botonPrevSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reproductor.botonPrevSong();
            }
        });
        botonNextSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reproductor.botonNextSong();
            }
        });

        actualizaDatosCancion(Reproductor.currentSong);

        return view;

    }

    public void actualizaDatosCancion(PaqueteCancionMedia cancion){

        String subStr = cancion.getCancionData().getTitulo();

        progressBar.setMax(Reproductor.currentSong.getMedia().getDuration());
        progressBar.setProgress(Reproductor.currentSong.getMedia().getCurrentPosition());

        MenuActivity.fragmentMini.progressBar.setMax(Reproductor.currentSong.getMedia().getDuration());
        MenuActivity.fragmentMini.progressBar.setProgress(Reproductor.currentSong.getMedia().getCurrentPosition());

        updatePlayButton();

        if(subStr.length() > 15){
            subStr = subStr.substring(0, 25) + "...";
        }

        textviewTituloCancion.setText(subStr);
        MenuActivity.fragmentMini.tituloView.setText(subStr);

    }

    public void updatePlayButton() {

        this.botonPlay.setImageResource(!Reproductor.isPlaying?R.drawable.ic_rep_play_button:R.drawable.ic_rep_pause);
        ReproductorFragmentMini.butonPlay.setImageResource(!Reproductor.isPlaying?R.drawable.ic_rep_play_button:R.drawable.ic_rep_pause);
    }

///// CODIGO REPRODUCTOR ////

    public void setReproductor(Reproductor reproductor){
        this.reproductor = reproductor;
    }
}
