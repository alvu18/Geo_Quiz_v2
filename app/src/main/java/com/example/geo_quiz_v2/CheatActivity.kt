package com.example.geo_quiz_v2

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.geo_quiz_v2.databinding.ActivityCheatBinding

const val EXTRA_ANSWER_SHOWN =
    "com.bignerdranch.android.geoquiz.answer_shown"
private const val EXTRA_ANSWER_IS_TRUE =
    "com.bignerdranch.android.geoquiz.answer_is_true"
class CheatActivity : AppCompatActivity() {
    private var answerIsTrue = false
    private lateinit var binding: ActivityCheatBinding
    private lateinit var answerTextView: TextView
    private lateinit var showAnswerButton: Button
    private lateinit var back_button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setContentView(R.layout.activity_cheat)
        updateAPIVersion()
        answerIsTrue =
            intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE,
                false)
        answerTextView =
            findViewById(R.id.answer_text_view)
        back_button = findViewById(R.id.back_button)
        showAnswerButton =
            findViewById(R.id.show_answer_button)

       showAnswerButton.setOnClickListener {
            val answerText = when {
                answerIsTrue ->
                    R.string.true_button
                else -> R.string.false_button
            }
            answerTextView.setText(answerText)
            setAnswerShownResult(true)
        }

        back_button.setOnClickListener {

            onBackPressed()
        }


    }
    private fun updateAPIVersion(){
       // val apiVersion = Build.VERSION.SDK_INT
        //binding.appVersionTextView.text = getString(R.string.api_version, apiVersion)
        val apiVersion = Build.VERSION.SDK_INT
        val formattedText = getString(R.string.api_version, apiVersion)
        binding.appVersionTextView.text = formattedText
    }
    private fun
            setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN,
                isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }
    companion object {
        fun newIntent(packageContext: Context,
                      answerIsTrue: Boolean): Intent {
            return Intent(packageContext,
                CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE,
                    answerIsTrue)
            }
        }
    }


}