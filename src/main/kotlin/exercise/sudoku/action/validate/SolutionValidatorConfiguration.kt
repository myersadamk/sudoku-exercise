package exercise.sudoku.action.validate

import exercise.sudoku.action.SudokuAction
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Additional actions can be added to this configuration, or incorporated by adding additional
 * components/configurations. For instance, a CLI "help" runner, would be a useful action to have as
 * a fallback.
 */
@Configuration
class SolutionValidatorConfiguration {

    @Bean
    fun solutionValidator(): SudokuAction<*> = SudokuSolutionValidator()
 }