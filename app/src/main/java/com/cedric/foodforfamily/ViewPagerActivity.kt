package com.cedric.foodforfamily

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.cedric.foodforfamily.util.getFoodCalendarDay
import kotlinx.android.synthetic.main.activity_view_pager.*
import java.util.*
import kotlin.collections.ArrayList

class ViewPagerActivity : AppCompatActivity() {

    private lateinit var pagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)


        val list = mutableListOf<Fragment>()

        pagerAdapter = ViewPagerAdapter(this@ViewPagerActivity, ArrayList(list))
        pager2_container.adapter = pagerAdapter

        for (i in 1..5) {
            getFoodCalendarDay(i - 1) {
                val x = it

                val m = x["M"]
                val l = x["L"]
                val d = x["D"]

                val ch = x["Chef"]
                println("$i) Middag: $m.")
                //pager2_container.adapter?.notifyDataSetChanged()

                val fd = FoodDay(i, ch, m, l, d)

                val f = ViewPagerFragment.newInstance(fd)
                list.add(f)

                val fr = t(i, f)

                pagerAdapter.setFragment(fr)
                //pagerAdapter.setFragments( i, list)
                setIndicator()
            }
        }


    }


    fun setIndicator() {
        indicator.setViewPager(pager2_container)


        //1 IS SUNDAY, 2 IS MONDAY, 7 IS SATERDAY
        val calendar: Calendar = Calendar.getInstance(Locale.FRANCE)
        val day: Int = calendar.get(Calendar.DAY_OF_WEEK)

        var index = day - 2

        //>= 5 is not supported
        if (index > 4) {
            index = 0
        }

        pager2_container.currentItem = index
    }
}

class FoodDay(
    val index: Int,
    val chef: String?,
    val morning: String?,
    val lunch: String?,
    val dinner: String?
)


class t(val index: Int, val f: Fragment)
