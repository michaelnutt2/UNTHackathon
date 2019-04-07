package com.example.unthackathon

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CompoundButton
import android.widget.Switch
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Pulls preferences, used to check if switch is enabled
        val sharedPreferences = getSharedPreferences("unt_hackathon_preferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Sets up buttons as variables
        val people_button = findViewById<Button>(R.id.main_peopleButton)
        val comms_button = findViewById<Button>(R.id.main_commsButton)
        val map_button = findViewById<Button>(R.id.main_mapButton)
        val em_switch = findViewById<Switch>(R.id.main_EMSwitch)

        em_switch.isChecked = sharedPreferences.getBoolean("em_switch", false)

        // Checks if switch changed
        em_switch.setOnCheckedChangeListener {buttonView, isChecked ->
            if (isChecked) {
                setupBluetooth()
                editor.putBoolean("em_switch", true)
            } else {
                editor.putBoolean("em_switch", false)
            }
            editor.apply()
            editor.commit()
        }

        people_button.setOnClickListener {
            val intent = Intent(this, PeopleActivity::class.java)
            startActivity(intent)
        }

        comms_button.setOnClickListener {
            val intent = Intent(this, CommsActivity::class.java)
            startActivity(intent)
        }

        map_button.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupBluetooth() {

        val discoverableIntent: Intent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE).apply {
            putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 3600)
        }
        startActivity(discoverableIntent)
    }
}
