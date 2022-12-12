import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readLines(name: String) = File("src", "$name.txt")
    .readLines()

/**
 * Reads lines from the given input txt file.
 */
fun readText(name: String) = File("src", "$name.txt")
    .readText()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * Splits list of String when given predicate is true.
 */
inline fun List<String>.splitWhen(predicate: (String) -> Boolean): List<List<String>> =
    foldIndexed(mutableListOf<MutableList<String>>()) { index, acc, s ->
        when {
            predicate(s) -> if (index < size - 1) acc.add(mutableListOf())
            acc.isNotEmpty() -> acc.last().add(s)
            else -> acc.add(mutableListOf(s))
        }
        acc
    }