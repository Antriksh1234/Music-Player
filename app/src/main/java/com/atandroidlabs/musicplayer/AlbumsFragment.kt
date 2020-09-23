package com.atandroidlabs.musicplayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AlbumsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.albums_fragment,container,false)
        val albumsRecyclerView: RecyclerView = view.findViewById(R.id.albums_recyclerview_list)
        albumsRecyclerView.layoutManager = LinearLayoutManager(context)
        val albumAdapter: AlbumAdapter = AlbumAdapter(context!!)
        albumsRecyclerView.adapter = albumAdapter
        return view
    }
}