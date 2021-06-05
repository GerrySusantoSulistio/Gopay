package com.example.gopay

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore

class SendActivity : AppCompatActivity(){
    var topup: EditText? = null
    var saldo: TextView? = null
    var value1: String? = null
    var value2: String? = null
    var finVal1: Int? = null
    var finVal2: Int? = null
    var finVal: Int? = null
    var value: String? = null
    var storage: String? = null
    var dref: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send)
        saldo= findViewById<View>(R.id.value_saldo) as TextView
        topup= findViewById<View>(R.id.value_bayar) as EditText
        value1= saldo.toString()
        finVal1= value1!!.toInt()
        value2= topup.toString()
        finVal2= value2!!.toInt()
        dref = FirebaseDatabase.getInstance().getReference()
        dref!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                value1 = dataSnapshot.child("data/temperature").getValue().toString()
                saldo!!.text = value1
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
    data class User(val saldo: Int, val topup: Int,val totalakhir : Int)
    fun topup(view: View) {
        val intent = Intent(this@SendActivity, MainActivity::class.java)
        if(finVal1!! > 0){
            finVal= finVal1!! - finVal2!!
        }else{
            finVal= finVal1!!
        }
        value=finVal.toString()
        val db = FirebaseFirestore.getInstance()
        storage= PayActivity.User(finVal1!!, finVal2!!, finVal!!).toString()
        db.collection("user").document("1")
            .set(storage!!)
            .addOnSuccessListener { documentReference ->
                Log.d(
                    ContentValues.TAG,"proses penyimpanan selesai")
            }
            .addOnFailureListener {
                Log.d(ContentValues.TAG,"proses penyimpanan gagal")
            }
        dref = FirebaseDatabase.getInstance().getReference()
        dref!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                value = dataSnapshot.child("saldo").getValue().toString()
                saldo!!.text = value
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
        startActivity(intent)
    }
}