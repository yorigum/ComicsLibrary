package com.yoriworks.comiclibrary.model.api

import com.yoriworks.comiclibrary.model.CharactersApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {
    @GET("characters")
    fun getCharacters(@Query("nameStartsWith") name:String) : Call<CharactersApiResponse>
}