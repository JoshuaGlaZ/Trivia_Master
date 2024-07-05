package com.jdt_160422042.trivia_master

data class Question(val id: Int, val question:String?, val answer_a: String?,
                    val answer_b: String?, val answer_c: String?, val answer_d: String?,
                    val correct_answer: String?, val difficulty: String?, val type: String?)
