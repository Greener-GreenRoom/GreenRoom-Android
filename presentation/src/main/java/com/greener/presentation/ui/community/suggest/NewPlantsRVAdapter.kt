package com.greener.presentation.ui.community.suggest

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.greener.presentation.databinding.ItemNewPlantBinding

class NewPlantsRVAdapter(private val newPlants: List<Int>) :
    RecyclerView.Adapter<NewPlantsRVAdapter.NewPlantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewPlantViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = newPlants.size

    override fun onBindViewHolder(holder: NewPlantViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
    inner class NewPlantViewHolder(val binding: ItemNewPlantBinding) : ViewHolder(binding.root) {

    }
}