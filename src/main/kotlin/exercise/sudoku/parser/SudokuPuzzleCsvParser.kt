package exercise.sudoku.parser

import exercise.sudoku.action.SudokuGrid
import org.springframework.util.StringUtils
import java.io.File

class SudokuPuzzleCsvParser : SudokuPuzzleParser<File> {

    override fun parse(argument: File): SudokuGrid {
        return SudokuGrid(rows = argument.readLines()
            .map(String::trim)
            .filter(StringUtils::hasText)
            .map { parseLine(it) })
    }

    private fun parseLine(rowLine: String): List<Int> {
        val splitValues = rowLine.split(',')
        if (splitValues.size != SudokuGrid.MAXIMUM_SIZE) {
            throw IllegalArgumentException(
                "Malformed Sudoku format; each line must have exactly 9 comma-separated numbers. Line: $rowLine"
            )
        }

        val parsedLines: List<Int>
        try {
            parsedLines = splitValues.map(String::toInt)
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException(
                "Malformed Sudoku puzzle; non-numeric value could not be parsed: $rowLine", e
            )
        }

        return parsedLines
    }
}