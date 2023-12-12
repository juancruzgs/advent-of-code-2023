import kotlin.math.pow

fun main() {
    fun part1(input: List<String>): Int =
        input.sumOf { card ->
            val cardRegex = "Card \\d: ".toRegex()
            val numbersList = card.replace(cardRegex, "").split("|")
            val winningNumbers = numbersList[0].split(" ").filter { it.isNotBlank() }
            val myNumbers = numbersList[1].split(" ").filter { it.isNotBlank() }

            val matchesCount = myNumbers.filter {
                winningNumbers.contains(it)
            }.size

            return@sumOf 2.0.pow(matchesCount - 1).toInt()
        }

    fun part2(input: List<String>): Int {
        val cards = mutableMapOf<Int, Int>()
        input.forEach {card ->
            val cardRegex = "Card\\s+(\\d+): ".toRegex()
            val id = cardRegex.find(card)?.groups?.get(1)?.value?.toInt() ?: 0 // Should never be 0
            val numbersList = card.replace(cardRegex, "").split("|")
            val winningNumbers = numbersList[0].split(" ").filter { it.isNotBlank() }
            val myNumbers = numbersList[1].split(" ").filter { it.isNotBlank() }

            val matchesCount = myNumbers.filter {
                winningNumbers.contains(it)
            }.size

            // Add current card
            cards[id] = cards.getOrDefault(id, 0) + 1

            // Loop each copy
            for (j in 1..cards.getOrDefault(id, 0)) {
                // Loop each card to add
                for (i in 1..matchesCount) {
                    if (i > input.size) {
                        // No cards remaining
                        return@forEach
                    }

                    // Add copies
                    cards[i + id] = cards.getOrDefault(i + id, 0) + 1
                }
            }
        }

        return cards.values.reduce { acc, i -> acc + i }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
