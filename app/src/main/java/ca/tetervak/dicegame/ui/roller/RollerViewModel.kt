package ca.tetervak.dicegame.ui.roller

import androidx.lifecycle.ViewModel
import ca.tetervak.dicegame.domain.RollData
import ca.tetervak.dicegame.repository.RollDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Date

@HiltViewModel
class RollerViewModel @Inject constructor(
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