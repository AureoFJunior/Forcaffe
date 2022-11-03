package com.example.forcaffe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.forcaffe.databinding.ActivityFeedbackBinding
import com.example.forcaffe.databinding.ActivityMainBinding
import com.example.forcaffe.db.DatabaseHandler
import com.example.forcaffe.model.Feedback
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FeedbackActivity : AppCompatActivity() {

    // Database
    val databaseHandler = DatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        var btnAdd = findViewById(R.id.button) as Button
        var tvAdpDescricao = findViewById(R.id.editTextTextMultiLine) as TextView
        var btnBack = findViewById(R.id.floatingActionButton3)  as FloatingActionButton

        btnAdd.setOnClickListener {
            if (tvAdpDescricao.text.toString() == "") {
                Toast.makeText(this, "O feedback está vazio.", Toast.LENGTH_SHORT).show()

            } else {
                val feedback = Feedback(0, tvAdpDescricao.text.toString())
                databaseHandler.addfeedback(feedback)
                Toast.makeText(this, "Feedback enviado com sucesso!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        btnBack.setOnClickListener { view ->
            val intent = Intent(this, MainActivity::class.java)
            startActivityForResult(intent, 1)
        }
    }
}