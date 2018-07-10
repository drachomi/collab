//package com.richard.imoh.collab
//
//import android.content.Context
//import android.support.v7.widget.RecyclerView
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import com.bumptech.glide.Glide
//import java.util.*
//
//
//
///**
// * Created by LENOVO on 6/13/2018.
// */
// class FollowAdapter(val items: ArrayList<User>, val context:Context): RecyclerView.Adapter<ViewHolder>() {
//    override fun getItemCount(): Int {
//        return items.size
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
//        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.follow_model,parent,false))
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
//        holder?.name?.text = items[position].name
//        holder?.image?.resources = items[position].image
//        Glide.with(context)
//                .load(items[position].image)
//                .into(holder?.image)
//
//    }
//    fun getImage
//}
//class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
//    val name = view.findViewById<TextView>(R.id.follow_name)
//    val image = view.findViewById<TextView>(R.id.follow_image)
//}
