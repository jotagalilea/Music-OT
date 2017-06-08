package fdi.ucm.musicot;

import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.usuario_local.music_ot.R;

import fdi.ucm.musicot.Misc.Utils;
import fdi.ucm.musicot.Modelo.ListasReproduccion;

import static fdi.ucm.musicot.MenuActivity.fragmentListaCanciones;
import static fdi.ucm.musicot.MenuActivity.menuActivity;


public class ListasReproduccionFragment extends Fragment {

    TableLayout tabla;
    LayoutInflater inflater;
    ViewGroup container;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view;

        this.inflater = inflater;
        this.container = container;
        view = inflater.inflate(R.layout.fragment_listas_reproduccion, container, false);

        tabla = (TableLayout) view.findViewById(R.id.contenedor_listas_reproduccion);
        rellenarTablaInit(tabla, menuActivity, view);

        return view;
    }


    public void rellenarTablaInit(TableLayout tabla, MenuActivity menuActivity, View view){

        tabla.setStretchAllColumns(true);

        TableRow fila = new TableRow(menuActivity);

        fila.setBackgroundResource(R.drawable.listabackground);
        TableLayout.LayoutParams tableParams;

        tableParams = new TableLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tableParams.setMargins(2, 5, 2, 0);

        fila.setLayoutParams(tableParams);
        tabla.addView(fila);


        ListasReproduccion listasRep = MenuActivity.dao.getListasReproduccion();

        if (!listasRep.getListasReproduccion().isEmpty()) {
            for (String nombreLista : listasRep.getListasReproduccion().keySet()) {
                fila = new TableRow(menuActivity);
                fila.setBackgroundResource(R.drawable.listabackground);
                fila.setLayoutParams(tableParams);
                tabla.addView(fila);
                fila.addView(generateLinearLista(nombreLista));
            }
        }

        // Implementar aquí el botón para crear una lista nueva:
        //TableRow filaBotonAddLista = new TableRow(menuActivity);

        Button botonCrearLista = (Button) view.findViewById(R.id.boton_crear_lista);
        botonCrearLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoCrearLista();
            }
        });

        ////////////////////////////////////////////////////
        /*filaBotonAddLista.setBackgroundResource(R.drawable.listabackground);
        //TableLayout.LayoutParams tableParams;

        tableParams = new TableLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tableParams.setMargins(2, 5, 2, 0);

        filaBotonAddLista.setLayoutParams(tableParams);
        filaBotonAddLista.addView(botonCrearLista);
        tabla.addView(filaBotonAddLista);*/
    }

    private static void mostrarDialogoCrearLista(){
        FragmentTransaction ft = menuActivity.getFragmentManager().beginTransaction();
        Fragment prev = menuActivity.getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        DialogFragment df = CrearListasDialogFragment.newInstance();
        df.show(ft, "dialog");
    }



    private LinearLayout generateLinearLista(final String nombreLista) {
        LinearLayout linearLayout = new LinearLayout(menuActivity);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        TextView textView = new TextView(menuActivity);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, Utils.convertirAdp(20, menuActivity)
        );
        textParams.gravity = Gravity.CENTER;
        textView.setLayoutParams( textParams );
        textView.setText(nombreLista);

        linearLayout.addView(textView);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implementar el acceso a la lista de canciones aquí
                crearVistaLista(nombreLista);
            }
        });
        //tabla.addView(linearLayout);
        return linearLayout;
    }

    private void crearVistaLista(String nombreLista) {
        if(MenuActivity.fragmentListaCanciones.initiated())
            MenuActivity.fragmentListaCanciones.vaciarLista();
        else
            fragmentListaCanciones.iniciarContenedor();

        MenuActivity.fragmentListaCanciones.rellenarLista(nombreLista);
        MenuActivity.menuActivity.cambiaFragment(R.id.fragment_contentmenu1, MenuActivity.fragmentListaCanciones);
        MenuActivity.observer.actualizaDatosCancion();
    }

    /**
     * Actualiza el fragment cuando se crea una nueva lista.
     * Este código sólo vale en esta clase en particular. Si se quiere usar
     * en los fragment con tablas con varias columnas hay que comprobar que
     * la fila no tiene más elementos antes de borrarla. Falta pulirlo.
     */
    public void actualizar(){
        /*
        View view = inflater.inflate(R.layout.fragment_listas_reproduccion, container, false);

        // La i accede a la fila y la j al elemento dentro de la fila.

        for (int i = 0; i < tabla.getChildCount(); i++){
            LinearLayout hijo = (LinearLayout) tabla.getChildAt(i);
            for (int j = 0; i < hijo.getChildCount(); i++) {
                View nieto = hijo.getChildAt(i);
                if (nieto instanceof LinearLayout) {
                    hijo.removeViewAt(j);
                    tabla.removeViewAt(i);
                }
            }
        }
        tabla.refreshDrawableState();
        rellenarTablaInit(tabla, menuActivity, view);
        */
    }

}