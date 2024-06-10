package com.greener.presentation.ui.mypage.level

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.greener.domain.model.GreenRoomItem
import com.greener.presentation.R
import com.greener.presentation.databinding.ItemNextRewardBinding

class NextRewardRVAdapter(
    private val items: List<GreenRoomItem>
) : RecyclerView.Adapter<NextRewardRVAdapter.NextRewardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NextRewardViewHolder {
        val binding: ItemNextRewardBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_next_reward,
            parent,
            false
        )
        return NextRewardViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: NextRewardViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class NextRewardViewHolder(val binding: ItemNextRewardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GreenRoomItem) {
            Log.d("확인","bind: $item")
            binding.greenRoomItem = item
        }

    }
}

