package com.reza.mymvvm.util

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.test.rule.ActivityTestRule

/**
 * Utility methods for espresso tests.
 */
object EspressoTestUtil {

    fun disableProgressBarAnimations(activityTestRule: ActivityTestRule<out FragmentActivity>) {
        activityTestRule.activity.supportFragmentManager
            .registerFragmentLifecycleCallbacks(
                object : FragmentManager.FragmentLifecycleCallbacks() {
                    override fun onFragmentViewCreated(
                        fm: FragmentManager,
                        f: Fragment,
                        v: View,
                        savedInstanceState: Bundle?
                    ) {
                        // traverse all views, if any is a progress bar, replace its animation
                        traverseViews(v)
                    }
                }, true
            )
    }

    private fun traverseViews(view: View?) {
        if (view is ViewGroup) {
            traverseViewGroup(view)
        } else if (view is ProgressBar) {
            disableProgressBarAnimation(view)
        }
    }

    private fun traverseViewGroup(view: ViewGroup) {
        val count = view.childCount
        (0 until count).forEach {
            traverseViews(view.getChildAt(it))
        }
    }


    private fun disableProgressBarAnimation(progressBar: ProgressBar) {
        progressBar.indeterminateDrawable = ColorDrawable(Color.BLUE)
    }
}