package com.example.mkitab.viewmodel;

import android.media.MediaPlayer;

import androidx.lifecycle.ViewModel;

import com.example.mkitab.MApplication;
import com.example.mkitab.model.Networking;
import com.example.mkitab.model.entity.Volumes;
import com.example.mkitab.ui.VolumesRecyclerAdapter;

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
    private boolean firstLoaded;
    private File file;
    private int currentPosition;


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

    public void resume(File file) {
        this.file = file;

        try {
            if (mediaPlayer.isPlaying()) {
                pause();
                return;
            }

            if (!firstLoaded) {
                mediaPlayer.setDataSource(file.getPath());
                mediaPlayer.prepare();
            } else {
                mediaPlayer.seekTo(currentPosition);
            }

            mediaPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void pause() {
        mediaPlayer.pause();

        currentPosition = mediaPlayer.getCurrentPosition();
        firstLoaded = true;
    }

    public File getFile() {
        return file;
    }
}
