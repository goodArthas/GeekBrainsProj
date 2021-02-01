package ru.geekbrainsproj

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var okBtn: Button
    private lateinit var dataTxtView: TextView
    lateinit var objects: Any

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val genders = TwoGenders()
        objects = genders.copy()

        findViews()
        initClickListener()
        setFieldData()


    }

    private fun findViews() {
        okBtn = findViewById(R.id.btnOk)
        dataTxtView = findViewById(R.id.textViewData)
    }

    private fun initClickListener() {
        okBtn.setOnClickListener {
            Toast.makeText(this, getString(R.string.done), Toast.LENGTH_SHORT).show()
            cycles()
        }
    }

    private fun setFieldData() {
        dataTxtView.text = objects.toString()
    }

    private fun cycles() {
        val arrays = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 10)

        for (i in arrays) print(i)

        for (i in 1..10) {
            Log.e("QWE", "i in 1..10  text is $i")
        }

        for (i in 10 downTo 1 step 2) {
            print("i in 10 downTo 1 step 2")
            Log.e("QWE", "i in 10 downTo 1 step 2 text is $i")
        }


    }

}
