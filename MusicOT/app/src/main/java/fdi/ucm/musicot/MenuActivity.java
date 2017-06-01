package fdi.ucm.musicot;

import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;

import com.example.usuario_local.music_ot.R;

import fdi.ucm.musicot.Observers.Observer;
import fdi.ucm.musicot.Misc.Utils;
import fdi.ucm.musicot.Modelo.DAO;
import fdi.ucm.musicot.Modelo.Reproductor;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static DAO dao;

    public static BusquedaFragment fragmentBusqueda;
    private ArtistasFragment fragmentArtistas;
    private CancionesFragment fragmentCanciones;
    private AlbumesFragment fragmentAlbumes;
    public static ReproductorFragment fragmentReproductor;
    public static ReproductorFragmentMini fragmentMini;
    public static ListaCancionesFragment fragmentListaCanciones;
    private View searchButtonView;
    private TableLayout fragmentArtistasContenedor;

    public static Observer observer;
    public static MenuActivity menuActivity;

    public static Reproductor reproductor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(dao == null) {
            dao = new DAO();
        }

        if(reproductor == null) {
            reproductor = new Reproductor();
        }

        if(observer == null) {
            observer = new Observer();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(fragmentBusqueda == null){
            fragmentBusqueda = new BusquedaFragment();
            //Observadores
            observer.addToList(fragmentBusqueda);
        }

        if(fragmentListaCanciones == null) {
            fragmentListaCanciones = new ListaCancionesFragment();
            fragmentListaCanciones.setRetainInstance(true);
        }

        if(fragmentArtistas == null) {
            fragmentArtistas = new ArtistasFragment();
            fragmentArtistas.setRetainInstance(true);
        }

        if(fragmentCanciones == null) {
            fragmentCanciones = new CancionesFragment();
            fragmentCanciones.setRetainInstance(true);
        }

        if(fragmentAlbumes == null) {
            fragmentAlbumes = new AlbumesFragment();
            fragmentAlbumes.setRetainInstance(true);
        }

        if(fragmentReproductor == null) {
            fragmentReproductor = new ReproductorFragment();
            fragmentReproductor.setRetainInstance(true);
            //Observadores
            observer.addToList(fragmentReproductor);
        }

        if(fragmentMini == null) {
            fragmentMini = new ReproductorFragmentMini();
            fragmentMini.setRetainInstance(true);
            //Observadores
            observer.addToList(fragmentMini);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(Utils.currentFragment == null) {
            Utils.currentFragment = fragmentReproductor;
            Utils.currentMiniFragment = fragmentMini;
        }
        transicionarMenuFragmento(R.id.mini_bot_reproductor, Utils.currentMiniFragment);
        cambiaFragment(R.id.fragment_contentmenu1, Utils.currentFragment);

        menuActivity = this;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (Utils.currentFragment.equals(fragmentReproductor)) {
            exitProcess();
        } else if (Utils.currentFragment.equals(fragmentListaCanciones)) {
            super.onBackPressed();
        } else {
            cambiaFragment(R.id.fragment_contentmenu1, fragmentReproductor);
        }
    }

    public void exitProcess(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("¿Esta seguro que desea salir?")
                .setTitle("Salir");

        // Add the buttons
        builder.setNegativeButton("Salir", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                System.exit(0);
            }
        });
        builder.setPositiveButton("Volver", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
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
        FragmentManager manager = getFragmentManager();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.app_bar_search){
            cambiaFragment(R.id.fragment_contentmenu1, fragmentBusqueda);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.nav_inicio){
            cambiaFragment(R.id.fragment_contentmenu1, fragmentReproductor);
        }

        if (id == R.id.nav_buscar) {
            cambiaFragment(R.id.fragment_contentmenu1, fragmentBusqueda);
        }

        if(id == R.id.nav_temas){
            cambiaFragment(R.id.fragment_contentmenu1, fragmentCanciones);
        }

        if(id == R.id.nav_albumes){
            cambiaFragment(R.id.fragment_contentmenu1, fragmentAlbumes);
        }

        if(id == R.id.nav_artista){
            cambiaFragment(R.id.fragment_contentmenu1, fragmentArtistas);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void cambiaFragment(int idNewFragment, Fragment newFragment){

        FragmentManager manager = getFragmentManager();

        if(Reproductor.isDeployed
                && idNewFragment==R.id.fragment_contentmenu1
                && newFragment != fragmentReproductor){

            manager.beginTransaction()
                    .show(fragmentMini)
                    .commit();
            //findViewById(R.id.mini_bot_reproductor).setVisibility(View.VISIBLE);
        }else if(idNewFragment == R.id.fragment_contentmenu1
                && newFragment == fragmentReproductor){

            manager.beginTransaction()
                    .hide(fragmentMini)
                    .commit();
            //findViewById(R.id.mini_bot_reproductor).setVisibility(View.INVISIBLE);
        }

        if(idNewFragment == R.id.fragment_contentmenu1){
            Utils.currentFragment = newFragment;
        }

        transicionarMenuFragmento(idNewFragment, newFragment);
    }

    /**
     * Sustituye el fragmento del contenedor con la ID dada por el nuevo fragmento dado, también
     * encoge el menú lateral si está dsplegado
     * @param id
     * @param newFragment
     */
    public void transicionarMenuFragmento(int id, Fragment newFragment){

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

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        observer.onKeyUp(keyCode);

        return super.onKeyUp(keyCode, event);
    }

}
