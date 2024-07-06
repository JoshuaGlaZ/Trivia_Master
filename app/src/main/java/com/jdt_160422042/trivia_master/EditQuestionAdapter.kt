package com.jdt_160422042.trivia_master

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.jdt_160422042.trivia_master.databinding.CardQuestionBinding


class EditQuestionAdapter(val questions: ArrayList<Question>,
                          val fragmentManager: FragmentManager
): RecyclerView.Adapter<EditQuestionAdapter.QuestionViewHolder>() {
    class QuestionViewHolder(val binding:CardQuestionBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val binding = CardQuestionBinding
            .inflate(LayoutInflater.from(parent.context), parent,false )
        return QuestionViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return questions.size
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.binding.txtIsi.text = questions[position].question

        holder.binding.btnEdit.setOnClickListener {
            val questionEdit = Question(questions[position].id,
                questions[position].question, questions[position].answer_a,
                questions[position].answer_b, questions[position].answer_c,
                questions[position].answer_d, questions[position].correct_answer,
                null, null)

//            val intent = Intent(holder.itemView.context, UpdateQuestionActivity::class.java)
//            intent.putExtra(EditQuestionActivity.REQUEST_UPDATE_QUESTION, Gson().toJson(questionEdit))
//            holder.itemView.context.startActivity(intent)
            val fragment = EditQuestionFragment.newInstance(questionEdit)
            fragmentManager.beginTransaction().apply {
                replace(R.id.editContainer, fragment)
                addToBackStack(null)
                commit()
            }

        }
    }
}