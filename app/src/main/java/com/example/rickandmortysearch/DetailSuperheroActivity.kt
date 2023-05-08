package com.example.rickandmortysearch

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import com.example.rickandmortysearch.databinding.ActivityDetailSuperheroBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailSuperheroActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private lateinit var binding: ActivityDetailSuperheroBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSuperheroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //orEmpty sirve por si te devuelve vacio quiero que sea texto para que no haya error
        val id = intent.getStringExtra(EXTRA_ID).orEmpty()
        getSuperHeroInformation(id)
    }

    private fun getSuperHeroInformation(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val superheroDetail = getRetrofit().create(ApiService::class.java).getSuperheroDetail(id)
            if(superheroDetail.body() != null ){
                runOnUiThread { createUI(superheroDetail.body()!!) }
                superheroDetail.body()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun createUI(superHero: SuperHeroDetailResponse) {
        Picasso.get().load(superHero.img).into(binding.ivSuperHero2)
        binding.tvSuperHeroName2.text = superHero.name
        binding.tvSuperHeroStatus.text = "Status: ${superHero.status}"
        binding.tvSuperHeroSpecie.text = "Species: ${superHero.especie}"
        binding.tvSuperHeroGener.text = "Gender: ${superHero.genero}"
        binding.tvSuperHeroId.text = "Id: ${superHero.id}"
        binding.tvSuperHeroLocation.text = "Location: ${superHero.location.name}"
    }

    private fun getRetrofit(): Retrofit {
        val retrofit = Retrofit
            .Builder()
            .baseUrl("https://rickandmortyapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit
    }
}