package com.cute_white_bear.guessinggame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.cute_white_bear.guessinggame.databinding.FragmentGameBinding
import com.cute_white_bear.guessinggame.model.GameViewModel

class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this)[GameViewModel::class.java]

        updateScreen()

        binding.guessButton.setOnClickListener {
            viewModel.makeGuess(binding.letterEditText.text.toString().uppercase())
            binding.letterEditText.text.clear()
            updateScreen()
            if (viewModel.isWon() || viewModel.isLost()) {
                val action = GameFragmentDirections.actionGameFragmentToResultFragment(viewModel.wonLostMessage())
                view.findNavController().navigate(action)
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateScreen() {
        binding.wordTextView.text = viewModel.secretWordDisplay
        binding.incorrectGuessesTextView.text = "Incorrect guesses: ${viewModel.incorrectGuesses}"
        binding.livesTextView.text = "Lives left: ${viewModel.lives}"
    }
}