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
    public static PaqueteCancionMedia[] listaPlayer;
    ProgressTracker progressTracker;
    String[] urlCanciones;
    public static PaqueteCancionMedia currentSong;

    int currSong;

    public Reproductor(){

        listaPlayer = new PaqueteCancionMedia[1];

        listaPlayer[0] = new PaqueteCancionMedia((Cancion)DAO.getCanciones().toArray()[0]);

        currentSong = listaPlayer[0];
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

            MenuActivity.fragmentReproductor.actualizaDatosCancion(currentSong);
        }else{

        }
    }


    /**
     * Genera la lista de canciones de la lista de reproducción seleccionada, y actualiza la lista .
     * @return
     */
    private PaqueteCancionMedia[] rellenarListaCanciones(Cancion[] canciones){

        PaqueteCancionMedia[] listaPaquetes = new PaqueteCancionMedia[canciones.length];
        MediaPlayer media = new MediaPlayer();

        for(int i=0; i<canciones.length; i++){

            try {
                media = new MediaPlayer();
                media.setDataSource(canciones[i].getRuta().getPath());
                media.prepare();
                listaPaquetes[i] = new PaqueteCancionMedia(media, canciones[i]);
            } catch (IOException e) {
                e.printStackTrace();
                // TODO añadir aqui una función que avise al usuario de que no se ha encontrado la cancion en disco y actualice la lista
            }
        }

        listaPlayer = listaPaquetes;

        return listaPaquetes;
    }

    public void botonNextSong(){

        if(currentSong != null) {
            currSong++;
            if (currSong > listaPlayer.length - 1) {
                currSong = 0;
            }

            currentSong.getMedia().stop();
            currentSong.getMedia().prepareAsync();

            currentSong = listaPlayer[currSong];
            if (isPlaying) {
                currentSong.getMedia().start();
            }

            MenuActivity.fragmentReproductor.actualizaDatosCancion(Reproductor.currentSong);
        }
    }

    public void botonPrevSong(){

        if(currentSong != null) {
            currSong--;
            if (currSong < 0) {
                currSong = listaPlayer.length - 1;
            }

            currentSong.getMedia().stop();
            currentSong.getMedia().prepareAsync();

            currentSong = listaPlayer[currSong];
            if (isPlaying) {
                currentSong.getMedia().start();
            }

            MenuActivity.fragmentReproductor.actualizaDatosCancion(Reproductor.currentSong);
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
