package com.sench.financetracker.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ShowChart
import androidx.compose.material.icons.outlined.MonetizationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import com.sench.financetracker.R
import com.sench.financetracker.ui.component.model.FinancialActionModel
import com.sench.financetracker.ui.component.model.FinancialActionTypeModel
import com.sench.financetracker.ui.theme.greenContainer
import com.sench.financetracker.ui.theme.onGreenContainer
import com.sench.financetracker.ui.theme.onRedContainer
import com.sench.financetracker.ui.theme.redContainer
import com.sench.financetracker.ui.util.PreviewTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinancialActionDialog(
    financialActionModel: FinancialActionModel,
    onConfirm: () -> Unit
) {
    var showDialog by remember { mutableStateOf(true) }
    var isExpense by remember { mutableStateOf(financialActionModel.isExpense()) }

    if (showDialog) {
        AnimatedVisibility(visible = showDialog) {
            AlertDialog(
                icon = {
                    Icon(
                        Icons.Outlined.MonetizationOn,
                        contentDescription = null
                    )
                },
                title = {
                    Text(
                        text = financialActionModel.title,
                        maxLines = 2
                    )
                },
                text = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = financialActionModel.description,
                            maxLines = 5
                        )
                        Spacer(modifier = Modifier.padding(top = 24.dp))
                        SingleChoiceSegmentedButtonRow {
                            SegmentedButton(
                                selected = isExpense,
                                onClick = { isExpense = true },
                                shape = SegmentedButtonDefaults.itemShape(
                                    index = 0,
                                    count = 2
                                ),
                                colors = SegmentedButtonDefaults.colors(
                                    activeContainerColor = MaterialTheme.colorScheme.redContainer,
                                    activeContentColor = MaterialTheme.colorScheme.onRedContainer
                                ),
                                icon = {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Outlined.ShowChart,
                                        contentDescription = null,
                                        modifier = Modifier.scale(
                                            scaleX = -1f,
                                            scaleY = 1f
                                        )
                                    )
                                },
                            ) {
                                Text(text = stringResource(id = R.string.common_expense))
                            }

                            SegmentedButton(
                                selected = !isExpense,
                                onClick = { isExpense = false },
                                shape = SegmentedButtonDefaults.itemShape(
                                    index = 1,
                                    count = 2
                                ),
                                colors = SegmentedButtonDefaults.colors(
                                    activeContainerColor = MaterialTheme.colorScheme.greenContainer,
                                    activeContentColor = MaterialTheme.colorScheme.onGreenContainer
                                ),
                                icon = {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Outlined.ShowChart,
                                        contentDescription = null
                                    )
                                }
                            ) {
                                Text(text = stringResource(id = R.string.common_income))
                            }
                        }
                    }
                },
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showDialog = false
                            onConfirm()
                        }
                    ) {
                        Text(text = stringResource(id = android.R.string.ok))
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showDialog = false }
                    ) {
                        Text(text = stringResource(id = android.R.string.cancel))
                    }
                }
            )
        }
    }
}

@PreviewTheme
@Composable
fun PreviewFinancialActionDialog() {
    PreviewTheme {
        val action = FinancialActionModel(
            title = LoremIpsum().values.joinToString(),
            description = LoremIpsum().values.joinToString(),
            sum = "1000",
            type = FinancialActionTypeModel.EXPENSE
        )

        FinancialActionDialog(
            financialActionModel = action,
            onConfirm = {}
        )
    }
}
