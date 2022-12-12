package day08

import readLines

fun main() {

    fun part1(input: List<String>): Int {
        val transposedInput = input
            .transpose()

        return input
            .mapIndexed { rowIndex, row ->
                row.mapIndexed { columnIndex, c ->
                    val column = transposedInput[columnIndex]
                    when {
                        (columnIndex == 0 || columnIndex == row.length - 1) -> 1
                        (rowIndex == 0 || rowIndex == input.size - 1) -> 1
                        row.substring(0 until columnIndex).all { it < c } -> 1 //left
                        row.substring(columnIndex + 1).all { it < c } -> 1 //right
                        column.substring(0 until rowIndex).all { it < c } -> 1 //up
                        column.substring(rowIndex + 1).all { it < c } -> 1 //up
                        else -> 0
                    }
                }
            }
            .flatten()
            .sum()
    }

    fun part2(input: List<String>): Int {

        val transposedInput = input
            .transpose()

        return input
            .mapIndexed { rowIndex, row ->
                row.mapIndexed { columnIndex, c ->
                    val column = transposedInput[columnIndex]
                    val up = column.substring(0 until rowIndex).reversed()
                        .getDistance(c)
                    val down = column.substring(rowIndex + 1)
                        .getDistance(c)
                    val left = row.substring(0 until columnIndex).reversed()
                        .getDistance(c)
                    val right = row.substring(columnIndex + 1)
                        .getDistance(c)
                    up * down * right * left
                }
            }
            .flatten()
            .max()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readLines("day08/Day08_test")

    val input = readLines("day08/Day08")
    println(part1(input))
    println(part2(input))
}

private fun String.getDistance(tree: Char) =
    fold(0) { acc, nextTree ->
        if (nextTree >= tree) {
            return acc + 1
        } else acc + 1
    }

fun List<String>.transpose(): List<String> {
    val ret: MutableList<String> = ArrayList()
    val n = this[0].length
    for (i in 0 until n) {
        val col: MutableList<Char> = ArrayList()
        for (row in this) {
            col.add(row[i])
        }
        ret.add(col.fold("") { acc, c -> acc + c })
    }
    return ret
}
