package com.example.cleanarchtemplate.presentation.ui.home.fragments.main.adapters

import android.util.SparseArray
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.cleanarchtemplate.presentation.ui.home.fragments.main.LeftFragment
import com.example.cleanarchtemplate.presentation.ui.home.fragments.main.RightFragment

/**
 * Используется для связывания отдельных фрагментов (воспитатели, дети, сотрудники) и элемента
 * прокрутки элементов (ViewPager)
 *
 * @author EvgenySamarin
 * @since 20190923 v2
 */
class MainPageAdapter(fm: FragmentManager, private val numOfTabs: Int = 0) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val registeredFragments = SparseArray<Fragment>()

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as Fragment
        registeredFragments.put(position, fragment)
        return fragment
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        registeredFragments.remove(position)
        super.destroyItem(container, position, `object`)
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> LeftFragment()
            1 -> RightFragment()
            else -> throw IllegalArgumentException("pointer index out of range")
        }
    }

    override fun getCount(): Int = numOfTabs
}