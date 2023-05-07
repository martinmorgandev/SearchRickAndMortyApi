package com.example.rickandmortysearch

import com.google.gson.annotations.SerializedName

data class SuperHeroDataResponse(
        //@SerializedName("name") val name:String,
        @SerializedName("results") val superHeroes: List<SuperHeroItemResponse>
    )



data class SuperHeroItemResponse(
    @SerializedName("id") val superHeroId: String,
    @SerializedName("name") val name: String
)

