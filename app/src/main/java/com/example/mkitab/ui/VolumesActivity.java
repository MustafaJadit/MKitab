package com.example.mkitab.ui;

import android.os.Bundle;
import android.os.Handler;
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
import com.example.mkitab.util.NumToTime;
import com.example.mkitab.viewmodel.VolumesModel;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class VolumesActivity extends AppCompatActivity {


    Handler handler = new Handler();
    private VolumesModel viewModel;
    private String id, title;
    ActivityVolumesBinding viewDataBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        init();
    }

    private void onclickListener(ActivityVolumesBinding viewDataBinding) {
        viewDataBinding.playBtn.setOnClickListener((v) -> {
            viewModel.resume();
//            if (volumesModel.getFile() != null) {
//                v.setSelected(!v.isSelected());
//            }


        });

        viewDataBinding.left.setOnClickListener((v) -> {
            viewModel.previous();
        });

        viewDataBinding.right.setOnClickListener((v) -> {
            viewModel.next();
        });

    }

    private void init() {
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_volumes);
        ViewModelProvider viewModelProvider = new ViewModelProvider(this);
        viewModel = viewModelProvider.get(VolumesModel.class);
        VolumesRecyclerAdapter volumesRecyclerAdapter = new VolumesRecyclerAdapter(this, viewModel);
        viewModel.setAdapter(volumesRecyclerAdapter, id);
        viewDataBinding.volumes.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        viewDataBinding.volumes.setLayoutManager(new LinearLayoutManager(this));
        viewDataBinding.volumes.setAdapter(volumesRecyclerAdapter);

        onclickListener(viewDataBinding);

        viewModel.loadData();
        seekbarListener(viewDataBinding);

        viewDataBinding.title.setText(title);
    }

    @Subscribe
    public void onMessageEvent(String message) {
        if (Keys.displayAudioController.equals(message)) {
            viewDataBinding.mediaController.setVisibility(View.VISIBLE);
        } else if (Keys.displayPlayIcon.equals(message)) {
            viewDataBinding.playBtn.setSelected(false);
            viewDataBinding.slidingUpPanel.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else if (Keys.displayPauseIcon.equals(message)) {
            viewDataBinding.playBtn.setSelected(true);
            viewDataBinding.slidingUpPanel.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
        }
    }

    @Subscribe
    public void onMessage(Bundle bundle) {
        final int[] result = {0};
        handler.post(() -> {
            if ((result[0] = bundle.getInt(Keys.audioDuration)) != 0) {
                viewDataBinding.seekbar.setMax(result[0]);
                viewDataBinding.seekbar.setProgress(0);
                viewDataBinding.title.setText(bundle.getString(Keys.audioTitle));
                viewDataBinding.duration.setText(NumToTime.getTimeFromNum(result[0]));
            } else if ((result[0] = bundle.getInt(Keys.audioProgress)) != 0) {
                viewDataBinding.seekbar.setProgress(result[0]);
                viewDataBinding.audioTime.setText(NumToTime.getTimeFromNum(result[0]));
            }
        });

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
                if (fromUser && viewModel.getMediaPlayer() != null) {
                    //update the progress of audio
                    viewModel.updateAudioProgress(progress);
                    viewDataBinding.audioTime.setText(NumToTime.getTimeFromNum(progress));
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.destroyMediaPlayer();
    }
}
