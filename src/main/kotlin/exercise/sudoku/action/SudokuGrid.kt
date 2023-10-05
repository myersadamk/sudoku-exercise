package exercise.sudoku.action

/**
 * Represents a solved 9x9 Sudoku puzzle.
 *
 * For convenience, the following methods are provided for reading the state of the puzzle:
 * - [SudokuGrid][rows]
 * - [SudokuGrid][columns]
 * - [SudokuGrid][subGrids]
 *
 * *Exercise notes:* sub-grids are represented by this same class. Given more time, I would likely
 * break that down into a discrete class. Furthermore, I may add support for unsolved puzzles, as
 * discussed in [SudokuAction]. I wound up spending more time on Spring abstractions, and this core
 * object model is "just enough" to solve the exercise.
 *
 * @implNote For brevity, only fully-populated solution states are supported. For supporting the
 * pluggable {@link SudokuAction} pattern, 0 could be used to represent
 */
data class SudokuGrid constructor(private val rows: List<List<Int>>) {

    companion object {
        const val MAXIMUM_SIZE = 9
        private const val MINIMUM_SIZE = 3
        private const val MAXIMUM_VALUE = 9
        private const val MINIMUM_VALUE = 1
    }

    init {
        if (rows.size != MINIMUM_SIZE && rows.size != MAXIMUM_SIZE) {
            throw IllegalArgumentException(
                "Valid Grid sizes for this exercise include 9 and 3 (for subgrids). Found: ${rows.size}"
            )
        }

        val invalidNumbers = rows.flatten().filter { it < MINIMUM_VALUE || it > MAXIMUM_VALUE }
        if (invalidNumbers.isNotEmpty()) {
            throw IllegalArgumentException(
                "The following values are out of range for a puzzle with size ${rows.size}: ${invalidNumbers.joinToString(",")}"
            )
        }
    }

    fun row(rowIndex: Int): List<Int> {
        validateIndex(rowIndex)
        return rows[rowIndex]
    }

    fun column(columnIndex: Int): List<Int> {
        validateIndex(columnIndex)
        return indexRange().map { rowIndex -> rows[rowIndex][columnIndex] }
    }

    fun rows() = rows

    fun columns() = indexRange().map { column(it) }

    /**
     * @return A List of the 3x3 [SudokuGrid]s composing this solution, or an [emptyList] if the
     *  receiving grid is already a subgrid and cannot be broken down farther (I'm not crazy about
     *  this - see notes on [SudokuGrid] for details).
     */
    fun subGrids(): List<SudokuGrid> {
        if (rows.size == MINIMUM_SIZE) {
            return emptyList()
        }
        /*
        These numbers are a bit magical...
        There are many variations of Sudoku Puzzles, so there's an opportunity to abstract this.
        Given the way the code is written, I'm not sure a variable name would lend much clarity.
         */
        return listOf(
            buildSubRowFromIndexRange(0, 2),
            buildSubRowFromIndexRange(3, 5),
            buildSubRowFromIndexRange(6, 8)
        ).flatten()
    }

    private fun indexRange() = IntRange(0, maxIndex())

    private fun maxIndex() = rows.size - 1

    private fun validateIndex(index: Int) {
        if (index > maxIndex()) {
            throw IndexOutOfBoundsException()
        }
    }

    private fun buildSubRowFromIndexRange(startIndex: Int, endIndex: Int): List<SudokuGrid> {
        return IntRange(startIndex, endIndex)
            .map { index -> rows[index].chunked(endIndex - startIndex + 1) }
            .map { SudokuGrid(it) }
    }
}