package fdi.ucm.musicot.Modelo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Julio on 12/06/2017.
 */

public class DBHelper extends SQLiteOpenHelper{

    public static final String DB_NAME = "musica.db";

    /**
     * Atributos para la tabla de artistas:
     */
    private static final String TABLA_ARTISTAS = "t_artistas";
    private static final String[] COLS_ARTISTAS =
            new String[] {"id", "nombre"};
    private static final String crearTablaArtistas = "CREATE TABLE t_artistas (" +
            "id INTEGER, " +
            "nombre TEXT, " +
            "PRIMARY KEY (id))";
    private static final String borrarTablaArtistas = "DROP TABLE IF EXISTS t_artistas";
    /**
     * Atributos para la tabla de álbumes:
     */
    private static final String TABLA_ALBUMES = "t_albumes";
    private static final String[] COLS_ALBUMES =
            new String[] {"id", "titulo", "artista_id", "caratula"};
    private static final String crearTablaAlbumes = "CREATE TABLE t_albumes (" +
            "id INTEGER, " +
            "titulo TEXT, " +
            "artista_id INTEGER, " +
            "caratula , " +
            "PRIMARY KEY (id), " +
            "FOREIGN KEY (artista_id) REFERENCES t_artistas (id))";
    private static final String borrarTablaAlbumes = "DROP TABLE IF EXISTS t_albumes";
    /**
     * Atributos para la tabla de canciones:
     */
    public static final String TABLA_TEMAS = "t_temas";
    private static final String[] COLS_TEMAS =
            new String[] {"id", "titulo", "artista_id", "album_id", "ruta", "duracion"};
    private static final String crearTablaTemas = "CREATE TABLE t_temas (" +
            "id INTEGER, " +
            "titulo TEXT, " +
            "artista_id INTEGER, " +
            "album_id INTEGER, " +
            "ruta TEXT, " +
            "duracion TEXT, " +
            "PRIMARY KEY (id), " +
            "FOREIGN KEY (artista_id) REFERENCES t_artistas (id), " +
            "FOREIGN KEY (album_id) REFERENCES t_albumes (id))";
    private static final String borrarTablaTemas = "DROP TABLE IF EXISTS t_temas";

    private SQLiteDatabase db;


    public DBHelper(Context context, String nombre, SQLiteDatabase.CursorFactory cursorFactory, int version){
        super(context, nombre, cursorFactory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        crearTablas(db);
    }

    private void crearTablas(SQLiteDatabase db) {
        db.execSQL(crearTablaArtistas);
        db.execSQL(crearTablaAlbumes);
        db.execSQL(crearTablaTemas);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        borrarTablas(db);
        crearTablas(db);
    }

    private void borrarTablas(SQLiteDatabase db) {
        db.execSQL(borrarTablaTemas);
        db.execSQL(borrarTablaAlbumes);
        db.execSQL(borrarTablaArtistas);
    }
}
