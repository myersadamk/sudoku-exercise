package exercise.sudoku.action.validate

import exercise.sudoku.parser.SudokuPuzzleCsvParser
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.util.ResourceUtils

class SudokuSolutionValidatorTest {

    companion object {
        // Can probably reduce verbosity/improve readability with some utility classes
        private val PARSER = SudokuPuzzleCsvParser()
        val VALID_PUZZLE = PARSER.parse(ResourceUtils.getFile("classpath:example_valid.csv"))
        val INVALID_PUZZLE = PARSER.parse(ResourceUtils.getFile("classpath:example_invalid.csv"))
        val INVALID_PUZZLE_TRICKY = PARSER.parse(ResourceUtils.getFile("classpath:example_invalid_last_square.csv"))
    }

    private val validator = SudokuSolutionValidator()

    @Test
    fun `Validator returns true for a valid puzzle`() {
        assertThat(validator.performAction(VALID_PUZZLE)).isEqualTo(ValidationResult.valid)
    }

    @Test
    fun `Validator recognizes an invalid puzzle`() {
        assertThat(validator.performAction(INVALID_PUZZLE)).isEqualTo(ValidationResult.invalid)
    }

    @Test
    fun `Validator recognizes an invalid puzzle when the error is in the last subgrid`() {
        assertThat(validator.performAction(INVALID_PUZZLE_TRICKY)).isEqualTo(ValidationResult.invalid)
    }
}