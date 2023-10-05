package exercise.sudoku.commandline

import exercise.sudoku.action.SudokuAction
import exercise.sudoku.parser.SudokuPuzzleCsvParser
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

/**
 * Other profiles may include {@code interactive}, which could use a REPL to interactively solve an
 * in-progress puzzle. Conceivably, these modules could also be broken down such that the core
 * functionality is available for consumption by a service that manages a user's game session.
 */
@Configuration
@Profile("commandline")
class CommandLineRunnerConfiguration {

    @Bean
    fun csvFileParser() = SudokuPuzzleCsvParser()

    @Bean
    fun singleInvocationRunner(supportedActions: List<SudokuAction<*>>): ApplicationRunner =
        CommandLineRunner(SudokuPuzzleCsvParser(), supportedActions)

}