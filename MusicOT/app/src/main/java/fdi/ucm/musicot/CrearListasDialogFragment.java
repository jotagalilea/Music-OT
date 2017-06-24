package fdi.ucm.musicot;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.EditText;


import com.example.usuario_local.music_ot.R;

import java.util.LinkedList;

import fdi.ucm.musicot.Misc.Utils;
import fdi.ucm.musicot.Modelo.ListasReproduccion;

import static fdi.ucm.musicot.MenuActivity.dao;
import static fdi.ucm.musicot.MenuActivity.menuActivity;


public class CrearListasDialogFragment extends DialogFragment {


    public static CrearListasDialogFragment newInstance(){
        CrearListasDialogFragment f = new CrearListasDialogFragment();
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        //String[] listas = getArrayListas(dao.getListasReproduccion());

        builder.setView(inflater.inflate(R.layout.fragment_crear_listas_dialog, null));

        /*TextView viewNombre = new TextView(menuActivity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, Utils.convertirAdp(20, menuActivity)
        );
        params.gravity = Gravity.LEFT;
        viewNombre.setLayoutParams(params);
        viewNombre.setText("Nombre de la lista:");
        viewNombre.setSingleLine(true);
        viewNombre.setEllipsize(TextUtils.TruncateAt.END);
        viewNombre.setTypeface(null, Typeface.ITALIC);
        viewNombre.setTextSize(13);
        */

        final EditText input = new EditText(menuActivity);
        input.setHint("Nombre de la lista");

        builder.setTitle("Crear lista de reproducción")
                .setView(input)
                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Comprobar si la lista existe y añadirla
                        ListasReproduccion listas = dao.getListasReproduccion();
                        String nombre = input.getText().toString();
                        try {
                            // Si no existe otra con igual nombre
                            // se crea y se actualiza el fragmento listasDialogFragment.

                            listas.crearLista(nombre);
                            menuActivity.fragmentListasReproduccion.actualizar();

                        } catch (Exception e) {
                            // Si ya hay una lista con ese nombre notificarlo.
                            AlertDialog alertDialog = new AlertDialog.Builder(menuActivity).create();
                            alertDialog.setTitle("Error");
                            alertDialog.setMessage(e.getMessage());
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        }
                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
