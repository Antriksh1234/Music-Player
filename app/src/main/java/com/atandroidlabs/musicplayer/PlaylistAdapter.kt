package com.atandroidlabs.musicplayer

import android.content.ContentResolver
import android.content.Context
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView


class PlaylistAdapter(var context: Context) :
    RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val view: View = LayoutInflater.from(context).inflate(
            R.layout.playlist_recyclerview,
            parent,
            false
        )
        return PlaylistViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        if (MainActivity.listOfPlaylist.get(position).name.length < 26) {
            holder.playlistName.text = MainActivity.listOfPlaylist.get(position).name
        } else
            holder.playlistName.text =
                MainActivity.listOfPlaylist.get(position).name.substring(0, 26) + "..."

        if (MainActivity.listOfPlaylist.get(position).listOfSongs.size > 1)
            holder.noOfSongsInPlaylist.text =
                "${MainActivity.listOfPlaylist.get(position).listOfSongs.size} Songs"
        else
            holder.noOfSongsInPlaylist.text =
                "${MainActivity.listOfPlaylist.get(position).listOfSongs.size} Songs"


        holder.playlistName.setOnLongClickListener(View.OnLongClickListener {
            deletePlaylist(position)
            return@OnLongClickListener true
        })

        holder.noOfSongsInPlaylist.setOnLongClickListener(View.OnLongClickListener {
            deletePlaylist(position)
            return@OnLongClickListener true
        })
    }

    private fun deletePlaylist(position: Int) {
        val playlistId: String = "" + MainActivity.listOfPlaylist[position].id
        val resolver: ContentResolver = context.contentResolver
        val where = MediaStore.Audio.Playlists._ID + "=?"
        val whereVal = arrayOf(playlistId)
        resolver.delete(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI, where, whereVal)
        Toast.makeText(context, "deleted",Toast.LENGTH_SHORT).show()
        MainActivity.listOfPlaylist.removeAt(position)
        ListsFragment.adapter.notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return MainActivity.listOfPlaylist.size
    }

    class PlaylistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var playlistName: TextView = itemView.findViewById(R.id.name_of_playlist)
        var noOfSongsInPlaylist: TextView = itemView.findViewById(R.id.playlist_no_of_songs)
    }

}