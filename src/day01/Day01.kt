package day01

import readInput
import splitWhen

fun main() {
    fun part1(input: List<String>): Int =
        input
            .splitWhen { it.isEmpty() }
            .maxOf { it.sumOf { singleItemCalories -> singleItemCalories.toInt() } }

    fun part2(input: List<String>): Int =
        input
            .splitWhen { it.isEmpty() }
            .map { it.sumOf { singleItemCalories -> singleItemCalories.toInt() } }
            .sortedByDescending { it }
            .take(3)
            .sum()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day01/Day01_test")

    val input = readInput("day01/Day01")
    println(part1(input))
    println(part2(input))
}