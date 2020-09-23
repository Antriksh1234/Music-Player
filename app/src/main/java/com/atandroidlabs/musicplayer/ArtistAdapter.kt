package com.atandroidlabs.musicplayer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.coroutines.coroutineContext

class ArtistAdapter(var context: Context) : RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>() {

    class ArtistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var artistTextView: TextView
        lateinit var artistSongView: TextView
        init {
            artistTextView = itemView.findViewById(R.id.artist_name)
            artistSongView = itemView.findViewById(R.id.artist_no_of_songs)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.artist_recyclerview,parent,false)
        return ArtistViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        if (MainActivity.artistList.get(position).no_of_songs > 1)
            holder.artistSongView.text = "${MainActivity.artistList.get(position).no_of_songs} Songs"
        else
            holder.artistSongView.text = "${MainActivity.artistList.get(position).no_of_songs} Song"

        if (MainActivity.artistList.get(position).name.length > 26) {
            holder.artistTextView.text = MainActivity.artistList.get(position).name.substring(0,26) + "..."
        }
        else
            holder.artistTextView.text = MainActivity.artistList.get(position).name
    }

    override fun getItemCount(): Int {
        return MainActivity.artistList.size
    }

}