package fdi.ucm.musicot;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;

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
        mContieneCanciones = (GridLayout) findViewById(R.id.contenedor_albumes);

        Cancion[] listaCanciones = MenuActivity.dao.getListaCanciones();

        for (Cancion tema: listaCanciones ) {
            mContieneCanciones.addView(this.generateLinearCanciones(tema));
        }
    }

    /**
     * Convierte el objeto Canción introducido en un formato XML de layout para presentarlo en GridLayout
     *
     * @param cancion
     * @return
     */
    private LinearLayout generateLinearCanciones(Cancion cancion){

        //TODO(2) Terminar esta función, hay que investigar como generar un Linealayout con propiedades
        LinearLayout resCancion = null;

        return resCancion;
    }

    /*  <LinearLayout
            android:layout_width="130dp"
            android:layout_height="wrap_content"
            android:orientation="vertical"
            android:onClick="">

            <ImageView
                android:layout_width="match_parent"
                android:layout_height="150dp"
                android:src="@drawable/ic_menu_albumes"></ImageView>
            <TextView
                android:layout_width="wrap_content"
                android:layout_height="20dp"
                android:layout_gravity="center"
                android:text="HOLA HOLA"></TextView>
        </LinearLayout>
    */
}
