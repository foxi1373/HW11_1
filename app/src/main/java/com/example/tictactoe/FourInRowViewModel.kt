package com.example.tictactoe

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FourInRowViewModel : ViewModel() {
    var n = 5
    private var turnNum = 0
    var filledRow = 0
    val board = Array(n) { Array(n) { Cell(FourInRowCheap.None.cheap, true) } }
    var announce = MutableLiveData("Four In Row")


    fun chooseCheap(): Int =
        if (turnNum % 2 == 0) FourInRowCheap.Blue.cheap else FourInRowCheap.Red.cheap

    fun findFilledRow(clickedIndex: Int) {
        for (i in 0 until n) {
            if (board[i][clickedIndex % n].cheap == FourInRowCheap.None.cheap){
                filledRow = i
                break
            }
        }
    }

    fun setCheap(clickedIndex: Int) {
        board[filledRow][clickedIndex % n].cheap = chooseCheap()
    }

    fun setIsEnabled(clickedIndex: Int, isEnabled: Boolean) {
        board[filledRow][clickedIndex % n].isEnabled = isEnabled
    }

    fun setIsEnabledAll(isEnabled: Boolean) {
        for (i in 0 until n * n) {
            board[i / n][i % n].isEnabled = isEnabled
        }
    }

    private fun setCheapAll(cheap: Int) {
        for (i in 0 until n * n) {
            board[i / n][i % n].cheap = cheap
        }
    }

    fun changeGameTurn() {
        turnNum++
    }

    private fun checkDraw(): Boolean {
        return turnNum == n * n - 1
    }

    inner class CheckWin {
        private val diameterDirection =
            { clickedIndex: Int, i: Int -> intArrayOf(filledRow - 3 + i, clickedIndex % 5 - 3 + i) }

        private val subDiameterDirection =
            { clickedIndex: Int, i: Int -> intArrayOf(filledRow - 3 + i, clickedIndex % 5 + 3 - i) }
        private val rowDirection =
            { clickedIndex: Int, i: Int -> intArrayOf(filledRow, clickedIndex % 5 - 3 + i) }

        private val columnDirection =
            { clickedIndex: Int, i: Int -> intArrayOf(filledRow - 3 + i, clickedIndex % 5) }
        private val winDirs =
            arrayOf(diameterDirection, subDiameterDirection, rowDirection, columnDirection)

        private fun checkInRange(row: Int, column: Int): Boolean =
            row in 0 until n && column in 0 until n

        private fun checkCheap(row: Int, column: Int): Boolean =
            board[row][column].cheap == chooseCheap()

        private fun checkWinDir(clickedIndex: Int, winDir: (Int, Int) -> IntArray): Boolean {
            var cheapSequence = 0
            for (i in 0..7) {
                if (checkInRange(winDir(clickedIndex, i)[0], winDir(clickedIndex, i)[1])
                    && checkCheap(winDir(clickedIndex, i)[0], winDir(clickedIndex, i)[1])
                ) {
                    cheapSequence++
                } else {
                    if (cheapSequence >= 4) return true
                    else cheapSequence = 0
                }
            }
            return cheapSequence >= 4
        }

        fun checkWin(clickedIndex: Int): Boolean {
            for (dir in winDirs) {
                if (checkWinDir(clickedIndex, dir))
                    return true
            }

            return false
        }
    }


    fun reset() {
        setCheapAll(FourInRowCheap.None.cheap)
        setIsEnabledAll(true)
        announce.value = "Four in Row"
    }

    fun setResult(clickedIndex: Int) {
        announce.value =
            if (CheckWin().checkWin(clickedIndex) && chooseCheap() == FourInRowCheap.Blue.cheap) "player 1 win"
            else if (CheckWin().checkWin(clickedIndex)) "player 2 win"
            else if (checkDraw()) "draw"
            else "Four in Row"
    }
}