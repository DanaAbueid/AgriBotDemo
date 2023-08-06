package com.icare.icare.lookup_data

import android.content.Context
import org.json.JSONObject


class Lookups(context: Context) {
    private val countries by lazy {
        LocalJSONParser.inputStreamToString(context, "CountriesAndCities.json")
    }

    fun getCountries(): List<String> {
        val keys: Iterator<String> = JSONObject(countries).keys()
        return keys.asSequence().toList()
    }

    fun getCities(countryName: String): List<String> {
        val citiesJson = JSONObject(countries).getJSONArray(countryName)
        val listOfCities = mutableListOf<String>()
        for (i in 0 until citiesJson.length()) {
            listOfCities.add(citiesJson.getString(i))
        }
        return listOfCities
    }

    fun getGender(): List<String> {
        return listOf<String>(
            "Male",
            "Female"
        )
    }
}