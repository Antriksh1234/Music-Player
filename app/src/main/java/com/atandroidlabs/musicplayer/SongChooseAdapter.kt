package com.atandroidlabs.musicplayer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SongChooseAdapter(var context: Context) : RecyclerView.Adapter<SongChooseAdapter.SongChooseViewHolder>(){

    override fun onBindViewHolder(holder: SongChooseViewHolder, position: Int) {
        if ( AddToPlaylistActivity.songToBeAdded[position].songName.length > 26)
            holder.songTextView.text = AddToPlaylistActivity.songToBeAdded[position].songName.substring(0,26) + "..."
        else
            holder.songTextView.text = AddToPlaylistActivity.songToBeAdded[position].songName
            if (holder.checkBox.isChecked) {
                AddToPlaylistActivity.songs.add(AddToPlaylistActivity.songToBeAdded[position])
            } else {
                AddToPlaylistActivity.songs.remove(AddToPlaylistActivity.songToBeAdded[position])
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongChooseViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.song_selector,parent,false)
        return SongChooseViewHolder(view)
    }

    override fun getItemCount(): Int {
        return AddToPlaylistActivity.songToBeAdded.size
    }

    class SongChooseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var songTextView: TextView = itemView.findViewById(R.id.song_name_to_be_selected)
        var checkBox: CheckBox = itemView.findViewById(R.id.is_song_selected)
    }
}