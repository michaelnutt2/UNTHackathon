package com.example.unthackathon

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.widget.Button
import android.widget.Switch

class MainActivity : AppCompatActivity() {

    private val bluetoothAdapter : BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {

        // Pulls preferences
        val sharedPreferences = getSharedPreferences("unt_hackathon_preferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editor.putString("bluetoothName", bluetoothAdapter.getName())
        editor.commit()
    }

    override fun onStart() {
        super.onStart()

        // Pulls preferences
        val sharedPreferences = getSharedPreferences("unt_hackathon_preferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Sets up buttons as variables
        val commsButton = findViewById<Button>(R.id.main_commsButton)
        val mapButton = findViewById<Button>(R.id.main_mapButton)
        val emSwitch = findViewById<Switch>(R.id.main_EMSwitch)

        emSwitch.isChecked = sharedPreferences.getBoolean("em_switch", false)

        // Checks if switch changed, starts BT discovery if it is
        emSwitch.setOnCheckedChangeListener {buttonView, isChecked ->
            if (isChecked) {
                setupBluetooth()
            } else {
                turnOffEM()
            }
            editor.commit()
        }

        // Goes to Comms activity on button press
        commsButton.setOnClickListener {
            val intent = Intent(this, CommsActivity::class.java)
            startActivity(intent)
        }

        // Goes to Map activity on button press
        mapButton.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupBluetooth() {
        // Pulls preferences

        val sharedPreferences = getSharedPreferences("unt_hackathon_preferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val discoverableIntent: Intent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE).apply {
            putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 3600)
        }
        startActivity(discoverableIntent)

        bluetoothAdapter.setName("guardNet")
        editor.putBoolean("em_switch", true)
        editor.commit()
    }

    private fun turnOffEM() {
        // Pulls preferences
        val sharedPreferences = getSharedPreferences("unt_hackathon_preferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val name = sharedPreferences.getString("bluetoothName","guardNet")
        bluetoothAdapter.setName(name)
        bluetoothAdapter.disable()
        editor.putBoolean("em_switch", false)
        editor.commit()
    }

}
