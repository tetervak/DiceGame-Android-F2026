package ca.tetervak.dicegame.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import ca.tetervak.dicegame.ui.roller.RollerScreen
import ca.tetervak.dicegame.ui.roller.RollerUiState
import ca.tetervak.dicegame.ui.roller.RollerViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppRootScreen(
    viewModel: RollerViewModel = koinViewModel()
) {
    val state: State<RollerUiState> = viewModel.uiState.collectAsState()

    RollerScreen(
        uiState = state.value,
        onChangeNumberOfDice = viewModel::onChangeNumberOfDice,
        onRoll = viewModel::onRoll,
        onReset = viewModel::onReset,
    )
}

