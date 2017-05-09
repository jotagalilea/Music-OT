package fdi.ucm.musicot.Modelo;

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
    public String[][] tituloAlbumArtista = {
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

    public static Cancion[] canciones;
    public static Album[] albumes;
    public static Artista[] artistas;

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //!!!!!! COSAS QUE FALTAN POR HACER !!!!!!
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // TODO(1) Relacionar la imagen con el album, eso no tengo claro como hacerlo

    public DAO() {

        // TODO: Pensar en estructuras más eficientes para canciones, álbumes y artistas.
        canciones = new Cancion[NUM_TEMAS];
        albumes = new Album[0];
        artistas = new Artista[0];

        for (int i = 0; i < NUM_TEMAS; i++) {

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

        while (i < this.albumes.length && (res == null)) {
            //Si el album no existe ya
            if (albumes[i].getTitulo().equalsIgnoreCase(nombreAlbum) &&
                    albumes[i].getArtista().getNombre().equalsIgnoreCase(artista)) {
                res = albumes[i];
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

        while (i < this.artistas.length && (res == null)) {
            //Si el album no existe ya
            if (artistas[i].getNombre().equalsIgnoreCase(nombreArtista)) {
                res = artistas[i];
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
    public Cancion[] getListaCanciones() {

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

        Cancion canRes = this.canciones[ID];

        return canRes.clone();
    }

    public Album[] getListaAlbumes() {

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

    public Cancion[] getCanciones() {
        return Arrays.copyOf(this.canciones, canciones.length);
    }

    private Album[] getAlbum() {
        return Arrays.copyOf(this.albumes, albumes.length);
    }

    private Artista[] getArtista() {
        return Arrays.copyOf(this.artistas, artistas.length);
    }
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