package com.app.moviedb.view.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.*
import com.app.moviedb.R
import com.app.moviedb.data.Movie
import com.app.moviedb.presentation.di.Injector
import com.app.moviedb.presentation.presenter.PopularMoviePresenter
import com.app.moviedb.presentation.presenter.SearchPresenter
import com.app.moviedb.view.adapter.MovieRecyclerAdapter
import com.app.moviedb.view.inter.MovieListView
import kotlinx.android.synthetic.main.recycler_view.*
import org.jetbrains.anko.alert
import javax.inject.Inject

class SearchFragment:BaseFragment(),MovieListView {
    override fun nameOfFragment(): String = "Search"

    var adapter: MovieRecyclerAdapter? = null

    lateinit var searchView:SearchView

    @Inject
    lateinit var searchPresenter: SearchPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        Injector.appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.recycler_view,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (adapter == null) adapter = MovieRecyclerAdapter(activity!!)
        val displayMetrics = activity!!.resources.displayMetrics
        var spanCount = Math.round((displayMetrics.widthPixels / displayMetrics.density)/300)
        if (spanCount == 0)spanCount = 1
        recyclerView.layoutManager = GridLayoutManager(context,spanCount)
        recyclerView.adapter = adapter!!
        searchPresenter.attachView(this)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutPosition = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (layoutPosition + 5 > adapter!!.itemCount) {
                    searchPresenter.next()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchView.clearFocus()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search, menu)
        val searchItem = menu.findItem(R.id.menu_item_search)
        searchView = searchItem.actionView as SearchView
        searchView.queryHint = "Search"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && query.isNotEmpty()) {
                    searchPresenter.init(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        searchView.isFocusable = true
        searchView.requestFocusFromTouch()
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun renderObject(objects: List<Movie>) {
        adapter?.list = objects
        adapter?.notifyDataSetChanged()
        recyclerView.scrollToPosition(0)
    }

    override fun addObjects(objects: List<Movie>) {
        adapter?.addObjects(-1,objects)
    }

    override fun showLoading(message: String) {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    override fun showError(message: String) {
        activity?.alert(message){
            positiveButton("OK"){dismiss()}
        }?.show()
    }
}