package com.app.moviedb.view.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.app.moviedb.R
import com.app.moviedb.view.fragment.PopularListFragment
import com.app.moviedb.view.fragment.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val POPULAR_FRAGMENT = 1
        const val SEARCH_FRAGMENT = 2
    }

    private val popularListFragment:Lazy<PopularListFragment> = lazy { PopularListFragment() }
    private val searchFragment:Lazy<SearchFragment> = lazy { SearchFragment() }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_popular -> {
                initFragment(POPULAR_FRAGMENT)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_search -> {
                initFragment(SEARCH_FRAGMENT)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        if (savedInstanceState == null)initFragment(POPULAR_FRAGMENT)

    }


    private fun initFragment(fragment:Int){
        fun startFragment(fragment: Fragment){
            supportFragmentManager.beginTransaction().replace(R.id.container,fragment).commit()
        }
        startFragment(when(fragment){
            POPULAR_FRAGMENT -> popularListFragment.value
            SEARCH_FRAGMENT -> searchFragment.value
            else -> throw IllegalStateException("unknown fragment")
        })
    }
}
