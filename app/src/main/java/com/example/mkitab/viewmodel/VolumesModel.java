package com.example.mkitab.viewmodel;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.lifecycle.ViewModel;

import com.example.mkitab.MApplication;
import com.example.mkitab.model.Networking;
import com.example.mkitab.model.entity.Volumes;
import com.example.mkitab.ui.VolumesRecyclerAdapter;
import com.example.mkitab.util.Keys;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VolumesModel extends ViewModel {

    private VolumesRecyclerAdapter adapter;
    private String id;

    MediaPlayer mediaPlayer = new MediaPlayer();
    private boolean haveLoaded;
    private File file;
    private int currentPosition;
    private String currentAudioToken = "-1";
    private String token;


    public void setAdapter(VolumesRecyclerAdapter adapter, String id) {
        this.adapter = adapter;
        this.id = id;
    }


    public void loadData() {
        Networking instance = MApplication.getNetworking();
        instance.getVolumes(id, new Callback<List<Volumes>>() {
            @Override
            public void onResponse(Call<List<Volumes>> call, Response<List<Volumes>> response) {
                List<Volumes> body = response.body();
                adapter.update(body);
            }

            @Override
            public void onFailure(Call<List<Volumes>> call, Throwable t) {

            }
        });
    }

    public void previous() {

    }

    public void next() {

    }

    public void resume() {
        resume(file, token);
    }

    public void resume(File file, String token) {
        this.file = file;
        this.token = token;
        try {
            if (mediaPlayer.isPlaying()) {
                if (currentAudioToken.equals(token)) {
                    mediaPlayer.pause();
                    currentPosition = mediaPlayer.getCurrentPosition();
                    EventBus.getDefault().post(Keys.displayPlayIcon);
                    return;
                } else {
                    mediaPlayer.reset();
                    haveLoaded = false;
                }
            }
            // launch different audio
            if (!haveLoaded || !currentAudioToken.equals(token)) {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(file.getPath());
                mediaPlayer.prepare();
                haveLoaded = true;
                currentAudioToken = token;

                //update duration on UI
                Bundle bundle = new Bundle();
                bundle.putInt("audioDuration", mediaPlayer.getDuration());
                EventBus.getDefault().post(bundle);
            } else { //launch same audio again
                mediaPlayer.seekTo(currentPosition);
            }
            EventBus.getDefault().post(Keys.displayPauseIcon);


            mediaPlayer.start();

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    EventBus.getDefault().post(Keys.displayPlayIcon);
                    mediaPlayer.release();
                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void updateAudioProgress(int progress) {
        mediaPlayer.seekTo(progress);
        mediaPlayer.start();
        EventBus.getDefault().post(Keys.displayPauseIcon);
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}
