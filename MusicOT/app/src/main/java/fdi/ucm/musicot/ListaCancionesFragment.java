package fdi.ucm.musicot;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.usuario_local.music_ot.R;

import fdi.ucm.musicot.Misc.Utils;
import fdi.ucm.musicot.Modelo.Album;
import fdi.ucm.musicot.Modelo.Artista;
import fdi.ucm.musicot.Modelo.Cancion;

import static fdi.ucm.musicot.MenuActivity.menuActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ListaCancionesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaCancionesFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private View view;
    private LinearLayout mContieneCanciones;

    private Album album = null;
    private Artista artista = null;

    public ListaCancionesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaCancionesAlbumFragment.
     */
    public static ListaCancionesFragment newInstance(String param1, String param2) {
        ListaCancionesFragment fragment = new ListaCancionesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setRetainInstance(true);
        view = inflater.inflate(R.layout.fragment_lista_canciones, container, false);
        mContieneCanciones = (LinearLayout) view.findViewById(R.id.listaCancionesBaseLayout);

        if(album != null){
            mContieneCanciones.removeAllViews();
            rellenarLista(album);
        } else{
            mContieneCanciones.removeAllViews();
            rellenarLista(artista);
        }

        return view;
    }


    public boolean initiated(){
        return (mContieneCanciones != null);
    }

    public void rellenarLista(Album album){

        LinearLayout fila = new LinearLayout(menuActivity);
        LinearLayout.LayoutParams tableParams;

        tableParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tableParams.setMargins(2, 5, 2, 5);

        for (Cancion tema: album.getCanciones()) {
            fila = generateLinearCanciones(tema, album);
            fila.setBackgroundResource(R.drawable.listabackground);
            fila.setLayoutParams(tableParams);
            mContieneCanciones.addView(fila);
        }
    }

    public void rellenarLista(Artista artista){

        LinearLayout fila = new LinearLayout(menuActivity);
        LinearLayout.LayoutParams tableParams;

        tableParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tableParams.setMargins(2, 5, 2, 0);

        for (Cancion tema: artista.getCanciones()) {
            fila = generateLinearCanciones(tema, artista);
            fila.setBackgroundResource(R.drawable.listabackground);
            fila.setLayoutParams(tableParams);
            mContieneCanciones.addView(fila);
        }
    }

    /**
     * Vacía la lista de sus elementos
     */
    public void vaciarLista(){
        mContieneCanciones.removeAllViews();
        mContieneCanciones.removeAllViewsInLayout();
    }

    /**
     * Convierte el objeto Canción introducido en un formato XML de layout para presentarlo en TableLayout
     *
     * @param cancion
     * @return
     */
    private LinearLayout generateLinearCanciones(final Cancion cancion, final Album album){

        LinearLayout linearLayoutBackgroundColor = new LinearLayout(menuActivity);
        LinearLayout linearLayout = new LinearLayout(menuActivity);
        LinearLayout linearLayoutInternal = new LinearLayout(menuActivity);

        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayoutInternal.setOrientation(LinearLayout.VERTICAL);

        linearLayoutBackgroundColor.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        );
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

        // TODO Hacer que las imágenes salgan cuadradas.

        //----- TextView -----
        TextView textView = new TextView(menuActivity);

        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, Utils.convertirAdp(20, menuActivity)
        );
        textParams.gravity = Gravity.LEFT;

        textView.setLayoutParams( textParams );

        textView.setText(cancion.getTitulo());
        //textView.setBackgroundColor(Color.RED);
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
        //textViewAlbum.setBackgroundColor(Color.GREEN);
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

        textViewArtist.setLayoutParams( textParamsArtist );

        textViewArtist.setText(cancion.getArtista().getNombre());
        textViewArtist.setSingleLine(true);
        textViewArtist.setEllipsize(TextUtils.TruncateAt.END);
        textViewArtist.setTypeface(null, Typeface.ITALIC);
        textViewArtist.setTextSize(13);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = MenuActivity.reproductor.rellenarLista(album, cancion);
                MenuActivity.reproductor.setCurrentSongDeCancion(cancion, i);

                MenuActivity.observer.actualizaDatosCancion();

                menuActivity.cambiaFragment(R.id.fragment_contentmenu1, MenuActivity.fragmentReproductor);
            }
        });

        //Se añaden los componentes al LinearLayout
        linearLayoutBackgroundColor.addView(imageView);
        linearLayoutInternal.addView(textView);
        linearLayoutInternal.addView(textViewAlbum);
        linearLayoutInternal.addView(textViewArtist);
        linearLayoutBackgroundColor.addView(linearLayoutInternal);
        linearLayout.addView(linearLayoutBackgroundColor);

        linearLayoutBackgroundColor.setBackgroundColor(Color.CYAN);

        return linearLayout;
    }

    /**
     * Convierte el objeto Canción introducido en un formato XML de layout para presentarlo en TableLayout
     *
     * @param cancion
     * @return
     */
    private LinearLayout generateLinearCanciones(final Cancion cancion, final Artista artista){

        LinearLayout linearLayoutBackgroundColor = new LinearLayout(menuActivity);
        LinearLayout linearLayout = new LinearLayout(menuActivity);
        LinearLayout linearLayoutInternal = new LinearLayout(menuActivity);

        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayoutInternal.setOrientation(LinearLayout.VERTICAL);

        linearLayoutBackgroundColor.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        );
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

        // TODO Hacer que las imágenes salgan cuadradas.

        //----- TextView -----
        TextView textView = new TextView(menuActivity);

        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, Utils.convertirAdp(20, menuActivity)
        );
        textParams.gravity = Gravity.LEFT;

        textView.setLayoutParams( textParams );

        textView.setText(cancion.getTitulo());
        //textView.setBackgroundColor(Color.RED);
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
        //textViewAlbum.setBackgroundColor(Color.GREEN);
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

        textViewArtist.setLayoutParams( textParamsArtist );

        textViewArtist.setText(cancion.getArtista().getNombre());
        textViewArtist.setSingleLine(true);
        textViewArtist.setEllipsize(TextUtils.TruncateAt.END);
        textViewArtist.setTypeface(null, Typeface.ITALIC);
        textViewArtist.setTextSize(13);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = MenuActivity.reproductor.rellenarLista(artista, cancion);
                MenuActivity.reproductor.setCurrentSongDeCancion(cancion, i);

                MenuActivity.observer.actualizaDatosCancion();

                menuActivity.cambiaFragment(R.id.fragment_contentmenu1, MenuActivity.fragmentReproductor);
            }
        });

        //Se añaden los componentes al LinearLayout
        linearLayoutBackgroundColor.addView(imageView);
        linearLayoutInternal.addView(textView);
        linearLayoutInternal.addView(textViewAlbum);
        linearLayoutInternal.addView(textViewArtist);
        linearLayoutBackgroundColor.addView(linearLayoutInternal);
        linearLayout.addView(linearLayoutBackgroundColor);

        linearLayoutBackgroundColor.setBackgroundColor(Color.CYAN);

        return linearLayout;
    }


    public void setAlbum(Album album) {
        artista = null;
        this.album = album;
    }

    public void setArtista(Artista artista) {
        album = null;
        this.artista = artista;
    }
}
