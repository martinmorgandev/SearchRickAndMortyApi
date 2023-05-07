package com.example.rickandmortysearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmortysearch.databinding.ActivitySearchBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var retrofit:Retrofit
    private lateinit var adapter: SuperHeroAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //guardar o que retorne la funcion para no etsarlo haciendo cada vez
        retrofit = getRetrofit()
        //inicializar la UI (el buscador)
        intiUI()
    }

    private fun intiUI() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener
            {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    //llamada a a funcion con el parametr query, si es null, devulvo texto vacio
                    searchByName(query.orEmpty())
                    //siempre se retorna false, cosas de android xd
                    return false
                }
                //esta funcion se llama cuando vallamos escribiendo, si lo borras da error
                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            }
        )

        adapter = SuperHeroAdapter()
        binding.rvSuperhero.setHasFixedSize(true)
        binding.rvSuperhero.layoutManager = LinearLayoutManager(this)
        binding.rvSuperhero.adapter = adapter
    }

    private fun searchByName(query: String) {
        //cuando le damos a buscar es cuando ejecuto el progress bar
        binding.progressBar.isVisible = true
        //todas las llamadas a apis van dentro de corroutinas, io es el hilo secundario
        CoroutineScope(Dispatchers.IO).launch {
            //a partir de aqui to do va dentro de la coroutina
            //como la variable es de tipo response, nos da ciertas propiedades
            val myResponse: Response<SuperHeroDataResponse> = retrofit.create(ApiService::class.java).getSuperHeroes(query)
            //una de ellas es comprobar si es exitosa
            if (myResponse.isSuccessful) {
                //si lo es, imprimimos el logcat y accedemos al body
                Log.i("martin", "funciona :D")
                val response: SuperHeroDataResponse? = myResponse.body()

                if(response != null){
                    Log.i("martin", response.toString())
                    //si fue exitoso escondo la progress bar, pero en el HILO PRINCIPAL
                    runOnUiThread {
                        adapter.updateList(response.superHeroes)
                        binding.progressBar.isVisible = false
                    }
                }
            } else
                Log.i("martin", "non funciona :(")
        }
    }

    //una funcion que retorne una instancia de retrofit
    private fun getRetrofit(): Retrofit{
        val retrofit = Retrofit
            .Builder()
            .baseUrl("https://rickandmortyapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit
    }
}