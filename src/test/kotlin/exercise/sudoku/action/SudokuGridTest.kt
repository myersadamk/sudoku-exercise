package exercise.sudoku.action

import exercise.sudoku.parser.SudokuPuzzleCsvParser
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.util.ResourceUtils

class SudokuGridTest {

    companion object {
        val PUZZLE =
            SudokuPuzzleCsvParser().parse(ResourceUtils.getFile("classpath:example_valid.csv"))

        val VALID_ROW = (1..9).toList()
    }

    @Test
    fun invalidNumbers() {
        val rowWithInvalidNumber = (2..10).toList()
        val exception = assertThrows<IllegalArgumentException> {
            SudokuGrid(
                listOf(
                    VALID_ROW, VALID_ROW, VALID_ROW,
                    VALID_ROW, rowWithInvalidNumber, VALID_ROW,
                    VALID_ROW, VALID_ROW, VALID_ROW
                )
            )
        }
        assertThat(exception).hasMessage("The following values are out of range for a puzzle with size 9: 10")
    }

    @Test
    fun `column(i) properly assembles the column from the 2D list`() {
        assertThat(PUZZLE.column(0)).containsExactly(1, 4, 7, 2, 5, 8, 3, 6, 9)
        assertThat(PUZZLE.column(1)).containsExactly(2, 5, 8, 3, 6, 9, 4, 7, 1)
        assertThat(PUZZLE.column(2)).containsExactly(3, 6, 9, 4, 7, 1, 5, 8, 2)
        // and so on... omitted for brevity
    }

    @Test
    fun `row(i) properly retrieves the row from the 2D list`() {
        assertThat(PUZZLE.row(0)).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9)
        assertThat(PUZZLE.row(1)).containsExactly(4, 5, 6, 7, 8, 9, 1, 2, 3)
        assertThat(PUZZLE.row(2)).containsExactly(7, 8, 9, 1, 2, 3, 4, 5, 6)
        // and so on... omitted for brevity
    }

    @Test
    fun `subGrids() properly subdivides the SudokuGrid into 9 3x3 grids`() {
        val subGrids = PUZZLE.subGrids()
        assertThat(subGrids).hasSize(9)
        subGrids.forEach {
            assertThat(it.rows()).hasSize(3)
            assertThat(it.columns()).hasSize(3)
            // more verification to be done here - omitted for brevity
        }
    }
}
