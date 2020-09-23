package com.atandroidlabs.musicplayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ArtistsFragment : Fragment() {

    var artistList: ArrayList<Artist> = ArrayList()
    var artistNameList: ArrayList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.artists_fragment,container,false)
        val artistsRecyclerView: RecyclerView = view.findViewById(R.id.artist_recyclerview_list)
        artistsRecyclerView.layoutManager = LinearLayoutManager(context)
        val artistAdapter: ArtistAdapter = ArtistAdapter(context!!)
        artistsRecyclerView.adapter = artistAdapter
        return view
    }
}