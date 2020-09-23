package com.atandroidlabs.musicplayer

import android.Manifest
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.atandroidlabs.musicplayer.ui.main.SectionsPagerAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout


class MainActivity : AppCompatActivity() {

    companion object {
        val songList: ArrayList<Song> = ArrayList()
        val albumsList: ArrayList<Album> = ArrayList()
        val artistList: ArrayList<Artist> = ArrayList()
        val listOfPlaylist: ArrayList<Playlist> = ArrayList()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1000 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            fillSongList()
            fillAlbumList()
            fillArtistList()
            fillListOfPlaylist()
        } else {
            @RequiresApi(Build.VERSION_CODES.M)
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1000
                )
            } else {
                Toast.makeText(this@MainActivity, "Permission denied forever", Toast.LENGTH_SHORT).show()
            }
        }

        if (requestCode == 1001 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        } else {
            @RequiresApi(Build.VERSION_CODES.M)
            if (shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1001
                )
            } else {
                Toast.makeText(this@MainActivity, "Permission denied forever", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fillSongList() {
        val uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.AudioColumns.DATA,
            MediaStore.Audio.AudioColumns.ALBUM,
            MediaStore.Audio.ArtistColumns.ARTIST,
            MediaStore.Audio.AudioColumns._ID
        )

        val c: Cursor? = this@MainActivity.contentResolver.query(
            uri,
            projection,
            null,
            null,
            MediaStore.Audio.Media.DEFAULT_SORT_ORDER
        )
        if (c != null) {
            c.moveToFirst()
            while (!c.isAfterLast) {
                val path = c.getString(0)
                val album = c.getString(1)
                val artists = c.getString(2)
                val name = path.substring(path.lastIndexOf("/") + 1)
                val id: Long = c.getLong(3)
                val song: Song = Song(id, name, artists, album, path)
                songList.add(song)
                c.moveToNext()
            }
        }

        c?.close()
    }

    private fun fillAlbumList() {

        val projection = arrayOf(
            MediaStore.Audio.Albums.ALBUM,
            MediaStore.Audio.Artists.Albums.NUMBER_OF_SONGS
        )

        val c: Cursor? = contentResolver.query(
            MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            MediaStore.Audio.Albums.DEFAULT_SORT_ORDER
        )

        if (c != null) {
            c.moveToFirst()

            while (!c.isAfterLast) {
                val albumName = c.getString(0)
                val noOfSongs = c.getInt(1)
                val album: Album =  Album(albumName, noOfSongs)

                albumsList.add(album)
                c.moveToNext()
            }
        }

        c?.close()
    }

    private fun fillArtistList() {
        val projection = arrayOf<String>(
            MediaStore.Audio.Artists.ARTIST,
            MediaStore.Audio.Artists.NUMBER_OF_TRACKS
        )

        val selection: String? = null
        val selectionArgs: Array<String>? = null
        val sortOrder: String = MediaStore.Audio.Artists.DEFAULT_SORT_ORDER + " ASC"
        val cursor = contentResolver.query(
            MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )

        cursor?.moveToFirst()

        if (cursor != null) {
            while (!cursor.isAfterLast) {
                val artistName = cursor.getString(0)
                val artistNoOfSongs = cursor.getInt(1)
                val artist: Artist = Artist(artistName, artistNoOfSongs)
                artistList.add(artist)
                cursor.moveToNext()
            }
        }

        cursor?.close()
    }

    fun addPlaylist(view: View) {
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this@MainActivity)
        val alertView1: View = LayoutInflater.from(this@MainActivity).inflate(
            R.layout.playlist_add_dialog,
            null,
            false
        )

        builder.setView(alertView1)

        val alertDialog: android.app.AlertDialog? = builder.create()

        val playlistEditText: EditText = alertView1.findViewById(R.id.playlist_name)
        val addButton: Button = alertView1.findViewById(R.id.add_playlist)
        val cancelButton: Button = alertView1.findViewById(R.id.cancel_adding_action)

        alertDialog?.show()
        addButton.setOnClickListener(View.OnClickListener {
            val name: String = playlistEditText.text.toString()
            if (name == "") {
                Toast.makeText(applicationContext, "Please enter a name!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                createPlaylist(name)
                alertDialog?.dismiss()
            }
        })

        cancelButton.setOnClickListener(View.OnClickListener {
            alertDialog?.dismiss()
        })
    }

    private fun createPlaylist(playlistName: String) {
        
        if(ContextCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED) {
            val contentResolver: ContentResolver = contentResolver

            val inserts: ContentValues = ContentValues()

            inserts.put(MediaStore.Audio.Playlists.NAME, playlistName)
            inserts.put(MediaStore.Audio.Playlists.DATE_ADDED, System.currentTimeMillis())
            inserts.put(MediaStore.Audio.Playlists.DATE_MODIFIED, System.currentTimeMillis())

            val uri: Uri? = contentResolver.insert(
                MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI,
                inserts
            )

            val projection = arrayOf(
                MediaStore.Audio.Playlists._ID,
                MediaStore.Audio.Playlists.NAME,
                MediaStore.Audio.Playlists.DATA
            )

            if (uri != null) {
                var mPlaylistId = -1;
                val c: Cursor? = contentResolver.query(uri, projection, null, null, null)

                if (c != null) {
                    c.moveToFirst()
                    mPlaylistId = c.getInt(c.getColumnIndex(MediaStore.Audio.Playlists._ID))
                    Toast.makeText(this@MainActivity, "Added", Toast.LENGTH_SHORT).show()

                    listOfPlaylist.clear()

                    fillListOfPlaylist()

                    ListsFragment.adapter.notifyDataSetChanged()

                    val intent: Intent = Intent(this@MainActivity, AddToPlaylistActivity::class.java)
                    intent.putExtra("id",mPlaylistId)
                    startActivity(intent)
                }
                c?.close()
            }
        }

        else {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1001
            )
        }


    }

    private fun fillListOfPlaylist() {

        val projection = arrayOf(
            MediaStore.Audio.Playlists._ID,
            MediaStore.Audio.Playlists.NAME
        )

        listOfPlaylist.clear()

        val c: Cursor? = contentResolver.query(
            MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            MediaStore.Audio.Playlists.DEFAULT_SORT_ORDER
        )
        if (c != null) {
            c.moveToFirst()

            while (!c.isAfterLast) {
                val id: Int = c.getInt(0)
                val name: String = c.getString(1)
                val songs: ArrayList<Song> = fillPlayListWithSongs(id.toLong(),this@MainActivity)
                val playlist: Playlist = Playlist(id, name, songs)
                listOfPlaylist.add(playlist)
                c.moveToNext()
            }
        }
        c?.close()

    }

    private fun fillPlayListWithSongs(id: Long, context: Context): ArrayList<Song>{
        val listOfSongsInPlaylist = ArrayList<Song>()
        val contentResolver = contentResolver
        val playListUri = MediaStore.Audio.Playlists.Members.getContentUri(
            "external",
            id
        )

        val mr = MediaMetadataRetriever()
        val cursor = contentResolver.query(playListUri, null, null, null, null)
        if (cursor != null) {
            if (cursor.moveToNext()) {
                do {
                    val trackId =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Playlists.Members.AUDIO_ID))
                    val mediaContentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    val trackProjection = arrayOf(
                        MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.TITLE,
                        MediaStore.Audio.Media.ARTIST,
                        MediaStore.Audio.Media.ALBUM,
                        MediaStore.Audio.Media.DATA
                    )
                    val selection = MediaStore.Audio.Media._ID + "=?"
                    val selectionArgs = arrayOf("" + trackId)
                    val mediaCursor = contentResolver.query(
                        mediaContentUri,
                        trackProjection,
                        selection,
                        selectionArgs,
                        null
                    )
                    if (mediaCursor != null) {
                        if (mediaCursor.count >= 0) {
                            mediaCursor.moveToPosition(0)
                            val songId =
                                mediaCursor.getInt(mediaCursor.getColumnIndex(MediaStore.Audio.Media._ID))
                            val songTitle =
                                mediaCursor.getString(mediaCursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                            val songArtist =
                                mediaCursor.getString(mediaCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                            val songAlbum =
                                mediaCursor.getString(mediaCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                            val songPath =
                                mediaCursor.getString(mediaCursor.getColumnIndex(MediaStore.Audio.Media.DATA))

                            val song: Song = Song(songId.toLong(),songTitle , songArtist,songAlbum, songPath)

                            listOfSongsInPlaylist.add(song)
                        }
                    }
                    mediaCursor?.close()
                } while (cursor.moveToNext())
            }
        }
        cursor?.close()

        return listOfSongsInPlaylist
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = findViewById(R.id.fab)

        if (ContextCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED) {
            fillSongList()
            fillAlbumList()
            fillArtistList()
            fillListOfPlaylist()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                1000
            )
        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }
}