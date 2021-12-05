import java.io.File

fun main(args: Array<String>) {
    val inputPath = "inputs\\input_day3.txt"
    val file = File(inputPath)
    val bufferedReader = file.inputStream().bufferedReader()

    // Part 1
    val symbolsCount = IntArray(12)

    bufferedReader.forEachLine {
        it.forEachIndexed { index, c -> symbolsCount[index] += c.digitToInt() }
    }

    bufferedReader.close()
    val linesCount = file.bufferedReader().lines().count().toInt()

    var gamma = 0
    var epsilon = 0

    symbolsCount.forEach {
        val gammaBit = 2 * it / linesCount

        gamma = gamma * 2 + gammaBit
        epsilon = epsilon * 2 + gammaBit xor 1
    }

    println(gamma * epsilon)

    // Part 2
    var oxygenLines = file.inputStream().bufferedReader().readLines()
    var scrubberLines = oxygenLines.toList()

    for (i in 0..11) {
        if (oxygenLines.count() == 1) {
            break
        }

        val mostFrequentBit = (2 * oxygenLines.count { it[i] == '1' } / oxygenLines.count())
            .coerceAtMost(1)

        oxygenLines = oxygenLines.filter { it[i].digitToInt() == mostFrequentBit }
    }

    val oxygenGeneratorRating = oxygenLines.first().toInt(2)

    for (i in 0..11) {
        if (scrubberLines.count() == 1) {
            break
        }

        val leastFrequentBit = (2 * scrubberLines.count { it[i] == '1' } / scrubberLines.count())
            .coerceAtMost(1) xor 1

        scrubberLines = scrubberLines.filter { it[i].digitToInt() == leastFrequentBit }
    }

    val carbonScrubberRating = scrubberLines.first().toInt(2)

    println(oxygenGeneratorRating * carbonScrubberRating)
}