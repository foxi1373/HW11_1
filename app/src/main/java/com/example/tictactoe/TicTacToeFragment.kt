package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.tictactoe.databinding.FragmentTictactoeBinding

class TicTacToeFragment : Fragment(R.layout.fragment_tictactoe) {
    private lateinit var binding: FragmentTictactoeBinding
    private val viewModel: GamePlayTicTacToe by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTictactoeBinding.bind(view)
        binding.apply {
            ticTacToe = viewModel
            lifecycleOwner = this@TicTacToeFragment
            val cells = arrayOf(
                binding.cell1,
                binding.cell2,
                binding.cell3,
                binding.cell4,
                binding.cell5,
                binding.cell6,
                binding.cell7,
                binding.cell8,
                binding.cell9
            )
            for (cellIndex in cells.indices) {
                val cell = cells[cellIndex]
                cell.setOnClickListener {
                    viewModel.setCheap(cellIndex)
                    viewModel.setIsEnabled(cellIndex, false)
                    viewModel.setIsEnabledAll(!viewModel.checkDraw())
                    viewModel.setResult(cellIndex)
                    viewModel.changeGameTurn()
                }
            }

            reset.setOnClickListener {
                viewModel.reset()
            }
        }

    }
}