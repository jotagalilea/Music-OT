package fdi.ucm.musicot;

import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
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
import fdi.ucm.musicot.Modelo.Cancion;
import fdi.ucm.musicot.Modelo.DAO;
import fdi.ucm.musicot.Modelo.RetenCanciones;
import fdi.ucm.musicot.Observers.OnNightModeEvent;

import static fdi.ucm.musicot.MenuActivity.menuActivity;
import static fdi.ucm.musicot.MenuActivity.observer;

/**
 * Fragmento encargado de la lista de canciones en disco
 */
public class CancionesFragment extends Fragment implements OnNightModeEvent{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RetenCanciones retenCanciones;
    ArrayList<Cancion> listaCanciones;
    TableLayout mContieneCanciones;

    public static ArrayList<LinearLayout> filasContenedor = new ArrayList<>();
    public static ArrayList<TableRow> tableRows = new ArrayList<>();
    public static ArrayList<TextView> tituloAlbum = new ArrayList<>();
    public static ArrayList<TextView> tituloCancion = new ArrayList<>();
    public static ArrayList<TextView> tituloArtist = new ArrayList<>();

    private String mParam1;
    private String mParam2;

    public CancionesFragment() {
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

        int maxColumnas = 1;
        listaCanciones = DAO.getCanciones();

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
        TableLayout.LayoutParams tableParams;

        tableParams = new TableLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tableParams.setMargins(2, 5, 2, 0);

        fila.setLayoutParams(tableParams);
        fila.setBackgroundResource(R.drawable.listabackground);
        tableRows.add(fila);
        mContieneCanciones.addView(fila);
        byte i = 1;
        fila.addView(generateLinearCanciones(listaCanciones.get(0),true));
        for (Cancion tema: listaCanciones) {
            if (i < maxColumnas){
                i++;
            }
            else {
                i = 1;
                fila = new TableRow(menuActivity);
                fila.setBackgroundResource(R.drawable.listabackground);
                fila.setLayoutParams(tableParams);
                tableRows.add(fila);
                mContieneCanciones.addView(fila);
            }
            fila.addView(generateLinearCanciones(tema, true));
        }

        if(observer.getNightMode()){
            toNightMode();
        }else{
            toDayMode();
        }

        return view;
    }

    /**
     * Convierte el objeto Canción introducido en un formato XML de layout para presentarlo en TableLayout
     *
     * @param cancion
     * @return
     */
    public static LinearLayout generateLinearCanciones(final Cancion cancion,boolean almacenar){

        LinearLayout linearLayout = new LinearLayout(menuActivity);
        LinearLayout linearLayoutInternal = new LinearLayout(menuActivity);

        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayoutInternal.setOrientation(LinearLayout.VERTICAL);
        //----- ImageView -----
        ImageView imageView = new ImageView(menuActivity);

        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, Utils.convertirAdp(50, menuActivity)
        );
        imageParams.width = Utils.convertirAdp(50,menuActivity);
        imageParams.height = Utils.convertirAdp(50,menuActivity);
        imageParams.leftMargin = Utils.convertirAdp(5,menuActivity);
        imageParams.rightMargin = Utils.convertirAdp(5,menuActivity);

        imageView.setLayoutParams(imageParams);
        Bitmap cover = cancion.getAlbum().getCaratula();
        if (cover != null)
            imageView.setImageBitmap(cover);
        else
            imageView.setImageResource(R.drawable.ic_menu_temas);

