package ca.tetervak.dicegame.ui.roller

import androidx.lifecycle.ViewModel
import ca.tetervak.dicegame.domain.RollData
import ca.tetervak.dicegame.repository.RollDataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Date

class RollerViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<RollerUiState> = MutableStateFlow(INIT_STATE)
    val uiState: StateFlow<RollerUiState> = _uiState

    private val rollDataRepository: RollDataRepository = RollDataRepository()

    fun onRoll() {
        val rollData: RollData = rollDataRepository.getRandomRollData(uiState.value.numberOfDice)
        _uiState.value = RollerUiState.Rolled(
            rollData = rollData, date = Date()
        )
    }

    fun onReset() {
        _uiState.value = INIT_STATE
    }

    fun onChangeOfNumberOfDice(newNumberOfDice: Int) {
        _uiState.value = RollerUiState.NotRolled(newNumberOfDice)
    }

    companion object {
        private val INIT_STATE = RollerUiState.NotRolled(numberOfDice = 3)
    }

}