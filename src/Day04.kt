import java.io.File

class BingoBoard(private val board: Array<IntArray>) {
    private var boardWin = false
    private val markedBoard: Array<Array<Boolean>> = Array(board.size) { Array(board.size) { false } }

    fun markNumber(number: Int) {
        try {
            val arr = board.first { it.contains(number) }

            val row = board.indexOf(arr)
            val col = arr.indexOf(number)

            markedBoard[row][col] = true

            if (markedBoard[row].filter { !it }.count() == 0
                || markedBoard.filter { !it[col] }.count() == 0) {
                this.boardWin = true
            }
        } catch (e: NoSuchElementException) {
            return
        }
    }

    fun score(): Int {
        var score = 0

        board.forEachIndexed { y, row -> run {
            row.forEachIndexed { x, number -> run {
                if (!markedBoard[y][x]) {
                    score += number
                }
            } }
        } }

        return score
    }

    fun didBoardWin(): Boolean {
        return boardWin
    }
}

fun findFirstWinningScore(boards: List<BingoBoard>, guessedNumbers: IntArray): Int {
    guessedNumbers.forEach {
        boards.forEach { board -> board.markNumber(it) }

        val winners = boards.filter { board -> board.didBoardWin() }

        if (winners.count() != 0) {
            return winners.first().score() * it
        }
    }

    return -1
}

fun findLastWinningScore(boards: List<BingoBoard>, guessedNumbers: IntArray): Int {
    var subBoards = boards

    guessedNumbers.forEach {
        subBoards.forEach { board -> board.markNumber(it) }
        val losers = subBoards.filter { board -> !board.didBoardWin() }

        if (losers.count() == 0) {
            return subBoards.first().score() * it
        }

        subBoards = losers
    }

    return -1
}

fun main(args: Array<String>) {
    val fileInput = "inputs\\input_day4.txt"
    val boardSize = 5

    val bufferedReader = File(fileInput).bufferedReader()

    val guessedNumbers = bufferedReader.readLine().split(',').map { it -> it.toInt() }.toIntArray()
    val lines = bufferedReader.readLines().filter { it.isNotEmpty() }.chunked(boardSize)

    val boards = lines.map { board ->
        BingoBoard(board.map {
                line -> line.split(' ').filter { it.isNotEmpty() }.map { it.toInt() }.toIntArray()
        }.toTypedArray())
    }

    // Part 1
    println(findFirstWinningScore(boards, guessedNumbers))

    // Part 2
    println(findLastWinningScore(boards, guessedNumbers))
}