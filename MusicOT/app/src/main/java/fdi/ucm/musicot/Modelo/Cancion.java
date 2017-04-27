package fdi.ucm.musicot.Modelo;

/**
 * Created by deekin on 25/04/17.
 */

public class Cancion {

    private String titulo;
    private Album album;
    private Artista artista;

    public Cancion(){
        this.album = null;
        this.titulo = null;
        this.artista = null;
    }

    public Cancion(String titulo, Album album, Artista artista){
        this.album = album;
        this.titulo = titulo;
        this.artista = artista;

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

        Cancion cloned = new Cancion(this.titulo, this.album, this.artista);

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
}
