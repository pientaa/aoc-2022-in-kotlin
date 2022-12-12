package day04

import readLines

fun main() {

    fun getEachElfSection(input: List<String>) =
        input.map { line ->
            line.split(",")
                .map { range ->
                    range.split('-').map { it.toInt() }
                        .let { it.first()..it.last() }
                        .toSet()
                }
        }

    fun part1(input: List<String>): Int =
        getEachElfSection(input)
            .filter {
                (it.first() + it.last()).size == it.first().size ||
                        (it.first() + it.last()).size == it.last().size
            }
            .size

    fun part2(input: List<String>): Int =
        getEachElfSection(input)
            .filter { pair ->
                pair.first().any { sectionID ->
                    pair.last().contains(sectionID)
                }
            }
            .size

    // test if implementation meets criteria from the description, like:
    val testInput = readLines("day04/Day04_test")

    val input = readLines("day04/Day04")
    println(part1(input))
    println(part2(input))
}
