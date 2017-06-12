package fdi.ucm.musicot.Modelo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.provider.MediaStore;

import com.example.usuario_local.music_ot.R;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import fdi.ucm.musicot.Misc.Utils;

/**
 * Created by Javier/Julio on 24/04/17.
 *
 * DAO (Data Access Object) será la clase que utilizaremos para interacturar con la base de datos
 * sin tener que embarrar el código de resto de la aplicación con Stirngs y cosas SQL raras.
 */

public class DAO {

    //Numero de temas que tiene la aplicación
    //public static final int NUM_TEMAS = 10;

    //Titulo[i][0] / Album[i][1] / Artista[i][2]
    /*public String[][] tituloAlbumArtista = {
            {"40:1", "War and Victory", "Sabaton"},
            {"Wolf Pack", "Primo Victoria", "Sabaton"},
            {"Swedish Pagans", "Last Stance", "Sabaton"},
            {"Sparta", "Last Stance", "Sabaton"},
            {"Dare(La La La)", "Shakira", "Shakira"},
            {"Primo Victoria", "Primo Victoria", "Sabaton"},
            {"Murmaider", "The Dethalbum", "Dethklok"},
            {"Awaken", "The Dethalbum", "Dethklok"},
            {"Face Fisted", "The Dethalbum", "Dethklok"},
            {"Deththeme", "The Dethalbum", "Dethklok"}};
    */
    /*public static Cancion[] canciones;
    public static Album[] albumes;
    public static Artista[] artistas;*/
    private static ArrayList<Cancion> canciones;
    private static ArrayList<Album> albumes;
    private static ArrayList<Artista> artistas;
    private ListasReproduccion listasReproduccion;

    String albumName;
    String artistName;
    String titulo;
    int duracion;
    byte[] imagen;

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //!!!!!! COSAS QUE FALTAN POR HACER !!!!!!
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    public DAO() {

        canciones = new ArrayList<>();
        albumes = new ArrayList<>();
        artistas = new ArrayList<>();
        listasReproduccion = new ListasReproduccion();

        File dir = Utils.parseMountDirectory();
        //File dir = new File("/mnt/sdcard/Music/Judas_Priest[1974-2016]");
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        cargarCancionesDeLaSD(dir.getAbsolutePath(), mmr);

    }

    private String[] extensions = { "mp3" };

