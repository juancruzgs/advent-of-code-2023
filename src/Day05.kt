fun main() {
    data class MapCategory(val source: Long, val destination: Long, val range: Long)

    fun List<String>.writeMapping(list: MutableList<MapCategory>): List<String> {
        run loop@{
            forEach { mapCategory ->
                if (mapCategory.isBlank()) {
                    return@loop
                }

                val (destination, source, range) = mapCategory.split(" ").map { it.toLong() }
                list.add(MapCategory(source, destination, range))
            }
        }

        return drop(list.size)
    }

    fun Long.mapValue(list: List<MapCategory>): Long {
        for (mapCategory in list) {
            if (this >= mapCategory.source && this <= mapCategory.source + mapCategory.range) {
                return mapCategory.destination + (this - mapCategory.source)
            }
        }

        return this
    }

    fun part1(input: List<String>): Long {
        val seeds = input[0].replace("seeds: ", "").split(" ").map { it.toLong() }
        val seedToSoil = mutableListOf<MapCategory>()
        val soilToFertilizer = mutableListOf<MapCategory>()
        val fertilizerToWater = mutableListOf<MapCategory>()
        val waterToLight = mutableListOf<MapCategory>()
        val lightToTemperature = mutableListOf<MapCategory>()
        val temperatureToHumidity = mutableListOf<MapCategory>()
        val humidityToLocation = mutableListOf<MapCategory>()

        input
            .drop(3)
            .writeMapping(seedToSoil)
            .drop(2)
            .writeMapping(soilToFertilizer)
            .drop(2)
            .writeMapping(fertilizerToWater)
            .drop(2)
            .writeMapping(waterToLight)
            .drop(2)
            .writeMapping(lightToTemperature)
            .drop(2)
            .writeMapping(temperatureToHumidity)
            .drop(2)
            .writeMapping(humidityToLocation)

        return seeds.map {
            it
                .mapValue(seedToSoil)
                .mapValue(soilToFertilizer)
                .mapValue(fertilizerToWater)
                .mapValue(waterToLight)
                .mapValue(lightToTemperature)
                .mapValue(temperatureToHumidity)
                .mapValue(humidityToLocation)
                .also { println(it) }
        }.minOf { it }
    }

    fun part2(input: List<String>): Long {
        // TODO Map it in reverse order, starting with location
        val seeds = input[0].replace("seeds: ", "").split(" ").map { it.toLong() }
        val newSeeds = seeds.windowed(2, step = 2).flatMap {
            it[0]..it[0] + (it[1] - 1)
        }
        val newInput = input.toMutableList().apply {
            this[0] = "seeds: ${newSeeds.joinToString(" ")}"
        }
        return part1(newInput)
    }

    val testInput = readInput("Day05_test")
//    check(part1(testInput) == 35L)
    check(part2(testInput) == 46L)

    val input = readInput("Day05")
//    part1(input).println()
    part2(input).println()
}
