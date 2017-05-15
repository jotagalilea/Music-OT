package com.example.deekin.myapplication;

import android.content.res.Resources;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {


    MediaPlayer[] mediaPlayer;
    MediaPlayer cancionActual;
    TextView nombreCancion;
    ImageButton botonPlay;
    ProgressBar lineReproduccion;
    Field[] fields;
    LinearLayout mListaCancionContainer;
    LinearLayout lin[];
    MediaObserver procF;

    int currTema = 0;
    int maxTema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fields=R.raw.class.getDeclaredFields();
        Resources res = getResources();

        mListaCancionContainer = (LinearLayout)findViewById(R.id.lista_container);

        mediaPlayer = new MediaPlayer[fields.length-2];

        for(int i=0; i < mediaPlayer.length; i++){
            int id = getResources().getIdentifier(fields[i+1].getName(), "raw", MainActivity.this.getPackageName());

            mediaPlayer[i] = MediaPlayer.create(getApplicationContext(), id);
        }

        maxTema = mediaPlayer.length;

        botonPlay = (ImageButton) findViewById(R.id.playButton);
        lineReproduccion = (ProgressBar) findViewById(R.id.progressBar);

        nombreCancion = (TextView) findViewById(R.id.nombre_cancion);

        lineReproduccion.setVisibility(View.VISIBLE);

        cancionActual = mediaPlayer[0];
        nombreCancion.setText(fields[currTema+1].getName());

        fields = Arrays.copyOfRange(fields, 1, fields.length-1);
        rellenaLista();

    }

    private void rellenaLista(){

        lin = new LinearLayout[fields.length];
        TextView[] tituloCancion = new TextView[fields.length];

        for (int i=0; i<fields.length; i++ ) {

            tituloCancion[i] = new TextView(this);
            lin[i] = new LinearLayout(this);

            tituloCancion[i].setText(fields[i].getName());
            lin[i].setOnClickListener(new ClickListener(lin[i], i) {
                @Override
                public void onClick(View view) {
                    boolean rep;

                    cleanAllListBackground(lin);
                    view.setBackgroundColor(Color.CYAN);

                    rep = reproduccionCancionNumStop();
                    reproducirCancionNumRep(i, rep);

                }
            });
            lin[i].addView(tituloCancion[i]);
            lin[i].setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            mListaCancionContainer.addView(lin[i]);

            procF = new MediaObserver();
            procF.start();
        }

        lin[currTema].setBackgroundColor(Color.CYAN);

    }

    private void setProgressBar(int momentoActual, int duracionMax){


    }

    private void cleanAllListBackground(LinearLayout[] lin){

        for(LinearLayout ind: lin){
            ind.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    public float floatTodp(float num){

        Resources r = getResources();
        num = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, num, r.getDisplayMetrics());
        return num;
    }

    private boolean reproduccionCancionNumStop(){

        boolean rep = cancionActual.isPlaying();

        if(rep) {
            cancionActual.stop();
            cancionActual.prepareAsync();
        }

        return rep;

    }

    private void reproducirCancionNumRep(int num, boolean rep){

        currTema = num;

        cancionActual = mediaPlayer[currTema];
        nombreCancion.setText(fields[currTema].getName());

        if(rep){
            cancionActual.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    nombreCancion.setText(fields[currTema].getName() + "  DONE");
                    resetPlayButton();
                }
            });
        }
    }

    public void nextSong(View view){

        boolean rep = cancionActual.isPlaying();

        botonPlay.setImageResource(R.drawable.ic_stop_pause);

        cancionActual.stop();
        cancionActual.prepareAsync();

        currTema++;
        if(currTema >= maxTema) {
            currTema = 0;
        }

        cancionActual = mediaPlayer[currTema];
        lineReproduccion.setMax(cancionActual.getDuration());
        lineReproduccion.setProgress(0);
        nombreCancion.setText(fields[currTema].getName());

        if(rep){
            cancionActual.start();

            cancionActual.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    nombreCancion.setText(fields[currTema].getName() + "  DONE");
                    resetPlayButton();
                }
            });
        }
    }

    public void previousSong(View view){
        boolean rep = cancionActual.isPlaying();

        botonPlay.setImageResource(R.drawable.ic_stop_pause);

        cancionActual.stop();
        cancionActual.prepareAsync();

        currTema--;
        if(currTema < 0) {
            currTema = maxTema-1;
        }

        cancionActual = mediaPlayer[currTema];
        nombreCancion.setText(fields[currTema].getName());

        if(rep){
            cancionActual.start();

            cancionActual.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    nombreCancion.setText(fields[currTema].getName() + "  DONE");
                    resetPlayButton();
                }
            });
        }
    }

    boolean isPlaying;
    int pointPLaying;

    /*@Override
    protected void onPause() {
        super.onPause();

        isPlaying = cancionActual.isPlaying();

        if(isPlaying){

            pointPLaying = cancionActual.getCurrentPosition();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(isPlaying){
            cancionActual.seekTo(pointPLaying);
            cancionActual.start();
        }
    }*/

    public void playButton(View view){

        if(cancionActual.isPlaying()) {
            cancionActual.pause();
        }else {
            cancionActual.start();

        }

        resetPlayButton();

    }

    private void resetPlayButton(){
        botonPlay.setImageResource(cancionActual.isPlaying()?R.drawable.ic_stop_pause:R.drawable.ic_playing);
    }

    /////////////////////////////////////

    private class MediaObserver extends Thread {

        boolean keep = true;

        public void condBucle(boolean cond){
            keep = cond;
        }

        @Override
        public void run() {
            while(keep) {
                try {
                    Thread.sleep(750);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lineReproduccion.setMax(cancionActual.getDuration());
                lineReproduccion.setProgress(cancionActual.getCurrentPosition());
                
            }
        }
    }
}
