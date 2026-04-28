package ca.tetervak.dicegame.ui.roller

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ca.tetervak.dicegame.R
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

@Composable
fun timeStampFormat(date: Date): String {
    val dateAndTimeFormatter =
        DateTimeFormatter.ofPattern(stringResource(R.string.timestamp_pattern))
    return date.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()
        .format(dateAndTimeFormatter)
}