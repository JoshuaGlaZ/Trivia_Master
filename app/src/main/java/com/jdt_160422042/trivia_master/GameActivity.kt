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
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jdt_160422042.trivia_master.databinding.ActivityGameBinding
import org.json.JSONObject

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding

    companion object {
        val SCORE_KEY = "score"

        val ASK_AUDIENCE_KEY = "askAudience"

        val FIFTY_KEY = "fifty"

        val PHONE_FRIEND_KEY = "phoneFriend"

    }

    var score = 0
    lateinit var selected:Question

    fun getQuestions(difficulty: String, type: String){
        val q = Volley.newRequestQueue(this)
        val url = "http://10.0.2.2/trivia_master/get_questions.php"
        val sr = object: StringRequest(
            Request.Method.POST,
            url,
            {
                Log.d("apiresult", it)
                val obj = JSONObject(it)
                if(obj.getString("result") == "OK") {
                    val data = obj.getJSONObject("data")
                    val sType = object : TypeToken<Question>() {}.type

                    selected = Gson().fromJson(data.toString(), sType) as Question
                    Log.d("apiresult", selected.toString())
                    shuffleAnswer()
                }
            },
            { Log.e("apiresult", it.message.toString()) }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["difficulty"] = difficulty
                params["type"] = type
                return params
            }
        }
        q.add(sr)
    }
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val playerName: String? = intent.getStringExtra(GameSetupActivity.PLAYER_NAME_KEY)
        val difficulty: String? = intent.getStringExtra(GameSetupActivity.DIFFICULTY_KEY)
        val type: String? = intent.getStringExtra(GameSetupActivity.TYPE_KEY)
        var askAudience = intent.getBooleanExtra(ASK_AUDIENCE_KEY, true)
        var fifty = intent.getBooleanExtra(FIFTY_KEY, true)
        var phoneFriend = intent.getBooleanExtra(PHONE_FRIEND_KEY, true)

        score = intent.getIntExtra(SCORE_KEY, 0)

        getQuestions(difficulty!!, type!!)

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
                        "Easy" -> successRate = 100
                        "Medium" -> successRate = 85
                        "Hard" -> successRate = 70
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

    fun correctAnswer(player_name: String?, difficulty: String?, type: String?, askAudience: Boolean,
                      fifty: Boolean, phoneFriend: Boolean) {
        score += when (difficulty) {
            "Easy" -> 1000
            "Medium" -> 2000
            "Hard" -> 3000
            else -> 0        }
        val intent = Intent(this, CorrectAnswerActivity::class.java)
        intent.putExtra(GameSetupActivity.PLAYER_NAME_KEY, player_name)
        intent.putExtra(GameSetupActivity.DIFFICULTY_KEY, difficulty)
        intent.putExtra(GameSetupActivity.TYPE_KEY, type)
        intent.putExtra(ASK_AUDIENCE_KEY, askAudience)
        intent.putExtra(FIFTY_KEY, fifty)
        intent.putExtra(PHONE_FRIEND_KEY, phoneFriend)
        intent.putExtra(SCORE_KEY, score)
        startActivity(intent)
    }

    fun gameOver(player_name: String?, difficulty: String?, type: String?) {
        val intent = Intent(this, ScoreListActivity::class.java)
        intent.putExtra(GameSetupActivity.PLAYER_NAME_KEY, player_name)
        intent.putExtra(GameSetupActivity.DIFFICULTY_KEY, difficulty)
        intent.putExtra(GameSetupActivity.TYPE_KEY, type)
        intent.putExtra(SCORE_KEY, score)
        startActivity(intent)
    }

    fun shuffleAnswer() {
        binding.txtQuestion.text = selected.question

        val answers: Array<String?> = arrayOf(selected.answer_a, selected.answer_b, selected.answer_c, selected.answer_d)
        answers.shuffle()

        binding.btnAnswerA.text = answers[0]
        binding.btnAnswerB.text = answers[1]
        binding.btnAnswerC.text = answers[2]
        binding.btnAnswerD.text = answers[3]
    }

    fun getPercentage(selected: Question, answers: ArrayList<Button>): Array<String?> {
        val percentages = arrayOfNulls<Int?>(answers.size)
        val correctPercentage = when (selected.difficulty) {
            "Easy" -> (70..75).random()
            "Medium" -> (65..70).random()
            "Hard" -> (55..65).random()
            else -> 0
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