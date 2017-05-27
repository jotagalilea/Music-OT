package fdi.ucm.musicot.Modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Julio on 27/05/2017.
 */

public class ListasReproduccion {

    /*
    Las listas de reproducción estarán en un HashMap con el nombre de la lista como clave
    y la lista como valor.
    Cada una de las listas de reproducción serán listas enlazadas para conseguir unos buenos
    costes cuando el usuario cambie las canciones de posición.
     */
    private HashMap<String,List<Cancion>> listasReproduccion;

    public ListasReproduccion(){
        this.listasReproduccion = new HashMap<String, List<Cancion>>();
    }

    public HashMap<String, List<Cancion>> getListasReproduccion() {
        return listasReproduccion;
    }

    public void crearLista(String nombre) throws Exception{
        if (!listasReproduccion.containsKey(nombre)){
            List lista = new LinkedList<Cancion>();
            listasReproduccion.put(nombre, lista);
        }
        else
            throw new Exception("Ya existe una lista con ese nombre");
    }

    public void borrarLista(String nombreLista){
        if (listasReproduccion.containsKey(nombreLista))
            listasReproduccion.remove(nombreLista);
    }

    public List getLista(String nombreLista){
        return listasReproduccion.get(nombreLista);
    }

    public void añadirCancion(Cancion c, List l){
        l.add(c);
    }

    public void borrarCancion(Cancion c, List l){
        l.remove(c);
    }

    /**
     * Coloca la canción en la posición elegida por el usuario.
     * @param c
     */
    public void moverCancion(Cancion c, List l, int pos){
        l.remove(c);
        l.set(pos, c);
    }
}
