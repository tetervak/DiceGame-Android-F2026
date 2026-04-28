package ca.tetervak.dicegame.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ca.tetervak.dicegame.R
import ca.tetervak.dicegame.ui.common.DiceGameAbout
import ca.tetervak.dicegame.ui.roller.NotRolledBody
import ca.tetervak.dicegame.ui.roller.RolledBody
import ca.tetervak.dicegame.ui.common.GameTopAppBar
import ca.tetervak.dicegame.ui.roller.RollerUiState
import ca.tetervak.dicegame.ui.roller.RollerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppRootScreen(
    viewModel: RollerViewModel = viewModel()
) {
    val state: State<RollerUiState> = viewModel.uiState.collectAsState()
    val uiState: RollerUiState = state.value
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
            FloatingActionButton(onClick = viewModel::onRoll) {
                Icon(
                    imageVector = Icons.Outlined.Refresh,
                    contentDescription = stringResource(R.string.roll_dice)
                )
            }
        },
        modifier = Modifier
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
                onChange = viewModel::onChangeOfNumberOfDice
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
                onClick = viewModel::onRoll, modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(text = stringResource(R.string.roll_button_label, uiState.numberOfDice))
            }
            Button(
                onClick = viewModel::onReset, modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = stringResource(R.string.reset_button_label))
            }
        }
    }

    if (showAboutDialog) {
        DiceGameAbout(onDismissRequest = { showAboutDialog = false })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberOfDiceInput(
    numberOfDice: Int,
    onChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectExpanded: Boolean by remember { mutableStateOf(false) }
    val selectOptions = stringArrayResource(id = R.array.choices_of_numbers_of_dice)
    val selectedText = selectOptions[numberOfDice - 1]

    ExposedDropdownMenuBox(
        expanded = selectExpanded,
        onExpandedChange = { selectExpanded = it },
        modifier = modifier
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedText,
            onValueChange = { },
            label = {
                Text(
                    text = stringResource(R.string.number_of_dice),
                    fontSize = 14.sp
                )
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = selectExpanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            textStyle = TextStyle.Default.copy(
                fontSize = 20.sp,
                color = colorResource(id = R.color.purple_500)
            ),
            modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
        )
        ExposedDropdownMenu(
            expanded = selectExpanded,
            onDismissRequest = {
                selectExpanded = false
            }
        ) {
            selectOptions.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        selectExpanded = false
                        onChange(selectOptions.indexOf(option) + 1)
                    },
                    text = {
                        Text(text = option, fontSize = 20.sp)
                    }
                )
            }
        }
    }
}

