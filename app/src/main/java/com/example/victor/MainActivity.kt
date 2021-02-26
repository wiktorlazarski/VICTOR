package com.example.victor

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlin.system.exitProcess

const val SERVER_ADDRESS = "com.example.victor.SERVER_ADDRESS"

class MainActivity : AppCompatActivity() {
    private var serverAddress: String? = null
    private var isServerAddressParsed: Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!isServerAddressParsed!!) {
            showServerAddressDialog()
            isServerAddressParsed = true
        }
    }

    fun startProphecyProcess(view: View) {
        val prophecyActivityIntent = Intent(this, ProphecyActivity::class.java).apply {
            putExtra(SERVER_ADDRESS, serverAddress)
        }

        startActivity(prophecyActivityIntent)
    }

    private fun showServerAddressDialog() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.dialog_insert_ip, null)

        with(builder){
            setPositiveButton("OK") { dialog, id ->
                val serverIp = dialogLayout.findViewById<EditText>(R.id.ip_text).text.toString().trim()
                val serverPort = dialogLayout.findViewById<EditText>(R.id.port_text).text.toString().trim()

                serverAddress = "$serverIp:$serverPort"
            }
            setNegativeButton("Cancel") { dialog, id ->
                finish();
                exitProcess(0);
            }

            setCancelable(false)
            setView(dialogLayout)
            show()
        }
    }
}