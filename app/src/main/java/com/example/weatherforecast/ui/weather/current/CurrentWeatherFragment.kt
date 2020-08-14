package com.example.weatherforecast.ui.weather.current

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.weatherforecast.HomeFragmentDirections
import com.example.weatherforecast.R
import com.example.weatherforecast.data.response.WeatherStackApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CurrentWeatherFragment : Fragment() {

    companion object {
        fun newInstance() =
            CurrentWeatherFragment()
    }

    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.current_weather_fragment, container, false)

        view.findViewById<Button>(R.id.button).setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(4)
            activity?.findNavController(R.id.nav_container)?.navigate(action)
        }

        val api = WeatherStackApiService()

        GlobalScope.launch(Dispatchers.Main) {
            val response = api.getCurrentWeather("Lagos")
            view.findViewById<TextView>(R.id.textView2).text = response.toString()
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CurrentWeatherViewModel::class.java)
        // TODO: Use the ViewModel
    }

}