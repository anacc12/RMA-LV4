package com.example.birdscounter2

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.birdscounter2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel= ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        setupUI()
        viewModel.countLiveData.observe(this, androidx.lifecycle.Observer {
            binding.invalidateAll()
        })
        viewModel.colourLiveData.observe(this, androidx.lifecycle.Observer {
            binding.invalidateAll()
        })
        changeBackground(getPreferences(Context.MODE_PRIVATE))
    }

    private fun setupUI() {
        binding.apply{
            count = viewModel
            binding.btnRed.setOnClickListener { apply(R.color.red) }
            binding.btnGray.setOnClickListener { apply(R.color.gray) }
            binding.btnBlue.setOnClickListener { apply(R.color.blue) }
            binding.btnYellow.setOnClickListener { apply(R.color.yellow) }
            binding.btnReset.setOnClickListener { reset() }
        }
    }

    override fun onResume() {
        super.onResume()
        val pref = getPreferences(Context.MODE_PRIVATE)
        viewModel.setCount(pref.getInt("COUNT", 0))
        changeBackground(pref)
    }

    override fun onPause() {
        super.onPause()
        val pref = getPreferences(Context.MODE_PRIVATE)
        val editor = pref.edit()
        viewModel.getLiveDataCount()?.let { editor.putInt("COUNT", it) }
        viewModel.getLiveDataColour()?.let { editor.putInt("COLOUR", it) }
        editor.apply()
    }

    private fun apply(colour: Int){
        viewModel.setCount(viewModel.getLiveDataCount()?.plus(1))
        viewModel.setColour(colour)
    }

    private fun reset(){
        viewModel.setCount(0)
        viewModel.setColour(R.color.white)
    }

    private fun changeBackground(pref: SharedPreferences ){
        when (pref.getInt("COLOUR", 0)) {
            R.color.red -> viewModel.setColour(R.color.red)
            R.color.gray -> viewModel.setColour(R.color.gray)
            R.color.blue -> viewModel.setColour(R.color.blue)
            R.color.yellow -> viewModel.setColour(R.color.yellow)
            else -> viewModel.setColour(R.color.white)
        }
    }
}



