package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.tictactoe.databinding.FragmentTictactoeBinding

class TicTacToeFragment : Fragment(R.layout.fragment_tictactoe) {
    private lateinit var binding: FragmentTictactoeBinding
    private val viewModel: TicTacToeViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTictactoeBinding.bind(view)
        binding.apply {
            ticTacToe = viewModel
            lifecycleOwner = this@TicTacToeFragment
            val cells1 = arrayOf(
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
            for (cellIndex in cells1.indices) {
                val cell = cells1[cellIndex]
                cell.setOnClickListener {
                    viewModel.setCheap(cellIndex)
                    viewModel.setIsEnabled(cellIndex, false)
                    setIsEnabled(cells1[cellIndex], false)
                    setSrc(cells1[cellIndex], viewModel.chooseCheap())
                    viewModel.setIsEnabledAll(!viewModel.CheckWin().checkWin(cellIndex))
                    setIsEnabledAll(cells1, !viewModel.CheckWin().checkWin(cellIndex))
                    viewModel.setResult(cellIndex)
                    viewModel.changeGameTurn()
                }
            }

            reset.setOnClickListener {
                viewModel.reset()
            }
        }

    }
    private fun setSrc(view: ImageView, cheap: Int){
        view.setImageResource(cheap)
    }
    private fun setSrcAll(cells: Array<ImageView>){
        for (cell in cells)
            setSrc(cell, R.drawable.four_none)
    }
    private fun setIsEnabled(view: ImageView, isEnabled: Boolean){
        view.isEnabled = isEnabled
    }
    private fun setIsEnabledAll(cells: Array<ImageView>, isEnabled: Boolean){
        for (cell in cells)
            setIsEnabled(cell, isEnabled)
    }
}