package fdi.ucm.musicot;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.usuario_local.music_ot.R;

import fdi.ucm.musicot.Modelo.Cancion;
import fdi.ucm.musicot.Misc.Utils;




public class CancionesActivity extends AppCompatActivity {

    TableLayout mContieneCanciones;
    // TODO Hacer un método que modifique el número de columnas según la orientación del cacharro.
    static final byte maxColumnas = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canciones);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //---------------- A partir de aqui no es código automático

        //Rellenamos con todas las canciones disponibles en la aplicación
        mContieneCanciones = (TableLayout) findViewById(R.id.contenedor_canciones);
        mContieneCanciones.setStretchAllColumns(true);
        Cancion[] listaCanciones = MenuActivity.dao.getListaCanciones();

        TableRow fila = new TableRow(this);
        mContieneCanciones.addView(fila);
        byte i = 1;
        fila.addView(generateLinearCanciones(listaCanciones[0]));
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


        //////////////////////////////////////////////////////////
        linearLayout.setOnClickListener(new View.OnClickListener() {
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
        });
        /////////////////////////////////////////////////////////////


        return linearLayout;
    }

}
