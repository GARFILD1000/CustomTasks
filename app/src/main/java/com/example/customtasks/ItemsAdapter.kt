package com.example.customtasks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class ItemsAdapter: RecyclerView.Adapter<ItemsAdapter.ItemsHolder>() {
    private var items : MutableList<Item> = LinkedList()

    var onItemClicked = fun(position: Int): Unit = null!!
    var onTaskStartClicked = fun(item: Item, isStarted: Boolean): Unit = null!!


    fun addItem(newItem : Item){
        items.add(newItem)
        notifyItemInserted(items.size-1)
    }

    fun removeAllItems(){
        val previousSize = items.size
        items.clear()
        notifyItemRangeRemoved(0, previousSize)
    }

    fun removeItemAt(position : Int){
        if(position < items.size) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun getItem(position : Int) : Item{
        return items[position]
    }

    fun setItem(position : Int, item : Item){
        if(position < items.size){
            items[position] = item
            notifyItemChanged(position)
        }
    }

    fun updateItems(newItems: MutableList<Item>){
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_item_view, parent, false)
        return ItemsHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemsHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ItemsHolder(private var view : View) : RecyclerView.ViewHolder(view){
        init{
            view.setOnClickListener{v -> onItemClicked(layoutPosition)}
        }

        val nameTextView : TextView by lazy(LazyThreadSafetyMode.NONE){
            view.findViewById<TextView>(R.id.itemNameTextView)
        }

        val taskStartButton : CheckBox by lazy(LazyThreadSafetyMode.NONE){
            view.findViewById<CheckBox>(R.id.taskStartButton)
        }


        fun bind(item : Item ){
            nameTextView.setText(item.name)
            val onCheckedChangeListener = CompoundButton.OnCheckedChangeListener{buttonView, isChecked -> onTaskStartClicked(item, isChecked)}
           // taskStartButton.set
        }
    }
}