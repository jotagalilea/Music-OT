package fdi.ucm.musicot.Misc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
            {"40:1","War and Victory","Sabaton"},
            {"Wolf Pack","Primo Victoria","Sabaton"},
            {"Swedish Pagans","Last Stance","Sabaton"},
            {"Sparta","Last Stance","Sabaton"},
            {"Dare(La La La)","Shakira","Shakira"},
            {"Primo Victoria","Primo Victoria","Sabaton"},
            {"Murmaider","The Dethalbum","Dethklok"},
            {"Awaken","The Dethalbum","Dethklok"},
            {"Face Fisted","The Dethalbum","Dethklok"},
            {"Deththeme","The Dethalbum","Dethklok"} };

    //String de canciones dummies
    private Cancion[] canciones;


    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //!!!!!! COSAS QUE FALTAN POR HACER !!!!!!
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // TODO(1) Relacionar la imagen con el album, eso no tengo claro como hacerlo

    public DAO(){

        for (int i=0; i<NUM_TEMAS-1;i++ ) {
            //Crea las canciones con los datos dummies en el String
            canciones[i] = new Cancion(
                    tituloAlbumArtista[i][0],
                    tituloAlbumArtista[i][1],
                    tituloAlbumArtista[i][2] );
        }
    }

    /**
     * Devuelve una lista con todas las canciones almacenadas en la aplicacion.
     * @return
     */
    public Cancion[] getListaCanciones(){

        Cancion[] lista = new Cancion[NUM_TEMAS];

        for (int i=0;i<NUM_TEMAS-1;i++) {
            lista[i] = canciones[i].clone();
        }

        return lista;
    }

    /**
     * Devuelve la canción con el ID introducido, devuelve null si la canción no existe.
     * @param ID
     * @return
     */
    public Cancion getCancionByID(int ID){

        Cancion canRes = this.canciones[ID];

        return canRes.clone();
    }

    /**
     * Devuelve el numero de temas almacenados en la BBDD
     * @return
     */
    public int getNumeroTemas(){

        return NUM_TEMAS;
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