package fdi.ucm.musicot.Modelo;

import android.media.MediaPlayer;
import android.os.Environment;
import android.view.View;

import com.example.usuario_local.music_ot.R;

import java.io.IOException;

import fdi.ucm.musicot.MenuActivity;
import fdi.ucm.musicot.Misc.Utils;
import fdi.ucm.musicot.ReproductorFragment;
import fdi.ucm.musicot.ReproductorFragmentMini;

public class Reproductor {

    public static boolean isPlaying = false;
    public static boolean isDeployed = false;
    public static MediaPlayer[] listaPlayer;
    ProgressTracker progressTracker;
    String[] urlCanciones;
    public static MediaPlayer currentSong;

    int currSong;

    public Reproductor(){

        // TODO Sacar cosas del DAO
        //urlCanciones = DAO.initialSongs();
        urlCanciones= new String[]{
                "/Sabaton - The Last Stand (Limited Edition)/01. Sparta.mp3",
                "/Sabaton - The Last Stand (Limited Edition)/02. Last Dying Breath.mp3",
                "/Sabaton - The Last Stand (Limited Edition)/03. Blood Of Bannockburn.mp3"
        };

        listaPlayer = rellenarListaCanciones(urlCanciones);

        currentSong = listaPlayer[0];
        currSong = 0;

        progressTracker = new ProgressTracker();
        progressTracker.start();
    }

    public void botonReproducirCancion(){

        if(isPlaying) {
            isPlaying = false;
            currentSong.pause();

        }else {
            isPlaying = true;
            isDeployed = true;
            currentSong.start();
        }

        MenuActivity.fragmentReproductor.actualizaDatosCancion();
    }


    /**
     * Genera la lista de canciones de la lista de reproducción seleccionada, y actualiza la lista .
     * @return
     */
    public MediaPlayer[] rellenarListaCanciones(String[] listaURLcanciones){

        MediaPlayer[] listaCanciones = new MediaPlayer[listaURLcanciones.length];

        for(int i=0; i<listaURLcanciones.length; i++){

            listaURLcanciones[i] = Utils.parseMountDirectory()+ listaURLcanciones[i];
            try {
                listaCanciones[i] = new  MediaPlayer();
                listaCanciones[i].setDataSource(listaURLcanciones[i]);
                listaCanciones[i].prepare();
            } catch (IOException e) {
                e.printStackTrace();
                // TODO añadir aqui una función que avise al usuario de que no se ha encontrado la cancion en disco y actualice la lista
            }
        }

        listaPlayer = listaCanciones;

        return listaCanciones;
    }

    public void botonNextSong(){

        currSong++;
        if(currSong > listaPlayer.length-1){
            currSong = 0;
        }

        currentSong.stop();
        currentSong.prepareAsync();

        currentSong = listaPlayer[currSong];
        if(isPlaying) {
            currentSong.start();
        }

        MenuActivity.fragmentReproductor.actualizaDatosCancion();
    }

    public void botonPrevSong(){

        currSong--;
        if(currSong < 0){
            currSong = listaPlayer.length-1;
        }

        currentSong.stop();
        currentSong.prepareAsync();

        currentSong = listaPlayer[currSong];
        if(isPlaying) {
            currentSong.start();
        }

        MenuActivity.fragmentReproductor.actualizaDatosCancion();
    }

    public class ProgressTracker extends Thread{

        @Override
        public void run() {
            super.run();

            while(true){

                try {
                    Thread.sleep(700);
                    if(ReproductorFragment.progressBar != null){
                        ReproductorFragment.progressBar.setProgress(currentSong.getCurrentPosition());
                    }
                    if(ReproductorFragmentMini.progressBar != null){
                        ReproductorFragmentMini.progressBar.setProgress(currentSong.getCurrentPosition());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
