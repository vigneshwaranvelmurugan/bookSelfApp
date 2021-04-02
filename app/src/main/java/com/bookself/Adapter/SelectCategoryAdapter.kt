package com.bookself.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bookself.R
import com.bookself.roomDatabase.RoomCategory


class SelectCategoryAdapter(
    var context: Context,
    private var catList: ArrayList<RoomCategory?>?,
    var onClickListener:SelectCategoryAdapterLis
) : RecyclerView.Adapter<SelectCategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.selectcategory_item, parent, false)
        )
    }

    interface SelectCategoryAdapterLis {
        fun checkCategoryStatus(catData: RoomCategory,position: Int)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val catData: RoomCategory = catList?.get(position)!!
        holder.bindItems(catData,position)
    }
    override fun getItemCount(): Int {
        return catList!!.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("SimpleDateFormat", "SetTextI18n", "CheckResult")
        fun bindItems(catData: RoomCategory, position: Int) {
            try {
                val categoryLabel = itemView.findViewById(R.id.categoryLabel) as TextView
                val categoryText  = itemView.findViewById(R.id.categoryText) as TextView
                val booksCount = itemView.findViewById(R.id.booksCount) as TextView
                val categoryCheck = itemView.findViewById(R.id.categoryCheck) as CheckBox
                categoryLabel.text = if(!catData.catName.isNullOrEmpty()) catData.catName!!.substring(0,1) else "B"
                categoryText.text = catData.catName
                booksCount.text= if (catData.bookCount!!.toInt()>1) catData.bookCount.toString()+" Books" else catData.bookCount.toString()+" Book"
                categoryCheck.setOnCheckedChangeListener(null); //important
                categoryCheck.isChecked=catData.isSelected
                categoryCheck.setOnCheckedChangeListener { compoundButton, b ->
                    catData.isSelected=b
                    onClickListener.checkCategoryStatus(catData,position)
                }
            } catch (e: Exception) {
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
    fun updateList(mDataList1: ArrayList<RoomCategory?>?) {
        catList = mDataList1
        notifyDataSetChanged()
    }
}
