package day10

import readLines

fun main() {

    fun generateOperations(input: List<String>): List<Operation> = input.map {
        when {
            it.contains("noop") -> {
                Operation(1, 0)
            }

            it.contains("addx") -> {
                Operation(2, it.split(" ").last().toInt())
            }

            else -> throw Exception()
        }
    }

    fun List<Operation>.calculateRegisterValues() =
        fold(mutableListOf(1)) { acc: MutableList<Int>, operation: Operation ->
            val currentX = acc.last()
            repeat(operation.cycles - 1) { acc.add(currentX) }
            acc.add(currentX + operation.toAdd)
            acc
        }

    fun part1(input: List<String>): Int {
        return generateOperations(input)
            .calculateRegisterValues()
            .mapIndexedNotNull { index, value ->
                if ((index - 19) % 40 == 0) (index + 1) * value else null
            }
            .sum()
    }

    fun part2(input: List<String>) {

        generateOperations(input)
            .calculateRegisterValues()
            .apply { removeAt(0) }
            .chunked(40)
            .fold(1) { previousLineLastRegisterValue, line ->
                var spriteRange = (1..3)

                line.foldIndexed(previousLineLastRegisterValue) { index: Int, _, next: Int ->
                    if (index + 1 in spriteRange) print("#") else print(".")
                    spriteRange = (next..next + 2)

                    next
                }
                println()
                line.last()
            }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readLines("day10/Day10_test")

    val input = readLines("day10/Day10")
    println(part1(input))
    part2(input)
}

data class Operation(
    val cycles: Int,
    val toAdd: Int
)
