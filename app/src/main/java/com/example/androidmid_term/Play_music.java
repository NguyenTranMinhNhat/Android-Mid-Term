package com.example.androidmid_term;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class Play_music extends AppCompatActivity {

    TextView txt_begin;
    TextView txt_end;
    SeekBar seek_song;
    ImageView btn_play;
    ImageView img_cd;
    MediaPlayer md;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        setControl();
        init();
        setEvent();
    }

    private void setEvent() {
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (md.isPlaying()) {
                    md.pause();
                    btn_play.setImageResource(R.drawable.play_button);
                    img_cd.clearAnimation();
                } else {
                    md.start();
                    btn_play.setImageResource(R.drawable.pause_button);
                    img_cd.startAnimation(animation);
                }

            }
        });
        seek_song.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                md.seekTo(seek_song.getProgress());
            }
        });
    }

    private void init() {
        md = new MediaPlayer();
        try {
            String url = "https://minhtri130699.000webhostapp.com/Music/";
            int id = 2;
            url = url + id + ".mp3";
            md.setAudioStreamType(AudioManager.STREAM_MUSIC);
            md.setDataSource(url);
            md.prepare();
        } catch (IOException e) {
            Toast.makeText(this, "This song not found on server", Toast.LENGTH_SHORT).show();
        }
        setTimeTotal();
        update_time_song();
        animation = AnimationUtils.loadAnimation(this, R.anim.disk_rotate);
    }

    private void setControl() {
        txt_begin = findViewById(R.id.txt_begin);
        txt_end = findViewById(R.id.txt_end);
        seek_song = findViewById(R.id.seek_song);
        txt_begin = findViewById(R.id.txt_begin);
        btn_play = findViewById(R.id.btn_play);
        img_cd = findViewById(R.id.img_cd);
    }

    private void setTimeTotal() {
        SimpleDateFormat myformat = new SimpleDateFormat("mm:ss");
        txt_end.setText(myformat.format(md.getDuration()) + "");
        seek_song.setMax(md.getDuration());
    }

    private void update_time_song() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat myformat = new SimpleDateFormat("mm:ss");
                txt_begin.setText(myformat.format(md.getCurrentPosition()));
                seek_song.setProgress(md.getCurrentPosition());

                //check end song -> loop
                md.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        md.seekTo(0);
                        md.start();
                    }
                });

                handler.postDelayed(this, 500);
            }
        }, 100);
    }
}