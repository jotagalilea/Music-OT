package fdi.ucm.musicot.Modelo;

import java.io.File;

/**
 * Created by deekin on 25/04/17.
 */

public class Cancion {

    private String titulo;
    private Album album;
    private Artista artista;
    private File ruta;
    private int duracion;

    public Cancion(){
        this.album = null;
        this.titulo = null;
        this.artista = null;
        this.ruta = null;
    }

    public Cancion(String titulo, Album album, Artista artista, File path){
        this.album = album;
        this.titulo = titulo;
        this.artista = artista;
        this.ruta = path;
    }

    public Cancion(String titulo, Album album, Artista artista, File path, int duracion){
        this.album = album;
        this.titulo = titulo;
        this.artista = artista;
        this.ruta = path;
        this.duracion = duracion;
    }

    public void setArtista(Artista newArtista){
        this.artista = newArtista;
    }

    public void setAlbum(Album newAlbum){
        this.album = newAlbum;
    }


    /**
     *Devuelve un clon del objeto canción.
     * @return
     */
    public Cancion clone(){

        Cancion cloned = new Cancion(this.titulo, this.album, this.artista, this.ruta);

        return cloned;
    }

    //--------------------------
    //-- GETTER's && SETTER's --
    //--------------------------

    public String getTitulo() {
        return titulo;
    }

    public Album getAlbum() {
        return album;
    }

    public Artista getArtista() { return artista; }

    public File getRuta() { return ruta; }

    public int getDuracion() { return duracion; }
}
