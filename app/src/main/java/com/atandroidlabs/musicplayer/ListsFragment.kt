package com.atandroidlabs.musicplayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListsFragment : Fragment() {

    companion object {

        lateinit var adapter: PlaylistAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.lists_fragment,container,false)
        val playlistRecyclerView: RecyclerView = view.findViewById(R.id.playlist_recyclerview)
        playlistRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = PlaylistAdapter(context!!)
        playlistRecyclerView.adapter = adapter
        return view
    }
}