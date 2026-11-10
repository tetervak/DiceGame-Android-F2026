package ca.tetervak.dicegame.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ca.tetervak.dicegame.ui.roller.RollerScreen
import ca.tetervak.dicegame.ui.roller.RollerUiState
import ca.tetervak.dicegame.ui.roller.RollerViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppRootScreen(
    viewModel: RollerViewModel = koinViewModel()
) {
    val state: State<RollerUiState> = viewModel.uiState.collectAsStateWithLifecycle()

    RollerScreen(
        uiState = state.value,
        onChangeNumberOfDice = viewModel::onChangeNumberOfDice,
        onRoll = viewModel::onRoll,
        onReset = viewModel::onReset,
    )
}

