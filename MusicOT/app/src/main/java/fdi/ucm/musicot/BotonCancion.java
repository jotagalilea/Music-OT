package fdi.ucm.musicot;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;

import com.example.usuario_local.music_ot.R;

import fdi.ucm.musicot.Modelo.Cancion;

import static fdi.ucm.musicot.MenuActivity.menuActivity;

/**
 * Created by Julio on 08/06/2017.
 *
 * Se ha creado esta clase con el objetivo de guardar la referencia a la canción
 * seleccionada para añadir a una de las listas de reproducción.
 */

public class BotonCancion extends AppCompatImageButton{

    private final Cancion cancion;

    public BotonCancion(MenuActivity menuActivity, final Cancion cancion) {
        super(menuActivity);
        this.cancion = cancion;
        this.setImageResource(R.mipmap.crear_icon);
        this.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Mostrar listas a las que se puede añadir en un dialog
                mostrarDialogoListas();
            }
        });
    }

    /**
     * La canción en la que está el botón se pasa al diálogo para añadirla
     * a la lista que seleccione el usuario.
     */
    private void mostrarDialogoListas(){
        FragmentTransaction ft = menuActivity.getFragmentManager().beginTransaction();
        Fragment prev = menuActivity.getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        DialogFragment df = ListasDialogFragment.newInstance(cancion);
        df.show(ft, "dialog");
    }


}
