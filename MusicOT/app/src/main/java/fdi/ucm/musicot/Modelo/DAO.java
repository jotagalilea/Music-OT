package fdi.ucm.musicot.Modelo;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by Javier/Julio on 24/04/17.
 *
 * DAO (Data Access Object) será la clase que utilizaremos para interacturar con la base de datos
 * sin tener que embarrar el código de resto de la aplicación con Stirngs y cosas SQL raras.
 */

public class DAO {

    //Numero de temas que tiene la aplicación
    public static final int NUM_TEMAS = 10;

    //Titulo[i][0] / Album[i][1] / Artista[i][2]
    /*public String[][] tituloAlbumArtista; = {
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

    //public static Cancion[] canciones;
    //public static Album[] albumes;
    //public static Artista[] artistas;
    public static ArrayList<Cancion> canciones;
    public static ArrayList<Album> albumes;
    public static ArrayList<Artista> artistas;

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //!!!!!! COSAS QUE FALTAN POR HACER !!!!!!
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // TODO(1) Relacionar la imagen con el album, eso no tengo claro como hacerlo

    //public DAO(Context context) {
    public DAO(Activity activity){
        // TODO: Pensar en estructuras más eficientes para canciones, álbumes y artistas.
        //canciones = new Cancion[NUM_TEMAS];
        //albumes = new Album[0];
        //artistas = new Artista[0];
        canciones = new ArrayList<>();
        albumes = new ArrayList<>();
        artistas = new ArrayList<>();
        //cargarCanciones(context);
        cargarCanciones(activity);
        cargarAlbumes();
        cargarArtistas();

        /*for (int i = 0; i < NUM_TEMAS; i++) {

            //Crea las canciones con los datos dummies en el String
            Album currAlbum = this.albumExiste(tituloAlbumArtista[i][1], tituloAlbumArtista[i][2]);
            Artista currArtista = this.artistaExiste(tituloAlbumArtista[i][2]);

            if(currAlbum != null)
                System.out.println("Album leido numero "+i+": "+currAlbum.getTitulo());
            else
                System.out.println("Album leido numero "+i);

            if (currAlbum == null) {
                if (currArtista == null) {
                    currArtista = new Artista(tituloAlbumArtista[i][2]);
                    artistas = Arrays.copyOf(artistas, artistas.length + 1);
                    artistas[artistas.length-1] = currArtista;
                }
                currAlbum = new Album(tituloAlbumArtista[i][1], currArtista);
                albumes = Arrays.copyOf(albumes, albumes.length + 1);
                albumes[albumes.length-1] = currAlbum;
                currArtista.addAlbum(currAlbum);
            }

            canciones[i] = new Cancion(
                    tituloAlbumArtista[i][0],
                    currAlbum,
                    currArtista);

            canciones[i].getAlbum().addCancion(canciones[i]);
            currAlbum.addCancion(canciones[i]);
            currArtista.addCancion(canciones[i]);
        }
        */
    }

    /**
     * Carga las canciones de la memoria en el array.
     */
    //private void cargarCanciones(Context context) {
    private void cargarCanciones(Activity activity){
        ContentResolver musicResolver = activity.getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String sel = MediaStore.Files.FileColumns.MIME_TYPE + "=?";
        String ext = MimeTypeMap.getSingleton().getMimeTypeFromExtension("mp3");
        String[] selExtARGS = new String[]{ext};
        //Peta aquí
        Cursor musicCursor = musicResolver.query(musicUri, null, sel, selExtARGS, null);

        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns
            int titleColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media._ID);
            int albumColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            int artistColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.ARTIST);
            int column_index = musicCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
            //add songs to list
            do {
                long id = musicCursor.getLong(idColumn);
                String title = musicCursor.getString(titleColumn);
                String artistName = musicCursor.getString(artistColumn);
                String albumTitle = musicCursor.getString(albumColumn);
                Artista artista = artistaExiste(artistName);
                Album album = albumExiste(albumTitle, artistName);
                // Pensar si hay que añadir albumes al array de albumes de Artista.
                // Pensar si ponemos array en lugar de ArrayList.
                if (artista == null) {
                    artista = new Artista(artistName);
                }
                if (album == null) {
                    album = new Album(albumTitle, artista);
                    artista.addAlbum(album);
                }
                if (!artistaTieneAlbum(artista, album))
                    artista.addAlbum(album);
                canciones.add(new Cancion(title, album, artista));
            }
            while (musicCursor.moveToNext());

        }
    }

    /**
     * Comprueba si el artista contiene el album pasado como parámetro.
     * @param artista
     * @param album
     * @return
     */
    private boolean artistaTieneAlbum(Artista artista, Album album) {
        for (Album al: artista.getAlbumes()) {
            if (al.getTitulo().equalsIgnoreCase(album.getTitulo()))
                return true;
        }
        return false;
    }

    /**
     * Carga los artistas de las canciones.
     */
    private void cargarArtistas() {
    }

    /**
     * Carga los álbumes de las canciones en el array.
     */
    private void cargarAlbumes() {
    }

    /**
     * Devuelve el objeto album si ya existe uno con estas características, si no devuelve null
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
            //Si el artista no existe ya
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

        /*Cancion[] lista = new Cancion[NUM_TEMAS];

        for (int i = 0; i < NUM_TEMAS; i++) {
            lista[i] = canciones[i].clone();
        }*/

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

        /*Album[] lista = new Album[NUM_TEMAS];

        for (int i = 0; i < NUM_TEMAS; i++) {
            lista[i] =
        }*/

        return DAO.albumes;
    }

    /**
     * Devuelve el numero de temas almacenados en la BBDD
     *
     * @return
     */
    public int getNumeroTemas() {

        return NUM_TEMAS;
    }

//--------------------
//----- GETTER's -----
//--------------------

    public ArrayList<Cancion> getCanciones(){
        return canciones;
    }

    public ArrayList<Album> getAlbumes(){
        return albumes;
    }

    public ArrayList<Artista> getArtistas(){
        return artistas;
    }

    /*public Cancion[] getCanciones() {
        return Arrays.copyOf(this.canciones, canciones.length);
    }

    public Album[] getAlbum() {
        return Arrays.copyOf(this.albumes, albumes.length);
    }

    public Artista[] getArtista() {
        return Arrays.copyOf(this.artistas, artistas.length);
    }
    */
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