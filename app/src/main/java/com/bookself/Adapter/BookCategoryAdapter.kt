package com.bookself.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bookself.R
import com.bookself.roomDatabase.RoomCategory

class BookCategoryAdapter(
    var context: Context,
    private var catList: ArrayList<RoomCategory?>?,
    var onClickListener:BookCategoryAdapterLis
) : RecyclerView.Adapter<BookCategoryAdapter.ViewHolder>() {
    val VIEW_ITEM = 1
    val VIEW_ADD = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == VIEW_ITEM) {
            return ShowCategoryViewHolder(
                LayoutInflater.from(context).inflate(R.layout.category_item, parent, false)
            )
        }
        return AddCategoryViewHolder(
            LayoutInflater.from(context).inflate(R.layout.add_category_item, parent, false)
        )
    }

    interface BookCategoryAdapterLis {
        fun viewBook(position: Int,categoryName:String)
        fun addCategory()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is ShowCategoryViewHolder) {
            val catData:RoomCategory= catList?.get(position)!!
            (holder as ShowCategoryViewHolder).bindItems(catData,position)
        }
        else{
            (holder as AddCategoryViewHolder).bindItems(position)
        }
    }
    override fun getItemViewType(position: Int): Int {
        return if (catList!![position] != null) VIEW_ITEM else VIEW_ADD
    }

    override fun getItemCount(): Int {
        return catList!!.size
    }

    open inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    private inner class ShowCategoryViewHolder(itemView: View) :
        ViewHolder(itemView) {
        @SuppressLint("SimpleDateFormat", "SetTextI18n", "CheckResult")
        fun bindItems(catData: RoomCategory,position: Int) {
            try {
                val categoryLabel = itemView.findViewById(R.id.categoryLabel) as TextView
                val categoryText  = itemView.findViewById(R.id.categoryText) as TextView
                categoryLabel.text = if(!catData.catName.isNullOrEmpty()) catData.catName!!.substring(0,1) else "B"
                categoryText.text = catData.catName
                itemView.setOnClickListener {
                    onClickListener.viewBook(position,catData.catName!!)
                }
            } catch (e: Exception) {
            }
        }
    }

    private inner class AddCategoryViewHolder(itemView: View) :
        ViewHolder(itemView) {
        @SuppressLint("SimpleDateFormat", "SetTextI18n", "CheckResult")
        fun bindItems(position: Int) {
            try {
                 itemView.setOnClickListener {
                    onClickListener.addCategory()
                }
            } catch (e: Exception) {
            }
        }
    }

    fun updateList(mDataList1: ArrayList<RoomCategory?>?) {
        catList = mDataList1
        notifyDataSetChanged()
    }
}
