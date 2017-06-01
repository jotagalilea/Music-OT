package fdi.ucm.musicot;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.usuario_local.music_ot.R;

import fdi.ucm.musicot.Misc.OnKeyDownEventHandler;
import fdi.ucm.musicot.Misc.Utils;
import fdi.ucm.musicot.Modelo.Cancion;
import fdi.ucm.musicot.Modelo.DAO;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusquedaFragment extends Fragment  implements OnKeyDownEventHandler{

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

        //cancionResults = (LinearLayout) view.findViewById(R.id.fragment_search_song_result);
        albumResults = (LinearLayout) view.findViewById(R.id.fragment_search_album_result);
        artistResults = (LinearLayout) view.findViewById(R.id.fragment_search_artist_result);
        searchInputText = (EditText) view.findViewById(R.id.fragment_search_editText);



        return view;
    }


    @Override
    public void keyPressed(int keyCode) {

        if(Utils.currentFragment.equals(this)){

            this.cancionResults.removeAllViews();
            this.albumResults.removeAllViews();
            this.artistResults.removeAllViews();

            for (Cancion cancion: DAO.getCanciones()) {

                if(cancion.getTitulo().contains(this.searchInputText.getText())){


                }
            }
        }
    }


}
