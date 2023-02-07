package com.yoriworks.comiclibrary.model.api

import android.util.Log
import com.yoriworks.comiclibrary.model.CharactersApiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarvelApiRepo(private val api: MarvelApi) {
    val characters = MutableStateFlow<NetworkResult<CharactersApiResponse>>(NetworkResult.Initial())
    
    fun query(query: String) {
        characters.value = NetworkResult.Loading()
        api.getCharacters(query).enqueue(object : Callback<CharactersApiResponse> {
            override fun onResponse(
                call: Call<CharactersApiResponse>, response: Response<CharactersApiResponse>
            ) {
    
                Log.i("responseNih","$response")
                if (response.isSuccessful) {
                    response.body()?.let {
                        characters.value = NetworkResult.Success(it)
                    }
                    
                } else characters.value = NetworkResult.Error(response.message())
    
            }
            
            override fun onFailure(call: Call<CharactersApiResponse>, t: Throwable) {
                t.localizedMessage?.let {
                    characters.value = NetworkResult.Error(it)
                }
            }
            
        })
        
    }
}