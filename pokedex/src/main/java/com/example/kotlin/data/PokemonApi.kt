package com.example.kotlin.data

import com.example.kotlin.data.model.PokemonListEntryResult
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon")
    suspend fun pokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = 20
    ): Response<PokemonListResponse>


//    https://pokeapi.co/api/v2/pokemon/{id or name}/

    @GET("pokemon/{name}")
    suspend fun pokemonDetail(
        @Path("name") name: String
    ): Response<PokemonDetail>

    data class PokemonListResponse(
        @SerializedName("count") val count: String,
        @SerializedName("next") val next: String?,
        @SerializedName("previous") val previous: String?,
        @SerializedName("results") val results: List<PokemonListEntryResult>
    )

    data class PokemonDetail( @SerializedName("id") val id: Int? = null,
                              @SerializedName("height") val height: Int? = null,
                              @SerializedName("name") val name: String?,)
}