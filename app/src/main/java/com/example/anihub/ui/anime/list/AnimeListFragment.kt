package com.example.anihub.ui.anime.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.apollographql.apollo.ApolloClient
import com.example.anihub.BaseFragment
import com.example.anihub.R
import com.example.anihub.di.DaggerAppComponent
import com.example.anihub.ui.GridSpaceDecoration
import com.example.anihub.ui.anime.ViewModelFactory
import com.example.anihub.ui.anime.shared.AnimeSharedViewModel
import kotlinx.android.synthetic.main.fragment_anime_list.*
import javax.inject.Inject

class AnimeListFragment : BaseFragment() {

    @Inject
    lateinit var apolloClient: ApolloClient

    private lateinit var animeListAdapter: AnimeListAdapter
    private lateinit var animeSharedViewModel: AnimeSharedViewModel

    private lateinit var listener: AnimeListInterface

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
        DaggerAppComponent.create().inject(this)
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
        anime_list.layoutManager = GridLayoutManager(view.context, 4)
        anime_list.addItemDecoration(GridSpaceDecoration(10))
        animeListAdapter =
            AnimeListAdapter(requireContext(), listener)
        anime_list.adapter = animeListAdapter
        setupObservableViewModels()
        animeSharedViewModel.loadAllAnime(1)
        super.onViewCreated(view, savedInstanceState)

    }

    private fun setupObservableViewModels() {
        animeSharedViewModel.browseAllAnimeLiveData.observe(this, Observer { it ->
            it.data()?.page?.media.let {
                if (!it.isNullOrEmpty()) {
                    initial_loading_pb.isGone = true
                    empty_results_view.isGone = true
                } else {
                    initial_loading_pb.isVisible = true
                    empty_results_view.isVisible = true
                    anime_list.isGone = true
                }
                animeListAdapter.setItems(it?.filterNotNull())
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