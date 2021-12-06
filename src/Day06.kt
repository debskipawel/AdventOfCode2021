import java.io.File
import kotlin.math.max

const val resetCounter = 6
const val newbornCounter = 8

const val daysCounterPart1 = 80
const val daysCounterPart2 = 256

var recursionMap: Array<LongArray> = Array(max(daysCounterPart1, daysCounterPart2) + 1) { LongArray(9) { -1L } }

fun preprocessing() {
    recursionMap.forEachIndexed { dayIndex, counters -> run {
        counters.forEachIndexed { counterIndex, _ -> run {
            if (dayIndex <= counterIndex) {
                recursionMap[dayIndex][counterIndex] = 1
            } else {
                val updatedDaysLeft = dayIndex - counterIndex - 1
                recursionMap[dayIndex][counterIndex] =
                    recursionMap[updatedDaysLeft][newbornCounter] + recursionMap[updatedDaysLeft][resetCounter]
            }
        } }
    }}
}

fun main() {
    val inputPath = "inputs\\input_day6.txt"
    val bufferedReader = File(inputPath).bufferedReader()
    val fishCounters = bufferedReader.readLine().split(',').map { it.toInt() }

    val fishMap: MutableMap<Int, Int> = mutableMapOf()

    fishCounters.forEach {
        when (val count = fishMap[it])
        {
            null -> fishMap[it] = 0
            else -> fishMap[it] = count + 1
        }
    }

    preprocessing()

    // Part 1
    val childrenCountPart1 = fishMap.map { it.value * recursionMap[daysCounterPart1][it.key] }.sum()
    println(childrenCountPart1)

    // Part 2
    val childrenCountPart2 = fishMap.map { it.value * recursionMap[daysCounterPart2][it.key] }.sum()
    println(childrenCountPart2)
}