package com.emplomanage.staffhub.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.emplomanage.staffhub.R;
import com.emplomanage.staffhub.model.Item;

import java.util.ArrayList;
import java.util.List;
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<Item> items;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Item item);
    }

    public ItemAdapter(OnItemClickListener listener) {
        this.listener = listener;
        this.items = new ArrayList<>();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item currentItem = items.get(position);
        holder.bind(currentItem, listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView itemNameTextView;
        private TextView itemQuantityTextView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.item_name_text_view);
            itemQuantityTextView = itemView.findViewById(R.id.item_quantity_text_view);
        }

        public void bind(final Item item, final OnItemClickListener listener) {
            itemNameTextView.setText(item.getName());
            itemQuantityTextView.setText(String.valueOf(item.getQuantity()));
            itemView.setOnClickListener(v -> listener.onItemClick(item));
        }
    }
}
