@file:JvmName("FMExt")

package com.example.anihub

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit

fun AppCompatActivity.addFragmentToBackstack(frameId: Int, fragment: Fragment, tag: String) {
    supportFragmentManager.commit(true) { add(frameId, fragment, tag).addToBackStack(tag) }
}

fun AppCompatActivity.addFragment(frameId: Int, fragment: Fragment, tag: String) {
    supportFragmentManager.commit(true) { add(frameId, fragment, tag) }
}

fun AppCompatActivity.replaceFragment(frameId: Int, fragment: Fragment, tag: String, isSharedElement: Boolean, sharedView: View) {
    if (isSharedElement) {
        supportFragmentManager.transaction({setReorderingAllowed(true).addSharedElement(sharedView, tag).replace(frameId, fragment, tag) }, tag)
    } else {
        supportFragmentManager.transaction({replace(frameId, fragment, tag)}, tag)
    }
}

// Could make the sharedView into a map of sharedView's with the transitionNames as the keys.
inline fun FragmentManager.transactionWithSharedElements(function: FragmentTransaction.() -> FragmentTransaction, tag: String, sharedView: View) {
    if (tag.isNotEmpty()) {
        ViewCompat.getTransitionName(sharedView)?.let { beginTransaction().function().addSharedElement(sharedView, it).addToBackStack(tag).commit() }
    } else {
        ViewCompat.getTransitionName(sharedView)?.let { beginTransaction().function().addSharedElement(sharedView, it).commit() }
    }
}

inline fun FragmentManager.transaction(function: FragmentTransaction.() -> FragmentTransaction, tag: String) {
    if (tag.isNotEmpty()) {
        beginTransaction().function().addToBackStack(tag).commit()
    } else {
        beginTransaction().function().commit()
    }
}