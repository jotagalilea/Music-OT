package fdi.ucm.musicot;

import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.example.usuario_local.music_ot.R;

import fdi.ucm.musicot.Modelo.Album;
import fdi.ucm.musicot.Modelo.Artista;
import fdi.ucm.musicot.Observers.OnKeyUpEventHandler;
import fdi.ucm.musicot.Misc.Utils;
import fdi.ucm.musicot.Modelo.Cancion;
import fdi.ucm.musicot.Modelo.DAO;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusquedaFragment extends Fragment implements OnKeyUpEventHandler {

    View view;
    LinearLayout cancionResults;
    LinearLayout albumResults;
    LinearLayout artistResults;
    EditText searchInputText;

    public BusquedaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_busqueda, container, false);

        cancionResults = (LinearLayout) view.findViewById(R.id.fragment_search_song_result);
        albumResults = (LinearLayout) view.findViewById(R.id.fragment_search_album_result);
        artistResults = (LinearLayout) view.findViewById(R.id.fragment_search_artist_result);
        searchInputText = (EditText) view.findViewById(R.id.fragment_search_editText);

        searchInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                MenuActivity.observer.onTextModified();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }


    @Override
    public void keyPressed(int keyCode) {
        //VACIO//
    }

    @Override
    public void textModified() {

        if(Utils.currentFragment.equals(MenuActivity.fragmentBusqueda)){

            LinearLayout line;
            LinearLayout.LayoutParams lineParams = new TableLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lineParams.setMargins(2, 5, 2, 5);

            this.cancionResults.removeAllViews();
            this.albumResults.removeAllViews();
            this.artistResults.removeAllViews();

            for (Cancion cancion: DAO.getCanciones()) {

                if(cancion.getTitulo().toLowerCase().contains(this.searchInputText.getText().toString().toLowerCase())){

                    line = CancionesFragment.generateLinearCanciones(cancion);
                    //line.setBackgroundResource(R.drawable.listabackground);
                    line.setLayoutParams(lineParams);
                    cancionResults.addView(line);

                }
            }

            for (Album album: DAO.getAlbumes()) {

                if(album.getTitulo().toLowerCase().contains(this.searchInputText.getText().toString().toLowerCase())){


                    line = AlbumesFragment.generateLinearAlbumes(album, MenuActivity.menuActivity);
                    //line.setBackgroundResource(R.drawable.listabackground);
                    line.setLayoutParams(lineParams);
                    albumResults.addView(line);

                }
            }

            for (Artista artista: DAO.getArtistas()) {

                if(artista.getNombre().toLowerCase().contains(this.searchInputText.getText().toString().toLowerCase())){

                    line = ArtistasFragment.generateLinearArtista(artista, MenuActivity.menuActivity);
                    //line.setBackgroundResource(R.drawable.listabackground);
                    line.setLayoutParams(lineParams);
                    artistResults.addView(line);
                }
            }
        }
    }
}
