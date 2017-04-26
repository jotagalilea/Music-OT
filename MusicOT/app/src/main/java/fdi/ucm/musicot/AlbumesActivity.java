package fdi.ucm.musicot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import com.example.usuario_local.music_ot.R;

import fdi.ucm.musicot.Misc.Cancion;

public class AlbumesActivity extends AppCompatActivity {

    GridLayout mContieneAlbumes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albumes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //---------------- A partir de aqui no es código automático

        mContieneAlbumes = (GridLayout) findViewById(R.id.contenedor_canciones);

        //mContieneAlbumes.add


    }
}
