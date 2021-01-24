package com.bunbeauty.papakarlo.ui.cafe_list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bunbeauty.papakarlo.data.model.cafe.Cafe
import com.bunbeauty.papakarlo.databinding.ElementCafeBinding
import com.bunbeauty.papakarlo.view_model.CafeListViewModel
import javax.inject.Inject

class CafeAdapter @Inject constructor() :
    ListAdapter<Cafe, CafeAdapter.CafeViewHolder>(CafeDiffUtilCallback()) {

    lateinit var cafeListViewModel: CafeListViewModel

    override fun onBindViewHolder(holder: CafeViewHolder, position: Int) {
        holder.binding?.cafe = getItem(position)
        holder.binding?.cafeListViewModel = cafeListViewModel
        holder.setCafeClickListener(getItem(position))
        Log.d("test", "onBindViewHolder")
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): CafeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ElementCafeBinding.inflate(inflater, parent, false)
        Log.d("test", "onCreateViewHolder")

        return CafeViewHolder(binding.root)
    }

    inner class CafeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = DataBindingUtil.bind<ElementCafeBinding>(itemView)

        fun setCafeClickListener(cafe: Cafe) {
            binding?.elementCafeCvMain?.setOnClickListener {
                cafeListViewModel.onCafeCardClick(cafe.cafeEntity.id)
            }
        }
    }
}