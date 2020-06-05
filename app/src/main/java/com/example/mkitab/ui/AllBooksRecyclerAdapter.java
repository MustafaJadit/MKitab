package com.example.mkitab.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mkitab.model.entity.AllBooks;

public class AllBooksRecyclerAdapter extends RecyclerView.Adapter<AllBooksRecyclerAdapter.AllBooksViewHolder> {

    private final Context context;
    AllBooks result;

    public AllBooksRecyclerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public AllBooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_activated_1, parent, false);
        AllBooksViewHolder allBooksViewHolder = new AllBooksViewHolder(inflate);
        return allBooksViewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull AllBooksViewHolder holder, int position) {
        holder.textView.setText(result.get_$1().get(position).getTitle());
        holder.itemView.setOnClickListener((view) -> {
            Intent intent = new Intent(context, VolumesActivity.class);
            intent.putExtra("id", result.get_$1().get(position).getId() + "");
            Bundle bundle = new Bundle();
            bundle.putString("id", result.get_$1().get(position).getId() + "");
            intent.putExtras(bundle);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (result != null && result.get_$1() != null && !result.get_$1().isEmpty()) {
            count = result.get_$1().size();
        }
        return count;
    }

    static class AllBooksViewHolder extends RecyclerView.ViewHolder {

        public final TextView textView;

        public AllBooksViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }

    public void update(AllBooks allBooks) {
        this.result = allBooks;
        notifyDataSetChanged();
    }
}
