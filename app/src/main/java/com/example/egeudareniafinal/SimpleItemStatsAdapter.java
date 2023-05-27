package com.example.egeudareniafinal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.egeudareniafinal.Stats.ItemStatsAdapter;
import com.example.egeudareniafinal.Stats.Note;
import com.example.egeudareniafinal.databinding.ItemStatsBinding;
import com.example.egeudareniafinal.databinding.SimpleStatsItemBinding;

import java.util.List;

public class SimpleItemStatsAdapter extends RecyclerView.Adapter<SimpleItemStatsAdapter.ViewHolder> {

    private final List<Note> data;

    public SimpleItemStatsAdapter(List<Note> data) {
        this.data = data;
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
        SimpleStatsItemBinding item = SimpleStatsItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(item.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

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


    }

    public interface NoteClickListener {
        void onClick(Note note);
    }

}
