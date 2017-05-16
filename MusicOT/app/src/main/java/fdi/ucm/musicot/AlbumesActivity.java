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
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.usuario_local.music_ot.R;

import java.util.ArrayList;

import fdi.ucm.musicot.Misc.Utils;
import fdi.ucm.musicot.Modelo.Album;
import fdi.ucm.musicot.Modelo.Cancion;

public class AlbumesActivity extends AppCompatActivity {

    TableLayout mContieneAlbumes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albumes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //---------------- A partir de aqui no es código automático

        mContieneAlbumes = (TableLayout) findViewById(R.id.contenedor_albumes);

        ArrayList<Album> listaAlbumes = MenuActivity.dao.getListaAlbumes();

        for (Album album: listaAlbumes ) {
            this.generateLinearAlbumes(album);
        }
    }

    /**
     * Convierte el objeto Canción introducido en un formato XML de layout para presentarlo en GridLayout
     *
     * @param album
     * @return
     */
    private LinearLayout generateLinearAlbumes(Album album){

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
                getResources().getDrawable(R.drawable.ic_menu_albumes));
        // TODO Averiguar como funciona lo de poner imagenes en el ImageView

        //----- TextView -----
        TextView textView = new TextView(this);

        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, Utils.convertirAdp(20, this)
        );
        textParams.gravity = Gravity.CENTER;

        textView.setLayoutParams( textParams );

        textView.setText(album.getTitulo());
        textView.setBackgroundColor(Color.RED);

        //Se añaden los componentes al LinearLayout
        linearLayout.addView(imageView);
        linearLayout.addView(textView);

        linearLayout.setBackgroundColor(Color.CYAN);

        mContieneAlbumes.addView(linearLayout);

        return linearLayout;
    }
}

