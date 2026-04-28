package ca.tetervak.dicegame.repository

import ca.tetervak.dicegame.domain.RollData
import kotlin.random.Random

class RollDataRepository(
    private val random: Random = Random.Default
) {

    fun getRandomRollData(numberOfDice: Int): RollData {
        if (numberOfDice > 0) {
            val list: List<Int> =
                buildList(capacity = numberOfDice) {
                    repeat(numberOfDice) {
                        add(random.nextInt(from = 1, until = 7))
                    }
                }
            return RollData(list)
        } else {
            throw IllegalArgumentException("Illegal numberOfDice = $numberOfDice")
        }
    }
}