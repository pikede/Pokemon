package com.example.kotlin.view.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.R
import com.example.kotlin.data.PokemonApi
import com.example.kotlin.data.model.PokemonListEntryResult
import com.example.kotlin.pokemondetails.PokemonDetailFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PokemonListFragment : Fragment(), PokemonPagingListAdapter.PokemonListener {

    private val adapter: PokemonPagingListAdapter?
        get() = view?.findViewById<RecyclerView>(R.id.pokemon_list)?.adapter as? PokemonPagingListAdapter

    private val viewModel: PokemonListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pokemon_list, container, false)
        view.findViewById<RecyclerView>(R.id.pokemon_list).apply {
            adapter = PokemonPagingListAdapter(this@PokemonListFragment)
        }
        return view
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch { viewModel.pokemonFlow.collectLatest { populateList(it) } }
    }

    private suspend fun populateList(results: PagingData<PokemonListEntryResult>) {
        adapter?.submitData(results)
    }

    companion object {
        fun newInstance(): Fragment {
            return PokemonListFragment()
        }
    }

    override fun selectedPokemon(pokemon: PokemonListEntryResult) {
        getPokemonDetails(pokemon)
    }

    private fun getPokemonDetails(pokemon: PokemonListEntryResult) {
        val bundle = Bundle().apply {
            putString(PokemonDetailFragment.POKEMON_NAME, pokemon.name)
        }

        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(android.R.id.content, PokemonDetailFragment.newInstance(bundle))
            ?.addToBackStack(null)?.commit()
    }
}