//package com.richard.imoh.collab
//
//import android.os.Bundle
//import android.os.PersistableBundle
//import android.support.v7.app.AppCompatActivity
//import android.support.v7.widget.GridLayoutManager
//import android.support.v7.widget.LinearLayoutManager
//import android.support.v7.widget.RecyclerView
//import com.richard.imoh.collab.R.id.follow_recycle
//
///**
// * Created by LENOVO on 6/13/2018.
// */
//class Follow : AppCompatActivity(){
//    val user : ArrayList<String> = ArrayList()
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_follow)
//        val follow_recycle = findViewById<RecyclerView>(R.id.follow_recycle)
//
//
//        addUser()
//        follow_recycle.layoutManager = GridLayoutManager(this,3)
//        follow_recycle.adapter = FollowAdapter(user,this)
//
//
//    }
//
//    fun addUser(){
//        user.add("imoh")
//        user.add("zinny")
//        user.add("admin")
//        user.add("solo")
//        user.add("ada")
//        user.add("eluwa")
//        user.add("abigail")
//        user.add("imoh")
//        user.add("zinny")
//        user.add("admin")
//        user.add("solo")
//        user.add("ada")
//        user.add("eluwa")
//        user.add("abigail")
//        user.add("imoh")
//        user.add("zinny")
//        user.add("admin")
//        user.add("solo")
//        user.add("ada")
//        user.add("eluwa")
//        user.add("abigail")
//    }
//}
