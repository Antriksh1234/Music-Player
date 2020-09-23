package com.atandroidlabs.musicplayer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SongAdapter(var context: Context) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    class SongViewHolder(var itemView: View) : RecyclerView.ViewHolder(itemView) {
        var songNameTextView: TextView = itemView.findViewById(R.id.song_name)
        var songArtistTextView: TextView = itemView.findViewById(R.id.song_artists)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val songView: View = inflater.inflate(R.layout.songs_recyclerview,parent,false)
        return SongViewHolder(songView)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {

        //Setting text of song
        if (MainActivity.songList.get(position).songName.length > 26) {
            val textOfSong = MainActivity.songList.get(position).songName.substring(0,26) + "..."
            holder.songNameTextView.text = textOfSong
        } else {
            holder.songNameTextView.text = MainActivity.songList.get(position).songName
        }

        //Setting text of artists
        if (MainActivity.songList.get(position).artists.length > 26) {
            val artistsOfSong = MainActivity.songList.get(position).artists.substring(0,26) + "..."
            holder.songArtistTextView.text = artistsOfSong
        } else {
            holder.songArtistTextView.text = MainActivity.songList.get(position).artists
        }
    }

    override fun getItemCount(): Int {
        return MainActivity.songList.size
    }
}