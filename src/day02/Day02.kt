package day02

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        fun Char.shapeScore() = this - 'X' + 1

        fun outcome(round: String): Int =
            when (round) {
                "C X", "A Y", "B Z" -> 6
                "A X", "B Y", "C Z" -> 3
                "B X", "C Y", "A Z" -> 0
                else -> throw Exception()
            }

        return input.sumOf {
            outcome(it) + it[2].shapeScore()
        }
    }

    fun part2(input: List<String>): Int {
        fun Char.outcome() = (this - 'X') * 3

        fun shapeScore(round: String): Int =
            when (round) {
                "B X", "A Y", "C Z" -> 1
                "C X", "B Y", "A Z" -> 2
                "A X", "C Y", "B Z" -> 3
                else -> throw Exception()
            }

        return input.sumOf {
            it[2].outcome() + shapeScore(it)
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day02/Day02_test")

    val input = readInput("day02/Day02")
    println(part1(input))
    println(part2(input))
}

