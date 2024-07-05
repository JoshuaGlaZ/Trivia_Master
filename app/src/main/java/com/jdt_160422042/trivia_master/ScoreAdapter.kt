package com.jdt_160422042.trivia_master

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jdt_160422042.trivia_master.databinding.ScoreItemBinding

class ScoreAdapter(var players: ArrayList<Player>) : RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>() {
    class ScoreViewHolder(val binding: ScoreItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val binding = ScoreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScoreViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return players.size
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        with (holder.binding) {
            txtName.text = players[position].name
            txtScore.text = players[position].score.toString()
            txtDifficulty.text = players[position].difficulty
            txtType.text = players[position].type
        }
    }
}