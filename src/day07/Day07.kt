package day07

import readLines

fun main() {

    fun createFileSystem(input: List<String>): Directory {
        val fileSystem = Directory("/")
        val currentPath: MutableList<String> = mutableListOf()

        input.drop(1).map {
            when {
                it.contains("dir") -> {
                    val directoryName = it.substring(4)
                    fileSystem.addDirectory(directoryName, currentPath)
                }

                it.any { c -> c.isDigit() } -> {
                    fileSystem.addFile(File(it), currentPath)
                }

                it.contains("cd") && it.contains("..") -> {
                    currentPath.removeLast()
                }

                it.contains("cd") && !it.contains("..") -> {
                    val directoryName = it.substring(5)
                    currentPath.add(directoryName)
                }

                else -> {}
            }
        }
        return fileSystem
    }

    fun part1(input: List<String>): Int {
        val fileSystem = createFileSystem(input)
        return fileSystem.getAllDirectoriesFlatten()
            .map { it.getOverallSize() }
            .filter { it <= 100000 }
            .sum()
    }

    fun part2(input: List<String>): Int {
        val fileSystem = createFileSystem(input)

        val sizes = fileSystem.getAllDirectoriesFlatten()
            .map { it.getOverallSize() }

        val toDelete = sizes.max() - 40000000


        return fileSystem.getAllDirectoriesFlatten()
            .map { it.getOverallSize() }
            .also { println(it) }
            .filter { it >= toDelete }
            .minBy { it }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readLines("day07/Day07_test")

    val input = readLines("day07/Day07")
    println(part1(input))
    println(part2(input))
}

class Directory(private val name: String) {
    private val directories: MutableList<Directory> = mutableListOf()
    private val files: MutableList<File> = mutableListOf()
    private var size: Int = 0

    fun addDirectory(directoryName: String, path: List<String>) {
        val nestedDirectory = getDirectoryAtPath(path)
        nestedDirectory.addDirectory(Directory(directoryName))
    }

    private fun addDirectory(directory: Directory) {
        if (directories.none { it.name == directory.name })
            directories.add(directory)
    }

    fun addFile(file: File, path: List<String>) {
        val directory = getDirectoryAtPath(path)
        directory.addFile(file)
    }

    private fun addFile(file: File) {
        if (files.none { it.name == file.name }) {
            files.add(file)
            size += file.size
        }
    }

    fun getOverallSize(): Int = size + directories.sumOf { it.getOverallSize() }

    private fun getDirectoryAtPath(path: List<String>) = path.fold(this) { acc, s ->
        acc.directories.first { it.name == s }
    }

    fun getAllDirectoriesFlatten(): Set<Directory> =
        setOf(this) + directories + directories.flatMap { it.getAllDirectoriesFlatten() }
}

class File private constructor(val name: String, val size: Int) {
    companion object {
        operator fun invoke(line: String): File {
            val (size, name) = line.split(" ")
            return File(name, size.toInt())
        }
    }
}