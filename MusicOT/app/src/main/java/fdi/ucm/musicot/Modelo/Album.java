package fdi.ucm.musicot.Modelo;

import android.graphics.Bitmap;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by Javier on 27/04/2017.
 */

public class Album {

    String titulo;
    Artista artista;
    Cancion[] canciones;
    Bitmap caratula;

    public Album(){
        this.titulo = null;
        this.artista = null;
        this.canciones = null;
        this.caratula = null;
    }

    public Album(String titulo, Artista artista){
        this.titulo = titulo;
        this.artista = artista;
        this.canciones = new Cancion[0];
        this.caratula = null;
    }

    public Album(String titulo, Artista artista, Bitmap cover){
        this.titulo = titulo;
        this.artista = artista;
        this.canciones = new Cancion[0];
        this.caratula = cover;
    }

    public Album(String titulo, Artista artista, Cancion[] canciones, Bitmap cover){
        this.titulo = titulo;
        this.artista = artista;
        this.canciones = canciones;
        this.caratula = cover;
    }

    //-----------------
    //----- UTILS -----
    //-----------------

    public void addCancion(Cancion newCancion){

        Cancion[] res = new Cancion[canciones.length+1];

        newCancion.setAlbum(this);
        newCancion.setArtista(this.artista);

        for (int i = 0; i < canciones.length; i++) {
            res[i] = canciones[i];
        }
        res[canciones.length] = newCancion;

        //Organiza el array en orden alfabetico
        Arrays.sort(res, new Comparator<Cancion>(){
            @Override
            public int compare(Cancion b1, Cancion b2) {
                return b1.getTitulo().compareTo(b2.getTitulo());
            }
        });

        this.canciones = res;
    }

    //-------------------------------
    //----- GETTER's && SETTER's-----
    //-------------------------------

    public String getTitulo() {
        return titulo;
    }

    public Artista getArtista() {
        return artista;
    }

    public Cancion[] getCanciones() {
        return canciones;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setArtista(Artista artista) {

        this.artista = artista;

        for (Cancion cancion: canciones ) {
            cancion.setArtista(artista);
        }
    }

    public void setCanciones(Cancion[] canciones) {
        this.canciones = canciones;
    }

    public Bitmap getCaratula() { return caratula; }

    public void setCaratula(Bitmap caratula) { this.caratula = caratula; }

/*
    public void addAlbum(String nombreAlbum,String artista, String[] canciones){

        String[][] newAlbumes = new String[albumes.length+1][];

        for (int i=0; i<albumes.length; i++ ) {
            newAlbumes[i] = albumes[i]; 
        }
        
        newAlbumes[albumes.length][0] = nombreAlbum;
        newAlbumes[albumes.length][1] = artista;

        for (int i = 2; i < canciones.length; i++) {
            newAlbumes[albumes.length][i] = canciones[i];
        }
    }

    public String[] getNombreAlbumes(){

        String[] nombres = new String[albumes.length];

        for (int i = 0; i < albumes.length; i++) {
            nombres[i] = albumes[i][0];
        }

        return nombres;
    }*/
}
