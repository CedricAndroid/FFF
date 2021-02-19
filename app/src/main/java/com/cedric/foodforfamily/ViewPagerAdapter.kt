package com.cedric.foodforfamily

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fa: FragmentActivity, private val fragments: ArrayList<Fragment>) :
    FragmentStateAdapter(fa) {

    val flist: MutableList<t> = mutableListOf()

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]

    fun setFragments(newItems: List<Fragment>) {
        fragments.clear()
        fragments.addAll(newItems)
        notifyDataSetChanged()
    }

    fun setFragment(nf: t) {
        //nf.f
        flist.add(nf)

        flist.sortBy { it.index }
        setFragments(flist.map { it.f })

        //fragments.add()
        notifyDataSetChanged()
    }

}