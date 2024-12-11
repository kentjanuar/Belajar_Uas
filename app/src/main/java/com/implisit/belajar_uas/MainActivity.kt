package com.implisit.belajar_uas

import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //tolong buatkan navigation fragment
        val _keF1 = findViewById<Button>(R.id.keF1)
        val _keF2 = findViewById<Button>(R.id.keF2)
        val _keF3 = findViewById<Button>(R.id.keF3)

        val fragmentManager = supportFragmentManager
        val defaultFragment = f1()

        fragmentManager.beginTransaction()
            .add(R.id.frame, defaultFragment, f1::class.java.simpleName)
            .commit()


        _keF1.setOnClickListener{
            fragmentManager.beginTransaction()
                .replace(R.id.frame, f1(), f1::class.java.simpleName)
                .commit()
        }

        _keF2.setOnClickListener{
            fragmentManager.beginTransaction()
                .replace(R.id.frame, f2(), f2::class.java.simpleName)
                .commit()
        }
        _keF3.setOnClickListener{
            fragmentManager.beginTransaction()
                .replace(R.id.frame, f3(), f3::class.java.simpleName)
                .commit()
        }


    }
}