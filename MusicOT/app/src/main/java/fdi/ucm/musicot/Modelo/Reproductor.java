package fdi.ucm.musicot.Modelo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Environment;
import android.view.View;

import com.example.usuario_local.music_ot.R;

import java.io.IOException;

import fdi.ucm.musicot.MenuActivity;
import fdi.ucm.musicot.Misc.PaqueteCancionMedia;
import fdi.ucm.musicot.Misc.Utils;
import fdi.ucm.musicot.ReproductorFragment;
import fdi.ucm.musicot.ReproductorFragmentMini;

/**
 * Objeto encargado de la reproducción del programa
 */
public class Reproductor {

    public static boolean isPlaying = false;
    public static boolean isDeployed = false;
    public static Cancion[] listaPlayer;
    ProgressTracker progressTracker;
    public static PaqueteCancionMedia currentSong;

    int currSong;

    public Reproductor(){

        currentSong = new PaqueteCancionMedia(new MediaPlayer(), new Cancion());

        listaPlayer = rellenarListaCanciones(DAO.getAlbumes().get(0).getCanciones());

        progressTracker = new ProgressTracker();
        progressTracker.start();
    }

    /**
     * Rellena la lista de reproducción actual con las canciones que el album tiene relacionadas
     * @param album
     */
    public void rellenarLista(Album album){

        listaPlayer = rellenarListaCanciones(album.getCanciones());
    }

    /**
     * Rellena la lista de reproducción con el album de la canción dada, y devuelve la posición de
     * la canción en el album
     * @param cancion
     * @return posicion
     */
    public int rellenarLista(Cancion cancion){

        int i = 0;
        boolean encontrada = false;

        while(i < cancion.getAlbum().getCanciones().length && !encontrada){
            encontrada = cancion.getAlbum().getCanciones()[i].equals(cancion);
            i++;
        }

        listaPlayer = rellenarListaCanciones(cancion.getAlbum().getCanciones(),i-1);

        return i;
    }

    /**
     * Rellena la lista de preoducción actual con las canciones relacionadas con el artista dado
     * @param artista
     */
    public void rellenarLista(Artista artista){

        listaPlayer = rellenarListaCanciones(artista.getCanciones());
    }

    /**
     * Rellena la lista de reproducción con las canciones relacionadas con el artista y devuelve
     * la posicion de la canción en la lista de reproducción de la canción dada
     * @param artista
     * @param cancion
     * @return posicion
     */
    public int rellenarLista(Artista artista, Cancion cancion){

        int i = 0;
        boolean encontrada = false;

        while(i < artista.getCanciones().length && !encontrada){
            encontrada = artista.getCanciones()[i].equals(cancion);
            i++;
        }

        listaPlayer = rellenarListaCanciones(artista.getCanciones(),i-1);

        return i;
    }

    public int rellenarLista(Album album, Cancion cancion){

        int i = 0;
        boolean encontrada = false;

        while(i < album.getCanciones().length && !encontrada){
            encontrada = album.getCanciones()[i].equals(cancion);
            i++;
        }

        listaPlayer = rellenarListaCanciones(album.getCanciones(),i-1);

        return i;
    }

    public void botonReproducirCancion(){

        if(currentSong != null) {
            if (isPlaying) {
                isPlaying = false;
                currentSong.getMedia().pause();

            } else {
                isPlaying = true;
                isDeployed = true;
                currentSong.getMedia().start();
            }

            MenuActivity.fragmentReproductor.actualizaDatosCancion();
        }else{

        }
    }

    /**
     * Genera la lista de canciones de la lista de reproducción seleccionada, y actualiza la lista .
     * @return
     */
    private Cancion[] rellenarListaCanciones(Cancion[] canciones, int currSong){

        Cancion[] listaPaquetes = new Cancion[canciones.length];

        for(int i=0; i<canciones.length; i++){
            listaPaquetes[i] = canciones[i];
        }

        this.currSong = currSong;

        currentSong.cancionData = listaPaquetes[currSong];

        currentSong.media.stop();
        currentSong.media.reset();
        try {
            currentSong.media.setDataSource(listaPaquetes[currSong].getRuta().getPath());
            currentSong.media.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(isPlaying){
            currentSong.media.start();
        }

        MenuActivity.fragmentReproductor.actualizaDatosCancion();

        return listaPaquetes;
    }

    private Cancion[] rellenarListaCanciones(Cancion[] canciones){

        Cancion[] listaPaquetes = new Cancion[canciones.length];

        for(int i=0; i<canciones.length; i++){
            listaPaquetes[i] = canciones[i];
        }

        this.currSong = 0;

        currentSong.cancionData = listaPaquetes[currSong];

        currentSong.media.stop();
        currentSong.media.reset();
        try {
            currentSong.media.setDataSource(listaPaquetes[currSong].getRuta().getPath());
            currentSong.media.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(isPlaying){
            currentSong.media.start();
        }

        if(MenuActivity.fragmentReproductor != null) {
            MenuActivity.fragmentReproductor.actualizaDatosCancion();
        }
        return listaPaquetes;
    }

    public void botonNextSong(){

        if(currentSong != null) {
            currSong++;
            if (currSong > listaPlayer.length - 1) {
                currSong = 0;
            }

            currentSong.media.stop();
            currentSong.media.reset();
            try {
                currentSong.cancionData = listaPlayer[currSong];
                currentSong.media.setDataSource(listaPlayer[currSong].getRuta().getPath());
                currentSong.media.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (isPlaying) {
                currentSong.media.start();
            }

            MenuActivity.fragmentReproductor.actualizaDatosCancion();
        }
    }

    public void botonPrevSong() {

        if(currentSong != null) {
            currSong--;
            if (currSong < 0) {
                currSong = listaPlayer.length - 1;
            }

            currentSong.media.stop();
            currentSong.media.reset();
            try {
                currentSong.cancionData = listaPlayer[currSong];
                currentSong.media.setDataSource(listaPlayer[currSong].getRuta().getPath());
                currentSong.media.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (isPlaying) {
                currentSong.getMedia().start();
            }

            MenuActivity.fragmentReproductor.actualizaDatosCancion();
        }
    }

    /**
     * Clase Thread encargada de mantener las barras de progreso actualizadas.
     */
    public class ProgressTracker extends Thread{

        @Override
        public void run() {
            super.run();

            while(true){

                try {
                    Thread.sleep(700);
                    if(ReproductorFragment.progressBar != null && currentSong != null){
                        ReproductorFragment.progressBar.setProgress(currentSong.getMedia().getCurrentPosition());
                    }
                    if(ReproductorFragmentMini.progressBar != null && currentSong != null){
                        ReproductorFragmentMini.progressBar.setProgress(currentSong.getMedia().getCurrentPosition());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setCurrentSongDeCancion(Cancion cancion,int numeroTema){

        currSong = numeroTema-1;

        currentSong.media.stop();
        currentSong.media.reset();
        try {
            currentSong.cancionData = cancion;
            currentSong.media.setDataSource(cancion.getRuta().getPath());
            currentSong.media.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (isPlaying) {
            currentSong.media.start();
        }
    }
}
