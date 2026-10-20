package ca.tetervak.dicegame.fake

import ca.tetervak.dicegame.data.RollDataRepository
import ca.tetervak.dicegame.model.RollData

class FakeRollDataRepository: RollDataRepository {
    override fun getRandomRollData(numberOfDice: Int): RollData {
        // return a fake roll data of all ones for testing
        return RollData(List(numberOfDice) { 1 })
    }
}