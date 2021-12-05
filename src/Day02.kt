import java.io.File

class CourseTracker {
    private var depth = 0
    private var horizontal = 0
    private var aim = 0

    fun move (direction: String, value: Int) {
        when (direction) {
            "up" -> aim -= value
            "down" -> aim += value
            "forward" -> {
                horizontal += value
                depth += aim * value
            }
        }
    }

    fun getCoords(): Int {
        return depth * horizontal
    }
}

fun main(args: Array<String>) {
    val inputPath = "inputs\\input_day2.txt"

    val bufferedReader = File(inputPath).inputStream().bufferedReader()
    val courseTracker = CourseTracker()

    bufferedReader.forEachLine {
        val options = it.split(' ')
        courseTracker.move(options[0], options[1].toInt())
    }

    println(courseTracker.getCoords())
}