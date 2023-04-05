package com.example.kotlin.data

import android.net.Uri
import android.util.Log
import com.example.kotlin.data.model.PokemonListResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object PokemonListRepository {

    private val api: PokemonApi? = ApiClient.api()

    suspend fun getPokemonListResults(page: Int): PokemonListResult =
        withContext(Dispatchers.IO) {
            val response = api?.pokemonList(page * PER_PAGE)
            response?.body()?.let {
                PokemonListResult(
                    nextPage = getPageFromUrl(it.next),
                    prevPage = getPageFromUrl(it.previous),
                    results = it.results
                )
            } ?: PokemonListResult(null, null, emptyList())
        }

    private fun getPageFromUrl(urlString: String?): Int? {
        return urlString?.let {
            Uri.parse(it).getQueryParameter(PARAM_OFFSET)?.toInt()?.div(PER_PAGE)
        }
    }

    suspend fun getPokemonDetail(pokemonName: String): PokemonApi.PokemonDetail {
        return try {
            val response = api?.pokemonDetail(pokemonName)
            response?.body()?.let {
                it
            } ?: PokemonApi.PokemonDetail(null, null, null)
        } catch (e: Exception) {
            // log error
            Log.e("**logged", e.message ?: e.toString())
            PokemonApi.PokemonDetail(null, null, null)
        }
    }

    private const val PARAM_OFFSET = "offset"
    const val PER_PAGE = 20
}