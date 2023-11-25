package com.example.triumphtree

import android.os.Bundle
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson


/*
    GoalList activity is a dynamically growing list that will populate as the user is putting in
    goals. It saves goals and their details into a data class and throws them into a JSON and then
    reads them to populate the list in activity_goal_list.xml. This is done through a factory model
    where a new goal is made from the NewGoal activity, which then makes the NewGoalFragment.kt make
    a data class with the information from the NewGoal activity, saves it in the shared preferences
    for later loading.
 */
class GoalList : AppCompatActivity(){

    private lateinit var listView: ListView
    private lateinit var arrayAdapter: ArrayAdapter<GoalModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_list)

        listView = findViewById(R.id.goalListView)

        // Read goals from SharedPreferences
        val goalsList = readGoalsFromSharedPreferences()

        //Populate the list
        fillGoals(goalsList)

        // Create an array adapter to display the goals in the ListView
        arrayAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            goalsList
        )

        listView.adapter = arrayAdapter

        // Set the OnItemClickListener for the ListView
        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedGoal = goalsList[position]
            // Handle click on the selected goal, show details, etc.
        }
    }


    private fun saveGoalsToSharedPreferences() {
        val sharedPreferences = getSharedPreferences("Goals", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Convert the goals list to JSON using Gson
        val goalsList = mutableListOf<GoalModel>()
        for (i in 0 until arrayAdapter.count) {
            goalsList.add(arrayAdapter.getItem(i)!!)
        }

        val jsonGoalsList = Gson().toJson(goalsList)

        // Save the JSON string to SharedPreferences
        editor.putString("GoalsList", jsonGoalsList)
        editor.apply()
    }


    private fun readGoalsFromSharedPreferences(): List<GoalModel> {
        val sharedPreferences =
            getSharedPreferences("Goals", MODE_PRIVATE)

        // Retrieve the JSON string from SharedPreferences
        val jsonGoalsList = sharedPreferences.getString("GoalsList", null)

        // Convert the JSON string back to a list of GoalModel using Gson
        val type = object : TypeToken<List<GoalModel>>() {}.type
        return Gson().fromJson(jsonGoalsList, type) ?: emptyList()
    }

    private fun fillGoals(goals: List<GoalModel>)
    {
        val layout = findViewById<ListView>(R.id.goalListView)
        for(i in goals.indices)
        {
            //Create TextViews Programmatically
            val textView = TextView(this)

            //setting height and width
            textView.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT)

            //Setting the text to the name
            textView.setText(goals.get(i)?.name)

            layout?.addView(textView)
        }
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        saveGoalsToSharedPreferences()
    }

    override fun onResume() {
        super.onResume()
    }
}
