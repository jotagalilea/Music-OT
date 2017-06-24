package fdi.ucm.musicot;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.usuario_local.music_ot.R;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fdi.ucm.musicot.Observers.OnNightModeEvent;

import static fdi.ucm.musicot.MenuActivity.menuActivity;


public class BuscadorAmazonFragment extends Fragment implements OnNightModeEvent{

    WebView wb;

    public BuscadorAmazonFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buscador_amazon, container, false);

        //if(wb == null) {
            wb = (WebView) view.findViewById(R.id.webView_amazon);
            wb.getSettings().setJavaScriptEnabled(true);
            wb.setWebViewClient(new WebViewClient());
            wb.loadUrl("file:///android_asset/searchresults.html");
            wb.addJavascriptInterface(new WebViewBuyer(), "JSInterface");
            checkModoNoct(menuActivity.observer.getNightMode());
        //}

        return view;
    }

    private void checkModoNoct(boolean toNightMode){
        wb.evaluateJavascript("checkModoNocturno("+toNightMode+")", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {}
        });
    }

    public class WebViewBuyer{

        @JavascriptInterface
        public void loadAmazonWeb(String link){
            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        @JavascriptInterface
        public String getWebAmazon(String key){
            String str = null;
            try {
                URL url =
                        new URL("https://www.amazon.es/s/ref=nb_sb_noss_1?__mk_es_ES=%C3%85M%C3%85%C5%BD%C3%95%C3%91&url=search-alias%3Ddigital-music&field-keywords="+key);
                URLConnection con = url.openConnection();
                Pattern p = Pattern.compile("text/html;\\s+charset=([^\\s]+)\\s*");
                Matcher m = p.matcher(con.getContentType());

                String charset = m.matches() ? m.group(1) : "ISO-8859-1";
                Reader r = new InputStreamReader(con.getInputStream(), charset);
                StringBuilder buf = new StringBuilder();
                while (true) {
                    int ch = r.read();
                    if (ch < 0)
                        break;
                    buf.append((char) ch);
                }
                str = buf.toString();
            }catch (Exception e){}

            return str;
        }
    }

    //OnNightModeEvent

    @Override
    public void toNightMode() {
        if(wb != null) {
            checkModoNoct(true);
        }
    }

    @Override
    public void toDayMode() {
        if(wb != null) {
            checkModoNoct(false);
        }
    }
}
