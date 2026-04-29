package ca.tetervak.dicegame.repository

import ca.tetervak.dicegame.domain.RollData
import kotlin.random.Random

class RollDataRepository(
    private val random: Random = Random.Default
) {

    fun getRandomRollData(numberOfDice: Int): RollData {
        if (numberOfDice <= 0) throw IllegalArgumentException("numberOfDice must be > 0")
        return RollData(List(numberOfDice) { random.nextInt(1, 7) })
    }
}