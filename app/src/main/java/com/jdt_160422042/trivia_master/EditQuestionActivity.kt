package com.jdt_160422042.trivia_master

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jdt_160422042.trivia_master.databinding.ActivityEditQuestionBinding
import org.json.JSONObject

class EditQuestionActivity : AppCompatActivity() {
    private var questions: ArrayList<Question> = ArrayList()
    private lateinit var binding: ActivityEditQuestionBinding

    companion object {
        val REQUEST_UPDATE_QUESTION = "question_id"
    }
//
//    fun getQuestions(){
//        val q = Volley.newRequestQueue(this)
//        val url = "http://10.0.2.2/trivia_master/get_questions.php"
//        val sr = StringRequest(
//            Request.Method.POST,
//            url,
//            {
//                Log.d("apiresult", it)
//                val obj = JSONObject(it)
//                if(obj.getString("result") == "OK") {
//                    val data = obj.getJSONArray("data")
//                    val sType = object : TypeToken<List<Question>>() {}.type
//
//                    questions = Gson().fromJson(data.toString(), sType) as ArrayList<Question>
//
//                    Log.d("apiresult", questions.toString())
//                    updateList()
//                }
//            },
//            { Log.e("apiresult", it.message.toString()) }
//        )
//        q.add(sr)
//    }
//
//    fun updateList() {
//        binding.questionsRecView.layoutManager = LinearLayoutManager(this)
//        binding.questionsRecView.setHasFixedSize(true)
//        binding.questionsRecView.adapter = EditQuestionAdapter(questions)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        getQuestions()

        binding.bottomNavigationView.selectedItemId = R.id.edit

        binding.bottomNavigationView.setOnItemSelectedListener {
            val intent = when(it.itemId) {
                R.id.home -> Intent(this, GameSetupActivity::class.java)
                R.id.score -> Intent(this, ScoreListActivity::class.java)
                else -> Intent(this, EditQuestionActivity::class.java)
            }
            intent.putExtra(Navigation.INTENT_SETUP, true)
            startActivity(intent)
            true
        }

        var fragment = EditQuestionFragmentList()
        supportFragmentManager?.beginTransaction()?.let {
            it.replace(R.id.editContainer, fragment)
            it.addToBackStack(null)
            it.commit()
        }
    }
}