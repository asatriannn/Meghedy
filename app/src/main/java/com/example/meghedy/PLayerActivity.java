package com.example.meghedy;

import static com.example.meghedy.MainActivity.musicModelList;

import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PLayerActivity extends AppCompatActivity {

    ImageView back_btn, current_cover, current_status, shuffle, prev_track, pause_btn, next_track, loop;
    TextView current_title, current_artist, played_time, duration_time;
    SeekBar seekbar;

    int position = -1;

    static Uri uri;
    static MediaPlayer mediaPlayer;

    private Handler handler = new Handler();

    static ArrayList<MusicModel> listSongs = new ArrayList<>();
    private Thread playThread, prevThread, nextThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        init();
        getIntentMethod();
        current_title.setText(listSongs.get(position).getTitle());
        current_artist.setText(listSongs.get(position).getArtist());
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mediaPlayer != null && fromUser){
                   mediaPlayer.seekTo(progress * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        PLayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null){
                    int mCurrentPosition = mediaPlayer.getCurrentPosition()/1000;
                    seekbar.setProgress(mCurrentPosition);
                    played_time.setText(formatedTime(mCurrentPosition));
                }
                handler.postDelayed(this, 1000);
            }
        });


    }

    @Override
    protected void onResume(){
        playThreadBtn();
        nextThreadBtn();
        prevThreadBtn();

        super.onResume();
    }

    private void prevThreadBtn() {
        prevThread = new Thread(){
            @Override
            public void run(){
                super.run();
                prev_track.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        prev_trackClicked();
                    }
                });
            }
        };
        prevThread.start();
    }

    private void prev_trackClicked(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position -1) < 0 ? listSongs.size() -1 : (position - 1));
            uri = Uri.parse(listSongs.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            current_title.setText(listSongs.get(position).getTitle());
            current_artist.setText(listSongs.get(position).getArtist());

            seekbar.setMax(mediaPlayer.getDuration() / 1000);

            PLayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition()/1000;
                        seekbar.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            pause_btn.setImageResource(R.drawable.pause_ic);
            mediaPlayer.start();
        }
        else{
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position -1) < 0 ? listSongs.size() -1 : (position - 1));
            uri = Uri.parse(listSongs.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            current_title.setText(listSongs.get(position).getTitle());
            current_artist.setText(listSongs.get(position).getArtist());

            seekbar.setMax(mediaPlayer.getDuration() / 1000);

            PLayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition()/1000;
                        seekbar.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            pause_btn.setImageResource(R.drawable.play_ic );
        }
    }

    private void nextThreadBtn() {
        nextThread = new Thread(){
            @Override
            public void run(){
                super.run();
                next_track.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        next_trackClicked();
                    }
                });
            }
        };
        nextThread.start();
    }

    private void  next_trackClicked(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position +1) % listSongs.size());
            uri = Uri.parse(listSongs.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            current_title.setText(listSongs.get(position).getTitle());
            current_artist.setText(listSongs.get(position).getArtist());

            seekbar.setMax(mediaPlayer.getDuration() / 1000);

            PLayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition()/1000;
                        seekbar.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            pause_btn.setImageResource(R.drawable.pause_ic);
            mediaPlayer.start();
        }
        else{
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position +1) % listSongs.size());
            uri = Uri.parse(listSongs.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            current_title.setText(listSongs.get(position).getTitle());
            current_artist.setText(listSongs.get(position).getArtist());

            seekbar.setMax(mediaPlayer.getDuration() / 1000);

            PLayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition()/1000;
                        seekbar.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            pause_btn.setImageResource(R.drawable.play_ic );
        }
    }

    private void playThreadBtn() {
        playThread = new Thread(){
            @Override
            public void run(){
                super.run();
                pause_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pause_btnClicked();
                    }
                });
            }
        };
        playThread.start();
    }

    private void pause_btnClicked(){
        if(mediaPlayer.isPlaying()){
            pause_btn.setImageResource(R.drawable.play_ic);
            mediaPlayer.pause();
            seekbar.setMax(mediaPlayer.getDuration() / 1000);

            PLayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition()/1000;
                        seekbar.setProgress(mCurrentPosition);
                   }
                    handler.postDelayed(this, 1000);
                }
            });
        }
        else{
            pause_btn.setImageResource(R.drawable.pause_ic);
            mediaPlayer.start();
            seekbar.setMax(mediaPlayer.getDuration()/1000);

            PLayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition()/1000;
                        seekbar.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
        }
    }

    private String formatedTime(int mCurrentPosition){
        String totalout =  "";
        String totalNew = "";
        String seconds = String.valueOf(mCurrentPosition%60);
        String minutes = String.valueOf(mCurrentPosition/60);

        totalout = minutes + ":" + seconds;
        totalNew = minutes + ":" + "0" + seconds;

        if(seconds.length() == 1){
            return totalNew;
        }
        else{
            return totalout;
        }
    }

    private void getIntentMethod(){
        position = getIntent().getIntExtra("position", -1);

        listSongs = musicModelList;

        if(listSongs != null){
            pause_btn.setImageResource(R.drawable.pause_ic);
            uri = Uri.parse(listSongs.get(position).getPath());
        }

        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
        }
        else{
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
        }

        seekbar.setMax(mediaPlayer.getDuration()/1000);
        metaData(uri);
    }

    private void init(){
        back_btn = findViewById(R.id.back_btn);
        current_cover = findViewById(R.id.current_cover);
        current_status = findViewById(R.id.current_status);
        shuffle = findViewById(R.id.shuffle);
        prev_track = findViewById(R.id.prev_track);
        pause_btn = findViewById(R.id.pause_btn);
        next_track = findViewById(R.id.next_track);
        loop = findViewById(R.id.loop);

        current_title = findViewById(R.id.current_title);
        current_artist = findViewById(R.id.current_artist);
        played_time = findViewById(R.id.played_time);
        duration_time = findViewById(R.id.duration);

        seekbar = findViewById(R.id.seekbar);

    }

    private void metaData(Uri uri){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri.toString());
        int duration = Integer.parseInt(listSongs.get(position).getDuration())/1000;
        duration_time.setText(formatedTime(duration));
        byte[] art = retriever.getEmbeddedPicture();
        if( art != null){
            Glide.with(this)
                    .asBitmap()
                    .load(art)
                    .into(current_cover);
        }
        else{
            Glide.with(this)
                    .asBitmap()
                    .load(R.drawable.music_default_bg)
                    .into(current_cover);
        }

    }
}