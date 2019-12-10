package com.example.anihub.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.anihub.AniHubApplication
import com.example.anihub.BaseFragment
import com.example.anihub.R
import com.example.anihub.ui.GridSpaceDecoration
import com.example.anihub.ui.anime.ViewModelFactory
import com.example.anihub.ui.anime.detail.PaginationScrollListener
import com.example.anihub.ui.anime.shared.AnimeSharedViewModel
import kotlinx.android.synthetic.main.fragment_search_anime_list.*
import javax.inject.Inject

class SearchAnimeFragment : BaseFragment() {

    private lateinit var animeListAdapter: AnimeSearchListAdapter
    private lateinit var animeSharedViewModel: AnimeSharedViewModel
    private lateinit var listener: AnimeSearchListInterface
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private var isLastPage: Boolean = false
    private var isLoading: Boolean = false
    private var page = 1

    private var lastSearchedQuery: String = ""

    @Inject
    lateinit var modelFactory: ViewModelFactory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AnimeSearchListInterface) {
            listener = context
        } else {
            throw ClassCastException(context.toString() + getString(R.string.no_implementation, TAG))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AniHubApplication.graph.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_anime_list, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            animeSharedViewModel = modelFactory.create(AnimeSharedViewModel::class.java)
        }
        layoutManager = GridLayoutManager(view.context, 4)
        anime_search_recycler.layoutManager = layoutManager
        anime_search_recycler?.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                initial_loading_pb.isGone = true
                loading_items_layout.isVisible = true
                animeSharedViewModel.loadAnimeBySearchTerms(page, lastSearchedQuery)
            }

            override fun hideFAB() {
                if (fab.isShown) {
                    fab.hide()
                }
            }

            override fun showFAB() {
                fab.show()
            }
        })
        anime_search_recycler.addItemDecoration(GridSpaceDecoration(10))
        animeListAdapter = AnimeSearchListAdapter(requireContext(), listener)
        anime_search_recycler.adapter = animeListAdapter
        setupObservableViewModels()
    }

    fun loadData(query: String) {
        if (query.isNotEmpty()) {
            page = 1
            lastSearchedQuery = query
            animeSharedViewModel.loadAnimeBySearchTerms(page, query)
        }
    }

    private fun setupObservableViewModels() {
        animeSharedViewModel.searchAnimeLiveData.observe(this, Observer { it ->
            it.data()?.let {
                isLoading = false
                //loading_items_layout.isVisible = false
                if (it.page?.media?.isNullOrEmpty() == false) {
                    anime_search_recycler.isVisible = true
                    initial_loading_pb.isGone = true
                    loading_items_layout.isGone = true
                    //empty_results_view.isGone = true
                    if (page > 1) {
                        animeListAdapter.addItems(it.page.media.requireNoNulls().toMutableList())
                    } else {
                        it.page.media.let { items ->
                            animeListAdapter.setItems(items.requireNoNulls().toMutableList())
                        }
                    }

                } else {
                    loading_items_layout.isGone = true
                    initial_loading_pb.isVisible = false
                    //empty_results_view.isVisible = true
                    anime_search_recycler.isGone = true
                }

                if (it.page?.pageInfo?.hasNextPage == true) {
                    isLastPage = false
                    page++
                } else {
                    isLastPage = true
                }
            }
        })

        animeSharedViewModel.animeError.observe(this, Observer { onError(it)})
    }

    interface AnimeSearchListInterface {
        fun onAnimeItemSelected(id: Int)
    }

    companion object {
        @JvmField
        val TAG: String = SearchAnimeFragment::class.java.simpleName

        val QUERY = "QUERY"

        fun newInstance(): SearchAnimeFragment {
            return SearchAnimeFragment()
        }
    }
}