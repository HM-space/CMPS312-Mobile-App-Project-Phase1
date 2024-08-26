package com.example.lingosnack.model

data class Score(
    val packageId: Int,
    val packageName: String,
    var unscrambleScore: Int,
    var unscrambleQuestions: Int,
    var matchScore: Int,
    var matchQuestions: Int
) {
    fun getUnscrambleScore(): Double {
        if (unscrambleQuestions == 0){
            return 0.0
        }
        return (unscrambleScore.toDouble() / unscrambleQuestions) * 100
    }

    fun getMatchScore(): Double {
        if (matchQuestions == 0) {
            return 0.0
        }
        return (matchScore.toDouble() / matchQuestions) * 100
    }
}
