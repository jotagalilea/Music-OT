package fdi.ucm.musicot;

import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.usuario_local.music_ot.R;

import java.net.URL;

import static fdi.ucm.musicot.MenuActivity.menuActivity;


public class BuscadorAmazonFragment extends Fragment {

    public BuscadorAmazonFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buscador_amazon, container, false);

        WebView wb = (WebView) view.findViewById(R.id.webView_amazon);

        wb.getSettings().setJavaScriptEnabled(true);
        wb.setWebViewClient(new WebViewClient());

        wb.loadUrl("http://musicot.esy.es");

        return view;
    }

}
