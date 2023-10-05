package exercise.sudoku.action.validate

import exercise.sudoku.action.SudokuAction
import exercise.sudoku.action.SudokuGrid

/**
 * Validates whether a {@linkplain SudokuGrid} is a valid Sudoku solution. A solution is valid if it
 * meets the following criteria:
 * - Contain every number from 1 to 9 exactly once in each row.
 * - Contain every number from 1 to 9 exactly once in each column.
 * - Contain every number from 1 to 9 exactly once in each of nine 3x3 sub-grids
 */
class SudokuSolutionValidator : SudokuAction<ValidationResult> {
    companion object {
        val SOLVED_SEQUENCE: Set<Int> = sequenceOf(1, 2, 3, 4, 5, 6, 7, 8, 9).toSet()
    }

    override fun name() = "validate"

    override fun description() = "validates whether the CSV is a valid Sudoku solution"

    override fun performAction(grid: SudokuGrid): ValidationResult {
        if (areAllRowsAndColumnsSolved(grid).and(areAllSubGridsSolved(grid))) {
            return ValidationResult.valid
        }
        return ValidationResult.invalid
    }

    private fun areAllRowsAndColumnsSolved(grid: SudokuGrid): Boolean =
        (grid.rows() + grid.columns()).all { it.containsAll(SOLVED_SEQUENCE) }

    private fun areAllSubGridsSolved(grid: SudokuGrid): Boolean =
        grid.subGrids().map { it.rows().flatten() }.all { it.containsAll(SOLVED_SEQUENCE) }
}