package exercise.sudoku.commandline

import exercise.sudoku.action.SudokuAction
import exercise.sudoku.parser.SudokuPuzzleParser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import java.io.File

class CommandLineRunner(
    private val parser: SudokuPuzzleParser<File>,
    @Autowired private val registeredActions: List<SudokuAction<*>>) :
    ApplicationRunner {

    companion object {
        val HELP_TEXT = """
            Usage:
                java -jar sudoku-exercise-0.0.1-SNAPSHOT.jar <path-to-cvs-file> --<action-to-execute>
            
            ===
            Arguments:
            - <path-to-csv-file>: Absolute path to a .csv file representing a Sudoku puzzle
            - <action-to-execute>: One or more actions to execute on the puzzle
         
            Actions:
        """.trimIndent()
    }

    override fun run(args: ApplicationArguments?) {
        if (args == null) {
            printHelp()
            return
        }

        if (args.nonOptionArgs.size == 0) {
            printHelp()
            return
        }

        val file = File(args.nonOptionArgs[0])
        if (!file.exists()) {
            throw IllegalArgumentException("File not found at path ${args.nonOptionArgs[0]}")
        }

        val sudokuPuzzle = parser.parse(file)

        val actions: List<SudokuAction<*>> = getRequestedActions(args)
        if (actions.isEmpty()) {
            printHelp()
            return
        }

        actions.map {
            it.performAction(sudokuPuzzle)
        }.forEach { println(it) }
    }

    private fun printHelp() {
        println(HELP_TEXT)
        registeredActions.map{ "--${it.name()} - ${it.description()}\n"}.forEach { println(it) }
    }

    private fun getRequestedActions(args: ApplicationArguments) =
        registeredActions.stream().filter { args.containsOption(it.name()) }.toList()
}