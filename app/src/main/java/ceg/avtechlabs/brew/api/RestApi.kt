package ceg.avtechlabs.brew.api

import ceg.avtechlabs.brew.model.Brew
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by adhithyan-3592 on 23/07/16.
 */

class RestApi() {
    private val brewApi: BrewApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://evening-scrubland-81723.herokuapp.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        brewApi = retrofit.create(BrewApi::class.java)
    }

    fun brew(): Call<Brew> {
        return brewApi.brew();
    }
}