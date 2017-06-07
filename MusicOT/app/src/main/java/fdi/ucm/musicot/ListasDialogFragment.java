package fdi.ucm.musicot;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.usuario_local.music_ot.R;

import java.util.LinkedList;

import fdi.ucm.musicot.Modelo.DAO;
import fdi.ucm.musicot.Modelo.ListasReproduccion;

import static fdi.ucm.musicot.MenuActivity.dao;


public class ListasDialogFragment extends DialogFragment {

    public static ListasDialogFragment newInstance(){
        ListasDialogFragment f = new ListasDialogFragment();
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        String[] listas = getArrayListas(dao.getListasReproduccion());

        builder.setView(inflater.inflate(R.layout.fragment_listas_dialog, null));
        builder.setTitle(R.string.titulo_lista_dialog)
            .setItems(listas, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dao.getListasReproduccion().añadirCancion();
                }
            });
        if (listas.length == 0){
            // Si no hay listas de reproducción se muestra un textView para informar.
            builder.setMessage("no hay listas");
            builder.setNegativeButton(R.string.volver, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
        }
        // Create the AlertDialog object and return it
        return builder.create();
    }


    private String[] getArrayListas(ListasReproduccion listas){
        LinkedList<String> listasLL = new LinkedList();
        if (!listas.getListasReproduccion().isEmpty()) {
            for (String nombreLista : listas.getListasReproduccion().keySet()) {
                listasLL.add(nombreLista);
            }
        }
        return listasLL.toArray(new String[listasLL.size()]);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
