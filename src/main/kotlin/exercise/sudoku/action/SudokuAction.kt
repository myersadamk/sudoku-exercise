package exercise.sudoku.action

/**
 * Represents a generic action that can be taken on a [SudokuGrid].
 *
 * I can imagine others implementations on a Sudoku puzzle. For instance, one can imagine
 * a "SudokuPuzzleSolver," or a "SudokuPuzzleVerifier" (which would verify whether a puzzle state is
 * solvable). Note that, for this to make sense, the SudokuGrid would have to be capable of handling
 * incomplete puzzle states (perhaps by representing empty spaces as 0).
 */
interface SudokuAction<T> {
    fun name(): String
    fun description(): String
    fun performAction(grid: SudokuGrid): T
}