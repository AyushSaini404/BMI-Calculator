package com.example.bmicalculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var etAge : EditText
    private lateinit var etHeight: EditText
    private lateinit var etWeight: EditText
    private lateinit var btnCalculate: Button
    private lateinit var tvResult: TextView
    private lateinit var tvCategory: TextView
    private lateinit var rgGender: RadioGroup
    private lateinit var rgHeightUnit: RadioGroup
    private lateinit var rgWeightUnit: RadioGroup

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etAge = findViewById(R.id.etAge)
        etHeight = findViewById(R.id.etHeight)
        etWeight = findViewById(R.id.etWeight)
        btnCalculate = findViewById(R.id.btnCalculate)
        tvResult = findViewById(R.id.tvResult)
        tvCategory = findViewById(R.id.tvCategory)
        rgGender = findViewById(R.id.rgGender)
        rgHeightUnit = findViewById(R.id.rgHeightUnit)
        rgWeightUnit = findViewById(R.id.rgWeightUnit)

        rgHeightUnit.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbCm -> {
                    etHeight.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
                }
                R.id.rbFeetInch -> {
                    etHeight.inputType = InputType.TYPE_CLASS_TEXT
                }
            }

            etHeight.setSelection(etHeight.text.length)
            etHeight.requestFocus()
        }

        btnCalculate.setOnClickListener {
            calculateBMI()
        }
    }


    @SuppressLint("SetTextI18n")
    private fun calculateBMI() {
        val heightText = etHeight.text.toString()
        val weightText = etWeight.text.toString()

        if (heightText.isEmpty() || weightText.isEmpty()) {
            Toast.makeText(this, "Please enter height and weight", Toast.LENGTH_SHORT).show()
            return
        }

        var height = heightText.toFloat()
        var weight = weightText.toFloat()

        val selectedHeightUnit = findViewById<RadioButton>(rgHeightUnit.checkedRadioButtonId).text
        if (selectedHeightUnit == "cm") {
            height /= 100
        } else if (selectedHeightUnit == "ft/in") {
            height *= 0.3048f
        }

        val selectedWeightUnit = findViewById<RadioButton>(rgWeightUnit.checkedRadioButtonId).text
        if (selectedWeightUnit == "lbs") {
            weight *= 0.453592f
        }

        val selectedGender = findViewById<RadioButton>(rgGender.checkedRadioButtonId).text.toString()

        val bmi = weight / (height * height)
        tvResult.text = "BMI: %.1f".format(bmi)
        tvCategory.text = "Category: ${getBMICategory(bmi)}"
    }

    private fun getBMICategory(bmi: Float): String {
        return when {
            bmi <= 15.9 -> "Very Highly Underweight"
            bmi <= 16.9 -> "Highly Underweight"
            bmi <= 18.4 -> "Underweight"
            bmi <= 24.9 -> "Normal"
            bmi <= 29.9 -> "Overweight"
            bmi <= 34.9 -> "Obese Stage I"
            bmi <= 39.9 -> "Obese Stage II"
            else -> "Obese Stage III"
                }
    }
}
