package fdi.ucm.musicot.Modelo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;



import static fdi.ucm.musicot.MenuActivity.menuActivity;

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

    private DBHelper dbHelper;

    String albumName;
    String artistName;
    String titulo;
    int duracion;
    byte[] imagen;



    public DAO() {

        canciones = new ArrayList<>();
        albumes = new ArrayList<>();
        artistas = new ArrayList<>();
        listasReproduccion = new ListasReproduccion();
        dbHelper = new DBHelper(menuActivity, DBHelper.DB_NAME, null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();

        if (dbHelper.tablaEstaVacia(db, DBHelper.TABLA_TEMAS)){
            //File dir = Utils.parseMountDirectory();
            File dir = new File("/mnt/sdcard/Music/Judas_Priest[1974-2016]");
            cargarCancionesDeLaSD(dir.getAbsolutePath(), mmr);
        }
        else {
            cargarCancionesDeLaBD(mmr);
        }

    }

    private String[] extensions = { "mp3" };

    private void cargarCancionesDeLaSD(String path, MediaMetadataRetriever mmr) {

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

    private void cargarCancionesDeLaBD(MediaMetadataRetriever mmr){
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String[] tTem = new String[]{DBHelper.TABLA_TEMAS};
            Cursor cTemas = db.rawQuery("SELECT * FROM ?", tTem);

            if (cTemas.moveToFirst()) {
                /* Usaremos un hashMap de artistas encontrados en consultas
                anteriores para no repetir consultas futuras. Lo mismo con álbumes.
                 */
                HashMap<Integer, String> artistasEncontrados = new HashMap<>();
                HashMap<Integer, String> albumesEncontrados = new HashMap<>();
                Artista art = null;
                Album alb = null;
                do {
                    titulo = cTemas.getString(1);
                    int artistID = cTemas.getInt(2);
                    if (artistasEncontrados.containsKey(artistID)) {
                        artistName = artistasEncontrados.get(artistID);
                        //art = artistaExiste(artistName);
                    }
                    else {
                        String[] args = new String[]{DBHelper.TABLA_ARTISTAS, Integer.toString(artistID)};
                        Cursor cArtista = db.rawQuery("SELECT * FROM ? WHERE id=?", args);
                        if (cArtista.moveToFirst()) {
                            int idArtista = cArtista.getInt(0);
                            artistName = cArtista.getString(1);
                            artistasEncontrados.put(idArtista, artistName);
                            /*art = artistaExiste(artistName);
                            if (art == null)
                                art = new Artista(artistName);*/

                        } else
                            throw new Exception("Error al buscar el artista de la canción " + titulo);
                    }
                    art = artistaExiste(artistName);
                    if (art == null)
                        art = new Artista(artistName);


                    int albumID = cTemas.getInt(3);
                    if (albumesEncontrados.containsKey(albumID))
                        albumName = albumesEncontrados.get(albumID);
                    else{
                        String[] args = new String[]{ DBHelper.TABLA_ALBUMES, Integer.toString(albumID) };
                        Cursor cAlbum = db.rawQuery("SELECT * FROM ? WHERE id=?", args);
                        if (cAlbum.moveToFirst()){
                            int idAlbum = cAlbum.getInt(0);
                            albumName = cAlbum.getString(1);
                            albumesEncontrados.put(idAlbum, albumName);
                        }
                        else throw new Exception("Error al buscar el álbum de la canción " + titulo);
                    }
                    alb = albumExiste(albumName, artistName);

                    Bitmap caratula = null;
                    if (alb == null) {
                        File f = new File(cTemas.getString(4));
                        if (albumName != "Desconocido") {
                            mmr.setDataSource(f.getAbsolutePath());
                            imagen = mmr.getEmbeddedPicture();
                            if (imagen != null)
                                caratula = BitmapFactory.decodeByteArray(imagen, 0, imagen.length);
                        }
                    }
                    alb = new Album(albumName, art, caratula);

                    duracion = cTemas.getInt(5);

                    Cancion tema = new Cancion(titulo, alb, art, cTemas.getString(4), duracion);

                    if (alb == null) {
                        art.addAlbum(alb);
                        albumes.add(alb);
                    }
                    alb.addCancion(tema);
                    if (art == null) {
                        artistas.add(art);
                    }
                    art.addCancion(tema);

                    canciones.add(tema);

                } while (cTemas.moveToNext());
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

            Cancion c = new Cancion(titulo, alb, art, f.getAbsolutePath(), duracion);

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

            // La información obtenida se insertará en la base de datos aquí:
            // Inserción del artista:
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues insertArt, insertTem, insertAlb;
            insertArt = new ContentValues();
            insertArt.put(DBHelper.getColstArtistas()[1], art.getNombre());
            int idArtista = (int) db.insert(DBHelper.TABLA_ARTISTAS, null, insertArt);

            // Inserción del álbum:
            insertAlb = new ContentValues();
            insertAlb.put(DBHelper.getColstAlbumes()[1], alb.getTitulo());
            insertAlb.put(DBHelper.getColstAlbumes()[2], idArtista);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Bitmap bm = alb.getCaratula();
            bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();
            insertAlb.put(DBHelper.getColstAlbumes()[3], b);
            int idAlbum = (int) db.insert(DBHelper.TABLA_ALBUMES, null, insertAlb);

            // Inserción de la canción:
            insertTem = new ContentValues();

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