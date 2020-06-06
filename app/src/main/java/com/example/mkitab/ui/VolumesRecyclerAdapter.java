package com.example.mkitab.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mkitab.MApplication;
import com.example.mkitab.model.Networking;
import com.example.mkitab.model.entity.Volumes;
import com.example.mkitab.util.Keys;
import com.example.mkitab.util.MLog;
import com.example.mkitab.util.PixelUtil;
import com.example.mkitab.viewmodel.VolumesModel;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.Key;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VolumesRecyclerAdapter extends RecyclerView.Adapter<VolumesRecyclerAdapter.MViewHolder> {
    private static final String TAG = "VolumesRecyclerAdapter";
    private final Context context;
    private final VolumesModel viewModel;
    List<Volumes> result;

    public VolumesRecyclerAdapter(Context context, VolumesModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public VolumesRecyclerAdapter.MViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(android.R.layout.activity_list_item, parent, false);
        VolumesRecyclerAdapter.MViewHolder viewHolder = new VolumesRecyclerAdapter.MViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull VolumesRecyclerAdapter.MViewHolder holder, int position) {
        holder.textView.setText(result.get(position).getTitle());
        String path = result.get(position).getPath();
        String fileName = path.substring(path.lastIndexOf("/") + 1);
        final File[] file = {new File(context.getApplicationInfo().dataDir + "/" + result.get(position).getId() + "/" + fileName)};
        holder.icon.setImageResource(file[0].exists() ? android.R.drawable.checkbox_on_background : android.R.drawable.checkbox_off_background);
        holder.itemView.setOnClickListener((view) -> {

            EventBus.getDefault().post(Keys.displayAudioController);

            Networking networking = MApplication.getNetworking();
            if (file[0].exists() && file[0].isFile()) {
                viewModel.resume(file[0], result.get(position).getId() + "");
                return;
            }
            networking.getMP3(path, new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                    System.out.println(call.request().url());
                    try {
                        byte[] bytes = response.body().bytes();
                        file[0] = new File(context.getApplicationInfo().dataDir + "/" + result.get(position).getId());
                        file[0].mkdirs();
                        File file1 = new File(file[0], fileName);
                        file1.createNewFile();
                        Files.asByteSink(file1, FileWriteMode.APPEND).write(bytes);
                        MLog.log(TAG + " completed");
                        holder.icon.setImageResource(android.R.drawable.checkbox_on_background);
                        viewModel.resume(file1, result.get(position).getId() + "");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
//            nativeDownload(path);


        });
    }


    private void nativeDownload(String path) {
        final FileOutputStream[] fileOutputStream = {null};
        final InputStream[] inputStream = {null};
        //native download
        new Thread(() -> {
            try {
                URL url = new URL(path);
                URLConnection urlConnection = url.openConnection();
                urlConnection.connect();
                inputStream[0] = urlConnection.getInputStream();
                byte[] bytes = new byte[1000];
                int result = 0;
                fileOutputStream[0] = new FileOutputStream(context.getExternalFilesDir("mp3"));
                while ((result = inputStream[0].read(bytes)) > 0) {
                    fileOutputStream[0].write(bytes, 0, result);
                }

//                while ((line = bufferedReader.readLine()) != null) {
//                    stringBuffer.append(line);
//                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream[0].close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        fileOutputStream[0].close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (result != null && !result.isEmpty()) {
            count = result.size();
        }
        return count;
    }

    static class MViewHolder extends RecyclerView.ViewHolder {

        public final TextView textView;
        public final ImageView icon;

        public MViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
            int i = 10 * PixelUtil.dpToPx(1);
            textView.setPadding(i, i, i, i);
            icon = itemView.findViewById(android.R.id.icon);
        }
    }

    public void update(List<Volumes> volumes) {
        this.result = volumes;
        notifyDataSetChanged();
    }
}
