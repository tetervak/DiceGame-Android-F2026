package ca.tetervak.dicegame.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import ca.tetervak.dicegame.ui.roller.RollerScreen
import ca.tetervak.dicegame.ui.roller.RollerUiState
import ca.tetervak.dicegame.ui.roller.RollerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppRootScreen(
    viewModel: RollerViewModel = viewModel()
) {
    val state: State<RollerUiState> = viewModel.uiState.collectAsState()

    RollerScreen(
        uiState = state.value,
        onChangeNumberOfDice = viewModel::onChangeNumberOfDice,
        onRoll = viewModel::onRoll,
        onReset = viewModel::onReset,
    )
}

