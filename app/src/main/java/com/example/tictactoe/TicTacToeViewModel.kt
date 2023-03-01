package com.example.tictactoe

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TicTacToeViewModel : ViewModel() {
    var n  = 3
    private var turnNum = 0
    val board = Array(n) { Array(n) { Cell(FourInRowCheap.None.cheap , true) } }
    var announce = MutableLiveData("TicTacToe")


    fun chooseCheap(): Int =
        if (turnNum % 2 == 0) TicTacToeCheap.O.cheap else TicTacToeCheap.X.cheap


    fun setCheap(clickedIndex: Int) {
        board[clickedIndex / n][clickedIndex % n].cheap = chooseCheap()
    }

    fun setIsEnabled(clickedIndex: Int, isEnabled: Boolean) {
        board[clickedIndex / n][clickedIndex % n].isEnabled = isEnabled
    }

    fun setIsEnabledAll(isEnabled: Boolean) {
        for (i in 0 until n * n) {
            board[i / n][i % n].isEnabled = isEnabled
        }
    }

    private fun setCheapAll(cheap: Int) {
        for (i in 0 until n * n) board[i / n][i % n].cheap = cheap
    }

    fun changeGameTurn() {
        turnNum++
    }

    fun checkDraw(): Boolean {
        return turnNum == n * n - 1
    }

    inner class CheckWin {
        private val diameterDirection =
            { clickedIndex: Int, i: Int -> intArrayOf(clickedIndex / n - 2 + i, clickedIndex % n - 2 + i) }

        private val subDiameterDirection =
            { clickedIndex: Int, i: Int -> intArrayOf(clickedIndex / n - 2 + i, clickedIndex % n + 2 - i) }
        private val rowDirection =
            { clickedIndex: Int, i: Int -> intArrayOf(clickedIndex / n, clickedIndex % n - 2 + i) }

        private val columnDirection =
            { clickedIndex: Int, i: Int -> intArrayOf(clickedIndex / n - 2 + i, clickedIndex % n) }
        private val winDirs =
            arrayOf(diameterDirection, subDiameterDirection, rowDirection, columnDirection)

        private fun checkInRange(row: Int, column: Int): Boolean =
            row in 0 until n && column in 0 until n

        private fun checkCheap(row: Int, column: Int): Boolean =
            board[row][column].cheap == chooseCheap()

        private fun checkWinDir(clickedIndex: Int, winDir: (Int, Int) -> IntArray): Boolean {
            var cheapSequence = 0
            for (i in 0..2) {
                if (checkInRange(winDir(clickedIndex, i)[0], winDir(clickedIndex, i)[1])
                    && checkCheap(winDir(clickedIndex, i)[0], winDir(clickedIndex, i)[1])
                ) {
                    cheapSequence++
                } else {
                    if (cheapSequence == 3) return true
                    else cheapSequence = 0
                }
            }
            return cheapSequence == 3
        }

        fun checkWin(clickedIndex: Int): Boolean {
            for (dir in winDirs) {
                if (checkWinDir(clickedIndex, dir))
                    return true
                return false
            }

            return false
        }
    }


    fun reset() {
        setCheapAll(TicTacToeCheap.None.cheap)
        setIsEnabledAll(true)
        turnNum = 0
    }

    fun setResult(clickedIndex: Int) {
        announce.value =
            if (CheckWin().checkWin(clickedIndex) && chooseCheap() == TicTacToeCheap.O.cheap) "player 1 win"
            else if (CheckWin().checkWin(clickedIndex)) "player 2 win"
            else if (checkDraw()) "draw"
            else "Tic Tac Toe"
    }



}
