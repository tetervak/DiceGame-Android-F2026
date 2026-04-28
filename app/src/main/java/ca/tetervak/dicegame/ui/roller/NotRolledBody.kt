package ca.tetervak.dicegame.ui.roller

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.tetervak.dicegame.R

@Composable
fun NotRolledBody(
    numberOfDice: Int,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(top = 40.dp, bottom = 40.dp)
    ) {
        Text(
            text = pluralStringResource(id = R.plurals.not_rolled_message, count = numberOfDice),
            fontSize = 20.sp,
            color = colorResource(id = R.color.green_500)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NotRolledBodyPreview() {
    NotRolledBody(numberOfDice = 3)
}