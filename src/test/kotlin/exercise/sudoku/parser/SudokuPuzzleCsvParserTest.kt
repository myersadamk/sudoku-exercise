package exercise.sudoku.parser

import exercise.sudoku.action.SudokuGrid
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.util.ResourceUtils

internal class SudokuPuzzleCsvParserTest {

    private val parser: SudokuPuzzleCsvParser = SudokuPuzzleCsvParser()

    @Test
    fun validCsvCanBeParsed() {
        val puzzle = parser.parse(ResourceUtils.getFile("classpath:example_valid.csv"))
        assertThat(puzzle.rows().size).isEqualTo(SudokuGrid.MAXIMUM_SIZE)
        assertThat(puzzle.rows()).allMatch { it.size == SudokuGrid.MAXIMUM_SIZE}
    }

    @Test
    fun `An error is reported when a row has extra values`() {
        assertThatThrownBy {
            parser.parse(ResourceUtils.getFile("classpath:example_malformed_row.csv"))
        }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Malformed Sudoku format; each line must have exactly 9 comma-separated numbers. Line: 3,3,3,5,7,8,6,5,5,4")
    }

    @Test
    fun `An error is reported when a column has extra values`() {
        assertThatThrownBy {
            parser.parse(ResourceUtils.getFile("classpath:example_malformed_column.csv"))
        }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Malformed Sudoku format; each line must have exactly 9 comma-separated numbers. Line: 4")
    }

    @Test
    fun `An error is reported when a comma-delimited value is blank`() {
        assertThatThrownBy {
            parser.parse(ResourceUtils.getFile("classpath:example_malformed_missing_values.csv"))
        }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Malformed Sudoku format; each line must have exactly 9 comma-separated numbers. Line: 3,3,3,5,,8,6,5,5,")
    }

    @Test
    fun `An error is reported when a non-numeric value is encountered`() {
        assertThatThrownBy {
            parser.parse(ResourceUtils.getFile("classpath:example_malformed_alpha_value.csv"))
        }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Malformed Sudoku puzzle; non-numeric value could not be parsed: 4,1,4,7,X,5,2,1,3")
    }
}