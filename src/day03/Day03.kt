package day03

import readInput

fun main() {

    fun findPriority(rucksack: String): Char {
        val first = rucksack.substring(0, rucksack.length / 2)
        val second = rucksack.substring(rucksack.length / 2)

        return first.first {
            second.contains(it)
        }
    }

    fun Char.toPriority() = if (isLowerCase()) this - 'a' + 1 else this - 'A' + 27

    fun part1(input: List<String>): Int =
        input.sumOf {
            findPriority(it)
                .toPriority()
        }

    fun findBadge(rucksacks: List<String>): Char =
        rucksacks.first()
            .first { c ->
                rucksacks.subList(1, 3)
                    .all { it.contains(c) }
            }

    fun part2(input: List<String>): Int =
        input.windowed(3, 3)
            .sumOf { findBadge(it).toPriority() }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day03/Day03_test")

    val input = readInput("day03/Day03")
    println(part1(input))
    println(part2(input))
}


