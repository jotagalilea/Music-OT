package fdi.ucm.musicot;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.usuario_local.music_ot.R;

import fdi.ucm.musicot.Misc.Utils;
import fdi.ucm.musicot.Modelo.Artista;
import fdi.ucm.musicot.Modelo.DAO;
import fdi.ucm.musicot.Modelo.ListasReproduccion;

import java.util.List;

import static fdi.ucm.musicot.MenuActivity.menuActivity;


public class ListasReproduccionFragment extends Fragment {

    TableLayout tabla;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view;

        view = inflater.inflate(R.layout.fragment_listas_reproduccion, container, false);

        tabla = (TableLayout) view.findViewById(R.id.contenedor_listas_reproduccion);
        rellenarTablaInit(tabla, menuActivity);

        return view;
    }


    public void rellenarTablaInit(TableLayout tabla, MenuActivity menuActivity){

        tabla.setStretchAllColumns(true);

        TableRow fila = new TableRow(menuActivity);

        fila.setBackgroundResource(R.drawable.listabackground);
        TableLayout.LayoutParams tableParams;

        tableParams = new TableLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tableParams.setMargins(2, 5, 2, 0);

        fila.setLayoutParams(tableParams);
        tabla.addView(fila);

        /////////////////////////////// RELLENAR CON LISTAS DUMMY //////////////////////////////////////
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
        TableRow filaAddLista = new TableRow(menuActivity);

        fila.setBackgroundResource(R.drawable.listabackground);
        TableLayout.LayoutParams tableParams;

        tableParams = new TableLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tableParams.setMargins(2, 5, 2, 0);

        fila.setLayoutParams(tableParams);
        tabla.addView(fila);
    }



    private LinearLayout generateLinearLista(String nombreLista) {
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

            }
        });
        //tabla.addView(linearLayout);
        return linearLayout;
    }

}