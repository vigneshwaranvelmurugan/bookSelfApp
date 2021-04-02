package com.bookself.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bookself.R
import com.bookself.roomDatabase.RoomBooks
import com.bumptech.glide.Glide
import java.util.*


class BookListAdapter(
    var context: Context,
    private var bookList: ArrayList<RoomBooks?>?,
    var onClickListener:BookListAdapterLis
) : RecyclerView.Adapter<BookListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.booklist_item, parent, false)
        )
    }

    interface BookListAdapterLis {
        fun viewBookDetail(bookData: RoomBooks, position: Int)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bookData: RoomBooks = bookList?.get(position)!!
        holder.bindItems(bookData,position)
    }
    override fun getItemCount(): Int {
        return bookList!!.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("SimpleDateFormat", "SetTextI18n", "CheckResult")
        fun bindItems(bookData: RoomBooks, position: Int) {
            try {
                val bookTitle = itemView.findViewById(R.id.bookTitle) as TextView
                val bookAuthor  = itemView.findViewById(R.id.bookAuthor) as TextView
                val publisherDate = itemView.findViewById(R.id.publisherDate) as TextView
                val booksPageCount = itemView.findViewById(R.id.booksPageCount) as TextView
                val authorTitle=itemView.findViewById(R.id.authorTitle) as TextView
                val bookImg=itemView.findViewById(R.id.bookImg) as ImageView
                bookTitle.text = bookData.title
                bookAuthor.text = TextUtils.join(", ", bookData.authors)


                var publishedDate:String=bookData.publishedDate!!
                try {
                    val separated= publishedDate.split("T")
                    publishedDate=separated[0]
                }catch (e:java.lang.Exception){

                }
                publisherDate.text= publishedDate
                booksPageCount.text=bookData.pageCount.toString()
                if (bookData.thumbnailUrl!!.isNotEmpty()){
                    Glide.with(context).load(bookData.thumbnailUrl).into(bookImg);
                }
                authorTitle.text=if (bookData.authors.size>1) "Authors" else "Author"

                itemView.setOnClickListener {
                    onClickListener.viewBookDetail(bookData,position)
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
    fun updateList(mDataList1: ArrayList<RoomBooks?>?) {
        bookList = mDataList1
        notifyDataSetChanged()
    }

}
