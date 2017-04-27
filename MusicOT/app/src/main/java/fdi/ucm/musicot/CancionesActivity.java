package fdi.ucm.musicot;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.usuario_local.music_ot.R;

import fdi.ucm.musicot.Modelo.Cancion;
import fdi.ucm.musicot.Misc.Utils;

public class CancionesActivity extends AppCompatActivity {

    GridLayout mContieneCanciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canciones);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //---------------- A partir de aqui no es código automático

        //Rellenamos con todas las canciones disponibles en la aplicación
        mContieneCanciones = (GridLayout) findViewById(R.id.contenedor_canciones);

        Cancion[] listaCanciones = MenuActivity.dao.getListaCanciones();

        for (Cancion tema: listaCanciones ) {
            this.generateLinearCanciones(tema);
        }
    }

    /**
     * Convierte el objeto Canción introducido en un formato XML de layout para presentarlo en GridLayout
     *
     * @param cancion
     * @return
     */
    private LinearLayout generateLinearCanciones(Cancion cancion){

        LinearLayout linearLayout = new LinearLayout(this);

        //Damos las propiedades al LinearLayout
        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                Utils.convertirAdp(130, this), ViewGroup.LayoutParams.WRAP_CONTENT
        );
        linearLayout.setLayoutParams(linearParams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        //----- ImageView -----
        ImageView imageView = new ImageView(this);

        imageView.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, Utils.convertirAdp(150, this))
        );
        imageView.setImageDrawable(
                getResources().getDrawable(R.drawable.ic_menu_temas));
        // TODO Averiguar como funciona lo de poner imagenes en el ImageView

        //----- TextView -----
        TextView textView = new TextView(this);

        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, Utils.convertirAdp(20, this)
        );
        textParams.gravity = Gravity.CENTER;

        textView.setLayoutParams( textParams );

        textView.setText(cancion.getTitulo());
        textView.setBackgroundColor(Color.RED);

        //----- TextViewAlbum -----
        TextView textViewAlbum = new TextView(this);

        LinearLayout.LayoutParams textParamsAlbum = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, Utils.convertirAdp(20, this)
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

        mContieneCanciones.addView(linearLayout);

        return linearLayout;
    }

}
