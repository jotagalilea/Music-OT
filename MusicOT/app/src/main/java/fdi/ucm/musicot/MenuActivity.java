package fdi.ucm.musicot;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;

import com.example.usuario_local.music_ot.R;

import fdi.ucm.musicot.Modelo.DAO;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static DAO dao;

    private ArtistasFragment fragmentArtistas;
    private CancionesFragment fragmentCanciones;
    private AlbumesFragment fragmentAlbumes;
    private TableLayout fragmentArtistasContenedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dao = new DAO();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Inicializamos los fragmentos de la actividad MAIN
        fragmentArtistas = new ArtistasFragment();
        fragmentArtistas.setRetainInstance(true);
        fragmentArtistas.setMenuActivity(this);

        fragmentCanciones = new CancionesFragment();
        fragmentCanciones.setRetainInstance(true);
        fragmentCanciones.setMenuActivity(this);

        fragmentAlbumes = new AlbumesFragment();
        fragmentAlbumes.setRetainInstance(true);
        fragmentAlbumes.setMenuActivity(this);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_albumes) {
            // Handle the camera action
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    /**
     * Va a la ventana Albumes ( onClick )
     * @param menuItem
     */
    public void menuAlbumesOnClick(MenuItem menuItem){

        transicionarMenuFragmento(R.id.fragment_contentmenu1, fragmentAlbumes);
        /*Intent moveToAlbumes = new Intent(this, AlbumesActivity.class);

        startActivity(moveToAlbumes);*/

    }

    /**
     * Va a la ventana Canciones ( onClick )
     * @param menuItem
     */
    public void menuTemasOnClick(MenuItem menuItem){

        transicionarMenuFragmento(R.id.fragment_contentmenu1, fragmentCanciones);

        /*Intent moveToAlbumes = new Intent(this, CancionesActivity.class);

        startActivity(moveToAlbumes);*/

    }

    /**
     * Va a la ventana Canciones ( onClick )
     * @param menuItem
     */
    public void menuArtistasOnClick(MenuItem menuItem){

        transicionarMenuFragmento(R.id.fragment_contentmenu1, fragmentArtistas);

    }

    /**
     * Sustituye el fragmento del contenedor con la ID dada por el nuevo fragmento dado, también
     * encoge el menú lateral si está dsplegado
     * @param id
     * @param newFragment
     */
    private void transicionarMenuFragmento(int id, Fragment newFragment){

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        transaction.replace(id, newFragment);
        transaction.addToBackStack(null);

        transaction.commit();

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    public DAO getDAO(){

        return this.dao;
    }

}
