package com.example.egeudareniafinal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FinishAdapter extends RecyclerView.Adapter<FinishAdapter.FinishViewHolder> {

    private List<FinishItem> mFinishItems;

    public static class FinishViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTitleTextView;
        public TextView mDescriptionTextView;

        public FinishViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
            mTitleTextView = itemView.findViewById(R.id.title_text_view);
            mDescriptionTextView = itemView.findViewById(R.id.description_text_view);
        }
    }

    public FinishAdapter(List<FinishItem> finishItems) {
        mFinishItems = finishItems;
    }

    @Override
    public FinishViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_finish, parent, false);
        return new FinishViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FinishViewHolder holder, int position) {
        FinishItem currentItem = mFinishItems.get(position);

        // установка значений в элементы списка
        holder.mImageView.setImageResource(currentItem.getmImageResource());
        holder.mTitleTextView.setText(currentItem.getmTitle());
        holder.mDescriptionTextView.setText(currentItem.getmDescription());
    }

    @Override
    public int getItemCount() {
        return mFinishItems.size();
    }

}