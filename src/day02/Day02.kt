package day02

import readInput

fun main() {
    fun part1(input: List<String>): Int {

        return input.sumOf {
            scores1.getValue(it)
        }
    }

    fun part2(input: List<String>): Int {

        val scores2 = mutableMapOf(
            "A X" to "A Z",
            "A Y" to "A X",
            "A Z" to "A Y",
            "B X" to "B X",
            "B Y" to "B Y",
            "B Z" to "B Z",
            "C X" to "C Y",
            "C Y" to "C Z",
            "C Z" to "C X"
        )

        return input.sumOf {
            scores1.getValue(
                scores2.getValue(it)
            )
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day02/Day02_test")

    val input = readInput("day02/Day02")
    println(part1(input))
    println(part2(input))
}

val scores1 = mutableMapOf(
    "A X" to 4,
    "A Y" to 8,
    "A Z" to 3,
    "B X" to 1,
    "B Y" to 5,
    "B Z" to 9,
    "C X" to 7,
    "C Y" to 2,
    "C Z" to 6
)

