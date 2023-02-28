package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.add
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.ticTacToe).setOnClickListener {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<TicTacToeFragment>(R.id.fragment_container_view)
            }
        }

        findViewById<Button>(R.id.fourInRow).setOnClickListener {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<FourInRowFragment>(R.id.fragment_container_view)
            }
        }
    }


}