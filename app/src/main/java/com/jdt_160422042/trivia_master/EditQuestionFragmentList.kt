
package com.jdt_160422042.trivia_master

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.ListFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jdt_160422042.trivia_master.databinding.FragmentEditQuestionListBinding
import org.json.JSONObject

class EditQuestionFragmentList : Fragment() {
    private var questions: ArrayList<Question> = ArrayList()
    private lateinit var binding: FragmentEditQuestionListBinding

    fun getQuestions(){
        val q = Volley.newRequestQueue(activity)
        val url = "http://10.0.2.2/trivia_master/get_questions.php"
        val sr = StringRequest(
            Request.Method.POST,
            url,
            {
                Log.d("apiresult", it)
                val obj = JSONObject(it)
                if(obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")
                    val sType = object : TypeToken<List<Question>>() {}.type

                    questions = Gson().fromJson(data.toString(), sType) as ArrayList<Question>

                    Log.d("apiresult", questions.toString())
                    updateList()
                }
            },
            { Log.e("apiresult", it.message.toString()) }
        )
        q.add(sr)
    }

    fun updateList() {
        binding.questionsRecView.layoutManager = LinearLayoutManager(activity)
        binding.questionsRecView.setHasFixedSize(true)
        binding.questionsRecView.adapter = EditQuestionAdapter(questions, parentFragmentManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getQuestions()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditQuestionListBinding.inflate(inflater,container, false )
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        getQuestions()
    }
}