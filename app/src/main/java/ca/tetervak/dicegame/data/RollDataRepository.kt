package ca.tetervak.dicegame.data

import ca.tetervak.dicegame.model.RollData
import kotlin.random.Random

interface RollDataRepository {
    fun getRandomRollData(numberOfDice: Int): RollData
}

class LocalRollDataRepository(
    private val random: Random = Random.Default
) : RollDataRepository {

    override fun getRandomRollData(numberOfDice: Int): RollData {
        if (numberOfDice <= 0) throw IllegalArgumentException("numberOfDice must be > 0")
        return RollData(List(numberOfDice) { random.nextInt(1, 7) })
    }
}