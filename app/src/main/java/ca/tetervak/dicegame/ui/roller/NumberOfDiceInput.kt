package ca.tetervak.dicegame.ui.roller

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import ca.tetervak.dicegame.R
import ca.tetervak.dicegame.ui.theme.Purple500

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
                    style = MaterialTheme.typography.bodyMedium
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
            textStyle = MaterialTheme.typography.titleLarge.copy(
                color = Purple500
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
                        Text(
                            text = option,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                )
            }
        }
    }
}
