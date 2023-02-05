package com.example.bmic

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.bmic.databinding.ActivityMainBinding
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding
    var unitList: List<String> = listOf(UiUtils.Metric,UiUtils.English)
    var isMatric = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var adapter: ArrayAdapter<String> = ArrayAdapter<String>(applicationContext,android.R.layout.simple_list_item_1,unitList)
        binding.unitSpnner.adapter = adapter

        binding.unitSpnner.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {

                //Log.i("TAG","onItemSelected: ${unitList[position]}")

                when(unitList[position]){
                    UiUtils.Metric -> {
                        binding.weightUnit.text="kg"
                        binding.heightUnit.text="ft"

                        isMatric = true
                    }
                    UiUtils.English -> {
                        binding.weightUnit.text="lbs"
                        binding.heightUnit.text="in"

                        isMatric = false
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        })







        binding.calculateBtn.setOnClickListener{

            var heightStr = binding.inputHeightTitle.text.toString().trim()
            var weightStr = binding.inputWeightTitle.text.toString().trim()


            if (heightStr.isEmpty()){
                binding.inputHeightTitle.error = "Height Needed...!"
            }
            else if (weightStr.isEmpty()){
                binding.inputWeightTitle.error = "Weight Needed...!"
            }
            else if (heightStr.toFloatOrNull() == null){
                binding.inputHeightTitle.error = "Numbers Only...!"
            }
            else if (weightStr.toFloatOrNull() == null){
                binding.inputHeightTitle.error = "Numbers Only...!"
            }
            else{
                val height: Float = heightStr.toFloat()
                val weight: Float = weightStr.toFloat()

                calculateBmi(height, weight)
            }

        }








    }

    private fun calculateBmi(height: Float, weight: Float) {

        if (isMatric){

            var heightCnv = (height * 0.3048f)
            var bmiScore = weight / (heightCnv * heightCnv)
            binding.resultView.text = String.format("Score : %.2f",bmiScore)

        }else{

            var bmiScore = (weight / (height * height)) * 703
            binding.resultView.text = String.format("Score : %.2f",bmiScore)

        }


    }
}