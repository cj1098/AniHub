package com.example.anihub.friends

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.anihub.BaseFragment
import com.example.anihub.R
import com.example.anihub.ui.CustomLinearLayout
import kotlinx.android.synthetic.main.fragment_friends.*

class FriendsFragment : BaseFragment() {

    private lateinit var adapter: FriendsAdapter

    private lateinit var listener: FriendsTouchInterface

    var usersInitialXTouch = 0F
    var usersInitialYTouch = 0F

    override fun onAttach(context: Context) {
        if (context is FriendsTouchInterface) {
            listener = context
        } else {
            throw ClassCastException(context.toString() + getString(R.string.no_implementation, TAG))

        }
        super.onAttach(context)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_friends, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FriendsAdapter((requireContext()))
        friends_recycler.layoutManager = CustomLinearLayout(requireContext(), RecyclerView.VERTICAL, false)
        friends_recycler.adapter = adapter
        friends_recycler.addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
            override fun onInterceptTouchEvent(rv: RecyclerView, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        usersInitialXTouch = event.rawX
                        usersInitialYTouch = event.rawY
                    }
                    // Hacky way to prevent recyclerView scrolling alongside my touch animation
                    // for this view.
                    MotionEvent.ACTION_MOVE -> {
                        if (event.rawX > usersInitialXTouch) {
                            (rv.layoutManager as CustomLinearLayout).setScrollingEnabled(false)

                        } else {
                            (rv.layoutManager as CustomLinearLayout).setScrollingEnabled(true)
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        // TODO: Fix this. Currently Pressing on a friends and swiping a little left
                        // TODO: then letting go will trigger the view onClickListener. We want to
                        // TODO: intercept that but allow other interactions.
                        if (event.rawX > usersInitialXTouch) {
                            listener.onMotionEventReceived(event)
                            return true
                        }
                    }
                }

                listener.onMotionEventReceived(event)
                return false
            }
        })

        val list = mutableListOf<String>()
        for (i in 0..40) {
            list.add("Garrett")
        }
        adapter.setItems(list)

    }

    interface FriendsTouchInterface {
        fun onMotionEventReceived(event: MotionEvent)
        fun close()
    }

    companion object {
        val TAG = FriendsFragment::class.java.simpleName

        fun newInstance(): FriendsFragment {
            return FriendsFragment()
        }
    }
}