package com.cedric.foodforfamily

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cedric.foodforfamily.util.days
import kotlinx.android.synthetic.main.fragment_view_pager.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * A simple [Fragment] subclass.
 * Use the [ViewPagerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class ViewPagerFragment(val s: FoodDay?) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_pager, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val d = days[(s?.index ?: 1) - 1]

        tvHeaderDay.text = d

        s?.let {
            tvChef.text = "Chef: " + s.chef

            //------ Food --------

            tvMorning.text = "Morning"
            tvMorningFood.text = s.morning

            tvLunch.text = "Lunch"
            tvLunchFood.text = s.lunch

            tvDinner.text = "Dinner"
            tvDinnerFood.text = s.dinner

            setListeners()
        }

    }

    private fun setListeners() {
        tvMorningFood.onClick { clickNAForSuggestion(tvMorningFood.text.toString(), "M") }
        tvLunchFood.onClick { clickNAForSuggestion(tvLunchFood.text.toString(), "L") }
        tvDinnerFood.onClick { clickNAForSuggestion(tvDinnerFood.text.toString(), "D") }
    }

    fun clickNAForSuggestion(s: String, kind: String) {
        if (s == "N/A") {
            //open suggestions page

        }
    }

    companion object {

        @JvmStatic
        fun newInstance(s: FoodDay?) = ViewPagerFragment(s)
    }
}
