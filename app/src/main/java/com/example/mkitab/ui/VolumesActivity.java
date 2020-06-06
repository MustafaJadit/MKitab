package com.example.mkitab.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mkitab.R;
import com.example.mkitab.databinding.ActivityVolumesBinding;
import com.example.mkitab.util.Keys;
import com.example.mkitab.viewmodel.VolumesModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.security.Key;

public class VolumesActivity extends AppCompatActivity {


    private VolumesModel volumesModel;
    private String id;
    ActivityVolumesBinding viewDataBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getStringExtra("id");
        init();
    }

    private void onclickListener(ActivityVolumesBinding viewDataBinding) {
        viewDataBinding.playBtn.setOnClickListener((v) -> {
            v.setSelected(!v.isSelected());
            volumesModel.resume();
        });


    }

    private void init() {
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_volumes);
        ViewModelProvider viewModelProvider = new ViewModelProvider(this);
        volumesModel = viewModelProvider.get(VolumesModel.class);
        VolumesRecyclerAdapter volumesRecyclerAdapter = new VolumesRecyclerAdapter(this, volumesModel);
        volumesModel.setAdapter(volumesRecyclerAdapter, id);
        viewDataBinding.volumes.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        viewDataBinding.volumes.setLayoutManager(new LinearLayoutManager(this));
        viewDataBinding.volumes.setAdapter(volumesRecyclerAdapter);

        onclickListener(viewDataBinding);

        seekbarListener(viewDataBinding);

        volumesModel.loadData();


    }

    @Subscribe
    public void onMessageEvent(String message) {
        if (Keys.displayAudioController.equals(message)) {
            viewDataBinding.mediaController.setVisibility(View.VISIBLE);
        } else if (Keys.displayPlayIcon.equals(message)) {
            viewDataBinding.playBtn.setSelected(false);
        } else if (Keys.displayPauseIcon.equals(message)) {
            viewDataBinding.playBtn.setSelected(true);
        }
    }

    @Subscribe
    public void onMessage(Bundle bundle) {
        int result = 0;
        if ((result = bundle.getInt("audioDuration")) != 0) {
            viewDataBinding.seekbar.setMax(result);
            viewDataBinding.seekbar.setProgress(0);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void seekbarListener(ActivityVolumesBinding viewDataBinding) {
        viewDataBinding.seekbar.setProgress(0);
        viewDataBinding.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && volumesModel.getMediaPlayer() != null) {
                    //update the progress of audio
                    volumesModel.updateAudioProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


}
