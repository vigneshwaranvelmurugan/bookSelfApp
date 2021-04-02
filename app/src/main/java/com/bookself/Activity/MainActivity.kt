package com.bookself.Activity

import android.os.Bundle
import android.os.Process.killProcess
import android.os.Process.myPid
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bookself.Fragment.AllCategoryPage
import com.bookself.Fragment.BookListPage
import com.bookself.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private val bottomNavigationSelectListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_all_category -> {
                showFragment(AllCategoryPage())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_my_interested -> {
                showFragment(BookListPage("", true))
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
    lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigation = findViewById(R.id.bottomNavigation)
        bottomNavigation.setOnNavigationItemSelectedListener(bottomNavigationSelectListener)
        //first tab item selection
        bottomNavigation.selectedItemId=R.id.navigation_all_category
    }
    private fun showFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.tabFrame, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    override fun onBackPressed() {
        if (bottomNavigation.selectedItemId == R.id.navigation_all_category) {
            super.onBackPressed()
            finish()
        } else {
            bottomNavigation.selectedItemId = R.id.navigation_all_category
        }
    }
}