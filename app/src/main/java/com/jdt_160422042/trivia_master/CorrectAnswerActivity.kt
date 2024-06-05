package com.jdt_160422042.trivia_master

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jdt_160422042.trivia_master.databinding.ActivityCorrectAnswerBinding

class CorrectAnswerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCorrectAnswerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCorrectAnswerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val player_name: String? = intent.getStringExtra(GameSetupActivity.PLAYER_NAME_KEY)
        val difficulty: Difficulty? = intent.getStringExtra(GameSetupActivity.DIFFICULTY_KEY)
            ?.let { Difficulty.valueOf(it) }
        val type: Type? = intent.getStringExtra(GameSetupActivity.TYPE_KEY)
            ?.let { Type.valueOf(it) }
        val askAudience = intent.getBooleanExtra(GameActivity.ASK_AUDIENCE_KEY, true)
        val fifty = intent.getBooleanExtra(GameActivity.FIFTY_KEY, true)
        val score = intent.getIntExtra(GameActivity.SCORE_KEY, 0)
        val phoneFriend = intent.getBooleanExtra(GameActivity.PHONE_FRIEND_KEY, true)

        binding.txtScore.text = score.toString()

        binding.txtInfo.text = "If you surrender now, you will learn $score points"

        binding.btnContinue.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra(GameSetupActivity.PLAYER_NAME_KEY, player_name)
            intent.putExtra(GameSetupActivity.DIFFICULTY_KEY, difficulty?.name)
            intent.putExtra(GameSetupActivity.TYPE_KEY, type?.name)
            intent.putExtra(GameActivity.ASK_AUDIENCE_KEY, askAudience)
            intent.putExtra(GameActivity.FIFTY_KEY, fifty)
            intent.putExtra(GameActivity.PHONE_FRIEND_KEY, phoneFriend)
            intent.putExtra(GameActivity.SCORE_KEY, score)
            startActivity(intent)
        }

        binding.btnSurrender.setOnClickListener {
            val intent = Intent(this, ScoreListActivity::class.java)
            intent.putExtra(GameSetupActivity.PLAYER_NAME_KEY, player_name)
            intent.putExtra(GameSetupActivity.DIFFICULTY_KEY, difficulty?.name)
            intent.putExtra(GameSetupActivity.TYPE_KEY, type?.name)
            intent.putExtra(GameActivity.SCORE_KEY, score)
            startActivity(intent)
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
//        super.onBackPressed()
    }
}