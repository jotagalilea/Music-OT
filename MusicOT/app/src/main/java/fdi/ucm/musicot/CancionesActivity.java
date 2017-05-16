package fdi.ucm.musicot;

import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.usuario_local.music_ot.R;

import java.util.ArrayList;

import fdi.ucm.musicot.Modelo.Cancion;
import fdi.ucm.musicot.Misc.Utils;
import fdi.ucm.musicot.Modelo.RetenCanciones;


public class CancionesActivity extends AppCompatActivity {

    TableLayout mContieneCanciones;
    // TODO Hacer un método que modifique el número de columnas según la orientación del cacharro.
    static final byte maxColumnas = 3;
    private ArrayList<Cancion> listaCanciones;
    private RetenCanciones retenCanciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canciones);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        mContieneCanciones = (TableLayout) findViewById(R.id.contenedor_canciones);
        mContieneCanciones.setStretchAllColumns(true);
        listaCanciones = MenuActivity.dao.getListaCanciones();

        TableRow fila = new TableRow(this);
        mContieneCanciones.addView(fila);
        byte i = 1;
        fila.addView(generateLinearCanciones(listaCanciones.get(0)));
        for (Cancion tema: listaCanciones) {
            if (i < maxColumnas){
                i++;
            }
            else {
                i = 1;
                fila = new TableRow(this);
                mContieneCanciones.addView(fila);
            }
            fila.addView(generateLinearCanciones(tema));
        }
    }

    /**
     * Convierte el objeto Canción introducido en un formato XML de layout para presentarlo en TableLayout
     *
     * @param cancion
     * @return
     */
    private LinearLayout generateLinearCanciones(Cancion cancion){

        LinearLayout linearLayout = new LinearLayout(this);

        linearLayout.setOrientation(LinearLayout.VERTICAL);


        //----- ImageView -----
        ImageView imageView = new ImageView(this);

        imageView.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, Utils.convertirAdp(50, this))
        );
        imageView.setImageDrawable(
                getResources().getDrawable(R.drawable.ic_menu_temas));
        // TODO Averiguar como funciona lo de poner imagenes en el ImageView.
        // TODO Hacer que las imágenes salgan cuadradas.

        //----- TextView -----
        TextView textView = new TextView(this);

        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, Utils.convertirAdp(20, this)
        );
        textParams.gravity = Gravity.CENTER;

        textView.setLayoutParams( textParams );

        textView.setText(cancion.getTitulo());
        textView.setBackgroundColor(Color.RED);

        //----- TextViewAlbum -----
        TextView textViewAlbum = new TextView(this);

        LinearLayout.LayoutParams textParamsAlbum = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, Utils.convertirAdp(20, this)
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

}
