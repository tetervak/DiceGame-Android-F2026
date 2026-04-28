package ca.tetervak.dicegame.ui.roller

import ca.tetervak.dicegame.domain.RollData
import java.util.Date

sealed interface RollerUiState {

    val numberOfDice: Int

    data class Rolled(
        val rollData: RollData,
        val date: Date,
    ) : RollerUiState {
        override val numberOfDice: Int
            get() = rollData.numberOfDice
    }

    data class NotRolled(
        override val numberOfDice: Int
    ) : RollerUiState
}