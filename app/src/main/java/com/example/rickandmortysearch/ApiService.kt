package com.example.rickandmortysearch

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    //Path es para remplazar partes del URL y query para parametros dinamicos
    @GET("/api/character/")
    suspend fun getSuperHeroes(@Query("name") superHeroName: String): Response<SuperHeroDataResponse>
    //@GET("/api/character/{name}")
    //suspend fun getSuperHeroes(@Path("name") superHeroName: String): Response<SuperHeroDataResponse>

}