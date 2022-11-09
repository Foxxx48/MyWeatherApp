package com.fox.myweatherapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fox.myweatherapp.domain.Repository


class ViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == MainViewModel::class.java) {
            return MainViewModel(repository) as T
        }
//        if (modelClass == MainViewModel2::class.java) {
//            return MainViewModel2(repository) as T
//        }
        throw RuntimeException("Unknown ViewModel class $modelClass")
    }
}