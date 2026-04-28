package ca.tetervak.dicegame.ui.common

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ca.tetervak.dicegame.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameTopAppBar(
    title: String,
    onHelpButtonClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) = CenterAlignedTopAppBar(
    colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer,
        navigationIconContentColor = MaterialTheme.colorScheme.primary,
        titleContentColor = MaterialTheme.colorScheme.primary,
        actionIconContentColor = MaterialTheme.colorScheme.primary
    ),
    title = {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall
        )
    },
    actions = {
        IconButton(
            onClick = onHelpButtonClick,
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_help_outline_24),
                contentDescription = stringResource(R.string.about)
            )
        }
    },
    scrollBehavior = scrollBehavior,
)
