package com.example.egeudareniafinal.Stats.Simple;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.egeudareniafinal.R;
import com.example.egeudareniafinal.databinding.ItemFinishBinding;

import java.util.List;

public class ItemStatsSimpleAdapter extends RecyclerView.Adapter<ItemStatsSimpleAdapter.ViewHolder> {

   private final List<NoteSimple> data;

    public ItemStatsSimpleAdapter(List<NoteSimple> data) {
        this.data = data;
    }

    public void addItem(NoteSimple note){
        data.add(note);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFinishBinding item = ItemFinishBinding.inflate(LayoutInflater.from(parent.getContext()));
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

    static class ViewHolder extends  RecyclerView.ViewHolder{

        private final ItemFinishBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemFinishBinding.bind(itemView);
        }

        public void bind(NoteSimple note){
            binding.titleTextView.setText(note.getTitle());
            binding.descriptionTextView.setText(note.getDescription());
            binding.imageView.setImageResource(R.drawable.wrong);

        }
    }
}
