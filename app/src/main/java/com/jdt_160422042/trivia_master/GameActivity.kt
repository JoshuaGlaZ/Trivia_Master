package com.jdt_160422042.trivia_master

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.INVISIBLE
import android.widget.Button
import com.jdt_160422042.trivia_master.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding

    companion object {
        val SCORE_KEY = "score"

        val ASK_AUDIENCE_KEY = "askAudience"

        val FIFTY_KEY = "fifty"

        val PHONE_FRIEND_KEY = "phoneFriend"

    }

    var score = 0
    var selectedQuestions:Array<Question> = Global.questions

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val playerName: String? = intent.getStringExtra(GameSetupActivity.PLAYER_NAME_KEY)
        val difficulty: Difficulty? = intent.getStringExtra(GameSetupActivity.DIFFICULTY_KEY)
            ?.let { Difficulty.valueOf(it) }
        val type: Type? = intent.getStringExtra(GameSetupActivity.TYPE_KEY)
            ?.let { Type.valueOf(it) }
        var askAudience = intent.getBooleanExtra(ASK_AUDIENCE_KEY, true)
        var fifty = intent.getBooleanExtra(FIFTY_KEY, true)
        var phoneFriend = intent.getBooleanExtra(PHONE_FRIEND_KEY, true)

        score = intent.getIntExtra(SCORE_KEY, 0)

        displayQuestion(difficulty, type)
        val selected = selectedQuestions[0]

        with (binding) {
            btnAskAudience.isEnabled = askAudience
            btn5050.isEnabled = fifty
            btnPhoneFriend.isEnabled = phoneFriend

            btnAnswerA.setOnClickListener {
                if (getAnswer(btnAnswerA.text as String) == selected.correct_answer) {
                    correctAnswer(playerName, difficulty, type, askAudience, fifty, phoneFriend)
                } else {
                    gameOver(playerName, difficulty, type)
                }
            }

            btnAnswerB.setOnClickListener {
                if (getAnswer(btnAnswerB.text as String) == selected.correct_answer) {
                    correctAnswer(playerName, difficulty, type, askAudience, fifty, phoneFriend)
                } else {
                    gameOver(playerName, difficulty, type)
                }
            }

            btnAnswerC.setOnClickListener {
                if (getAnswer(btnAnswerC.text as String) == selected.correct_answer) {
                    correctAnswer(playerName, difficulty, type, askAudience, fifty, phoneFriend)
                } else {
                    gameOver(playerName, difficulty, type)
                }
            }

            btnAnswerD.setOnClickListener {
                if (getAnswer(btnAnswerD.text as String) == selected.correct_answer) {
                    correctAnswer(playerName, difficulty, type, askAudience, fifty, phoneFriend)
                } else {
                    gameOver(playerName, difficulty, type)
                }
            }

            btnAskAudience.setOnClickListener {
                if (askAudience) {
                    val answers: ArrayList<Button> = arrayListOf(btnAnswerA, btnAnswerB, btnAnswerC, btnAnswerD)
                    val audiencePercentage = getPercentage(selected, answers)
                    btnAnswerA.text = "${btnAnswerA.text} (${audiencePercentage[0]}%)"
                    btnAnswerB.text = "${btnAnswerB.text} (${audiencePercentage[1]}%)"
                    btnAnswerC.text = "${btnAnswerC.text} (${audiencePercentage[2]}%)"
                    btnAnswerD.text = "${btnAnswerD.text} (${audiencePercentage[3]}%)"
                    askAudience = false
                    btnAskAudience.isEnabled = askAudience
                }
            }

            btn5050.setOnClickListener {
                if (fifty) {
                    val deletedAnswers: ArrayList<Button> = arrayListOf(btnAnswerA, btnAnswerB, btnAnswerC, btnAnswerD)
                        .filterNot { getAnswer(it.text as String) == selected.correct_answer } as ArrayList<Button>
                    repeat(2) {
                        val randomIndex = (0 until deletedAnswers.size).random()
                        when (getAnswer(deletedAnswers[randomIndex].text as String)) {
                            getAnswer(btnAnswerA.text as String) -> btnAnswerA.visibility = INVISIBLE
                            getAnswer(btnAnswerB.text as String) -> btnAnswerB.visibility = INVISIBLE
                            getAnswer(btnAnswerC.text as String)-> btnAnswerC.visibility = INVISIBLE
                            getAnswer(btnAnswerD.text as String) -> btnAnswerD.visibility = INVISIBLE
                        }
                        deletedAnswers.remove(deletedAnswers[randomIndex])
                    }
                    fifty = false
                    btn5050.isEnabled = fifty
                }
            }

            btnPhoneFriend.setOnClickListener {
                if (phoneFriend) {
                    var successRate = 0
                    when (selected.difficulty) {
                        Difficulty.Easy -> successRate = 100
                        Difficulty.Medium -> successRate = 85
                        Difficulty.Hard -> successRate = 70
                    }
                    val buttons: Array<Button> = arrayOf(btnAnswerA, btnAnswerB, btnAnswerC, btnAnswerD)
                        .filterNot { getAnswer(it.text as String) == selected.correct_answer }.toTypedArray()

                    if ((0..100).random() <= successRate) {
                        when (selected.correct_answer) {
                            getAnswer(btnAnswerA.text as String) -> btnAnswerA.setBackgroundColor(Color.CYAN)
                            getAnswer(btnAnswerB.text as String) -> btnAnswerB.setBackgroundColor(Color.CYAN)
                            getAnswer(btnAnswerC.text as String) -> btnAnswerC.setBackgroundColor(Color.CYAN)
                            getAnswer(btnAnswerD.text as String) -> btnAnswerD.setBackgroundColor(Color.CYAN)
                        }
                    } else {
                        val randomIndex = (0 until buttons.size).random()
                        buttons[randomIndex].setBackgroundColor(Color.CYAN)
                    }
                    phoneFriend = false
                    btnPhoneFriend.isEnabled = phoneFriend
                }
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
//        super.onBackPressed()
    }

    fun correctAnswer(player_name: String?, difficulty: Difficulty?, type: Type?, askAudience: Boolean,
                      fifty: Boolean, phoneFriend: Boolean) {
        score += when (difficulty) {
            Difficulty.Easy -> 1000
            Difficulty.Medium -> 2000
            Difficulty.Hard -> 3000
            else -> 0        }
        val intent = Intent(this, CorrectAnswerActivity::class.java)
        intent.putExtra(GameSetupActivity.PLAYER_NAME_KEY, player_name)
        intent.putExtra(GameSetupActivity.DIFFICULTY_KEY, difficulty?.name)
        intent.putExtra(GameSetupActivity.TYPE_KEY, type?.name)
        intent.putExtra(ASK_AUDIENCE_KEY, askAudience)
        intent.putExtra(FIFTY_KEY, fifty)
        intent.putExtra(PHONE_FRIEND_KEY, phoneFriend)
        intent.putExtra(SCORE_KEY, score)
        startActivity(intent)
    }

    fun gameOver(player_name: String?, difficulty: Difficulty?, type: Type?) {
        val intent = Intent(this, ScoreListActivity::class.java)
        intent.putExtra(GameSetupActivity.PLAYER_NAME_KEY, player_name)
        intent.putExtra(GameSetupActivity.DIFFICULTY_KEY, difficulty?.name)
        intent.putExtra(GameSetupActivity.TYPE_KEY, type?.name)
        intent.putExtra(SCORE_KEY, score)
        startActivity(intent)
    }

    fun displayQuestion(difficulty: Difficulty?, type: Type?) {
        selectedQuestions = selectedQuestions.filter { it.difficulty == difficulty && it.type == type }.toTypedArray()
        selectedQuestions.shuffle()
        binding.txtQuestion.text = selectedQuestions[0].question

        shuffleAnswer(selectedQuestions[0])
    }

    fun shuffleAnswer(selected: Question) {
        val answers: Array<String> = arrayOf(selected.answer_a, selected.answer_b, selected.answer_c, selected.answer_d)
        answers.shuffle()

        binding.btnAnswerA.text = answers[0]
        binding.btnAnswerB.text = answers[1]
        binding.btnAnswerC.text = answers[2]
        binding.btnAnswerD.text = answers[3]
    }

    fun getPercentage(selected: Question, answers: ArrayList<Button>): Array<String?> {
        val percentages = arrayOfNulls<Int?>(answers.size)
        val correctPercentage = when (selected.difficulty) {
            Difficulty.Easy -> (70..75).random()
            Difficulty.Medium -> (65..70).random()
            Difficulty.Hard -> (55..65).random()
        }
        var remainingPercentage = 100 - correctPercentage
        val correctAnswerIndex = answers.indexOfFirst { it.text == selected.correct_answer }

        if (correctAnswerIndex == -1) {
            throw IllegalArgumentException("Invalid correct answer")
        }
        percentages[correctAnswerIndex] = correctPercentage

        for (i in percentages.indices) {
            if (i != correctAnswerIndex && percentages[i] == null) {
                val randomPercentage = if (remainingPercentage>0) (1..remainingPercentage).random() else 0
                percentages[i] = randomPercentage
                remainingPercentage -= randomPercentage
            }
        }

        if (remainingPercentage > 0) {
            val randomIndex = (percentages.indices).filterNot { it == correctAnswerIndex }.random()
            percentages[randomIndex] = percentages[randomIndex]!! + remainingPercentage
        }
        return percentages.map { it?.toString() }.toTypedArray()
    }

        fun getAnswer(text: String): String {
            return if (text.lastIndexOf('(') > 0)
                text.substring(0, text.lastIndexOf('(')).trim()
            else text.trim()
    }
}