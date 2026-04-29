package ca.tetervak.dicegame.repository

import androidx.datastore.core.DataStore
import ca.tetervak.dicegame.data.RollDataProto
import ca.tetervak.dicegame.domain.RollData
import ca.tetervak.dicegame.domain.SavedRoll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.random.Random

class RollDataRepository(
    private val random: Random,
    private val dataStore: DataStore<RollDataProto>
) {
    val lastRollFlow: Flow<SavedRoll?> = dataStore.data.map { proto ->
        if (proto.valuesList.isEmpty()) {
            null
        } else {
            SavedRoll(RollData(proto.valuesList), proto.timestamp)
        }
    }

    suspend fun saveRollData(rollData: RollData, timestamp: Long) {
        dataStore.updateData { currentProto ->
            currentProto.toBuilder()
                .clearValues()
                .addAllValues(rollData.values)
                .setTimestamp(timestamp)
                .build()
        }
    }

    suspend fun clearRollData() {
        dataStore.updateData { currentProto ->
            currentProto.toBuilder()
                .clearValues()
                .setTimestamp(0L)
                .build()
        }
    }

    fun getRandomRollData(numberOfDice: Int): RollData {
        if (numberOfDice <= 0) throw IllegalArgumentException("numberOfDice must be > 0")
        return RollData(List(numberOfDice) { random.nextInt(1, 7) })
    }

    suspend fun saveRandomRollData(numberOfDice: Int) {
        val rollData = getRandomRollData(numberOfDice)
        saveRollData(rollData, System.currentTimeMillis())
    }
}
