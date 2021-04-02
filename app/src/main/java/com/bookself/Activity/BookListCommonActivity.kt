package com.bookself.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bookself.Fragment.BookListPage
import com.bookself.R

class BookListCommonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_list_common)
        bindHeaderUI()
        showFragment(BookListPage(intent.getStringExtra("categoryName"),false))
    }
    fun bindHeaderUI() {
        val backIcon: ImageView = findViewById(R.id.backIcon)
        backIcon.setOnClickListener {
            onBackPressed()
        }
        val headerTitle: TextView = findViewById(R.id.headerTitle)
        headerTitle.text = intent.getStringExtra("categoryName")
        val headerButton: TextView = findViewById(R.id.headerButton)
        headerButton.visibility=View.GONE
    }

    private fun showFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.commonFrame, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}