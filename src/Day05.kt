import java.io.File
import kotlin.math.min
import kotlin.math.max

class Point2D(val x: Int, val y: Int)

class Line(val v1: Point2D, val v2: Point2D) {
    fun leftEnd(): Point2D {
        return if (v1.x <= v2.x) v1 else v2
    }

    fun rightEnd(): Point2D {
        return if (v1.x > v2.x) v1 else v2
    }

    fun isHorizontal(): Boolean { return v1.y == v2.y }
    fun isVertical(): Boolean { return v1.x == v2.x }
}

class VentMap(private val lines: List<Line>) {
    private val map: Array<IntArray>

    init {
        val xMaxLine = lines.maxByOrNull { max(it.v1.x, it.v2.x) }
        val yMaxLine = lines.maxByOrNull { max(it.v1.y, it.v2.y) }
        val xMax = max(xMaxLine?.v1?.x ?: 0, xMaxLine?.v2?.x ?: 0)
        val yMax = max(yMaxLine?.v1?.y ?: 0, yMaxLine?.v2?.y ?: 0)

        this.map = Array(yMax + 1) { IntArray(xMax + 1) { 0 } }

        lines.forEach {
            when {
                it.isHorizontal() -> {
                    for (x in min(it.v1.x, it.v2.x) until max(it.v1.x, it.v2.x) + 1) {
                        this.map[it.v1.y][x] += 1
                    }
                }
                it.isVertical() -> {
                    for (y in min(it.v1.y, it.v2.y) until max(it.v1.y, it.v2.y) + 1) {
                        this.map[y][it.v1.x] += 1
                    }
                }
                else -> {
                    val leftEnd = it.leftEnd()
                    val rightEnd = it.rightEnd()

                    val verticalStep = if (leftEnd.y > rightEnd.y) -1 else 1
                    var currentY = leftEnd.y

                    for (x in min(it.v1.x, it.v2.x) until max(it.v1.x, it.v2.x) + 1) {
                        this.map[currentY][x] += 1
                        currentY += verticalStep
                    }
                }
            }
        }
    }

    fun countCrosses(): Int {
        return map.sumOf { row -> row.filter { it >= 2 }.count() }
    }
}

fun main(args: Array<String>) {
    val inputPath = "inputs\\input_day5.txt"
    val bufferedReader = File(inputPath).bufferedReader()
    val vents = bufferedReader.readLines().map {
        val nums = it.split(",", " -> ")
        Line(Point2D(nums[0].toInt(), nums[1].toInt()), Point2D(nums[2].toInt(), nums[3].toInt()))
    }

    // Part 1
    val perpendicularVents = vents.filter { it.v1.x == it.v2.x || it.v1.y == it.v2.y }
    val ventMap = VentMap(perpendicularVents)
    println(ventMap.countCrosses())

    // Part 2
    val fullVentMap = VentMap(vents)
    println(fullVentMap.countCrosses())
}