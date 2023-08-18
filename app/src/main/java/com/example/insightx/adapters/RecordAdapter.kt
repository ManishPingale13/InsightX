package com.example.insightx.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.insightx.R
import com.example.insightx.data.retrofit.model.Record
import com.example.insightx.databinding.RecorditemBinding

@SuppressLint("SetTextI18n")
class RecordAdapter(val context: Context, val itemClickListener: (Record) -> Unit) :
    ListAdapter<Record, RecordAdapter.RecordViewHolder>(DiffCallBack()) {

    inner class RecordViewHolder(private val binding: RecorditemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: Record) {
            binding.apply {
                root.setOnClickListener {
                    itemClickListener(item)
                }
                if (item.status!!.substring(0, 2) == "No") {
                    cardStatusImg.layoutParams.width = dpToPx(37)
                    cardStatusImg.layoutParams.height = dpToPx(37)
                    cardStatusImg.requestLayout()
                    cardStatusImg.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.success
                        )
                    )
                }

                val endIndex = item.status.indexOf("(")
                cardRecordName.text = item.machine_name
                cardErrorType.text = item.status.substring(0, endIndex)
                cardProbability.text = item.status.substring(endIndex + 1, item.status.length - 1)

            }
        }

    }

    fun dpToPx(dp: Int): Int {
        val density = Resources.getSystem().displayMetrics.density
        return (dp * density + 0.5f).toInt()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        return RecordViewHolder(
            RecorditemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class DiffCallBack : DiffUtil.ItemCallback<Record>() {
    override fun areItemsTheSame(oldItem: Record, newItem: Record) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Record, newItem: Record) =
        oldItem == newItem

}
