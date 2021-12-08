import helpers.permutations
import java.io.File

val keyAlphabet: List<Char> = listOf('a', 'b', 'c', 'd', 'e', 'f', 'g')

class DisplayDecoder(key: String) {
    private val charMap: MutableMap<Char, Char> = mutableMapOf()

    init {
        for (i in key.indices) {
            charMap[key[i]] = keyAlphabet[i]
        }
    }

    private val numberCodes: MutableMap<String, Int> = mutableMapOf(
        "abcefg" to 0, "cf" to 1, "acdeg" to 2, "acdfg" to 3, "bcdf" to 4,
        "abdfg" to 5, "abdefg" to 6, "acf" to 7, "abcdefg" to 8, "abcdfg" to 9
    )

    fun decode(value: String): Int? {
        val decoded = value.map { charMap[it] }.sortedBy { it }.joinToString("")
        return numberCodes[decoded]
    }

    fun canDecode(value: String): Boolean {
        return decode(value) != null
    }
}

class DisplayRead(input: List<String>, val output: List<String>) {
    private val decoder: DisplayDecoder

    init {
        // brute-forcing the key for decoding inputs
        val permutations = keyAlphabet.permutations()
            .map { it.joinToString("") }
            .filter { key -> run {
                val decoder = DisplayDecoder(key)

                input.forEach {
                    if (!decoder.canDecode(it)) { return@filter false }
                }

                true
            } }

        this.decoder = DisplayDecoder(permutations.first())
    }

    fun getOutputValue(): Int {
        return output.map { decoder.decode(it) }.joinToString("").toInt()
    }
}

fun main() {
    val inputFile = "inputs\\input_day8.txt"
    val bufferedReader = File(inputFile).bufferedReader()

    val displayReads = bufferedReader.readLines().map {
        val split = it.split(" | ")
        DisplayRead(split[0].split(' '), split[1].split(' '))
    }

    // Part 1
    val response = displayReads.sumOf {
        read -> read.output.filter { intArrayOf(2, 3, 4, 7).contains(it.length) }.size
    }
    println(response)

    // Part 2
    val result = displayReads.sumOf { it.getOutputValue() }
    println(result)
}