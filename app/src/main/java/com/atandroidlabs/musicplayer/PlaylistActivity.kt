package com.atandroidlabs.musicplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class PlaylistActivity : AppCompatActivity() {

    private val playlistName: TextView = findViewById(R.id.selected_playlist)

    companion object{
        var songsInPlaylist = ArrayList<Song>()
    }

    private fun getPlayList(id: Int): Playlist? {
        for (playlist in MainActivity.listOfPlaylist) {
            if (playlist.id == id) {
                return playlist
            }
        }

        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist)

        val id: Int = intent.getIntExtra("id",-100)
        var playlist: Playlist

        if (id != -100) {
            playlist = getPlayList(id)!!
            playlistName.text = playlist.name
            songsInPlaylist = playlist.listOfSongs
        }
    }
}