package ca.tetervak.dicegame.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import ca.tetervak.dicegame.R
import ca.tetervak.dicegame.ui.theme.DiceGameTheme

@Composable
fun DiceGameAbout(onDismissRequest: () -> Unit) =
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(stringResource(R.string.about_dice_game)) },
        text = {
            Text(
                text = stringResource(R.string.programming_example),
                fontSize = 18.sp
            )
        },
        confirmButton = {
            TextButton(
                onClick = onDismissRequest
            ) {
                Text(stringResource(R.string.ok))
            }
        }
    )

@Preview(name = "Dice Game About Dialog", showBackground = true)
@Composable
fun DiceGameAboutPreview() {
    DiceGameTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            DiceGameAbout(onDismissRequest = {})
        }
    }
}
