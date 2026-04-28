package ca.tetervak.dicegame.ui.roller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.tetervak.dicegame.domain.RollData
import ca.tetervak.dicegame.repository.PreferencesRepository
import ca.tetervak.dicegame.repository.RollDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
            preferencesRepository.numberOfDiceFlow.collect { numberOfDice ->
                _uiState.update { RollerUiState.NotRolled(numberOfDice) }
            }
        }
    }

    fun onRoll() {
        val rollData: RollData = rollDataRepository.getRandomRollData(uiState.value.numberOfDice)
        _uiState.update {
            RollerUiState.Rolled(
                rollData = rollData, date = Date()
            )
        }
    }

    fun onReset() {
        if (uiState.value.numberOfDice != PreferencesRepository.DEFAULT_NUMBER_OF_DICE){
            viewModelScope.launch {
                preferencesRepository.saveNumberOfDice(PreferencesRepository.DEFAULT_NUMBER_OF_DICE)
            }
        } else if (uiState.value is RollerUiState.Rolled) {
            _uiState.update { RollerUiState.NotRolled(uiState.value.numberOfDice) }
        }
    }

    fun onChangeNumberOfDice(newNumberOfDice: Int) {
        if (newNumberOfDice != uiState.value.numberOfDice) {
            viewModelScope.launch {
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
