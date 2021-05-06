package com.javlonrahimov1212.footbalteams

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.javlonrahimov1212.footbalteams.adapters.PlayerAdapter
import com.javlonrahimov1212.footbalteams.models.Clubs
import com.javlonrahimov1212.footbalteams.models.Player
import kotlinx.android.synthetic.main.fragment_items.view.*

class ItemsFragment(private val club: Clubs) : Fragment() {

    constructor() : this(Clubs.CHE)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_items, container, false)


        val adapter = PlayerAdapter(this)

        Base.footballers.observe(this,
            { t ->
                if (t != null) {
                    Log.d("HELLO", t.size.toString())
                    adapter.data = t.filter { it.club == club } as ArrayList<Player>
                }
            }
        )

        view.recycler.adapter = adapter


        return view
    }
}