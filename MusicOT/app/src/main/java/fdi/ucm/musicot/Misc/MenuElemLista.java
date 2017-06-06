package fdi.ucm.musicot.Misc;

import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.usuario_local.music_ot.R;

import static fdi.ucm.musicot.MenuActivity.menuActivity;

/**
 * Created by Julio on 06/06/2017.
 */

public class MenuElemLista implements PopupMenu.OnMenuItemClickListener {

    public static PopupMenu generarMenu(View v){
        PopupMenu popup = new PopupMenu(menuActivity, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu, popup.getMenu());
        // No sé si esto está bien
        //popup.setOnMenuItemClickListener();
        return popup;
        //  RECUERDA HACER popup.show() DESPUÉS DE GENERAR EL MENÚ.
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        // Generar submenú con las listas de reproducción disponibles.

        return true;
    }

}
