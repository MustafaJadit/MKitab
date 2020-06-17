package com.kodyuzz.kitabim.ui;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kodyuzz.kitabim.R;
import com.kodyuzz.kitabim.model.entity.Volumes;
import com.kodyuzz.kitabim.util.Keys;
import com.kodyuzz.kitabim.util.PixelUtil;
import com.kodyuzz.kitabim.viewmodel.VolumesModel;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class VolumesRecyclerAdapter extends RecyclerView.Adapter<VolumesRecyclerAdapter.MViewHolder> {
    private static final String TAG = "VolumesRecyclerAdapter";
    private final Context context;
    private final VolumesModel viewModel;
    private final VolumesActivityI activityContract;
    List<Volumes> result;
    private String bookId;
    private int currentPlayingPosition;


    public VolumesRecyclerAdapter(Context context, VolumesModel viewModel, VolumesActivityI activityContract) {
        this.context = context;
        this.viewModel = viewModel;
        this.activityContract = activityContract;
    }

    @NonNull
    @Override
    public VolumesRecyclerAdapter.MViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.detail_item, parent, false);
        VolumesRecyclerAdapter.MViewHolder viewHolder = new VolumesRecyclerAdapter.MViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull VolumesRecyclerAdapter.MViewHolder holder, int position) {
        holder.textView.setText(result.get(position).getTitle().trim());
        String path = result.get(position).getPath();
        String fileName = path.substring(path.lastIndexOf("/") + 1);
        File file = new File(context.getApplicationInfo().dataDir + "/" + bookId + "/" + fileName);
        holder.icon.setImageResource(file.exists() ? android.R.drawable.checkbox_on_background : android.R.drawable.checkbox_off_background);
        holder.itemView.setOnClickListener((view) -> {

            EventBus.getDefault().post(Keys.displayAudioController);

            openAudioFile(file, holder, fileName, position);


        });
    }

    public void openAudioFile(File file, @NonNull MViewHolder holder, String fileName, int position) {
//        Networking networking = MyApplication.getNetworking();
        if (file.exists() && file.isFile()) {
            viewModel.resume(file, position);
            return;
        }


        //  (first way)      loadFileViaRetrofit();

        //  (second way) loadFileViaAsyncTask
        file = new File(context.getApplicationInfo().dataDir + "/" + bookId);
        file.mkdirs();
        file = new File(file, fileName);
        try {
            file.createNewFile();
            DownloadTask downloadTask = new DownloadTask(context, file, position, holder);
            downloadTask.execute(result.get(position).getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public class DownloadTask extends AsyncTask<String, Integer, String> {

        private final int position;
        private final MViewHolder holder;
        private Context context;
        private File targetFile;

        @Override
        protected String doInBackground(String... murl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;

            try {
                URL url = new URL(murl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }
                Log.i("DownloadTask", "Response " + connection.getResponseCode());

                int fileLength = connection.getContentLength();

                input = connection.getInputStream();
                output = new FileOutputStream(targetFile, false);

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    if (fileLength > 0)
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }

            return null;
        }

        DownloadTask(Context context, File targetFile, int position, MViewHolder holder) {
            this.context = context;
            this.targetFile = targetFile;
            this.position = position;
            this.holder = holder;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            activityContract.getProgressBar().setIndeterminate(false);
            activityContract.getProgressBar().setProgress(progress[0]);
            activityContract.getProgressText().setText(progress[0] + " %");
        }

        @Override
        protected void onPostExecute(String result) {

            EventBus.getDefault().post(Keys.loaded);
            if (result != null)
                Toast.makeText(context, "Download error: " + VolumesRecyclerAdapter.this.result, Toast.LENGTH_LONG).show();
            else {
                viewModel.resume(targetFile, position);
                notifyItemChanged(position);
                Toast.makeText(context, "File Downloaded", Toast.LENGTH_SHORT).show();
            }

            if (holder != null)
                holder.icon.setImageResource(android.R.drawable.checkbox_on_background);


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            EventBus.getDefault().post(Keys.loading);
            activityContract.getProgressBar().setProgress(0);
            activityContract.getProgressText().setText("0 %");

        }
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
            textView = itemView.findViewById(R.id.text1);
            int i = 10 * PixelUtil.dpToPx(1);
            textView.setPadding(i, i, i, i);
            icon = itemView.findViewById(R.id.icon);
        }
    }

    public void update(List<Volumes> volumes, String bookId) {
        this.result = volumes;
        this.bookId = bookId;
        notifyDataSetChanged();
    }


    public int getCurrentPlayingPosition() {
        return currentPlayingPosition;
    }

    public void setCurrentPlayingPosition(int currentPlayingPosition) {
        this.currentPlayingPosition = currentPlayingPosition;
    }

    //one of the option
    private void loadFileViaRetrofit() {
        //change this network interaction
//        networking.getMP3(result.get(position).getPath(), new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    byte[] bytes = response.body().bytes();
//                    File file = new File(context.getApplicationInfo().dataDir + "/" + bookId);
//                    file.mkdirs();
//                    file = new File(file, fileName);
//                    file.createNewFile();
//                    Files.asByteSink(file, FileWriteMode.APPEND).write(bytes);
//                    if (holder != null)
//                        holder.icon.setImageResource(android.R.drawable.checkbox_on_background);
//
//                    viewModel.resume(file, position);
//                    notifyItemChanged(position);
//
//                    EventBus.getDefault().post(Keys.loaded);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//                EventBus.getDefault().post(Keys.loaded);
//
//            }
//        });
    }
}
