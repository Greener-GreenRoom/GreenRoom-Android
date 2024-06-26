package com.greener.presentation.ui.mypage.withdraw

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.greener.domain.model.mypage.WithdrawReason
import com.greener.presentation.R
import com.greener.presentation.databinding.ItemWithdrawReasonBinding

class WithdrawReasonRVAdapter(
    val items: List<WithdrawReason>,
    val showInputArea: () -> Unit,
    val hideInputArea: () -> Unit,
) : RecyclerView.Adapter<WithdrawReasonRVAdapter.WithdrawReasonViewHolder>() {

    private val selectedReasons = mutableSetOf<WithdrawReason>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WithdrawReasonViewHolder {
        val binding: ItemWithdrawReasonBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_withdraw_reason,
            parent,
            false,
        )
        return WithdrawReasonViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: WithdrawReasonViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            if (selectedReasons.contains(holder.binding.item)) {
                unselect(holder.binding)
            } else {
                select(holder.binding)
            }
        }
    }

    private fun select(binding: ItemWithdrawReasonBinding) {
        val context = binding.root.context
        selectedReasons.add(binding.item!!)
        binding.cvItemWithdrawReason.strokeColor = getColor(context, R.color.red100)
        binding.cvItemWithdrawReason.setCardBackgroundColor(
            getColor(
                context,
                R.color.withdraw_reason_selected_background,
            ),
        )

        binding.tvItemWithdrawReason.setTextColor(
            getColor(
                context,
                R.color.red100,
            ),
        )
        binding.ivWithdrawReasonCheck.visibility = View.VISIBLE
        if (binding.item == WithdrawReason.DIRECT_INPUT) {
            showInputArea()
        }
    }

    private fun unselect(binding: ItemWithdrawReasonBinding) {
        val context = binding.root.context
        selectedReasons.remove(binding.item)
        binding.cvItemWithdrawReason.strokeColor = getColor(context, R.color.gray200)
        binding.cvItemWithdrawReason.setCardBackgroundColor(getColor(context, R.color.white))
        binding.cvItemWithdrawReason.alpha = 1f

        binding.tvItemWithdrawReason.setTextColor(
            getColor(
                context,
                R.color.gray700,
            ),
        )
        binding.ivWithdrawReasonCheck.visibility = View.INVISIBLE
        if (binding.item == WithdrawReason.DIRECT_INPUT) {
            hideInputArea()
        }
    }

    fun getWithdrawReasons(): List<WithdrawReason> {
        return selectedReasons.toList()
    }

    inner class WithdrawReasonViewHolder(val binding: ItemWithdrawReasonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WithdrawReason) {
            binding.item = item
        }
    }
}
