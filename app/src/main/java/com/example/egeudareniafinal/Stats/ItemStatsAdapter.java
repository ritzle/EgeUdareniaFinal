package com.example.egeudareniafinal.Stats;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.egeudareniafinal.databinding.ItemStatsBinding;

import java.util.List;


public class ItemStatsAdapter extends RecyclerView.Adapter<ItemStatsAdapter.ViewHolder> {

    private final List<Note> data;
    private final NoteClickListener callback;

    public ItemStatsAdapter(List<Note> data, NoteClickListener callback) {
        this.data = data;
        this.callback = callback;
    }


    public void additem(Note note){
        data.add(note);
        notifyDataSetChanged();
    }

    public void dellitem(){
        data.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemStatsBinding item = ItemStatsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(item.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemStatsBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemStatsBinding.bind(itemView);
        }

        public void bind(Note note) {
            binding.getRoot().setOnClickListener(v -> callback.onClick(note));
            binding.title.setText(note.getTitle());
            binding.account.setText(note.getaccount());

        }
    }

    public interface NoteClickListener {
        void onClick(Note note);
    }

}
