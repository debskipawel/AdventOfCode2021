import helpers.permutations
import java.io.File

class DisplayDecoder(key: String) {
    private val charMap: Map<Char, Char>

    init {
        val map = mutableMapOf<Char, Char>()
        for (i in key.indices) {
            map[key[i]] = "abcdefg"[i]
        }
        this.charMap = map
    }

    private val numberCodes: Map<String, Int>

    init {
        this.numberCodes = HashMap()
        this.numberCodes["abcefg"] = 0
        this.numberCodes["cf"] = 1
        this.numberCodes["acdeg"] = 2
        this.numberCodes["acdfg"] = 3
        this.numberCodes["bcdf"] = 4
        this.numberCodes["abdfg"] = 5
        this.numberCodes["abdefg"] = 6
        this.numberCodes["acf"] = 7
        this.numberCodes["abcdefg"] = 8
        this.numberCodes["abcdfg"] = 9
    }

    fun decode(value: String): Int? {
        val decoded = value.map { charMap[it] }.sortedBy { it }.joinToString("")

        return numberCodes[decoded]
    }

    fun canDecode(value: String): Boolean {
        return decode(value) != null
    }
}

class DisplayRead(private val input: List<String>, val output: List<String>) {
    init {
        this.input.sortedBy { it.length }
        this.output.sortedBy { it.length }
    }

    private val decoder: DisplayDecoder

    init {
        // brute-forcing the key for decoding inputs
        val permutations = listOf('a', 'b', 'c', 'd', 'e', 'f', 'g').permutations()
            .map { it.joinToString("") }.filter {
                key -> run {
                    val decoder = DisplayDecoder(key)

                    input.forEach {
                        if (!decoder.canDecode(it)) {
                            return@filter false
                        }
                    }

                    true
                }
            }

        this.decoder = DisplayDecoder(permutations.first())
    }

    fun getOutputValue(): Int {
        var value = 0
        output.forEach {
            value = value * 10 + decoder.decode(it)!!
        }

        return value
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