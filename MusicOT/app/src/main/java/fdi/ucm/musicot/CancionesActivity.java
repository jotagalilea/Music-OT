package fdi.ucm.musicot;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.usuario_local.music_ot.R;

import fdi.ucm.musicot.Misc.Cancion;

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
                convertirAdp(143), ViewGroup.LayoutParams.WRAP_CONTENT
        );
        linearParams.leftMargin = convertirAdp(2);

        linearLayout.setLayoutParams(linearParams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        //----- ImageView -----
        ImageView imageView = new ImageView(this);

        imageView.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, convertirAdp(150))
        );
        imageView.setImageDrawable(
                getResources().getDrawable(R.drawable.ic_menu_temas));
        // TODO Averiguar como funciona lo de poner imagenes en el ImageView

        //----- TextView -----
        TextView textView = new TextView(this);

        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, convertirAdp(20)
        );
        textParams.gravity = Gravity.CENTER;

        textView.setLayoutParams( textParams );

        textView.setText(cancion.getTitulo());
        textView.setBackgroundColor(Color.RED);

        //Se añaden los componentes al LinearLayout
        linearLayout.addView(imageView);
        linearLayout.addView(textView);

        linearLayout.setBackgroundColor(Color.CYAN);

        mContieneCanciones.addView(linearLayout);

        return linearLayout;
    }

    /**
     * Convierte el numero introducido a DP
     * @param num
     * @return
     */
    private int convertirAdp(int num){

        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, num, getResources().getDisplayMetrics());
    }
}
