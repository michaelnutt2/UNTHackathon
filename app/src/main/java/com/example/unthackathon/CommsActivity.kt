package com.example.unthackathon

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.ListView

class CommsActivity : AppCompatActivity() {

    private val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    private val deviceNameList = mutableListOf<String>("No names found")
    private val deviceAddressList = mutableListOf<String>("Empty")
    private lateinit var listview : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comms)

        bluetoothAdapter?.startDiscovery()

        deviceNameList.add("Test")

        // Register for broadcasts when a device is discovered
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(receiver, filter)

    }

    override fun onStart() {
        super.onStart()

        listview = findViewById<ListView>(R.id.comms_List)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, deviceNameList)
        listview.adapter = adapter

        bluetoothAdapter?.cancelDiscovery()
    }

    // Create a BroadcastReceiver for ACTION_FOUND
    private val receiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val action: String = intent.action
            when (action) {
                BluetoothDevice.ACTION_FOUND -> {
                    //if(deviceAddressList[0] == "Empty") {
                    //    deviceAddressList.removeAt(0)
                    //    deviceNameList.removeAt(0)
                    //}
                    // Discovery has found a device. Get the BluetoothDevice object and it's info from the Intent
                    val device: BluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    deviceNameList.add("Test2")
                    deviceNameList.add(device.name)
                    deviceAddressList.add(device.address) // MAC Address
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}