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
    private String bookId;

    MediaPlayer mediaPlayer = new MediaPlayer();
    private boolean haveLoaded;
    private File file;
    private int currentPositionOfMediaPlayer;
    private String currentAudioId = "-1";
    // toven is id
    private String episodeId;
    private boolean stopTimer;
    List<Volumes> list;
    private String title;
    private int currentPlayingPosition;

    public File getFile() {
        return file;
    }

    public void setAdapter(VolumesRecyclerAdapter adapter, String bookId) {
        this.adapter = adapter;
        this.bookId = bookId;
    }


    public void loadData() {
        Networking instance = MApplication.getNetworking();
        instance.getVolumes(bookId, new Callback<List<Volumes>>() {
            @Override
            public void onResponse(Call<List<Volumes>> call, Response<List<Volumes>> response) {
                list = response.body();
                adapter.update(list, bookId);
            }

            @Override
            public void onFailure(Call<List<Volumes>> call, Throwable t) {

            }
        });
    }

    public void previous() {
        String absolutePath = file.getAbsolutePath();
        int tempPosition = adapter.getCurrentPlayingPosition();
        String path = absolutePath.substring(0, absolutePath.lastIndexOf("_") + 1) + (tempPosition < 10 ? "0" + tempPosition : tempPosition) + ".mp3";
        String fileName = path.substring(path.lastIndexOf("/") + 1);
        file = new File(path);
        //change the method of getting title
        adapter.openAudioFile(file, null, fileName, adapter.getCurrentPlayingPosition() - 1);
    }

    public void next() {

        String absolutePath = file.getAbsolutePath();
        int tempPosition = adapter.getCurrentPlayingPosition() + 2;
        String path = absolutePath.substring(0, absolutePath.lastIndexOf("_") + 1) + (tempPosition < 10 ? "0" + tempPosition : tempPosition) + ".mp3";
        String fileName = path.substring(path.lastIndexOf("/") + 1);
        file = new File(path);
        adapter.openAudioFile(file, null, fileName, adapter.getCurrentPlayingPosition() + 1);
    }

    public void resume() {
        resume(file, adapter.getCurrentPlayingPosition());
    }

    // delete episodeId field if possible
    public void resume(File file, int currentPlayingPosition) {
        if (file == null) return;
        this.file = file;
        this.title = list.get(currentPlayingPosition).getTitle();
        this.episodeId = list.get(currentPlayingPosition).getId() + "";
        try {
            if (mediaPlayer.isPlaying()) {
                stopTimer = true;
                if (currentAudioId.equals(episodeId)) {
                    mediaPlayer.pause();
                    currentPositionOfMediaPlayer = mediaPlayer.getCurrentPosition();
                    EventBus.getDefault().post(Keys.displayPlayIcon);
                    return;
                } else {
                    mediaPlayer.reset();
                    haveLoaded = false;
                }
            }
            // launch different audio
            if (!haveLoaded || !currentAudioId.equals(episodeId)) {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(file.getPath());
                mediaPlayer.prepare();
                haveLoaded = true;
                currentAudioId = episodeId;

                //update duration on UI
                Bundle bundle = new Bundle();
                bundle.putInt(Keys.audioDuration, mediaPlayer.getDuration());
                bundle.putString(Keys.audioTitle, title);
                EventBus.getDefault().post(bundle);
            } else { //launch same audio again
                mediaPlayer.seekTo(currentPositionOfMediaPlayer);
            }
            EventBus.getDefault().post(Keys.displayPauseIcon);
            adapter.setCurrentPlayingPosition(currentPlayingPosition);
            mediaPlayer.start();


            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    EventBus.getDefault().post(Keys.displayPlayIcon);
                }
            });

            //add timer in separated thread
            addTimer();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addTimer() {
        stopTimer = false;
        currentPositionOfMediaPlayer = mediaPlayer.getCurrentPosition();
        int duration = mediaPlayer.getDuration();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (mediaPlayer != null && mediaPlayer.isPlaying() && currentPositionOfMediaPlayer < duration) {
                    try {
                        if (stopTimer) break;
                        Thread.sleep(200);

                        currentPositionOfMediaPlayer = mediaPlayer.getCurrentPosition();
                    } catch (Exception e) {
                        return;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putInt(Keys.audioProgress, currentPositionOfMediaPlayer);
                    EventBus.getDefault().post(bundle);


                }
            }
        };
        new Thread(runnable).start();

    }


    public void updateAudioProgress(int progress) {
        if (progress >= 0) {
            mediaPlayer.seekTo(progress);
            mediaPlayer.start();
            EventBus.getDefault().post(Keys.displayPauseIcon);
        }

    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void destroyMediaPlayer() {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }

}
