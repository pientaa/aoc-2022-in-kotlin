package day01

import readInput

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

inline fun List<String>.splitWhen(predicate: (String) -> Boolean): List<List<String>> =
    foldIndexed(mutableListOf<MutableList<String>>()) { index, acc, s ->
        when {
            predicate(s) -> if (index < size - 1) acc.add(mutableListOf())
            acc.isNotEmpty() -> acc.last().add(s)
            else -> acc.add(mutableListOf(s))
        }
        acc
    }