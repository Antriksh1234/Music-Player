package com.atandroidlabs.musicplayer

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AddToPlaylistActivity : AppCompatActivity() {

    companion object {
        val songs: ArrayList<Song> = ArrayList()
        var songAlreadyPresent: ArrayList<Song> = ArrayList()
        var songToBeAdded: ArrayList<Song> = ArrayList()
    }

    fun addSongToPlaylist(view: View) {
        val id = intent.getIntExtra("id",-100)
        if (id != -100) {
            val playlist: Playlist = getPlayList(id)!!
            addTracksToPlaylist(playlist, songs, this@AddToPlaylistActivity)
            ListsFragment.adapter.notifyDataSetChanged()
        }

        finish()
    }

    private fun getPlayList(id: Int): Playlist? {
        for (playlist in MainActivity.listOfPlaylist) {
            if (playlist.id == id) {
                return playlist
            }
        }

        return null
    }

    fun cancelAction(view: View) {
        finish()
    }

    private fun addTracksToPlaylist(playlist: Playlist, tracks: List<Song>, context: Context): String? {
        val count: Int = playlist.listOfSongs.size
        val values = arrayOfNulls<ContentValues>(tracks.size)
        for (i in tracks.indices) {
            values[i] = ContentValues()
            values[i]!!.put(MediaStore.Audio.Playlists.Members.PLAY_ORDER, i + count + 1)
            values[i]!!.put(MediaStore.Audio.Playlists.Members.AUDIO_ID, tracks[i].id)
        }
        val uri = MediaStore.Audio.Playlists.Members.getContentUri("external", playlist.id.toLong())
        val resolver: ContentResolver = context.contentResolver
        val num = resolver.bulkInsert(uri, values)
        resolver.notifyChange(Uri.parse("content://media"), null)
        return "added"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_playlist)

        val songSelectorRecyclerView: RecyclerView = findViewById(R.id.song_selector_recyclerview)
        val adapter: SongChooseAdapter = SongChooseAdapter(applicationContext)

        songSelectorRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        songSelectorRecyclerView.adapter = adapter


        var playlist: Playlist
        if(intent.getIntExtra("id",-100) != -100){
            playlist = getPlayList(intent.getIntExtra("id",-100))!!

            songAlreadyPresent = playlist.listOfSongs

            for (song in MainActivity.songList) {
                if (!songAlreadyPresent.contains(song)) {
                    songToBeAdded.add(song)
                }
            }
        }

        else
            Toast.makeText(this@AddToPlaylistActivity,"No such playlist", Toast.LENGTH_SHORT).show()
    }
}