package com.example.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    SeekBar seekVolume;
    Button  bPlay,bPause,bReplay;
    AudioManager audiomanager;

    private MediaPlayer music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarseekbar();

        music = MediaPlayer.create(getApplicationContext(),R.raw.musica);

        bPlay = (Button) findViewById(R.id.buttonPlay);

        bPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executasom(view);
            }
        });

        bPause = (Button) findViewById(R.id.buttonPause);

        bPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pausasom(view);
            }
        });


        bReplay = (Button) findViewById(R.id.buttonReplay);

        bReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartsom(view);
            }
        });





    }

    private void inicializarseekbar()
    {
        seekVolume = (SeekBar) findViewById(R.id.seekBarVolume);

        //config o audio manager
        audiomanager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // recupera volume maximo e atual
        int volumeMaximo = audiomanager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        int volumeAtual = audiomanager.getStreamVolume(AudioManager.STREAM_MUSIC);

        seekVolume.setMax(volumeMaximo);

        seekVolume.setProgress(volumeAtual);

        seekVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

               audiomanager.setStreamVolume(audiomanager.STREAM_MUSIC,i,0);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }




    public void executasom(View view)
    {
        if(music != null) {
            music.start();
        }
    }

    public  void pausasom(View view)
    {
        if(music.isPlaying())
        {
            music.pause();
        }

    }

    public void restartsom(View view)
    {
        if(music.isPlaying())
        {
            music.stop();
            music = MediaPlayer.create(getApplicationContext(),R.raw.musica);
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        if(music.isPlaying())
        {
            music.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(music != null && music.isPlaying())
        {
            music.stop();
            music.release();
            music = null;
        }
    }
}