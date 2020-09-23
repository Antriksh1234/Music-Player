package com.atandroidlabs.musicplayer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AlbumAdapter(var context: Context) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.albums_recyclerview,parent,false)
        return AlbumViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        if (MainActivity.albumsList.get(position).no_of_songs > 1) {
            holder.noOfSongsTextView.text = "${MainActivity.albumsList.get(position).no_of_songs} Songs"
        } else {
            holder.noOfSongsTextView.text = "${MainActivity.albumsList.get(position).no_of_songs} Song"
        }

        if (MainActivity.albumsList.get(position).name.length > 26) {
            holder.albumTextView.text = MainActivity.albumsList.get(position).name.substring(0,26) + "..."
        } else
            holder.albumTextView.text = MainActivity.albumsList.get(position).name

    }

    override fun getItemCount(): Int {
        return MainActivity.albumsList.size
    }

    class AlbumViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        lateinit var albumTextView: TextView
        lateinit var noOfSongsTextView: TextView

        init {
            albumTextView = itemView.findViewById(R.id.album_name)
            noOfSongsTextView = itemView.findViewById(R.id.album_no_of_song)
        }
    }
}