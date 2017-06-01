package fdi.ucm.musicot;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.usuario_local.music_ot.R;

import fdi.ucm.musicot.Observers.DatosCancionEventHandler;
import fdi.ucm.musicot.Modelo.Reproductor;

import static fdi.ucm.musicot.MenuActivity.reproductor;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class ReproductorFragment extends Fragment implements DatosCancionEventHandler {

    ImageButton botonPlay;
    ImageButton botonNextSong;
    ImageButton botonPrevSong;
    TextView textviewTituloCancion;
    TextView textViewAlbumCancion;
    TextView textViewArtistaCancion;
    ImageView imagenReproductor;
    public static SeekBar progressBar;

    public ReproductorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reproductor, container, false);

        progressBar = (SeekBar) view.findViewById(R.id.reproductorBar);

        botonPlay = (ImageButton)view.findViewById(R.id.reproductorButtonPlay);
        botonNextSong = (ImageButton)view.findViewById(R.id.reproductorButtonNextSong);
        botonPrevSong = (ImageButton)view.findViewById(R.id.reproductorButtonPrevSong);
        textviewTituloCancion = (TextView) view.findViewById(R.id.reproductorTituloCancionRep);
        textViewAlbumCancion = (TextView) view.findViewById(R.id.reproductorAlbumCancionRep);
        textViewArtistaCancion = (TextView) view.findViewById(R.id.reproductorArtistaCancionRep);
        imagenReproductor = (ImageView) view.findViewById(R.id.imagenReproductor);

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

        //Reacción del programa cuando se pulsa el SeekBar
        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                reproductor.pulsaSeekBar(seekBar, i, b);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        MenuActivity.observer.actualizaDatosCancion();

        return view;

    }

    public void actualizaDatosCancion(){

        if(progressBar != null) {
            String subStr = Reproductor.currentSong.getCancionData().getTitulo();

            progressBar.setMax(Reproductor.currentSong.getMedia().getDuration());
            progressBar.setProgress(Reproductor.currentSong.getMedia().getCurrentPosition());

            if (Reproductor.currentSong.cancionData.getAlbum().getCaratula() != null) {
                MenuActivity.fragmentReproductor.imagenReproductor.setImageBitmap(Reproductor.currentSong.cancionData.getAlbum().getCaratula());
            }

            MenuActivity.observer.updatePlayButton();

            textviewTituloCancion.setText(subStr);
            textViewAlbumCancion.setText(Reproductor.currentSong.getCancionData().getAlbum().getTitulo());
            textViewArtistaCancion.setText(Reproductor.currentSong.getCancionData().getArtista().getNombre());
        }
    }

    public void updatePlayButton() {

        if(botonPlay != null) {
            botonPlay.setImageResource(!Reproductor.isPlaying ? R.drawable.ic_rep_play_button : R.drawable.ic_rep_pause);
        }
    }

///// CODIGO REPRODUCTOR ////
}
