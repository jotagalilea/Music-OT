package fdi.ucm.musicot;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.usuario_local.music_ot.R;

import java.util.ArrayList;

import fdi.ucm.musicot.Misc.Utils;
import fdi.ucm.musicot.Modelo.Album;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link AlbumesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlbumesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TableLayout mContieneAlbumes;
    private MenuActivity menuActivity;

    public AlbumesFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AlbumesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AlbumesFragment newInstance(String param1, String param2) {
        AlbumesFragment fragment = new AlbumesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_albumes, null, false);

        mContieneAlbumes = (TableLayout) view.findViewById(R.id.contenedor_albumes);

        ArrayList<Album> listaAlbumes = MenuActivity.dao.getListaAlbumes();

        mContieneAlbumes.setStretchAllColumns(true);

        TableRow fila = new TableRow(menuActivity);
        mContieneAlbumes.addView(fila);
        byte i = 0;

        for (Album album: listaAlbumes) {
            if (i < 3){
                i++;
            }
            else {
                i = 1;
                fila = new TableRow(menuActivity);
                mContieneAlbumes.addView(fila);
            }
            fila.addView(generateLinearAlbumes(album, menuActivity));
        }

        return view;
    }

    /**
     * Convierte el objeto Canción introducido en un formato XML de layout para presentarlo en GridLayout
     *
     * @param album
     * @return
     */
    private LinearLayout generateLinearAlbumes(final Album album, MenuActivity menuActivity){

        LinearLayout linearLayout = new LinearLayout(menuActivity);

        linearLayout.setOrientation(LinearLayout.VERTICAL);

        //----- ImageView -----
        ImageView imageView = new ImageView(menuActivity);

        imageView.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, Utils.convertirAdp(50, menuActivity))
        );
        Bitmap cover = album.getCaratula();
        if (cover != null)
            imageView.setImageBitmap(cover);
        else
            imageView.setImageDrawable(
                    getResources().getDrawable(R.drawable.ic_menu_temas));

        // TODO Hacer que las imágenes salgan cuadradas.

        //----- TextView -----
        TextView textView = new TextView(menuActivity);

        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, Utils.convertirAdp(20, menuActivity)
        );
        textParams.gravity = Gravity.CENTER;

        textView.setLayoutParams( textParams );

        textView.setText(album.getTitulo());
        textView.setBackgroundColor(Color.RED);

        //Se añaden los componentes al LinearLayout
        linearLayout.addView(imageView);
        linearLayout.addView(textView);

        linearLayout.setBackgroundColor(Color.CYAN);

        // Creación de un mensaje de alerta:
        linearLayout.setOnClickListener(new View.OnClickListener() {
            Album alb;
            @Override
            public void onClick(View view) {
                /*AlertDialog alertDialog = new AlertDialog.Builder(CancionesActivity.this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("cancion pulsada. Bieeeeen!!");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();*/

                alb = album;

                MenuActivity.reproductor.rellenarLista(alb);
                MenuActivity.fragmentReproductor.actualizaDatosCancion();

                MenuActivity.menuActivity.transicionarMenuFragmento(R.id.fragment_contentmenu1, MenuActivity.fragmentReproductor);
            }
        });

        return linearLayout;
    }

    /////////////////////////
    /// SETTERS & GETTERS ///
    /////////////////////////

    public void setMenuActivity(MenuActivity menuActivity) {
        this.menuActivity = menuActivity;
    }
}

