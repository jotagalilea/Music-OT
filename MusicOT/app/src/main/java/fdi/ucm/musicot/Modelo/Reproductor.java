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

public class Reproductor {

    public static boolean isPlaying = false;
    public static boolean isDeployed = false;
    public static Cancion[] listaPlayer;
    ProgressTracker progressTracker;
    public static PaqueteCancionMedia currentSong;

    int currSong;

    public Reproductor(){

        listaPlayer = new Cancion[1];

        listaPlayer[0] = DAO.getCanciones().get(0);

        currentSong = new PaqueteCancionMedia(new MediaPlayer(), listaPlayer[0]);

        try {
            currentSong.media.setDataSource(currentSong.cancionData.getRuta().getPath());
            currentSong.media.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        currSong = 0;

        progressTracker = new ProgressTracker();
        progressTracker.start();
    }

    public void rellenarLista(Album album){

        listaPlayer = rellenarListaCanciones(album.getCanciones());
    }

    public void rellenarLista(Cancion cancion){

        Cancion[] canArray = new Cancion[1];

        canArray[0] = cancion;

        listaPlayer = rellenarListaCanciones(canArray);
    }

    public void rellenarLista(Artista artista){

        listaPlayer = rellenarListaCanciones(artista.getCanciones());
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
    private Cancion[] rellenarListaCanciones(Cancion[] canciones){

        Cancion[] listaPaquetes = new Cancion[canciones.length];

        for(int i=0; i<canciones.length; i++){
            listaPaquetes[i] = canciones[i];
        }

        currSong = 0;

        currentSong.cancionData = listaPaquetes[0];

        currentSong.media.stop();
        currentSong.media.reset();
        try {
            currentSong.media.setDataSource(listaPaquetes[0].getRuta().getPath());
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
}
