package com.example.unthackathon

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView

class CommsActivity : AppCompatActivity() {

    private var bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    private var deviceNameList = mutableListOf<String>("No names found")
    private var deviceAddressList = mutableListOf<String>("Empty")
    private lateinit var listview : ListView



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comms)
        //var adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, deviceNameList)
        val permissions = arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION)
        ActivityCompat.requestPermissions(this, permissions,0)


//
//        listview = findViewById<ListView>(R.id.comms_List)
//        var adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, deviceNameList)
//        listview.adapter = adapter

////        val permissions1 = arrayOf(android.Manifest.permission.BLUETOOTH)
////
////        ActivityCompat.requestPermissions(this, permissions1,0)
////
////        val permissions2 = arrayOf(android.Manifest.permission.BLUETOOTH_PRIVILEGED)
//
//      //  ActivityCompat.requestPermissions(this, permissions2,0)
//        val permissions3 = arrayOf(android.Manifest.permission.BLUETOOTH_ADMIN)
//
//        ActivityCompat.requestPermissions(this, permissions3,0)

        bluetoothAdapter.startDiscovery()


        deviceNameList.add("Test123")

        // Register for broadcasts when a device is discovered
        var filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(receiver, filter)




    }

    override fun onStart() {
        super.onStart()
        listview = findViewById<ListView>(R.id.comms_List)
        var adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, deviceNameList)
        listview.adapter = adapter
        adapter.notifyDataSetChanged()
     //   bluetoothAdapter.cancelDiscovery()
    }

    // Create a BroadcastReceiver for ACTION_FOUND
    private var receiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {

            Log.i("HALOOOO",""+deviceNameList[0] )
            val action: String = intent.action
            when (action) {
                BluetoothDevice.ACTION_FOUND -> {
               //     if(deviceAddressList[0] == "Empty") {
               //         deviceAddressList.removeAt(0)
               //         deviceNameList.removeAt(0)
               //     }
             //        Discovery has found a device. Get the BluetoothDevice object and it's info from the Intent
                    val device: BluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                        .delay(2000)
                   // device.name.toString() = "halo"

                    if(device.name != null) {
                        deviceNameList.add(device.name)
                        deviceAddressList.add(device.address) // MAC Address
                    }
                    else{

                        deviceNameList.add("Browns")
                        deviceAddressList.add(device.address) // MAC Address

                    }
                    Log.i("HALOOOO",""+deviceNameList.last() )
                    Log.i("HALOOO1",""+deviceAddressList.last() )
                    listview.postInvalidate()

                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}