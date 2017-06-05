package fdi.ucm.musicot;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
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

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

import fdi.ucm.musicot.Misc.Utils;
import fdi.ucm.musicot.Modelo.Album;
import fdi.ucm.musicot.Observers.OnNightModeEvent;

import static fdi.ucm.musicot.MenuActivity.menuActivity;

/**
 * Fragmento encargado de la lista de albumes en disco
 */
public class AlbumesFragment extends Fragment implements OnNightModeEvent{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public static ArrayList<TextView> textViews = new ArrayList<>();
    public static ArrayList<TextView> albumTexts = new ArrayList<>();
    public static ArrayList<LinearLayout> linearLayouts = new ArrayList<>();

    private TableLayout mContieneAlbumes;

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
        fila.setBackgroundResource(R.drawable.listabackground);
        TableLayout.LayoutParams tableParams;

        tableParams = new TableLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tableParams.setMargins(2, 5, 2, 0);

        fila.setLayoutParams(tableParams);
        mContieneAlbumes.addView(fila);
        byte i = 0;

        for (Album album: listaAlbumes) {
            if (i < 1){
                i++;
            }
            else {
                i = 1;
                fila = new TableRow(menuActivity);
                fila.setBackgroundResource(R.drawable.listabackground);
                fila.setLayoutParams(tableParams);
                mContieneAlbumes.addView(fila);
            }
            fila.addView(generateLinearAlbumes(album,true));
        }

        return view;
    }

    /**
     * Convierte el objeto Canción introducido en un formato XML de layout para presentarlo en GridLayout
     *
     * @param album
     * @return
     */
    public static LinearLayout generateLinearAlbumes(final Album album,boolean almacenar){

        LinearLayout linearLayout = new LinearLayout(menuActivity);
        LinearLayout linearLayoutText = new LinearLayout(menuActivity);

        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setPadding(3,0,0,0);

        linearLayoutText.setOrientation(LinearLayout.VERTICAL);

        //----- ImageView -----
        ImageView imageView = new ImageView(menuActivity);

        LinearLayout.LayoutParams paramsImageView = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, Utils.convertirAdp(50, menuActivity));

        paramsImageView.width = Utils.convertirAdp(50,menuActivity);
        paramsImageView.height = Utils.convertirAdp(50,menuActivity);
        paramsImageView.leftMargin = Utils.convertirAdp(5,menuActivity);
        paramsImageView.rightMargin = Utils.convertirAdp(5,menuActivity);

        imageView.setLayoutParams(paramsImageView);
        Bitmap cover = album.getCaratula();
        if (cover != null) {
            imageView.setImageBitmap(cover);
        }else {
            imageView.setImageResource(R.drawable.ic_menu_temas);
        }

        // TODO Hacer que las imágenes salgan cuadradas.

        //----- TextView -----
        TextView textView = new TextView(menuActivity);

        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, Utils.convertirAdp(20, menuActivity)
        );

        textView.setLayoutParams( textParams );

        textView.setText(album.getTitulo());
        textView.setTextSize(17);
        textView.setTypeface(null, Typeface.BOLD);

        textView.setSingleLine(true);
        textView.setEllipsize(TextUtils.TruncateAt.END);

        //----- TextViewArtista -----
        TextView textViewArtista = new TextView(menuActivity);

        LinearLayout.LayoutParams textParamsArtista = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, Utils.convertirAdp(20, menuActivity)
        );
        textParams.gravity = Gravity.CENTER;

        textViewArtista.setLayoutParams( textParamsArtista );

        textViewArtista.setText(album.getArtista().getNombre());
        textViewArtista.setTextSize(13);
        //textViewArtista.setBackgroundColor(Color.YELLOW);

        textViewArtista.setSingleLine(true);
        textViewArtista.setEllipsize(TextUtils.TruncateAt.END);
        textViewArtista.setTypeface(null, Typeface.ITALIC);
        //textViewArtista.setTextColor(Color.GRAY);

        //Se añaden los componentes al LinearLayout
        linearLayout.addView(imageView);
        linearLayoutText.addView(textView);
        linearLayoutText.addView(textViewArtista);
        linearLayout.addView(linearLayoutText);

        if(almacenar){
            textViews.add(textView);
            albumTexts.add(textViewArtista);
            linearLayouts.add(linearLayout);
        }

        if(MenuActivity.observer.getNightMode()){
            textView.setTextColor(menuActivity.getResources().getColor(R.color.colorTituloTextRep_noct));
            textViewArtista.setTextColor(menuActivity.getResources().getColor(R.color.colorAlbumTextRep_noct));
            linearLayout.setBackgroundColor(menuActivity.getResources().getColor(R.color.backgroundColorFilas_noct));
        } else{
            textView.setTextColor(menuActivity.getResources().getColor(R.color.colorTituloTextRep));
            textViewArtista.setTextColor(menuActivity.getResources().getColor(R.color.colorAlbumTextRep));
            linearLayout.setBackgroundColor(menuActivity.getResources().getColor(R.color.backgroundColorFilas));
        }

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(MenuActivity.fragmentListaCanciones.initiated()){
                    MenuActivity.fragmentListaCanciones.setAlbum(album);
                    MenuActivity.fragmentListaCanciones.vaciarLista();
                    MenuActivity.fragmentListaCanciones.rellenarLista(album);
                } else{
                    MenuActivity.fragmentListaCanciones.setAlbum(album);
                }

                MenuActivity.menuActivity.cambiaFragment(R.id.fragment_contentmenu1, MenuActivity.fragmentListaCanciones);
                MenuActivity.observer.actualizaDatosCancion();
            }
        });

        return linearLayout;
    }

    //// OnNightModeEvent

    @Override
    public void toNightMode() {
        for (TextView titulo: textViews) {
            titulo.setTextColor(menuActivity.getResources().getColor(R.color.colorTituloTextRep_noct));
        }
        for (TextView album: albumTexts) {
            album.setTextColor(menuActivity.getResources().getColor(R.color.colorAlbumTextRep_noct));
        }
        for (LinearLayout fila: linearLayouts) {
            fila.setBackgroundColor(menuActivity.getResources().getColor(R.color.backgroundColorFilas_noct));
        }
    }

    @Override
    public void toDayMode() {
        for (TextView titulo: textViews) {
            titulo.setTextColor(menuActivity.getResources().getColor(R.color.colorTituloTextRep));
        }
        for (TextView album: albumTexts) {
            album.setTextColor(menuActivity.getResources().getColor(R.color.colorAlbumTextRep));
        }
        for (LinearLayout fila: linearLayouts) {
            fila.setBackgroundColor(menuActivity.getResources().getColor(R.color.backgroundColorFilas));
        }
    }

    /////////////////////////
    /// SETTERS & GETTERS ///
    /////////////////////////
}

