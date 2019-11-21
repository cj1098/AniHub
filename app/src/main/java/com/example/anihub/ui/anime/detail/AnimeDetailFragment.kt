package com.example.anihub.ui.anime.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import com.apollographql.apollo.ApolloClient
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.example.anihub.BaseFragment
import com.example.anihub.R
import com.example.anihub.di.DaggerAppComponent
import com.example.anihub.ui.anime.ViewModelFactory
import com.example.anihub.ui.anime.shared.AnimeSharedViewModel
import kotlinx.android.synthetic.main.fragment_anime_detail.*
import javax.inject.Inject

class AnimeDetailFragment : BaseFragment() {

    @Inject
    lateinit var apolloClient: ApolloClient

    private lateinit var animeSharedViewModel: AnimeSharedViewModel

    @Inject
    lateinit var modelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerAppComponent.create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_anime_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        animeSharedViewModel = modelFactory.create(AnimeSharedViewModel::class.java)

        setupObservableViewModels()
        //figure out why I have to use the double bang !!...
        val id: Int = arguments?.getInt(AnimeActivity.ID) ?: arguments!!.getInt(AnimeActivity.ID)
        animeSharedViewModel.loadAnimeById(id)
        super.onViewCreated(view, savedInstanceState)

    }

    private fun setupObservableViewModels() {
        animeSharedViewModel.searchAnimeByIdLiveData.observe(this, Observer { it ->
            it.data()?.media?.let {
                val bannerImage = if (!it.bannerImage.isNullOrEmpty()) it.bannerImage else it.coverImage?.large

                Glide.with(requireContext()).load(it.coverImage?.large).into(object : CustomTarget<Drawable>() {
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable>?
                    ) {
                        anime_banner_image.setImageDrawable(resource)
                    }
                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        Log.e("error", "eep")
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }

                })
                Glide.with(requireContext()).load(bannerImage).into(expanded_image)
                anime_detail_description.text = it.description
            }
        })

        animeSharedViewModel.animeError.observe(this, Observer { onError(it) })
    }

    companion object {
        @JvmField
        val TAG = AnimeDetailFragment::class.java.simpleName
        val ID = "ID"

        fun newInstance(id: Int): AnimeDetailFragment {
            val animeDetailFragment = AnimeDetailFragment()
            val bundle = Bundle()
            bundle.putInt(ID, id)
            animeDetailFragment.arguments = bundle
            return animeDetailFragment
        }
    }
}