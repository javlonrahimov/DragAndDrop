package com.javlonrahimov1212.footbalteams.adapters

import android.content.ClipData
import android.content.ClipDescription
import android.util.Log
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.javlonrahimov1212.footbalteams.Base
import com.javlonrahimov1212.footbalteams.Base.observeOnce
import com.javlonrahimov1212.footbalteams.R
import com.javlonrahimov1212.footbalteams.models.Player
import kotlinx.android.synthetic.main.item_player.view.*
import java.util.*
import kotlin.collections.ArrayList

class PlayerAdapter(val lifecycleOwner: LifecycleOwner) :
    RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {

    var data = ArrayList<Player>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val onItemSwapped = object : OnItemSwapped {
        override fun onSwapped(id: Int, player2: Player) {
            val player1: Player? = data.find { it.id == id }
            if (player1 != null) {
                swapItems(player1, player2)
            }
        }
    }

    private fun swapItems(player1: Player, player2: Player) {
        Base.footballers.observeOnce(lifecycleOwner, { t ->
            Collections.swap(t, t.indexOf(player1), t.indexOf(player2))
            Base.footballers.postValue(t)
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_player, parent, false),
            onItemSwapped
        )
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bindData(data[position])
    }

    override fun getItemCount(): Int = data.size

    class PlayerViewHolder(itemView: View, private val onItemSwapped: OnItemSwapped) :
        RecyclerView.ViewHolder(itemView) {
        fun bindData(player: Player) {
            itemView.image.setImageResource(player.image)
            itemView.name.text = player.name
            itemView.item_player.setOnDragListener { _, event ->
                when (event.action) {
                    DragEvent.ACTION_DROP -> {
                        val id = event.clipData.getItemAt(0).text.toString().toInt()
                        onItemSwapped.onSwapped(
                            id,
                            player
                        )
                        Log.d("HELLO", id.toString())
                        true
                    }
                    else -> true
                }

            }
            itemView.item_player.setOnLongClickListener {
                val clipText = (player.id).toString()
                val item = ClipData.Item(clipText)
                val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
                val data = ClipData(clipText, mimeTypes, item)
                val dragShadowBuilder = View.DragShadowBuilder(it)
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    it.startDragAndDrop(data, dragShadowBuilder, it, 0)
                } else {
                    it.startDrag(data, dragShadowBuilder, it, 0)
                }
                true
            }
        }

    }
}

interface OnItemSwapped {
    fun onSwapped(id: Int, player2: Player)
}


