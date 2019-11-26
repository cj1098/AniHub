package com.example.anihub.ui.anime.list

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
import kotlinx.android.synthetic.main.fragment_anime_list.*
import javax.inject.Inject

class AnimeListFragment : BaseFragment() {

    private lateinit var animeListAdapter: AnimeListAdapter
    private lateinit var animeSharedViewModel: AnimeSharedViewModel

    private lateinit var listener: AnimeListInterface
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private var isLastPage: Boolean = false
    private var isLoading: Boolean = false
    private var page = 1

    @Inject
    lateinit var modelFactory: ViewModelFactory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AnimeListInterface) {
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

        val view = inflater.inflate(R.layout.fragment_anime_list, container, false)
        view.apply {

        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        activity?.let {
            animeSharedViewModel = modelFactory.create(AnimeSharedViewModel::class.java)
        }
        layoutManager = GridLayoutManager(view.context, 4)
        anime_list.layoutManager = layoutManager
        anime_list?.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                loading_items_layout.isVisible = true
                animeSharedViewModel.loadAllAnime(page)
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
        anime_list.addItemDecoration(GridSpaceDecoration(10))
        animeListAdapter =
            AnimeListAdapter(requireContext(), listener)
        anime_list.adapter = animeListAdapter
        setupObservableViewModels()
        animeSharedViewModel.loadAllAnime(page)
        super.onViewCreated(view, savedInstanceState)

    }

    private fun setupObservableViewModels() {
        animeSharedViewModel.browseAllAnimeLiveData.observe(this, Observer { it ->
            it.data()?.let {
                isLoading = false
                loading_items_layout.isVisible = false
                if (it.page?.pageInfo?.hasNextPage == true) {
                    isLastPage = false
                    page++
                } else {
                    isLastPage = true
                }
                if (it.page?.media?.isNullOrEmpty() == false) {
                    initial_loading_pb.isGone = true
                    empty_results_view.isGone = true
                    it.page?.media?.let { items ->
                        animeListAdapter.setItems(items.requireNoNulls().toMutableList())
                    }

                } else {
                    initial_loading_pb.isVisible = true
                    empty_results_view.isVisible = true
                    anime_list.isGone = true
                }
            }
        })

        animeSharedViewModel.animeError.observe(this, Observer { onError(it)})
    }

    interface AnimeListInterface {
        fun onAnimeItemSelected(id: Int, transitionName: String, sharedView: View)
    }

    companion object {
        @JvmField
        val TAG: String = AnimeListFragment::class.java.simpleName

        fun newInstance(): AnimeListFragment =
            AnimeListFragment()
    }

}