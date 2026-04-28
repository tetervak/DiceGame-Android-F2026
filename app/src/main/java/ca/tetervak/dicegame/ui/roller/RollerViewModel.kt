package ca.tetervak.dicegame.ui.roller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.tetervak.dicegame.repository.PreferencesRepository
import ca.tetervak.dicegame.repository.RollDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.Date

@HiltViewModel
class RollerViewModel @Inject constructor(
    private val rollDataRepository: RollDataRepository,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<RollerUiState> = MutableStateFlow(INIT_STATE)
    val uiState: StateFlow<RollerUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                preferencesRepository.numberOfDiceFlow,
                rollDataRepository.lastRollFlow
            ) { numberOfDice, savedRoll ->
                if (savedRoll != null && savedRoll.rollData.numberOfDice == numberOfDice) {
                    RollerUiState.Rolled(savedRoll.rollData, Date(savedRoll.timestamp))
                } else {
                    RollerUiState.NotRolled(numberOfDice)
                }
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun onRoll() {
        viewModelScope.launch {
            rollDataRepository.saveRandomRollData(uiState.value.numberOfDice)
        }
    }

    fun onReset() {
        viewModelScope.launch {
            rollDataRepository.clearRollData()
            preferencesRepository.saveNumberOfDice(PreferencesRepository.DEFAULT_NUMBER_OF_DICE)
        }
    }

    fun onChangeNumberOfDice(newNumberOfDice: Int) {
        if (newNumberOfDice != uiState.value.numberOfDice) {
            viewModelScope.launch {
                rollDataRepository.clearRollData()
                preferencesRepository.saveNumberOfDice(newNumberOfDice)
            }
        }
    }

    companion object {
        private val INIT_STATE = RollerUiState.NotRolled(
            numberOfDice = PreferencesRepository.DEFAULT_NUMBER_OF_DICE
        )
    }

}
