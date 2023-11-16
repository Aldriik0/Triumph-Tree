package com.example.triumphtree

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.app_bar)
        setSupportActionBar(toolbar)

        val button = findViewById<Button>(R.id.new_goal)
        button.setOnClickListener {
            val intent = Intent(this, NewGoal::class.java)
            startActivity(intent)
        }

        val button2 = findViewById<Button>(R.id.goal_settings)
        button2.setOnClickListener {
            val intent = Intent(this, GoalSettings::class.java)
            startActivity(intent)
        }

        val logButton = findViewById<Button>(R.id.log_activity)
        logButton.setOnClickListener {
            val intent = Intent(this, LogActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.app_bar, menu)
        return true

//        val settingsButton = findViewById<Button>(R.id.settings_button)
//        settingsButton.setOnClickListener {
//            val intent = Intent(this, SettingsMenu::class.java)
//            startActivity(intent)
//        }
    }
}