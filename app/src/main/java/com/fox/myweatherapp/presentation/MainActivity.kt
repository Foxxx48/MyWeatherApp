package com.fox.myweatherapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.fox.myweatherapp.data.RetrofitHelper
import com.fox.myweatherapp.data.WeatherApi
import com.fox.myweatherapp.databinding.ActivityMainBinding
import com.fox.myweatherapp.domain.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {



    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("ActivityMainBinding = null")

    private var viewModelFactory: ViewModelFactory =
        ViewModelFactory(object : Repository {
        })
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var weatherApi: WeatherApi = RetrofitHelper.getWeatherApi()

       CoroutineScope(Dispatchers.Main).launch {
           var list= weatherApi.getWeatherInMoscow()
           binding.tvCity.text = list.body()?.name?: "None"
           binding.tvTemperature.text = list.body()?.main?.temp.toString()
           binding.tvDescription.text = list.body()?.weather?.component1()?.description
           myLog(list)

       }


//        viewModel.extractStringFromHtml()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun myLog(message: Any?) {
        Log.d(TAG, "$message")
    }
    companion object {
        private const val TAG = "myAPP"
    }
}