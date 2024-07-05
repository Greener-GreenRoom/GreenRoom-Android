package com.greener.presentation.ui.community.suggest

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.greener.presentation.databinding.ItemNewSuggestedBinding

const val MAX_SIZE = 5

class NewSuggestedPlantsRVAdapter(private val newSuggestedPlants: List<Int>) :
    RecyclerView.Adapter<NewSuggestedPlantsRVAdapter.NewSuggestedPlantViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewSuggestedPlantViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return if (newSuggestedPlants.size > MAX_SIZE) {
            MAX_SIZE
        } else {
            newSuggestedPlants.size
        }
    }

    override fun onBindViewHolder(holder: NewSuggestedPlantViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    inner class NewSuggestedPlantViewHolder(val binding: ItemNewSuggestedBinding) :
        ViewHolder(binding.root) {

    }

}