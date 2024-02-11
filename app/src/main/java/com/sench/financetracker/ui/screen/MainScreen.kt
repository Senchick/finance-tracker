package com.sench.financetracker.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ShowChart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.sench.financetracker.R
import com.sench.financetracker.data.financialaction.FinancialAction
import com.sench.financetracker.data.financialaction.FinancialActionType
import com.sench.financetracker.ui.theme.green
import com.sench.financetracker.ui.theme.onGreen
import com.sench.financetracker.ui.theme.onRed
import com.sench.financetracker.ui.theme.red
import com.sench.financetracker.ui.util.PreviewTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    when (state) {
        MainUiState.Loading -> LoadingScreen()
        is MainUiState.Error -> ErrorScreen(state as MainUiState.Error)
        is MainUiState.Success -> SuccessScreen(state as MainUiState.Success)
    }
}

@Composable
private fun LoadingScreen() {

}

@Composable
private fun EmptyScreen() {
    Text(text = "Пусто")
}

@Composable
private fun ErrorScreen(state: MainUiState.Error) {

}

@Composable
private fun SuccessScreen(state: MainUiState.Success) {
    val lazyPagingItems = state.financialActionsFlow.collectAsLazyPagingItems()

    Column(modifier = Modifier.fillMaxSize()) {
        if (lazyPagingItems.itemCount == 0) {
            EmptyScreen()
        } else {
            LazyColumn {
                items(
                    lazyPagingItems.itemCount,
                    key = lazyPagingItems.itemKey { it.id!! }
                ) { index ->
                    val item = lazyPagingItems[index]
                    Text("Item is $item")
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(modifier = Modifier.padding(16.dp)) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.green,
                    contentColor = MaterialTheme.colorScheme.onGreen
                )

            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ShowChart,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp)
                )

                Text(text = stringResource(id = R.string.common_income))
            }

            Spacer(modifier = Modifier.padding(end = 16.dp))

            Button(
                modifier = Modifier.weight(1f),
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.red,
                    contentColor = MaterialTheme.colorScheme.onRed
                )

            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ShowChart,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .scale(
                            scaleX = -1f,
                            scaleY = 1f
                        )
                )

                Text(text = stringResource(id = R.string.common_expense))
            }
        }
    }
}

@Composable
@PreviewTheme
private fun PreviewSuccessScreenEmpty() {
    PreviewTheme {
        SuccessScreen(
            MainUiState.Success(
                flowOf(
                    PagingData.from(
                        listOf(
                            FinancialAction(
                                id = 0,
                                sum = 100.0,
                                type = FinancialActionType.INCOME,
                                title = "cock",
                                description = "ne cock",
                                position = 0
                            )
                        )
                    )
                )
            )
        )
    }
}