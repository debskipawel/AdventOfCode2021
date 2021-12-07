import java.io.File
import kotlin.math.abs

open class CrabSwarm(private val positions: List<Int>) {
    fun minCostForAlignment() : Int {
        val range = positions.minOf { it } until positions.maxOf { it } + 1
        return range.minOf { costForMove(it) }
    }

    private fun costForMove(position: Int): Int {
        return positions.sumOf { singleMoveCost(it, position) }
    }

    protected open fun singleMoveCost(start: Int, end: Int): Int { return abs(start - end) }
}

class CostlyCrabSwarm(positions: List<Int>) : CrabSwarm(positions) {
    override fun singleMoveCost(start: Int, end: Int) : Int {
        val diff = abs(start - end)
        return diff * (diff + 1) / 2
    }
}

fun main() {
    val inputFile = "inputs\\input_day7.txt"
    val bufferedReader = File(inputFile).bufferedReader()

    val positions = bufferedReader.readLine().split(',').map { it.toInt() }

    // Part 1
    var swarm = CrabSwarm(positions)
    println(swarm.minCostForAlignment())

    // Part 2
    swarm = CostlyCrabSwarm(positions)
    println(swarm.minCostForAlignment())
}