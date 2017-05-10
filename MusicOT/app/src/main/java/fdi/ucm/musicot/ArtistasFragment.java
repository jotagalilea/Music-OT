package fdi.ucm.musicot;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
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
        tabla.addView(fila);
        byte i = 0;

        for (Artista artista: DAO.artistas) {
            if (i < 3){
                i++;
            }
            else {
                i = 1;
                fila = new TableRow(menuActivity);
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
    private LinearLayout generateLinearArtista(Artista artista, MenuActivity menuActivity){

        LinearLayout linearLayout = new LinearLayout(menuActivity);

        linearLayout.setOrientation(LinearLayout.VERTICAL);


        //----- ImageView -----
        ImageView imageView = new ImageView(menuActivity);

        imageView.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, Utils.convertirAdp(50, menuActivity))
        );
        imageView.setImageDrawable(
                getResources().getDrawable(R.drawable.ic_menu_artista));
        // TODO Averiguar como funciona lo de poner imagenes en el ImageView.
        // TODO Hacer que las imágenes salgan cuadradas.

        //----- TextView -----
        TextView textView = new TextView(menuActivity);

        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, Utils.convertirAdp(20, menuActivity)
        );
        textParams.gravity = Gravity.CENTER;

        textView.setLayoutParams( textParams );

        textView.setText(artista.getNombre());
        textView.setBackgroundColor(Color.RED);

        //----- TextViewAlbum -----
        TextView textViewAlbum = new TextView(menuActivity);

        LinearLayout.LayoutParams textParamsAlbum = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, Utils.convertirAdp(20, menuActivity)
        );
        textParamsAlbum.gravity = Gravity.CENTER;

        textViewAlbum.setLayoutParams( textParamsAlbum );

        textViewAlbum.setText(String.valueOf(artista.getAlbumes().length));
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