package com.example.lingosnack.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.lingosnack.model.Score
import com.example.lingosnack.model.User
import com.example.lingosnack.model.UserScore
import com.example.lingosnack.repo.UserRepo

class UsersViewModel (appContext: Application) : AndroidViewModel(appContext) {

    private val _users by lazy {
        UserRepo.getUsers(appContext).toMutableList()
    }

    private val usersScores = mutableListOf<UserScore>()
    private val guestUser = User("","","Guest","","","")
    private var currUser = guestUser

    private fun updateCurrUser(user: User) {
        currUser = user
    }

    fun handleLogin(email: String, password: String): User? {
        val user = _users.find { it.email == email && it.password == password }
        user?.let {
            usersScores.remove(getCurrUserScore())
            updateCurrUser(user)
        }
        return user
    }

    private fun addNewUserScores(guestUserScore: UserScore?) {
        val scores = guestUserScore?.scores
        scores?.let {
            usersScores.add(UserScore(currUser.email,guestUserScore?.scores))
        }
    }

    fun handleSignUp(firstName: String, lastName: String,
                      email: String, password: String, photoUri: String): User? {
        if (email == "Guest") return null
        val existingUser = _users.find{it.email == email}
        existingUser?.let {
            return null
        }
        val newUser = User(firstName, lastName, email,
            password, photoUri, "Student")
        _users.add(newUser)
        val guestUserScore = getCurrUserScore()
        updateCurrUser(newUser)
        addNewUserScores(guestUserScore)
        usersScores.remove(guestUserScore)
        return newUser
    }

    fun handleSignOut() {
        updateCurrUser(guestUser)
    }

    private fun getCurrUserScore(): UserScore? {
        return usersScores.find{it.userEmail == currUser.email}
    }

    fun getScores(): MutableList<Score> {
        return getCurrUserScore()?.scores ?: mutableListOf<Score>()
    }

    private fun getPackageScore(userScore: UserScore, packageId: Int): Score? {
        return userScore.scores.find {it.packageId == packageId}
    }

    fun addUserScore(
        packageId: Int, packageName: String,
        unScrambleScore: Int = 0, unScrambleQuestions: Int = 0,
        matchScore: Int = 0, matchQuestions: Int = 0
    ) {
        var userScore = getCurrUserScore()
        if (userScore==null) {
            userScore = UserScore(currUser.email,mutableListOf<Score>())
            usersScores.add(userScore)
        }
        userScore?.let {
            val packageScore = getPackageScore(userScore,packageId)
            packageScore?.let {
                packageScore.unscrambleScore += unScrambleScore
                packageScore.unscrambleQuestions += unScrambleQuestions
                packageScore.matchScore += matchScore
                packageScore.matchQuestions += matchQuestions
                return
            }
            val score = Score(
                packageId,packageName,unScrambleScore,unScrambleQuestions,matchScore,matchQuestions
            )
            userScore.scores.add(score)
        }
    }

    fun getTotalScore(): Double {
        val userScore = getCurrUserScore()
        userScore?.let { packageScore ->
            val scores = packageScore.scores
            var totalScore = 0.0
            var numQuestions = 0
            scores.forEach {
                totalScore += it.matchScore + it.unscrambleScore
                numQuestions += it.matchQuestions + it.unscrambleQuestions
            }
            return (totalScore/numQuestions) * 100
        }
        return 0.0
    }

}