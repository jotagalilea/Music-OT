package fdi.ucm.musicot.Modelo;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by Javier on 27/04/2017.
 */

public class Artista {

    String nombre;
    Cancion[] canciones;
    Album[] albumes;

    public Artista(){
        nombre = null;
        canciones = null;
        albumes = null;
    }

    public Artista(String nombre, Cancion[] canciones, Album[] albumes){
        this.nombre = nombre;
        this.canciones = canciones;
        this.albumes = albumes;
    }

    public Artista(String nombre){
        this.nombre = nombre;
        this.canciones = new Cancion[0];
        this.albumes = new Album[0];
    }

    //-----------------
    //----- UTILS -----
    //-----------------

    public void addCancion(Cancion newCancion){

        Cancion[] res = new Cancion[canciones.length+1];

        newCancion.setArtista(this);

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

    public void addAlbum(Album newAlbum){

        Album[] res = new Album[albumes.length+1];

        newAlbum.setArtista(this);

        for (int i = 0; i < albumes.length; i++) {
            res[i] = albumes[i];
        }
        res[albumes.length] = newAlbum;

        //Organiza el array en orden alfabetico
        Arrays.sort(res, new Comparator<Album>(){
            @Override
            public int compare(Album b1, Album b2) {
                return b1.getTitulo().compareTo(b2.getTitulo());
            }
        });

        this.albumes = res;
    }

    //--------------------------------
    //----- GETTER's && SETTER's -----
    //--------------------------------

    public String getNombre() {
        return nombre;
    }

    public Cancion[] getCanciones() {
        return canciones;
    }

    public Album[] getAlbumes() {
        return albumes;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCanciones(Cancion[] canciones) {
        this.canciones = canciones;
    }

    public void setAlbumes(Album[] albumes) {
        this.albumes = albumes;
    }
}
