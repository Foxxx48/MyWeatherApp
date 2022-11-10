package com.fox.myweatherapp.presentation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.fox.myweatherapp.data.RetrofitHelper
import com.fox.myweatherapp.data.WeatherApi
import com.fox.myweatherapp.data.models.WeatherDataModel
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

    var weatherDataModel: WeatherDataModel? = WeatherDataModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var weatherApi: WeatherApi = RetrofitHelper.getWeatherApi()


        binding.spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {

                CoroutineScope(Dispatchers.Main).launch {
                    val data = when (position) {

                        0 -> weatherApi.getWeatherInMoscow()
                        1 -> weatherApi.getWeatherInLondon()
                        2 -> weatherApi.getWeatherInParis()
                        3 -> weatherApi.getWeatherInNewYork()
                        4 -> weatherApi.getWeatherInLesnoyGorodok()
                        else -> {
                            throw RuntimeException("Wrong data")
                        }
                    }
                    weatherDataModel = data.body()

                    setViews()

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }

    private fun setViews() {
        binding.tvCity.text = weatherDataModel?.name ?: "None"
        binding.tvTemperature.text = "${weatherDataModel?.main?.temp.toString()}°"
        binding.tvDescription.text = weatherDataModel?.weather?.component1()?.description
        binding.tvMaxTemp.text = weatherDataModel?.main?.tempMax.toString()
        binding.tvMinTemp.text = weatherDataModel?.main?.tempMin.toString()
        binding.tvPressure.text = weatherDataModel?.main?.pressure.toString()
        binding.tvHumidity.text = weatherDataModel?.main?.humidity.toString()
        binding.tvFeelsLike.text = weatherDataModel?.main?.feelsLike.toString()

        Glide.with(this@MainActivity)
            .load("file:///android_asset/day_icons/ic_02d.png")
            .into(binding.imageView)
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