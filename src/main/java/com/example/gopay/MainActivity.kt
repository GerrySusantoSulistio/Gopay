package com.example.gopay

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {
    var contact: EditText? = null
    var saldo: TextView? = null
    var value1: String? = null
    var value2: String? = null
    var storage: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val db = FirebaseFirestore.getInstance()
        saldo= findViewById<View>(R.id.value_saldo) as TextView
        contact= findViewById<View>(R.id.value_phone) as EditText
        value1= saldo.toString()
        value2= contact.toString()

        storage = User(value1!!, value2!!).toString()
        db.collection("user").document("1")
                .set(storage!!)
                .addOnSuccessListener { documentReference ->
                    Log.d(
                            ContentValues.TAG,"proses penyimpanan selesai")
                }
                .addOnFailureListener {
                    Log.d(ContentValues.TAG,"proses penyimpanan gagal")
                }
    }
    data class User(val saldo: String, val contact: String)

    fun history(view: View) {
        val intent = Intent(this@MainActivity, HistoryActivity::class.java)
        startActivity(intent)
    }
    fun pay(view: View) {
        val intent = Intent(this@MainActivity, PayActivity::class.java)
        startActivity(intent)
    }

    fun send(view: View) {
        val intent = Intent(this@MainActivity, SendActivity::class.java)
        startActivity(intent)
    }

}