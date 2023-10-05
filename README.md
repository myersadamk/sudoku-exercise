## Sudoku Exercise

### Versions
```xml
<properties>
    <java.version>17</java.version>
    <kotlin.version>1.6.10</kotlin.version>
</properties>
```
#### Troubleshooting

If you do not have JDK 17 installed, the project should be easy to convert for JDK 11. I use a text block in one place:

```kotlin
class CommandLineRunner(
private val parser: SudokuPuzzleParser<File>,
@Autowired private val inputHandlers: List<SudokuAction<*>>) :
ApplicationRunner {

    companion object {
        // This text block can be blanked out/modified
        val HELP_TEXT = """
```

### Running the project
For convenience, the `runme.sh` script will attempt to package the maven project before execution.

The application can be invoked by passing the path to a CSV file as the only argument to `runme.sh`.
Optionally, you can also specify the filename for a .csv in the src/test/resources directory, and it
will resolve the path.

```shell
 ./runme.sh example_valid.csv
 ./runme.sh example_invalid.csv
```

Running the application or script with 0 arguments will print the usage block:

```shell
Usage:
    java -jar sudoku-runner-0.0.1-SNAPSHOT.jar <path-to-cvs-file> [--<action-to-execute>]

===
Arguments:
- <path-to-csv-file>: Absolute path to a .csv file representing a Sudoku puzzle
- <action>: One or more actions to execute on the puzzle

Actions:
--validate - validates whether the CSV is a valid Sudoku solution
```
