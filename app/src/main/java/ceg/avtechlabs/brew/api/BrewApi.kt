package ceg.avtechlabs.brew.api

import ceg.avtechlabs.brew.model.Brew
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by adhithyan-3592 on 23/07/16.
 */

interface BrewApi {
    @GET("/surpriseMe")
    fun brew(): Call<Brew>
}