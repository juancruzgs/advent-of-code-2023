import kotlin.math.max

fun main() {
    val cubeColors = mapOf(
        "red" to 12,
        "green" to 13,
        "blue" to 14
    )

    fun part1(input: List<String>): Int =
        input.sumOf { game ->
            val substrings = game.split(":", ";", ",")

            // The position 0 is the ID, start from 1
            for (i in 1 until substrings.size) {
                val cube = substrings[i].trim().split(" ")
                val quantity = cube[0].toInt()
                val color = cube[1]

                if (quantity > cubeColors.getValue(color)) {
                    // Invalid Game
                    return@sumOf 0
                }
            }

            // Valid Game
            val id = substrings[0].removePrefix("Game ")
            id.toInt()
        }

    fun part2(input: List<String>): Int =
        input.sumOf { game ->
            val requiredCubes = mutableMapOf(
                "red" to 0,
                "green" to 0,
                "blue" to 0
            )

            val substrings = game.split(":", ";", ",")

            // The position 0 is the ID, start from 1
            for (i in 1 until substrings.size) {
                val cube = substrings[i].trim().split(" ")
                val quantity = cube[0].toInt()
                val color = cube[1]

                requiredCubes[color] = max(quantity, requiredCubes.getValue(color))
            }

            requiredCubes.values.reduce { acc, i -> acc * i }
        }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
