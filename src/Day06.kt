import java.io.File
import kotlin.math.max

const val resetCounter = 6
const val newbornCounter = 8

const val daysCounterPart1 = 80
const val daysCounterPart2 = 256

var recursionMap: Array<LongArray> = Array(max(daysCounterPart1, daysCounterPart2)) { LongArray(9) { -1L } }

fun familyCount(fishCounter: Int, daysLeft: Int): Long {
    if (daysLeft <= fishCounter) {
        return 1L
    }

    val updatedDaysLeft = daysLeft - fishCounter - 1

    if (recursionMap[updatedDaysLeft][newbornCounter] == -1L) {
        recursionMap[updatedDaysLeft][newbornCounter] = familyCount(newbornCounter, updatedDaysLeft)
    }

    if (recursionMap[updatedDaysLeft][resetCounter] == -1L) {
        recursionMap[updatedDaysLeft][resetCounter] = familyCount(resetCounter, updatedDaysLeft)
    }

    return recursionMap[updatedDaysLeft][newbornCounter] + recursionMap[updatedDaysLeft][resetCounter]
}

fun main() {
    val inputPath = "inputs\\input_day6.txt"
    val bufferedReader = File(inputPath).bufferedReader()
    val fishCounters = bufferedReader.readLine().split(',').map { it.toInt() }.toMutableList()

    // Part 1
    val childrenCountPart1 = fishCounters.sumOf { familyCount(it, daysCounterPart1) }
    println(childrenCountPart1)

    // Part 2
    val childrenCountPart2 = fishCounters.sumOf { familyCount(it, daysCounterPart2) }
    println(childrenCountPart2)
}