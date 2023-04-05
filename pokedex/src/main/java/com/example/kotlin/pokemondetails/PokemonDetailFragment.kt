package com.example.kotlin.pokemondetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.kotlin.R
import com.example.kotlin.data.PokemonApi
import com.example.kotlin.databinding.FragmentPokemonDetailBinding
import com.example.kotlin.view.list.PokemonListViewModel

class PokemonDetailFragment : Fragment() {

    lateinit var binding: FragmentPokemonDetailBinding
    private val viewModel: PokemonListViewModel by viewModels()

    companion object {
        const val ID = "id"
        const val HEIGHT = "height"
        const val NAME = "NAME"
        const val POKEMON_NAME = "NAME"

        @JvmStatic
        fun newInstance(bundle: Bundle? = null) =
            PokemonDetailFragment().apply {
                arguments = bundle
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokemonDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // retrieve details and display here

        arguments?.let {
            it.getString(POKEMON_NAME)?.let { name ->
                viewModel.getPokemonDetail(name)
            }

            viewModel.pokemonDetailLiveData.observe(viewLifecycleOwner, pokemonDetailObserver)
        }
    }

    private val pokemonDetailObserver = Observer<PokemonApi.PokemonDetail> {
        binding.id.text = it.id.toString()
        binding.height.text = it.height.toString()
        binding.name.text = it.name
    }
}