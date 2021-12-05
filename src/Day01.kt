import java.io.File

fun countLargerByOffset(lineList: ArrayList<Int>, offset: Int): Int {
    return lineList.filterIndexed { index, value -> index >= offset && value > lineList[index - offset] }.count()
}

fun main(args: Array<String>) {
    val inputPath = "inputs\\input_day1.txt"
    val lineList = arrayListOf<Int>()

    File(inputPath).inputStream().bufferedReader().forEachLine { lineList.add(it.toInt()) }

    // Part 1
    println(countLargerByOffset(lineList, 1))

    // Part 2
    println(countLargerByOffset(lineList, 3))
}