package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.tictactoe.databinding.FragmentFourinrowBinding

class FourInRowFragment : Fragment(R.layout.fragment_fourinrow) {
    private lateinit var binding: FragmentFourinrowBinding
    private val viewModel: FourInRowViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentFourinrowBinding.bind(view)
        binding.apply {
            fourInRow = viewModel
            lifecycleOwner = this@FourInRowFragment
            val cells = arrayOf(
                binding.cell25,
                binding.cell24,
                binding.cell23,
                binding.cell22,
                binding.cell21,
                binding.cell20,
                binding.cell19,
                binding.cell18,
                binding.cell17,
                binding.cell16,
                binding.cell15,
                binding.cell14,
                binding.cell13,
                binding.cell12,
                binding.cell11,
                binding.cell10,
                binding.cell9,
                binding.cell8,
                binding.cell7,
                binding.cell6,
                binding.cell5,
                binding.cell4,
                binding.cell3,
                binding.cell2,
                binding.cell1
            )
            for (cellIndex in cells.indices) {
                val cell = cells[cellIndex]
                cell.setOnClickListener {
                    viewModel.findFilledRow(cellIndex)
                    viewModel.setCheap(cellIndex)
                    viewModel.setIsEnabled(cellIndex, false)
                    setIsEnabled(cells[viewModel.filledRow * 5 + cellIndex % 5], false)
                    setSrc(cells[viewModel.filledRow * 5 + cellIndex % 5], viewModel.chooseCheap())
                    viewModel.setIsEnabledAll(!viewModel.CheckWin().checkWin(cellIndex))
                    setIsEnabledAll(cells, !viewModel.CheckWin().checkWin(cellIndex))
                    viewModel.setResult(cellIndex)
                    viewModel.changeGameTurn()
                }
            }

            binding.reset.setOnClickListener {
                viewModel.reset()
                setIsEnabledAll(cells, true)
            }
        }
    }
    private fun setSrc(view: ImageView, cheap: Int){
        view.setImageResource(cheap)
    }
    private fun setSrcAll(cells: Array<ImageView>, cheap: Int){
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