package fdi.ucm.musicot;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.usuario_local.music_ot.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

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

        wb = (WebView) view.findViewById(R.id.webView_amazon);
        wb.getSettings().setJavaScriptEnabled(true);
        wb.setWebViewClient(new WebViewClient());
        wb.loadUrl("file:///android_asset/searchresults.html");
        wb.addJavascriptInterface(new WebViewBuyer(), "JSInterface");
        checkModoNoct(menuActivity.observer.getNightMode());

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
        public boolean getGetModoNoct(){
            return menuActivity.observer.getNightMode();
        }

        @JavascriptInterface
        public String getWebAmazon(String key){

            JSONObject res = new JSONObject();
            JSONArray ja = new JSONArray();
            int i = 0;

            Document doc = null;
            try {
                //doc = Jsoup.connect("http://en.wikipedia.org/").get();
                doc = Jsoup.connect("https://www.amazon.es/s/ref=nb_sb_noss_1?__mk_es_ES=ÅMÅŽÕÑ&url=search-alias%3Ddigital-music&field-keywords="+key).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Elements tracks = doc.select("#mp-itn b a");
            Elements tracks = doc.select(".s-music-track-row");
            try {

                for (Element elem: tracks) {
                    JSONObject jo = new JSONObject();
                    jo.put("titulo", elem.select("td.s-music-track-title > div > a").first().text());
                    jo.put("link", elem.select("td.s-music-track-title > div > a").first().attr("href"));
                    jo.put("duracion", elem.select("td.s-music-track-time > div > span").first().text());
                    jo.put("artista", elem.select("td.s-music-track-artist > div > span > a").first().text());

                    ja.put(jo);

                    i++;
                }

                res.put("elem", ja);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            System.out.println(res.toString());
            return res.toString();
        }

    }

    //OnNightModeEvent

    @Override
    public void toNightMode() {
        if(wb != null) {
            checkModoNoct(false);
        }
    }

    @Override
    public void toDayMode() {
        if(wb != null) {
            checkModoNoct(true);
        }
    }
}
