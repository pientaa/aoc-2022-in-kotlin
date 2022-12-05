package day05

import java.io.File

fun main() {

    fun transformStacks(rawStacks: List<String>) =
        rawStacks.dropLast(1)
            .map { row ->
                row.mapIndexedNotNull { index, s ->
                    if ((index - 1) % 4 == 0)
                        s
                    else null
                }
            }
            .asReversed()
            .transpose()
            .map { list ->
                list.filterNot { it == ' ' }.toMutableList()
            }.toMutableList()

    fun transformCommands(rawCommands: List<String>) =
        rawCommands.map { rawCommand ->
            val (howMany, from, to) = rawCommand.replace(regex = Regex("[A-Za-z]"), "").trim()
                .split("  ")
                .map { it.toInt() }
            Triple(howMany, from, to)
        }

    fun transformInput(input: String): Pair<MutableList<MutableList<Char>>, List<Triple<Int, Int, Int>>> {
        val (rawStacks: List<String>, rawCommands: List<String>) = input.split("\n\n").map { it.split("\n") }
        return Pair(transformStacks(rawStacks), transformCommands(rawCommands))
    }

    fun part1(input: String): String {
        val (stacks, commands) = transformInput(input)

        commands.forEach { (howMany, from, to) ->
            stacks
                .transform(howMany, from, to)
        }

        return stacks.getLastCrates()
    }

    fun part2(input: String): String {
        val (stacks, commands) = transformInput(input)

        commands.forEach { (howMany, from, to) ->
            stacks
                .transform(howMany, from, to, reversed = false)
        }

        return stacks.getLastCrates()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = File("src", "day05/Day05_test.txt").readText()

    val input = File("src", "day05/Day05.txt").readText()
    println(part1(input))
    println(part2(input))
}

private fun <E> MutableList<MutableList<E>>.getLastCrates(): String =
    fold("") { acc, chars -> acc + if (chars.isNotEmpty()) chars.last() else "" }

private fun <E> MutableList<MutableList<E>>.transform(
    howMany: Int,
    from: Int,
    to: Int,
    reversed: Boolean = true
): MutableList<MutableList<E>> {
    val load = if (reversed)
        this[from - 1].takeLast(howMany).reversed()
    else
        this[from - 1].takeLast(howMany)

    load.forEach {
        this[to - 1].add(it)
        this[from - 1].removeAt(this[from - 1].size - 1)
    }
    return this
}

fun <T> List<List<T>>.transpose(): List<List<T>> {
    val ret: MutableList<List<T>> = ArrayList()
    val n = this[0].size
    for (i in 0 until n) {
        val col: MutableList<T> = ArrayList()
        for (row in this) {
            col.add(row[i])
        }
        ret.add(col)
    }
    return ret
}