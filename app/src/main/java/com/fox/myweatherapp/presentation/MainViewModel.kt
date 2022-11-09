package com.fox.myweatherapp.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fox.myweatherapp.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL
import java.util.regex.Pattern

class MainViewModel(private val repository: Repository): ViewModel() {

    private val dataOfWeather = mutableListOf<String?>()
    private var datWeth = listOf<String>()

    private val _name = MutableLiveData<List<String?>>()
    val name: LiveData<List<String?>>
        get() = _name


    fun extractStringFromHtml() {
        viewModelScope.launch(Dispatchers.IO) {
            val connection = URL(LINK).openConnection() as HttpURLConnection
            val data = connection.inputStream.bufferedReader().readText()
//            myLog(data)

            val start =
                "<script type=\"text/javascript\">"
            val finish =
                "<div class=\"container\">"

            val pattern = Pattern.compile("$start(.*?)$finish")
            val matcher = pattern.matcher(data.replace("\n", ""))

            var splitContent: String? = "X"
            while (matcher.find()) {
                splitContent = matcher.group(1)
            }
        myLog(splitContent)

            val weatherPattern =
                Pattern.compile("var jsonurl = " + "(.*?)" + ";")
            val weatherMatcher = weatherPattern.matcher(splitContent)

            while (weatherMatcher.find()) {
                dataOfWeather.add(weatherMatcher.group(1))
            }

            datWeth = dataOfWeather.map { it?.replaceAfterLast(" ", "") ?: "None" }
            myLog(datWeth)

        }
    }

    private fun myLog(message: Any?) {
        Log.d(TAG, "$message")
    }

    companion object {
        private const val TAG = "myApp"
        private const val LINK = "https://openweathermap.org/find?q=Moscow"
    }
}