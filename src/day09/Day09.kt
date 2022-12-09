package day09

import readInput
import kotlin.math.abs

fun main() {

    fun part1(input: List<String>): Int {
        val head = Position(0, 0)
        val tail = Tail(Position(0, 0))
        input.map {
            val (direction, steps) = it.split(" ")

            repeat(steps.toInt()) {
                val oldHeadPosition = head.copy()
                head.move(Position.Direction(direction))
                tail.follow(head, oldHeadPosition)
            }
        }

        return tail.visited.size
    }

    fun part2(input: List<String>): Int {
        //TODO

        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day09/Day09_test")

    val input = readInput("day09/Day09")
    println(part1(input))
    println(part2(input))
}

class Tail(val position: Position, val visited: MutableSet<Position> = mutableSetOf()) {

    init {
        visited.add(position.copy())
    }

    fun follow(head: Position, oldHeadPosition: Position) {
        val distance = position distanceTo head
        when {
            abs(distance.first) == 2 && distance.second == 0 -> {
                position.moveTo(oldHeadPosition.x, oldHeadPosition.y)
                visited.add(position.copy())
            }

            distance.first == 0 && abs(distance.second) == 2 -> {
                position.moveTo(oldHeadPosition.x, oldHeadPosition.y)
                visited.add(position.copy())
            }

            abs(distance.first) + abs(distance.second) == 3 -> {
                position.moveTo(oldHeadPosition.x, oldHeadPosition.y)
                visited.add(position.copy())
            }
        }
    }
}

data class Position(var x: Int = 0, var y: Int = 0) {

    fun moveTo(x: Int, y: Int) {
        this.x = x
        this.y = y
    }

    fun move(direction: Direction): Position {
        when (direction) {
            Direction.RIGHT -> x += 1
            Direction.LEFT -> x -= 1
            Direction.UP -> y += 1
            Direction.DOWN -> y -= 1
        }
        return this
    }

    infix fun distanceTo(position: Position): Pair<Int, Int> {
        return Pair(position.x - x, position.y - y)
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
}
