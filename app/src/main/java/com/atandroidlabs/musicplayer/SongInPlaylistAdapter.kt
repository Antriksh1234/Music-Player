package com.atandroidlabs.musicplayer

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SongInPlaylistAdapter : RecyclerView.Adapter<SongInPlaylistAdapter.SongOfPlaylistViewHolder>(){

    override fun onBindViewHolder(holder: SongOfPlaylistViewHolder, position: Int) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongOfPlaylistViewHolder {
        TODO()
    }

    override fun getItemCount(): Int {
        return PlaylistActivity.songsInPlaylist.size
    }

    class SongOfPlaylistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        
    }
}