package fdi.ucm.musicot.Misc;

import android.media.MediaPlayer;

import java.io.IOException;

import fdi.ucm.musicot.Modelo.Cancion;

/**
 * Almacena pare de media y la canción que reproduce
 */
public class PaqueteCancionMedia {

    public MediaPlayer media;
    public Cancion cancionData;

    public PaqueteCancionMedia(Cancion cancionData){

        this.cancionData = cancionData;
        this.media = new MediaPlayer();
        try {
            media.setDataSource(cancionData.getRuta().getPath());
            media.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PaqueteCancionMedia(MediaPlayer media, Cancion cancionData){

        this.cancionData = cancionData;
        this.media =  media;
    }

    public PaqueteCancionMedia() {
        media = null;
        cancionData = null;
    }

    public MediaPlayer getMedia() {
        return media;
    }

    public void setMedia(MediaPlayer media) {
        this.media = media;
    }

    public Cancion getCancionData() {
        return cancionData;
    }

    public void setCancionData(Cancion cancionData) {
        this.cancionData = cancionData;
    }
}
