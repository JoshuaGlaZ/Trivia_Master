package com.jdt_160422042.trivia_master

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Question(val id: Int, var question:String?, var answer_a: String?,
                    var answer_b: String?, var answer_c: String?, var answer_d: String?,
                    var correct_answer: String?, val difficulty: String?, val type: String?) : Parcelable {
    override fun toString(): String = question!!
}
