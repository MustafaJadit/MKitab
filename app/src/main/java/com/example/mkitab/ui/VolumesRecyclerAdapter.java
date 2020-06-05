package com.example.mkitab.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mkitab.model.entity.Volumes;

import java.util.List;

public class VolumesRecyclerAdapter extends RecyclerView.Adapter<VolumesRecyclerAdapter.MViewHolder> {

    private final Context context;
    List<Volumes> result;

    public VolumesRecyclerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public VolumesRecyclerAdapter.MViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_activated_1, parent, false);
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
        holder.itemView.setOnClickListener((view) -> {
            Intent intent = new Intent(context, VolumesActivity.class);
        });
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

        public MViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }

    public void update(List<Volumes> volumes) {
        this.result = volumes;
        notifyDataSetChanged();
    }
}
