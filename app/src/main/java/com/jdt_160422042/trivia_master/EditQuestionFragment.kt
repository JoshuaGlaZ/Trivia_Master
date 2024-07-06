package com.jdt_160422042.trivia_master

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.jdt_160422042.trivia_master.databinding.FragmentEditQuestionBinding

private const val ARG_EDIT_QUESTION = "editquestion"

class EditQuestionFragment : Fragment() {
    private var question: Question? = null
    private lateinit var binding: FragmentEditQuestionBinding

    fun updateQuestion() {
        val url = "http://10.0.2.2/trivia_master/edit_question.php"
        val q = Volley.newRequestQueue(activity)

        val sr = object: StringRequest(
            Request.Method.POST, url,
            {
                Log.d("volleycek", it)
                question?.question = binding.txtEditQuestion.text.toString()
                question?.answer_a = binding.txtOptionA.text.toString()
                question?.answer_b = binding.txtOptionB.text.toString()
                question?.answer_c = binding.txtOptionC.text.toString()
                question?.answer_d = binding.txtOptionD.text.toString()
                question?.correct_answer = when(binding.groupQuestions.checkedRadioButtonId) {
                    R.id.radioOptionA -> binding.txtOptionA.text.toString()
                    R.id.radioOptionB -> binding.txtOptionB.text.toString()
                    R.id.radioOptionC -> binding.txtOptionC.text.toString()
                    R.id.radioOptionD -> binding.txtOptionD.text.toString()
                    else -> ""
                }
                Log.d("question", question?.toString()!!)
                Snackbar.make(requireActivity(), binding.root, "Question Updated", Snackbar.LENGTH_SHORT).show()
            },
            { Log.e("volleycek", it.message.toString()) }
        ) {
            // body class
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["question"] = binding.txtEditQuestion.text.toString()
                params["answer_a"] = binding.txtOptionA.text.toString()
                params["answer_b"] = binding.txtOptionB.text.toString()
                params["answer_c"] = binding.txtOptionC.text.toString()
                params["answer_d"] = binding.txtOptionD.text.toString()
                params["correct_answer"] = when(binding.groupQuestions.checkedRadioButtonId) {
                    R.id.radioOptionA -> binding.txtOptionA.text.toString()
                    R.id.radioOptionB -> binding.txtOptionB.text.toString()
                    R.id.radioOptionC -> binding.txtOptionC.text.toString()
                    R.id.radioOptionD -> binding.txtOptionD.text.toString()
                    else -> ""
                }
                params["id"] = question?.id.toString()
                return params
            }
        }
        q.add(sr)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            question = it.getParcelable(ARG_EDIT_QUESTION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            txtEditQuestion.setText(question?.question)
            txtOptionA.setText(question?.answer_a)
            txtOptionB.setText(question?.answer_b)
            txtOptionC.setText(question?.answer_c)
            txtOptionD.setText(question?.answer_d)
            groupQuestions.check(
                when (question?.correct_answer) {
                    question?.answer_a -> R.id.radioOptionA
                    question?.answer_b -> R.id.radioOptionB
                    question?.answer_c -> R.id.radioOptionC
                    question?.answer_d -> R.id.radioOptionD
                    else -> R.id.radioOptionA
                }
            )
            btnSubmit.setOnClickListener {
                updateQuestion()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(question: Question) =
            EditQuestionFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_EDIT_QUESTION, question)
                }
            }
    }
}