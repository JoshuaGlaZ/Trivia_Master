package com.jdt_160422042.trivia_master

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import com.jdt_160422042.trivia_master.databinding.ActivityGameSetupBinding

class GameSetupActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameSetupBinding

    private var NIGHT_MODE_KEY = "night_mode"

    companion object {
        val PLAYER_NAME_KEY = "player_name"
        val DIFFICULTY_KEY = "difficulty"
        val TYPE_KEY = "type"
    }

    var selected_difficult = Difficulty.Easy

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameSetupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedFile = "com.jdt_160422042.trivia_master"
        val shared: SharedPreferences = getSharedPreferences(sharedFile, Context.MODE_PRIVATE)
        val nightMode = shared.getBoolean(NIGHT_MODE_KEY, false)
        if (nightMode) {
            binding.switchNightMode.setChecked(nightMode)
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
        }

        val playerName: String? = intent.getStringExtra(PLAYER_NAME_KEY)
        val difficulty = intent.getStringExtra(DIFFICULTY_KEY)
            ?.let { Difficulty.valueOf(it) }
        val type = intent.getStringExtra(TYPE_KEY)
            ?.let { Type.valueOf(it) }

        if (difficulty != null) {
            selected_difficult = difficulty
        }

        val adapter = ArrayAdapter(this, R.layout.spinner_layout, Global.types)
        adapter.setDropDownViewResource(R.layout.spinner_item_layout)
        binding.comboType.adapter = adapter

        binding.txtPlayerName.setText(playerName)

        if (difficulty != null) {
            if (difficulty == Difficulty.Easy) {
                binding.groupDifficulty.check(R.id.radioEasy)
            } else if (difficulty == Difficulty.Medium) {
                binding.groupDifficulty.check(R.id.radioMedium)
            } else {
                binding.groupDifficulty.check(R.id.radioHard)
            }
        }

        if (type != null) {
            binding.comboType.setSelection(adapter.getPosition(type.name))
        }

        binding.groupDifficulty.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.radioEasy) {
                selected_difficult = Difficulty.Easy
            } else if (checkedId == R.id.radioMedium) {
                selected_difficult = Difficulty.Medium
            } else {
                selected_difficult = Difficulty.Hard
            }
        }

        binding.switchNightMode.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(if (nightMode) MODE_NIGHT_NO else MODE_NIGHT_YES)
            shared.edit().putBoolean(NIGHT_MODE_KEY, !nightMode).apply()
        }

        binding.btnStart.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra(DIFFICULTY_KEY, selected_difficult.name)
            intent.putExtra(TYPE_KEY, binding.comboType.selectedItem.toString())
            intent.putExtra(PLAYER_NAME_KEY, binding.txtPlayerName.text.toString())
            startActivity(intent)
        }
    }
}