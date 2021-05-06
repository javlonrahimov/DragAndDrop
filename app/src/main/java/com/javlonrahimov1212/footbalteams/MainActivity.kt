package com.javlonrahimov1212.footbalteams

import android.os.Bundle
import android.view.DragEvent
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.javlonrahimov1212.footbalteams.Base.observeOnce
import com.javlonrahimov1212.footbalteams.adapters.ViewPagerAdapter
import com.javlonrahimov1212.footbalteams.models.Clubs
import com.javlonrahimov1212.footbalteams.models.Player
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val clubs = arrayListOf("CHE", "PSG", "RMA", "MCI")
    private val clubsEnum = arrayListOf(Clubs.CHE, Clubs.PSG, Clubs.RMA, Clubs.MCI)

    init {
        val data = arrayListOf(
            Player(1, R.drawable.mount, "Mason Mount", Clubs.CHE),
            Player(2, R.drawable.werner, "Timo Werner", Clubs.CHE),
            Player(3, R.drawable.mendy, "Eduar Mendy", Clubs.CHE),
            Player(4, R.drawable.kante, "Ngolo Kante", Clubs.CHE),
            Player(5, R.drawable.silva, "Tiago Silva", Clubs.CHE),
            Player(6, R.drawable.pulisic, "Christian Pulisic", Clubs.CHE),
            Player(7, R.drawable.benzema, "Karim Benzema", Clubs.RMA),
            Player(8, R.drawable.courtois, "Tibo Courtois", Clubs.RMA),
            Player(9, R.drawable.de_bruyne, "Kevin De-Bruyne", Clubs.MCI),
            Player(10, R.drawable.draxler, "Julian Draxler", Clubs.PSG),
            Player(11, R.drawable.ederson, "Ederson Moraes", Clubs.MCI),
            Player(12, R.drawable.foden, "Phil Foden", Clubs.MCI),
            Player(13, R.drawable.mahrez, "Ryad Mahrez", Clubs.MCI),
            Player(14, R.drawable.marselo, "Marselo", Clubs.RMA),
            Player(15, R.drawable.mbappe, "Kilian Mbappe", Clubs.PSG),
            Player(16, R.drawable.modric, "Luca Modrich", Clubs.RMA),
            Player(17, R.drawable.navas, "Keylar Navas", Clubs.PSG),
            Player(18, R.drawable.neymar, "Neymar da Silva", Clubs.PSG),
            Player(19, R.drawable.ramos, "Sergio Ramos", Clubs.RMA),
            Player(20, R.drawable.sterling, "Raheem Sterling", Clubs.MCI),
            Player(21, R.drawable.veratti, "MArco Veratti", Clubs.PSG),
        )
        Base.footballers.postValue(data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = ViewPagerAdapter(this)
        myPager.adapter = adapter

        TabLayoutMediator(
            myTabLayout,
            myPager,
        ) { tab, position ->
            tab.text = clubs[position]
            tab.view.setOnDragListener { _, event ->
                when (event.action) {
                    DragEvent.ACTION_DROP -> {
                        val id = event.clipData.getItemAt(0).text.toString().toInt()
                        Base.footballers.observeOnce(this, { t ->
                            if (t != null) {
                                t.find { it.id == id }?.club = clubsEnum[position]
                                Base.footballers.postValue(t)
                            }
                        })
                    }
                }
                true
            }
        }.attach()
    }
}