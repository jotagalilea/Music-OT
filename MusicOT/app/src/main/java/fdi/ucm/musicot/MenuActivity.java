package fdi.ucm.musicot;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;

import com.example.usuario_local.music_ot.R;

import fdi.ucm.musicot.Observers.Observer;
import fdi.ucm.musicot.Misc.Utils;
import fdi.ucm.musicot.Modelo.DAO;
import fdi.ucm.musicot.Modelo.Reproductor;
import fdi.ucm.musicot.Observers.OnNightModeEvent;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,OnNightModeEvent {

    public static DAO dao;
    public static Observer observer;
    public static MenuActivity menuActivity;
    public static Reproductor reproductor;

    private SensorEventListener lightSensorListener;
    private SensorManager mSensorManager;
    private Sensor mPressure;

    public static BusquedaFragment fragmentBusqueda;
    public static ArtistasFragment fragmentArtistas;
    public static CancionesFragment fragmentCanciones;
    public static AlbumesFragment fragmentAlbumes;
    public static ReproductorFragment fragmentReproductor;
    public static ReproductorFragmentMini fragmentMini;
    public static ListaCancionesFragment fragmentListaCanciones;
    public static ListasReproduccionFragment fragmentListasReproduccion;
    public static BuscadorAmazonFragment fragmentBuscadorAmazon;
    private RelativeLayout menuLayout;

    private PopupMenu popupMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        menuActivity = this;

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
            observer.addOnKeyEventHandler(fragmentBusqueda);
            observer.addOnNightModeEvent(fragmentBusqueda);
        }

        if(fragmentListaCanciones == null) {
            fragmentListaCanciones = new ListaCancionesFragment();
            fragmentListaCanciones.setRetainInstance(true);
            //Observadores
            observer.addOnNightModeEvent(fragmentListaCanciones);
        }

        if(fragmentListasReproduccion == null) {
            fragmentListasReproduccion = new ListasReproduccionFragment();
            fragmentListasReproduccion.setRetainInstance(true);
        }

        if(fragmentArtistas == null) {
            fragmentArtistas = new ArtistasFragment();
            fragmentArtistas.setRetainInstance(true);
            //Observadores
            observer.addOnNightModeEvent(fragmentArtistas);
        }

        if(fragmentCanciones == null) {
            fragmentCanciones = new CancionesFragment();
            fragmentCanciones.setRetainInstance(true);
            //Observadores
            observer.addOnNightModeEvent(fragmentCanciones);
        }

        if(fragmentAlbumes == null) {
            fragmentAlbumes = new AlbumesFragment();
            fragmentAlbumes.setRetainInstance(true);
            //Observadores
            observer.addOnNightModeEvent(fragmentAlbumes);
        }

        if(fragmentReproductor == null) {
            fragmentReproductor = new ReproductorFragment();
            fragmentReproductor.setRetainInstance(true);
            //Observadores
            observer.addDatosCancionEventHandler(fragmentReproductor);
            observer.addOnNightModeEvent(fragmentReproductor);
        }

        if(fragmentMini == null) {
            fragmentMini = new ReproductorFragmentMini();
            fragmentMini.setRetainInstance(true);
            //Observadores
            observer.addDatosCancionEventHandler(fragmentMini);
            observer.addOnNightModeEvent(fragmentMini);
        }

        if(fragmentBuscadorAmazon == null){
            fragmentBuscadorAmazon = new BuscadorAmazonFragment();
            fragmentBuscadorAmazon.setRetainInstance(true);
            //Observadores
            observer.addOnNightModeEvent(fragmentBuscadorAmazon);
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

        observer.addOnNightModeEvent(this);
        menuActivity = this;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        menuLayout = (RelativeLayout) findViewById(R.id.content_menu);
        if(menuLayout != null) {
            if (observer.getNightMode()) {
                menuLayout.setBackgroundColor(getResources().getColor(R.color.backgroundApp_noct));
            } else {
                menuLayout.setBackgroundColor(getResources().getColor(R.color.backgroundApp));
            }
        }
        return super.onCreateView(name, context, attrs);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (Utils.currentFragment.equals(fragmentReproductor)) {
            minimizeApp();
        } else if (Utils.currentFragment.equals(fragmentListaCanciones)) {
            super.onBackPressed();
        } else {
            cambiaFragment(R.id.fragment_contentmenu1, fragmentReproductor);
        }
    }

    public void nightModeMenuItem(MenuItem menuItem){
        if(observer.getNightMode()){
            observer.toDayMode();
        }else{
            observer.toNightMode();
        }
    }

    public void exitProcess(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("¿Esta seguro que desea salir?")
                .setTitle("Salir");

        // Add the buttons
        builder.setNegativeButton("Salir", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Reproductor.currentSong.media.release();
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

        switch(id){
            case R.id.action_settings: {
                popupMenu = new PopupMenu(MenuActivity.this, findViewById(R.id.action_settings));
                popupMenu.inflate(R.menu.configuracion);
                popupMenu.show();
                popupMenu.getMenu().findItem(R.id.nav_config_noct).setChecked(observer.getNightMode());
                return true;
            }
            case R.id.app_bar_search: {
                cambiaFragment(R.id.fragment_contentmenu1, fragmentBusqueda);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.nav_inicio: {
                cambiaFragment(R.id.fragment_contentmenu1, fragmentReproductor);
            }break;
            case R.id.nav_buscar: {
                cambiaFragment(R.id.fragment_contentmenu1, fragmentBuscadorAmazon);
            }break;
            case R.id.nav_temas:{
                cambiaFragment(R.id.fragment_contentmenu1, fragmentCanciones);
            }break;
            case R.id.nav_albumes:{
                cambiaFragment(R.id.fragment_contentmenu1, fragmentAlbumes);
            }break;
            case R.id.nav_artista:{
                cambiaFragment(R.id.fragment_contentmenu1, fragmentArtistas);
            }break;
            case R.id.nav_salir:{
                exitProcess();
            }break;
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
     * encoge el menú lateral si está desplegado
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

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        observer.onKeyUp(keyCode);
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /**
     * Minimiza la aplicación
     */
    public void minimizeApp() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    //// OnNightModeEvent

    @Override
    public void toNightMode() {
        if(menuLayout != null) {
            menuLayout.setBackgroundColor(menuActivity.getResources().getColor(R.color.backgroundApp_noct));
        }
    }

    @Override
    public void toDayMode() {
        if(menuLayout != null){
            menuLayout.setBackgroundColor(menuActivity.getResources().getColor(R.color.backgroundApp));
        }
    }
}