    public void cargarCancionesDeLaSD(String path, MediaMetadataRetriever mmr) {

        try {
            File file = new File(path);
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null && files.length > 0) {
                    for (File f : files) {
                        if (f.isDirectory()) {
                            cargarCancionesDeLaSD(f.getAbsolutePath(), mmr);
                        } else {
                            for (int i = 0; i < extensions.length; i++) {
                                if (f.getAbsolutePath().endsWith(extensions[i])) {
                                    crearCancion(f, mmr);
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void crearCancion(File f, MediaMetadataRetriever mmr) {

        try {
            mmr.setDataSource(f.getAbsolutePath());

            albumName = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
            artistName = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            titulo = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            duracion = Integer.valueOf(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
            imagen = mmr.getEmbeddedPicture();

            if (titulo == null)
                titulo = f.getName();
            if (artistName == null)
                artistName = "Desconocido";
            if (albumName == null)
                albumName = "Desconocido";
            /*Bitmap caratula = null;
            if (imagen != null)
                caratula = BitmapFactory.decodeByteArray(imagen, 0, imagen.length);*/

            Artista art = artistaExiste(artistName);
            Album alb = albumExiste(albumName, artistName);
            boolean artistExists = art != null;
            boolean albumExists = alb != null;
            if (!artistExists)
                art = new Artista(artistName);
            if (!albumExists){
                Bitmap caratula = null;
                if (albumName != "Desconocido") {
                    byte[] imagen = mmr.getEmbeddedPicture();
                    if (imagen != null)
                        caratula = BitmapFactory.decodeByteArray(imagen, 0, imagen.length);
                }
                alb = new Album(albumName, art, caratula);
            }

            Cancion c = new Cancion(titulo, alb, art, f, duracion);

            if (!albumExists) {
                art.addAlbum(alb);
                albumes.add(alb);
            }
            alb.addCancion(c);

            if (!artistExists) {
                artistas.add(art);
            }
            art.addCancion(c);

            canciones.add(c);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * Devuelve el objeto album si ya existe uno con estas características, sino devuelve null
     *
     * @param nombreAlbum
     * @param artista
     * @return
     */
    private Album albumExiste(String nombreAlbum, String artista) {

        Album res = null;
        int i = 0;

        while (i < this.albumes.size() && (res == null)) {
            //Si el album no existe ya
            if (albumes.get(i).getTitulo().equalsIgnoreCase(nombreAlbum) &&
                    albumes.get(i).getArtista().getNombre().equalsIgnoreCase(artista)) {
                res = albumes.get(i);
            }
            i++;
        }

        return res;
    }

    /**
     * Devuelve el objeto Artista con el nombre dado, si no existe se devuelve null
     *
     * @param nombreArtista
     * @return
     */
    private Artista artistaExiste(String nombreArtista) {

        Artista res = null;
        int i = 0;

        while (i < this.artistas.size() && (res == null)) {
            //Si el album no existe ya
            if (artistas.get(i).getNombre().equalsIgnoreCase(nombreArtista)) {
                res = artistas.get(i);
            }
            i++;
        }

        return res;
    }

    /**
     * Devuelve una lista con todas las canciones almacenadas en la aplicacion.
     *
     * @return
     */
    public ArrayList<Cancion> getListaCanciones() {

        return DAO.canciones;
    }

    /**
     * Devuelve la canción con el ID introducido, devuelve null si la canción no existe.
     *
     * @param ID
     * @return
     */
    public Cancion getCancionByID(int ID) {

        Cancion canRes = this.canciones.get(ID);

        return canRes.clone();
    }

    public ArrayList<Album> getListaAlbumes() {

        return DAO.albumes;
    }

    /**
     * Devuelve el numero de temas almacenados en la BBDD
     *
     * @return
     */
    /*public int getNumeroTemas() {
        return NUM_TEMAS;
    }*/

//--------------------
//----- GETTER's -----
//--------------------

    public static ArrayList<Cancion> getCanciones(){
        return canciones;
    }
    public static ArrayList<Album> getAlbumes(){
        return albumes;
    }
    public static ArrayList<Artista> getArtistas(){
        return artistas;
    }
    public ListasReproduccion getListasReproduccion() { return listasReproduccion; }

}
//------------------------------------------------------------------------------------------
//------ No se recuperara esta parte del código hasta que no se vaya a hacer la BBDD -------
//------------------------------------------------------------------------------------------

/*public class DAO extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "musica.db";
    public static final String TABLE_NAME = "temas_table";

    public static final String[] COLS_TEMAS = {"ID","NOMBRE","ARTISTA","FAVORITO"};

    public DAO(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTemas = createTable(TABLE_NAME, COLS_TEMAS);

        db.execSQL(createTemas);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /**
     * Devuelve el array de Strings con los nombres de las columnas de la tabla dada.
     * @param nombreTabla
     * @return
     */
/*    public String[] getNombreColumnas(String nombreTabla){

        String[] res = null;

        switch(nombreTabla){

            case "temas_table":{ res = COLS_TEMAS.clone(); }break;
        }

        return res;
    }

    //------------------------------------------------------

    /**
     * Genera un String que contiene un CREATE TABLE con el nombre de la tabla y las columnas dadas,
     * EL ULTIMO ELEMENTO DEL ARRAY "nombreColumnas" SERÁ EL KEY
     * @param nombreTabla
     * @param nombreColumnas
     * @return
     */
/*    private String createTable(String nombreTabla, String[] nombreColumnas){

        String res = null;

        if(nombreColumnas.length >= 1){
            res = "CREATE TABLE" + nombreTabla + " (";

            for (int i=0;i<nombreColumnas.length-2;i++ ) {
                res = res + nombreColumnas[i] + ",";
            }

            res = res + nombreColumnas[nombreColumnas.length-1] + "PRIMARY KEY)";
        }

        return res;
    }

    /**
     * Genera el comando SQL para eliminar la tabla pasada por variable
     * @param nombreTabla
     * @return
     */
/*    private String deleteTable(String nombreTabla){

        String res = null;

        res = "DROP TABLE" + nombreTabla;

        return res;
    }

    /**
     * Devuelve un string con la linea SELECT SQl generada
     * @param nombreTabla
     * @param nombreColumnas
     * @param condiciones
     * @return
     */
/*    private String selectSQLString(String nombreTabla, String[] nombreColumnas, String[] condiciones){

        String res = null;

        res = "SELECT * FROM "+ nombreTabla;

        if(condiciones.length >= 1){

            res +=" WHERE ";

            for (int i=0;i<condiciones.length-2;i++ ) {
                res = res +nombreColumnas[i] + condiciones[i] + ",";
            }

            res = res + condiciones[condiciones.length-1] + ")";
        }

        return res;
    }
}
*/