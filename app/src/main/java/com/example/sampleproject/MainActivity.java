package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("RecyclerView","start init");
        initRecyclerView();
        Log.d("RecyclerView","end init");
        addSampleElements();
        Log.d("RecyclerView","elements added");
    }

    private void addSampleElements(){
        itemsAdapter.addItem(new Item("Задание 1","Описание задания 1"));
        itemsAdapter.addItem(new Item("Задание 2","Описание задания 2"));
        itemsAdapter.addItem(new Item("Задание 3","Описание задания 3"));
    }

    private void initRecyclerView(){
        recyclerView = findViewById(R.id.itemsView);
        itemsAdapter = new ItemsAdapter();
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(itemsAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

}
