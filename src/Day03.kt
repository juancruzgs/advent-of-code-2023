fun main() {

    fun part1(input: List<String>): Int =
        input.mapIndexed { index, line ->
            val symbolsRegex = "[^\\w.]+".toRegex()
            val numbersRegex = "\\d+".toRegex()
            val numbers = numbersRegex.findAll(line)

            numbers.sumOf { number ->
                val previousPosition = number.range.first.minus(1).coerceAtLeast(0)
                val nextPosition = number.range.last.plus(1).coerceAtMost(line.length - 1)

                // Check current line
                if (symbolsRegex.matches(line[previousPosition].toString()) ||
                    symbolsRegex.matches(line[nextPosition].toString())
                ) {
                    return@sumOf number.value.toInt()
                }

                // Check previous line
                if (symbolsRegex.containsMatchIn(
                        input.getOrNull(index - 1)
                            ?.subSequence(previousPosition..nextPosition) ?: ""
                    )
                ) {
                    return@sumOf number.value.toInt()
                }

                // Check next line
                if (symbolsRegex.containsMatchIn(
                        input.getOrNull(index + 1)
                            ?.subSequence(previousPosition..nextPosition) ?: ""
                    )
                ) {
                    return@sumOf number.value.toInt()
                }

                return@sumOf 0
            }
        }.sumOf { it }

    fun part2(input: List<String>): Int =
        input.mapIndexed { index, line ->
            val numbersRegex = "\\d+".toRegex()
            val gearMatches = "\\*".toRegex().findAll(line)
            gearMatches.sumOf { gearMatch ->
                val previousPosition = gearMatch.range.first.minus(1).coerceAtLeast(0)
                val nextPosition = gearMatch.range.last.plus(1).coerceAtMost(line.length - 1)
                val partNumbers = mutableListOf<Int>()

                // Part number in same line
                numbersRegex
                    .findAll(line)
                    .filter { previousPosition == it.range.last || nextPosition == it.range.first }
                    .forEach {
                        partNumbers.add(it.value.toInt())
                    }

                // Part number in previous line
                numbersRegex
                    .findAll(input.getOrNull(index - 1).toString())
                    .filter { previousPosition <= it.range.last && nextPosition >= it.range.first }
                    .forEach {
                        partNumbers.add(it.value.toInt())
                    }

                // Part number in next line
                numbersRegex
                    .findAll(input.getOrNull(index + 1).toString())
                    .filter { previousPosition <= it.range.last && nextPosition >= it.range.first }
                    .forEach {
                        partNumbers.add(it.value.toInt())
                    }

                // Check that matches exactly two part numbers
                if (partNumbers.size == 2) {
                    return@sumOf partNumbers[0] * partNumbers[1]
                } else {
                    return@sumOf 0
                }
            }
        }.sumOf { it }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
