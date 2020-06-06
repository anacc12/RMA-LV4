package com.example.birdscounter2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {
     var countLiveData= MutableLiveData<Int?>()
    var colourLiveData= MutableLiveData<Int>()

     fun getLiveDataCount(): Int? {
         return countLiveData.value
    }

    fun setCount(cnt: Int?){
        countLiveData.postValue(cnt)
    }

    fun getLiveDataColour(): Int? {
        return colourLiveData.value
    }

    fun setColour(clr: Int){
        colourLiveData.value = clr
    }
}