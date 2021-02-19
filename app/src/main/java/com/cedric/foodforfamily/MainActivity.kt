package com.cedric.foodforfamily

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cedric.foodforfamily.util.addFoodToCalendar
import com.cedric.foodforfamily.util.addSuggestionToCalendar
import com.cedric.foodforfamily.util.createFullWeek
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnToViewpager.onClick {
            val i = Intent(this@MainActivity, ViewPagerActivity::class.java)
            startActivity(i)
        }

        /* FirebaseDatabase.getInstance().reference.child("currentWeek")
             .addListenerForSingleValueEvent(object : ValueEventListener {
                 override fun onDataChange(snapshot: DataSnapshot) {

                     val calendar: Calendar = Calendar.getInstance(Locale.FRANCE)
                     val weekOfYear: Int = calendar.get(Calendar.WEEK_OF_YEAR)

                     if (weekOfYear.toLong() != snapshot.value) {
                         init()

                         FirebaseDatabase.getInstance().reference.child("currentWeek")
                             .setValue(weekOfYear)
                     }
                 }

                 override fun onCancelled(error: DatabaseError) {
                     TODO("Not yet implemented")
                 }
             })*/

        init()

        addFood()


    }

    private fun init() {
        createFullWeek("Emmanuelle")
    }

    fun addFood() {
        addFoodToCalendar(0, "D", "Brood van den aldi")
    }

    fun addSuggestions() {
        addSuggestionToCalendar(0, "L", "Broodje panos")

        addSuggestionToCalendar(0, "L", "Broodje kaas")
    }
}

fun containsCaseInsensitive(s: String?, l: List<String>): Boolean {
    for (string in l) {
        if (string.equals(s, ignoreCase = true)) {
            return true
        }
    }
    return false
}

class Suggestion(val title: String)


