package com.atandroidlabs.musicplayer

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SongsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.songs_fragment,container,false)
        val songRecyclerView: RecyclerView = view.findViewById(R.id.song_recyclerview)
        songRecyclerView.layoutManager = LinearLayoutManager(context)
        val adapter: SongAdapter = SongAdapter(context!!)
        songRecyclerView.adapter = adapter
        return view
    }
}