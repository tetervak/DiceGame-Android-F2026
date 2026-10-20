package ca.tetervak.dicegame.ui.roller

import androidx.lifecycle.ViewModel
import ca.tetervak.dicegame.model.RollData
import ca.tetervak.dicegame.data.LocalRollDataRepository
import ca.tetervak.dicegame.data.RollDataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

class RollerViewModel(
    private val rollDataRepository: RollDataRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<RollerUiState> = MutableStateFlow(INIT_STATE)
    val uiState: StateFlow<RollerUiState> = _uiState

    fun onRoll() {
        val rollData: RollData = rollDataRepository.getRandomRollData(uiState.value.numberOfDice)
        _uiState.value = RollerUiState.Rolled(
            rollData = rollData, date = Date()
        )
    }

    fun onReset() {
        _uiState.value = INIT_STATE
    }

    fun onChangeNumberOfDice(newNumberOfDice: Int) {
        if(newNumberOfDice != _uiState.value.numberOfDice){
            _uiState.value = RollerUiState.NotRolled(newNumberOfDice)
        }
    }

    companion object {
        private val INIT_STATE = RollerUiState.NotRolled(numberOfDice = 3)
    }

}