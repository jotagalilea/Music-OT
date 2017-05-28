package fdi.ucm.musicot;

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

import fdi.ucm.musicot.Misc.Utils;
import fdi.ucm.musicot.Modelo.Artista;
import fdi.ucm.musicot.Modelo.DAO;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 */
public class ArtistasFragment extends Fragment {

    TableLayout tabla;
    MenuActivity menuActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view;

        view = inflater.inflate(R.layout.fragment_artistas, container, false);

        tabla = (TableLayout) view.findViewById(R.id.contenedor_artistas);
        rellenarTablaInit(tabla, menuActivity);

        return view;
    }

    /**
     * Se llama esta funcion para rellenar la tabla de información
     */
    public void rellenarTablaInit(TableLayout tabla, MenuActivity menuActivity){

        //Rellenamos con todas los artistas disponibles en la aplicación
        tabla.setStretchAllColumns(true);

        TableRow fila = new TableRow(menuActivity);

        fila.setBackgroundResource(R.drawable.listabackground);
        TableLayout.LayoutParams tableParams;

        tableParams = new TableLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tableParams.setMargins(2, 5, 2, 5);

        fila.setLayoutParams(tableParams);
        tabla.addView(fila);
        byte i = 0;

        for (Artista artista: DAO.getArtistas()) {
            if (i < 1){
                i++;
            }
            else {
                i = 1;
                fila = new TableRow(menuActivity);
                fila.setBackgroundResource(R.drawable.listabackground);
                fila.setLayoutParams(tableParams);
                tabla.addView(fila);
            }
            fila.addView(generateLinearArtista(artista, menuActivity));
        }
    }

    /**
     * Convierte el objeto Canción introducido en un formato XML de layout para presentarlo en TableLayout
     *
     * @param artista
     * @return
     */
    private LinearLayout generateLinearArtista(final Artista artista, MenuActivity menuActivity){

        LinearLayout linearLayout = new LinearLayout(menuActivity);

        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setPadding(3,0,0,0);
        //----- ImageView -----
        ImageView imageView = new ImageView(menuActivity);

        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, Utils.convertirAdp(50, menuActivity)
        );
        imageParams.width = Utils.convertirAdp(50,menuActivity);
        imageParams.height = Utils.convertirAdp(50,menuActivity);
        imageParams.leftMargin = Utils.convertirAdp(5,menuActivity);
        imageParams.rightMargin = Utils.convertirAdp(5,menuActivity);

        if(artista.getAlbumes() != null && artista.getAlbumes().length > 0){

            imageView.setImageBitmap(artista.getAlbumes()[0].getCaratula());
        } else{

            imageView.setImageResource(R.drawable.ic_menu_artista);
        }

        imageView.setLayoutParams(imageParams);
        //----- TextView -----
        TextView textView = new TextView(menuActivity);

        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, Utils.convertirAdp(20, menuActivity)
        );

        textView.setLayoutParams( textParams );
        textView.setEllipsize(TextUtils.TruncateAt.END);

        textView.setText(artista.getNombre());
        textView.setTextSize(17);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setSingleLine(true);

        //Se añaden los componentes al LinearLayout
        linearLayout.addView(imageView);
        linearLayout.addView(textView);

        linearLayout.setBackgroundColor(Color.CYAN);

        // Creación de un mensaje de alerta:
        linearLayout.setOnClickListener(new View.OnClickListener() {
            Artista art;

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
                art = artista;

                if(MenuActivity.fragmentListaCanciones.initiated()){

                    MenuActivity.fragmentListaCanciones.setArtista(art);
                    MenuActivity.fragmentListaCanciones.vaciarLista();
                    MenuActivity.fragmentListaCanciones.rellenarLista(art);
                    //MenuActivity.reproductor.rellenarLista(art);

                } else{

                    MenuActivity.fragmentListaCanciones.setArtista(art);
                }

                MenuActivity.menuActivity.cambiaFragment(R.id.fragment_contentmenu1, MenuActivity.fragmentListaCanciones);

                MenuActivity.fragmentReproductor.actualizaDatosCancion();
            }
        });

        return linearLayout;
    }

    ////////////////////////////////
    ///// SETTERS && GETTERS ///////
    ////////////////////////////////

    public void setTabla(TableLayout tabla) {
        this.tabla = tabla;
    }

    public void setMenuActivity(MenuActivity menuActivity) {
        this.menuActivity = menuActivity;
    }
}