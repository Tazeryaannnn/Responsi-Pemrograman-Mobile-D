package com.pemmob.ridhotajrian.network

import com.pemmob.ridhotajrian.model.DigimonDetail
import com.pemmob.ridhotajrian.model.DigimonResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DigimonApiService {

    @GET("digimon")
    suspend fun getDigimonList(
        @Query("pageSize") pageSize: Int = 50
    ): DigimonResponse

    @GET("digimon/{id}")
    suspend fun getDigimonDetail(
        @Path("id") id: String
    ): DigimonDetail
}

object DigimonApi {
    private const val BASE_URL = "https://digi-api.com/api/v1/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val retrofitService: DigimonApiService by lazy {
        retrofit.create(DigimonApiService::class.java)
    }
}