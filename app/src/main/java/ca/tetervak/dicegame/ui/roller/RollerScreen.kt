package ca.tetervak.dicegame.ui.roller

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.tetervak.dicegame.R
import ca.tetervak.dicegame.domain.RollData
import ca.tetervak.dicegame.ui.common.DiceGameAbout
import ca.tetervak.dicegame.ui.common.GameTopAppBar
import ca.tetervak.dicegame.ui.theme.DiceGameTheme
import java.time.LocalDateTime
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RollerScreen(
    uiState: RollerUiState,
    onChangeNumberOfDice: (Int) -> Unit,
    onRoll: () -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var showAboutDialog: Boolean by rememberSaveable {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            GameTopAppBar(
                title = stringResource(id = R.string.app_name),
                onHelpButtonClick = { showAboutDialog = true },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onRoll) {
                Icon(
                    imageVector = Icons.Outlined.Refresh,
                    contentDescription = stringResource(R.string.roll_dice)
                )
            }
        },
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 32.dp)
        ) {

            NumberOfDiceInput(
                numberOfDice = uiState.numberOfDice,
                onChange = onChangeNumberOfDice
            )

            when (uiState) {
                is RollerUiState.Rolled -> RolledBody(
                    rollData = uiState.rollData,
                    date = uiState.date
                )

                is RollerUiState.NotRolled -> NotRolledBody(
                    numberOfDice = uiState.numberOfDice
                )
            }

            Button(
                onClick = onRoll, modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(text = stringResource(R.string.roll_button_label, uiState.numberOfDice))
            }
            Button(
                onClick = onReset, modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = stringResource(R.string.reset_button_label))
            }
        }
    }

    if (showAboutDialog) {
        DiceGameAbout(onDismissRequest = { showAboutDialog = false })
    }
}

@Preview(name = "Roller Screen Not Rolled", showBackground = true)
@Composable
fun RollerScreenNotRolledPreview() {
    DiceGameTheme {
        RollerScreen(
            uiState = RollerUiState.NotRolled(2),
            onChangeNumberOfDice = {},
            onRoll = {},
            onReset = {}
        )
    }
}

@Preview(name = "Roller Screen Rolled", showBackground = true)
@Composable
fun RollerScreenRolledPreview() {
    DiceGameTheme {
        RollerScreen(
            uiState = RollerUiState.Rolled(
                rollData = RollData(listOf(2, 3, 4)),
                date = Date()
            ),
            onChangeNumberOfDice = {},
            onRoll = {},
            onReset = {}
        )
    }
}


