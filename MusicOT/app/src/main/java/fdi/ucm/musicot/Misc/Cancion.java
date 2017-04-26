package fdi.ucm.musicot.Misc;

/**
 * Created by deekin on 25/04/17.
 */

public class Cancion {

    private String titulo;
    private String album;
    private String artista;

    public Cancion(){
        this.album = null;
        this.titulo = null;
    }

    public Cancion(String titulo, String album, String artista){
        this.album = album;
        this.titulo = titulo;
        this.artista = artista;
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

    public String getAlbum() {
        return album;
    }

    public String getArtista() {
        return artista;
    }
}
