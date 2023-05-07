package com.example.rickandmortysearch

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortysearch.databinding.ItemSuperheroBinding
import com.squareup.picasso.Picasso

class SuperHeroViewHolder(view:View): RecyclerView.ViewHolder(view) {

    private val binding = ItemSuperheroBinding.bind(view)

    fun bind(superHeroItemResponse: SuperHeroItemResponse){
        binding.tvSuperHeroName.text = superHeroItemResponse.name
        binding.tvEarth.text = superHeroItemResponse.origin.name
        Picasso.get().load(superHeroItemResponse.img).into(binding.ivSuperHero);
    }
}