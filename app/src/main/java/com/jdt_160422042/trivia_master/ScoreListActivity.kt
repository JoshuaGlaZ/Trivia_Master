package com.jdt_160422042.trivia_master

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jdt_160422042.trivia_master.databinding.ActivityScoreListBinding

class ScoreListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScoreListBinding

    companion object {
        val PLAYERS_KEY = "player_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedFile = "com.jdt_160422042.trivia_master"
        val shared: SharedPreferences = getSharedPreferences(sharedFile, Context.MODE_PRIVATE)
        val playersJson = shared.getString(PLAYERS_KEY, null)
        var players = arrayListOf<Player>()
        val gson = Gson()

        if (playersJson != null) {
            val type = object : TypeToken<ArrayList<Player>>() {}.type
            players = gson.fromJson(playersJson, type)
        }

        val playerName: String? = intent.getStringExtra(GameSetupActivity.PLAYER_NAME_KEY)
        val difficulty: Difficulty? = intent.getStringExtra(GameSetupActivity.DIFFICULTY_KEY)
            ?.let { Difficulty.valueOf(it) }
        val type: Type? = intent.getStringExtra(GameSetupActivity.TYPE_KEY)
            ?.let { Type.valueOf(it) }
        val score = intent.getIntExtra(GameActivity.SCORE_KEY, 0)

        val player = Player(playerName, score, difficulty, type)
        players.add(player)

        val savePlayers: String = gson.toJson(players)
        shared.edit().putString(PLAYERS_KEY, savePlayers).apply()

        val lm = LinearLayoutManager(this)
        with(binding.recyclerView) {
            layoutManager = lm
            setHasFixedSize(true)
            adapter = ScoreAdapter(players)
        }

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, GameSetupActivity::class.java)
            intent.putExtra(GameSetupActivity.PLAYER_NAME_KEY, playerName)
            intent.putExtra(GameSetupActivity.DIFFICULTY_KEY, difficulty?.name)
            intent.putExtra(GameSetupActivity.TYPE_KEY, type?.name)
            startActivity(intent)
        }
    }
}