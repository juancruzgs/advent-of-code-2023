fun main() {
    fun part1(input: List<String>): Int =
        input.sumOf { value ->
            // If the string does not contain any numbers the result will be "00"
            val first = value.firstOrNull { it.isDigit() } ?: '0'
            val last = value.lastOrNull { it.isDigit() } ?: '0'
            "$first$last".toInt()
        }

    val numbers = mapOf(
        "one" to "1",
        "two" to "2",
        "three" to "3",
        "four" to "4",
        "five" to "5",
        "six" to "6",
        "seven" to "7",
        "eight" to "8",
        "nine" to "9"
    )

    fun part2(input: List<String>): Int =
        input.sumOf { string ->
            // Find either written numbers or literal numbers
            val stringsToFind = numbers.keys + numbers.values
            val firstOccurrence = string.findAnyOf(stringsToFind)
            val lastOccurrence = string.findLastAnyOf(stringsToFind)

            // If it's not found in the strings map then it could've been a literal number
            val first = numbers[firstOccurrence?.second] ?: firstOccurrence?.second ?: '0'
            val last = numbers[lastOccurrence?.second] ?: lastOccurrence?.second ?: '0'
            "$first$last".toInt()
        }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)
    val testInput2 = readInput("Day01_test2")
    check(part2(testInput2) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
