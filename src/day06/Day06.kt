package day06

import java.io.File

fun main() {

    fun part1(input: String): Int {
        return input.windowedSequence(4, 1)
            .withIndex()
            .first {
                it.value.toSet().size == 4
            }
            .let { it.index + 4 }
    }

    fun part2(input: String): Int {
        return input.windowedSequence(14, 1)
            .withIndex()
            .first {
                it.value.toSet().size == 14
            }
            .let { it.index + 14 }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = File("src", "day06/Day06_test.txt").readText()

    val input = File("src", "day06/Day06.txt").readText()
    println(part1(input))
    println(part2(input))
}