        //----- TextView -----
        TextView textView = new TextView(menuActivity);

        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, Utils.convertirAdp(20, menuActivity)
        );
        textParams.gravity = Gravity.LEFT;

        textView.setLayoutParams( textParams );

        textView.setText(cancion.getTitulo());
        textView.setTextSize(17);
        textView.setTypeface(null, Typeface.BOLD);

        textView.setSingleLine(true);
        textView.setEllipsize(TextUtils.TruncateAt.END);

        //----- TextViewAlbum -----
        TextView textViewAlbum = new TextView(menuActivity);

        LinearLayout.LayoutParams textParamsAlbum = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, Utils.convertirAdp(20, menuActivity)
        );

        textViewAlbum.setLayoutParams( textParamsAlbum );

        textViewAlbum.setText(cancion.getAlbum().getTitulo());
        textViewAlbum.setSingleLine(true);
        textViewAlbum.setEllipsize(TextUtils.TruncateAt.END);
        textViewAlbum.setTypeface(null, Typeface.ITALIC);
        textViewAlbum.setTextSize(13);

        //----- TextViewArtist -----

        TextView textViewArtist = new TextView(menuActivity);

        LinearLayout.LayoutParams textParamsArtist = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, Utils.convertirAdp(20, menuActivity)
        );
        textParamsArtist.gravity = Gravity.LEFT;

        textViewArtist.setLayoutParams(textParamsArtist);

        textViewArtist.setText(cancion.getArtista().getNombre());
        textViewArtist.setSingleLine(true);
        textViewArtist.setEllipsize(TextUtils.TruncateAt.END);
        textViewArtist.setTypeface(null, Typeface.ITALIC);
        textViewArtist.setTextSize(13);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = MenuActivity.reproductor.rellenarLista(cancion);
                MenuActivity.reproductor.setCurrentSongDeCancion(cancion, i);

                MenuActivity.observer.actualizaDatosCancion();

                menuActivity.cambiaFragment(R.id.fragment_contentmenu1, MenuActivity.fragmentReproductor);
            }
        });

        //Se añaden los componentes al LinearLayout
        linearLayout.addView(imageView);
        linearLayoutInternal.addView(textView);
        linearLayoutInternal.addView(textViewAlbum);
        linearLayoutInternal.addView(textViewArtist);
        linearLayout.addView(linearLayoutInternal);

        linearLayout.setBackgroundColor(Color.CYAN);

        if(almacenar) {
            filasContenedor.add(linearLayout);
            tituloAlbum.add(textViewAlbum);
            tituloCancion.add(textView);
            tituloArtist.add(textViewArtist);
        }

        if(observer.getNightMode()){
            linearLayout.setBackgroundColor(menuActivity.getResources().getColor(R.color.backgroundColorFilas_noct));
            textView.setTextColor(menuActivity.getResources().getColor(R.color.colorTituloTextRep_noct));
            textViewAlbum.setTextColor(menuActivity.getResources().getColor(R.color.colorAlbumTextRep_noct));
            textViewArtist.setTextColor(menuActivity.getResources().getColor(R.color.colorAlbumTextRep_noct));
        } else{
            linearLayout.setBackgroundColor(menuActivity.getResources().getColor(R.color.backgroundColorFilas));
            textView.setTextColor(menuActivity.getResources().getColor(R.color.colorTituloTextRep));
            textViewAlbum.setTextColor(menuActivity.getResources().getColor(R.color.colorAlbumTextRep));
            textViewArtist.setTextColor(menuActivity.getResources().getColor(R.color.colorAlbumTextRep));
        }

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

    //// OnNightModeEvent
    
    @Override
    public void toNightMode() {
        for (LinearLayout line: filasContenedor) {
            line.setBackgroundColor(menuActivity.getResources().getColor(R.color.backgroundColorFilas_noct));
        }
        for (TextView titulo: tituloCancion) {
            titulo.setTextColor(menuActivity.getResources().getColor(R.color.colorTituloTextRep_noct));
        }
        for (TextView album: tituloAlbum) {
            album.setTextColor(menuActivity.getResources().getColor(R.color.colorAlbumTextRep_noct));
        }
        for (TextView artist: tituloArtist) {
            artist.setTextColor(menuActivity.getResources().getColor(R.color.colorAlbumTextRep_noct));
        }
    }

    @Override
    public void toDayMode() {
        for (LinearLayout line: filasContenedor) {
            line.setBackgroundColor(menuActivity.getResources().getColor(R.color.backgroundColorFilas));
        }
        for (TextView titulo: tituloCancion) {
            titulo.setTextColor(menuActivity.getResources().getColor(R.color.colorTituloTextRep));
        }
        for (TextView album: tituloAlbum) {
            album.setTextColor(menuActivity.getResources().getColor(R.color.colorAlbumTextRep));
        }
        for (TextView artist: tituloArtist) {
            artist.setTextColor(menuActivity.getResources().getColor(R.color.colorAlbumTextRep));
        }
    }

    /////////////////////////
    /// SETTERS & GETTERS ///
    /////////////////////////
}
