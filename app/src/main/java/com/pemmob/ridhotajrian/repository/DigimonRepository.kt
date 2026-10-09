package com.pemmob.ridhotajrian.repository


import com.pemmob.ridhotajrian.model.DigimonDetail
import com.pemmob.ridhotajrian.model.DigimonResponse
import com.pemmob.ridhotajrian.network.DigimonApi

class DigimonRepository {

    // Fungsi untuk memanggil daftar Digimon
    suspend fun getDigimons(): DigimonResponse {
        return DigimonApi.retrofitService.getDigimonList()
    }

    // Fungsi untuk memanggil detail Digimon
    suspend fun getDigimonDetail(nameOrId: String): DigimonDetail {
        return DigimonApi.retrofitService.getDigimonDetail(nameOrId)
    }
}