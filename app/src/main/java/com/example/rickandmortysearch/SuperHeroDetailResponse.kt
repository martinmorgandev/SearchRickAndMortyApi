package com.example.rickandmortysearch

import com.google.gson.annotations.SerializedName

data class SuperHeroDetailResponse(
    @SerializedName("name") val name:String,
    @SerializedName("status") val status: String,
    @SerializedName("species") val especie: String,
    @SerializedName("gender") val genero: String,
    @SerializedName("id") val id: String,
    @SerializedName("image") val img: String,
    @SerializedName("location") val location: SuperHeroLocation,
    )

data class SuperHeroLocation(@SerializedName("name") val name:String)