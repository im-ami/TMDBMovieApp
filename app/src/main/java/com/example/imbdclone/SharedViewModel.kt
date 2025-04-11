package com.example.imbdclone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title

    fun setTitle(newTitle: String) {
        _title.value = newTitle
    }
}
