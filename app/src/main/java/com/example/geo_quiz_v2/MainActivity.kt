package com.example.geo_quiz_v2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.geo_quiz_v2.databinding.ActivityMainBinding



private const val TAG = "MainActivity"
private var count=0
private var countCheat=0
private const val REQUEST_CODE_CHEAT = 0
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val quizViewModel: QuizViewModel by viewModels()
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var questionTextView: TextView
    private lateinit var previous_button: Button
    private lateinit var Cheat_button: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
         binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        trueButton = findViewById(R.id.true_button)
        Cheat_button = findViewById(R.id.Cheat_button)
        falseButton = findViewById(R.id.false_button)
        nextButton =
            findViewById(R.id.next_button)
        previous_button=findViewById(R.id.previous_button)
        questionTextView =
            findViewById(R.id.question_text_view)
        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
            CountPrint()
            HideButton(trueButton)
            HideButton(falseButton)
        }
        falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
            CountPrint()
            HideButton(trueButton)
            HideButton(falseButton)
        }

        updateQuestion()

        nextButton.setOnClickListener {
           quizViewModel.moveToNext(nextButton,previous_button)
            updateQuestion()
            if (quizViewModel.currentIndex!=quizViewModel.questionBank.size-1){

                updateQuestion()
            }
            else{
                nextButton.isEnabled=false
                updateQuestion()


            }
//1111



            SeekerButton(trueButton)
            SeekerButton(falseButton)


        }

        Cheat_button.setOnClickListener {
            if (countCheat<3){
                val answerIsTrue =
                    quizViewModel.currentQuestionAnswer
                val intent =
                    CheatActivity.newIntent(this@MainActivity,
                        answerIsTrue)
                startActivityForResult(intent,
                    REQUEST_CODE_CHEAT)

            }
            else {
                CheatPrint()

            }
            countCheat+=1



        }
        updateQuestion()




        previous_button.setOnClickListener {

            if (quizViewModel.currentIndex>0){
                updateQuestion()
                quizViewModel.moveToPrevi(previous_button,nextButton)
            }
            else {
                updateQuestion()
                previous_button.isEnabled=false
            }

            SeekerButton(trueButton)
            SeekerButton(falseButton)
        }

        trueButton =findViewById(R.id.true_button)



    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode,
            resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        if (requestCode == REQUEST_CODE_CHEAT)
        {
            quizViewModel.isCheater =
                data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }
    }
    override fun onStart() {
        super.onStart()
        Log.d(TAG,"onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG,"onResume() called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG,"onPause() called")
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG,"onStop() called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroy() called")
    }


    private fun checkAnswer(userAnswer:
                            Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = when {
            quizViewModel.isCheater -> R.string.judgment_toast
            userAnswer == correctAnswer ->
                R.string.correct_toast
            else -> R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId,
            Toast.LENGTH_SHORT)
            .show()
    }

    private fun HideButton(but:Button){
        but.visibility=View.GONE

    }

    private fun CheatPrint(){
        Toast.makeText(this, "Все попытки потрачены",
            Toast.LENGTH_SHORT)
            .show()

    }

    private fun SeekerButton(but:Button){
        but.visibility=View.VISIBLE

    }
    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)

    }

    private fun CountPrint(){
        if (quizViewModel.currentIndex==quizViewModel.questionBank.size-1){
            Toast.makeText(this, "Количество верных ответов="+count,
                Toast.LENGTH_SHORT)
                .show()

        }


    }


}