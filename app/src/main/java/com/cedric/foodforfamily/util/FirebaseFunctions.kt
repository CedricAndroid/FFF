package com.cedric.foodforfamily.util

import com.cedric.foodforfamily.Suggestion
import com.cedric.foodforfamily.containsCaseInsensitive
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

val days = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday")


//region suggestionPlace
fun addSuggestion(foodKind: Int, title: String) {

    //getSuggestionsFoodTime(foodKind)

    val db =
        FirebaseDatabase.getInstance().reference.child("FoodTime").child(foodKind.toString())
            .child("Suggestion")
    db.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {

            if (snapshot.value == null) {
                val dbadd = FirebaseDatabase.getInstance().reference
                val ref =
                    dbadd.child("FoodTime").child(foodKind.toString()).child("Suggestion")
                        .push()
                val s = Suggestion(title)

                ref.setValue(s)
                return
            }

            val hashMap = (snapshot.value as HashMap<*, *>)
            val list = mutableListOf<Suggestion>()

            hashMap.values.forEach {
                val h = it as HashMap<*, *>
                list.add(Suggestion(h["title"] as String))
            }

            val suggestionList = list.map { it.title }.distinct()

            //check if allready exists
            if (!containsCaseInsensitive(title, suggestionList)) {
                val dbadd = FirebaseDatabase.getInstance().reference
                val ref =
                    dbadd.child("FoodTime").child(foodKind.toString()).child("Suggestion")
                        .push()
                val s = Suggestion(title)

                ref.setValue(s)
            } else {
                println("Already added $title")
            }
        }

        override fun onCancelled(error: DatabaseError) {

        }
    })
}

fun getSuggestionsFoodTime(foodTimeKind: Int) {
    val db = FirebaseDatabase.getInstance().reference.child("FoodTime")
        .child(foodTimeKind.toString()).child("Suggestion")
    db.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {

            val hashMap = snapshot.value as HashMap<*, *>
            val list = mutableListOf<Suggestion>()

            hashMap.values.forEach {
                val h = it as HashMap<*, *>
                list.add(Suggestion(h["title"] as String))
            }

        }

        override fun onCancelled(error: DatabaseError) {

        }
    })

    println("2")
}

fun deleteSuggestion(foodKind: Int, title: String) {
    val db = FirebaseDatabase.getInstance().reference.child("FoodTime").child(foodKind.toString())
        .child("Suggestion")

    db.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val s = snapshot.value as HashMap<*, *>

            val keys = (s.keys.map { it.toString() })

            var index = 0
            val o = s.values.forEach {
                val h = it as HashMap<*, *>
                val zzz = h["title"] as String
                if (zzz == title) {
                    val d = db.child(keys[index])
                    d.removeValue()
                    //val z = 3
                }

                index += 1
            }
        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }

    })
}

fun deleteAllSugestions(foodKind: Int) {
    val db = FirebaseDatabase.getInstance().reference.child("FoodTime").child(foodKind.toString())
        .child("Suggestion")
    db.removeValue()

    println("removed")
}

//endregion


//region Food Calendar
fun createFullWeek(chef: String) {


    days.forEach {
        val db = FirebaseDatabase.getInstance().reference.child("Calendar").child(it)
        db.child("Chef").setValue(chef)
        db.child("M").setValue("N/A")
        db.child("L").setValue("N/A")
        db.child("D").setValue("N/A")
    }

    createFullSuggestions()

}

fun addFoodToCalendar(dayOfWeekIndex: Int, momentToEat: String, food: String) {
    val d = days[dayOfWeekIndex]
    FirebaseDatabase.getInstance().reference.child("Calendar").child(d).child(momentToEat)
        .setValue(food)

    println("food successfully added to calendar!")

    val index = when (momentToEat) {
        "M" -> 1
        "L" -> 2
        "D" -> 3
        else -> 3
    }

    addSuggestion(index, food)

    println("food successfully added to suggestion!")

}

fun getFoodCalendarDay(dayOfWeekIndex: Int, returnFood: (HashMap<String, String>) -> Unit = { }) {
    val d = days[dayOfWeekIndex]
    val z = FirebaseDatabase.getInstance().reference.child("Calendar").child(d)
    z.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val x = snapshot.value as HashMap<String, String>

            returnFood(x)
            val y = 1
        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }

    })

}

fun getFoodCalendarMoment(dayOfWeekIndex: Int, momentToEat: String) {

}

fun getFoodTimes() {
    val db = FirebaseDatabase.getInstance().reference.child("FoodTime")
    db.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {

            val noNullElements = (snapshot.value as List<HashMap<String, *>>).filterNotNull()

            //Show options to select for suggestions
            noNullElements.forEach {
                println("keys:" + it["Name"])
            }

            //Show all Suggestions from 1 FoodTime
            getSuggestionsFoodTime(1)
        }

        override fun onCancelled(error: DatabaseError) {
            //todo show error
        }

    })
}
//endregion


//region SuggestionsPeople
fun createFullSuggestions() {


    days.forEach {
        val db = FirebaseDatabase.getInstance().reference.child("Suggestions").child(it)
        db.child("M").setValue("N/A")
        db.child("L").setValue("N/A")
        db.child("D").setValue("N/A")
    }

}

fun addSuggestionToCalendar(dayOfWeekIndex: Int, momentToEat: String, food: String) {
    val d = days[dayOfWeekIndex]
    val db =
        FirebaseDatabase.getInstance().reference.child("Suggestions").child(d).child(momentToEat)
            .push()
    db.setValue(food)
}

fun getSuggestionFromOtherPeople(dayOfWeekIndex: Int, momentToEat: String) {
    val d = days[dayOfWeekIndex]
    FirebaseDatabase.getInstance().reference.child("Suggestions").child(d).child(momentToEat)
        .addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val x = mutableListOf<String>()
                snapshot.children.forEach {
                    x.add(it.value as String)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
}

//endregion






