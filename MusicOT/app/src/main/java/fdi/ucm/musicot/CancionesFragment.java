package fdi.ucm.musicot;

import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
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

import fdi.ucm.musicot.Misc.Utils;
import fdi.ucm.musicot.Modelo.Cancion;
import fdi.ucm.musicot.Modelo.DAO;
import fdi.ucm.musicot.Modelo.RetenCanciones;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link CancionesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CancionesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private MenuActivity menuActivity;

    RetenCanciones retenCanciones;
    Cancion[] listaCanciones;
    TableLayout mContieneCanciones;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CancionesFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CancionesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CancionesFragment newInstance(String param1, String param2) {
        CancionesFragment fragment = new CancionesFragment();
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

        int maxColumnas = 3;
        listaCanciones = DAO.canciones;

        View view = inflater.inflate(R.layout.fragment_canciones, null, false);
        //---------------- A partir de aqui no es código automático
        // Uso del fragmento RetenCanciones para cuando ocurra un cambio de orientación, etc.
        FragmentManager fm = getFragmentManager();
        retenCanciones = (RetenCanciones) fm.findFragmentByTag("cancionesRetenidas");
        if (retenCanciones == null){
            retenCanciones = new RetenCanciones();
            fm.beginTransaction().add(retenCanciones, "cancionesRetenidas").commit();
            retenCanciones.setCancionesRetenidas(listaCanciones);
        }

        //Rellenamos con todas las canciones disponibles en la aplicación
        mContieneCanciones = (TableLayout) view.findViewById(R.id.contenedor_canciones);
        mContieneCanciones.setStretchAllColumns(true);
        listaCanciones = MenuActivity.dao.getListaCanciones();

        TableRow fila = new TableRow(menuActivity);
        mContieneCanciones.addView(fila);
        byte i = 1;
        fila.addView(generateLinearCanciones(listaCanciones[0]));
        for (Cancion tema: listaCanciones) {
            if (i < maxColumnas){
                i++;
            }
            else {
                i = 1;
                fila = new TableRow(menuActivity);
                mContieneCanciones.addView(fila);
            }
            fila.addView(generateLinearCanciones(tema));
        }

        return view;
    }

//////////////////////////////


    /**
     * Convierte el objeto Canción introducido en un formato XML de layout para presentarlo en TableLayout
     *
     * @param cancion
     * @return
     */
    private LinearLayout generateLinearCanciones(Cancion cancion){

        LinearLayout linearLayout = new LinearLayout(menuActivity);

        linearLayout.setOrientation(LinearLayout.VERTICAL);


        //----- ImageView -----
        ImageView imageView = new ImageView(menuActivity);

        imageView.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, Utils.convertirAdp(50, menuActivity))
        );
        imageView.setImageDrawable(
                getResources().getDrawable(R.drawable.ic_menu_temas));
        // TODO Averiguar como funciona lo de poner imagenes en el ImageView.
        // TODO Hacer que las imágenes salgan cuadradas.

        //----- TextView -----
        TextView textView = new TextView(menuActivity);

        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, Utils.convertirAdp(20, menuActivity)
        );
        textParams.gravity = Gravity.CENTER;

        textView.setLayoutParams( textParams );

        textView.setText(cancion.getTitulo());
        textView.setBackgroundColor(Color.RED);

        //----- TextViewAlbum -----
        TextView textViewAlbum = new TextView(menuActivity);

        LinearLayout.LayoutParams textParamsAlbum = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, Utils.convertirAdp(20, menuActivity)
        );
        textParamsAlbum.gravity = Gravity.CENTER;

        textViewAlbum.setLayoutParams( textParamsAlbum );

        textViewAlbum.setText(cancion.getAlbum().getTitulo());
        textViewAlbum.setBackgroundColor(Color.GREEN);

        //Se añaden los componentes al LinearLayout
        linearLayout.addView(imageView);
        linearLayout.addView(textView);
        linearLayout.addView(textViewAlbum);

        linearLayout.setBackgroundColor(Color.CYAN);


        // Creación de un mensaje de alerta:
        /*linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(CancionesActivity.this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("cancion pulsada. Bieeeeen!!");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });*/


        return linearLayout;
    }

    /*
    Guarda la lista de canciones ante la destrucción o el reinicio de la actividad.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        retenCanciones.setCancionesRetenidas(listaCanciones);
    }


    /////////////////////////
    /// SETTERS & GETTERS ///
    /////////////////////////


    public void setMenuActivity(MenuActivity menuActivity) {
        this.menuActivity = menuActivity;
    }
}
