package day09

import day09.Direction.DOWN
import day09.Direction.LEFT
import day09.Direction.RIGHT
import day09.Direction.UP
import readInput
import kotlin.math.abs

fun main() {

    fun applyMoves(input: List<String>, knots: List<Knot>) {
        input.map { line ->
            val (direction, steps) = line.split(" ")

            repeat(steps.toInt()) {
                knots.onEach { knot ->
                    knot.move(Direction(direction))
                }
            }
        }
    }

    fun part1(input: List<String>): Int {
        val head = Knot(id = 0)
        val tail = Knot(id = 1, following = head)

        applyMoves(input, listOf(head, tail))

        return tail.visited.size
    }

    fun part2(input: List<String>): Int {
        val knots = (1..9)
            .fold(mutableListOf(Knot(id = 0))) { acc: MutableList<Knot>, index: Int ->
                acc.add(Knot(index, acc.last()))
                acc
            }

        applyMoves(input, knots)

        return knots.last().visited.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day09/Day09_test")

    val input = readInput("day09/Day09")
    println(part1(input))
    println(part2(input))
}

private infix fun Pair<Int, Int>.isExactlyFarInOneAxis(distance: Int): Boolean =
    (first == 0 && abs(second) == distance) || (abs(first) == distance && second == 0)

data class Knot(
    val id: Int,
    val following: Knot? = null,
    val position: Position = Position(0, 0),
    val visited: MutableSet<Position> = mutableSetOf()
) {

    init {
        visited.add(position.copy())
    }

    fun move(direction: Direction) {
        when (following) {
            null -> position.move(direction)
            else -> {
                val distance = position distanceTo following.position
                val directions = distance.getDirections()

                if (distance isFurtherThanMoves 2 || distance isExactlyFarInOneAxis 2)
                    directions.forEach { position.move(it) }
            }
        }
        visited.add(position.copy())
    }
}

private fun Pair<Int, Int>.getDirections() = listOfNotNull(first.getDirection(Axis.X), second.getDirection(Axis.Y))

private infix fun Pair<Int, Int>.isFurtherThanMoves(overallDistance: Int): Boolean =
    (abs(first) + abs(second)) > overallDistance

private fun Int.getDirection(axis: Axis): Direction? =
    when {
        this > 0 && axis == Axis.X -> RIGHT
        this < 0 && axis == Axis.X -> LEFT
        this > 0 && axis == Axis.Y -> UP
        this < 0 && axis == Axis.Y -> DOWN
        else -> null
    }

data class Position(var x: Int = 0, var y: Int = 0) {

    fun move(direction: Direction): Position {
        when (direction) {
            RIGHT -> x += 1
            LEFT -> x -= 1
            UP -> y += 1
            DOWN -> y -= 1
        }
        return this
    }

    infix fun distanceTo(position: Position): Pair<Int, Int> {
        return Pair(position.x - x, position.y - y)
    }
}

enum class Axis {
    X,
    Y
}

enum class Direction {
    RIGHT,
    LEFT,
    UP,
    DOWN;

    companion object {
        operator fun invoke(from: String) = when (from) {
            "R" -> RIGHT
            "L" -> LEFT
            "U" -> UP
            "D" -> DOWN
            else -> throw Exception()
        }
    }
}
