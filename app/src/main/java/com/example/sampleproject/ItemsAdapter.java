package com.example.sampleproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsHolder> {

    private List<Item> items = new LinkedList<>();

    public ItemsAdapter(){

    }

    public void addItems(List<Item> newItems){
        items.addAll(newItems);
    }

    public void addItem(Item item){
        items.add(item);
    }

    public void removeAllItems(){
        items.clear();
    }

    @NonNull
    @Override
    public ItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_view, parent, false);
        return new ItemsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ItemsHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView dataTextView;

        private ItemsHolder(View view){
            super(view);
            nameTextView = view.findViewById(R.id.itemNameTextView);
            dataTextView = view.findViewById(R.id.itemDataTextView);
        }

        public void bind(Item item){
            nameTextView.setText(item.getName());
            dataTextView.setText(item.getData());

        }


    }
}
