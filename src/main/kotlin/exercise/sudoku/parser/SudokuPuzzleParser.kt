package exercise.sudoku.parser

import exercise.sudoku.action.SudokuGrid

@FunctionalInterface
interface SudokuPuzzleParser<T> {
    fun parse(argument: T): SudokuGrid
}