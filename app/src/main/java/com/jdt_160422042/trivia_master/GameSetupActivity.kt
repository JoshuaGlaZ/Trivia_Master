package com.jdt_160422042.trivia_master

import android.annotation.SuppressLint
import android.app.UiModeManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.core.graphics.toColor
import com.jdt_160422042.trivia_master.databinding.ActivityGameSetupBinding

class GameSetupActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameSetupBinding

    private var NIGHT_MODE_KEY = "night_mode"
    private var isRecreating = false
    companion object {
        val PLAYER_NAME_KEY = "player_name"
        val DIFFICULTY_KEY = "difficulty"
        val TYPE_KEY = "type"
    }

    var selected_difficult = "Easy"

    @SuppressLint("ServiceCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameSetupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null) {
            isRecreating = true
        }

        val sharedFile = "com.jdt_160422042.trivia_master"
        val shared: SharedPreferences = getSharedPreferences(sharedFile, Context.MODE_PRIVATE)

        val nightMode = shared.getInt(NIGHT_MODE_KEY, MODE_NIGHT_FOLLOW_SYSTEM)

        val playerName: String? = intent.getStringExtra(PLAYER_NAME_KEY)
        val difficulty = intent.getStringExtra(DIFFICULTY_KEY)
        val type = intent.getStringExtra(TYPE_KEY)

        if (difficulty != null) {
            selected_difficult = difficulty
        }

        val adapter = ArrayAdapter(this, R.layout.spinner_layout, arrayOf("History", "Geography", "Math"))
        adapter.setDropDownViewResource(R.layout.spinner_item_layout)
        binding.comboType.adapter = adapter

        binding.switchNightMode.isChecked = nightMode == MODE_NIGHT_YES
        binding.switchNightMode.setOnCheckedChangeListener { _, isChecked ->
            val uiModeManager = getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
            if (isChecked) {
                uiModeManager.nightMode = UiModeManager.MODE_NIGHT_YES
                shared.edit().putInt(NIGHT_MODE_KEY, AppCompatDelegate.MODE_NIGHT_YES).apply()
            } else {
                uiModeManager.nightMode = UiModeManager.MODE_NIGHT_NO
                shared.edit().putInt(NIGHT_MODE_KEY, AppCompatDelegate.MODE_NIGHT_NO).apply()
            }
        }

        binding.txtPlayerName.setText(playerName)

        if (difficulty != null) {
            if (difficulty == "Easy") {
                binding.groupDifficulty.check(R.id.radioEasy)
            } else if (difficulty == "Medium") {
                binding.groupDifficulty.check(R.id.radioMedium)
            } else {
                binding.groupDifficulty.check(R.id.radioHard)
            }
        }

        if (type != null) {
            binding.comboType.setSelection(adapter.getPosition(type))
        }

        binding.groupDifficulty.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.radioEasy) {
                selected_difficult = "Easy"
            } else if (checkedId == R.id.radioMedium) {
                selected_difficult = "Medium"
            } else {
                selected_difficult = "Hard"
            }
        }

        binding.btnStart.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra(DIFFICULTY_KEY, selected_difficult)
            intent.putExtra(TYPE_KEY, binding.comboType.selectedItem.toString())
            intent.putExtra(PLAYER_NAME_KEY, binding.txtPlayerName.text.toString())
            startActivity(intent)
        }

        binding.bottomNavBarSetup.setOnItemSelectedListener {
            val intent = when(it.itemId) {
                R.id.score -> Intent(this, ScoreListActivity::class.java)
                R.id.edit -> Intent(this, EditQuestionActivity::class.java)
                else -> Intent(this, GameSetupActivity::class.java)
            }
            intent.putExtra(Navigation.INTENT_SETUP, true)
            startActivity(intent)
            true
        }
    }
}