package com.sench.financetracker.ui.component.dialog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ShowChart
import androidx.compose.material.icons.outlined.MonetizationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import com.sench.financetracker.R
import com.sench.financetracker.data.financialaction.FinancialAction
import com.sench.financetracker.data.financialaction.FinancialActionType
import com.sench.financetracker.ui.component.dialog.FinancialActionDialogModel.Companion.toFinancialActionDialogModel
import com.sench.financetracker.ui.theme.green
import com.sench.financetracker.ui.theme.greenContainer
import com.sench.financetracker.ui.theme.onGreenContainer
import com.sench.financetracker.ui.theme.onRedContainer
import com.sench.financetracker.ui.theme.red
import com.sench.financetracker.ui.theme.redContainer
import com.sench.financetracker.ui.util.PreviewTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinancialActionDialog(
    showDialog: Boolean,
    dialogState: FinancialActionDialogState = FinancialActionDialogState.Add,
    setShowDialog: (Boolean) -> Unit,
    onConfirm: (FinancialAction, FinancialActionDialogState) -> Unit
) {
    var model by remember(dialogState) {
        mutableStateOf(
            when (dialogState) {
                FinancialActionDialogState.Add -> FinancialActionDialogModel()
                is FinancialActionDialogState.Edit ->
                    dialogState.financialAction.toFinancialActionDialogModel()
            }
        )
    }

    val isExpense by remember(dialogState) {
        derivedStateOf {
            model.isExpense()
        }
    }

    AnimatedVisibility(
        visible = showDialog,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        AlertDialog(
            icon = {
                Icon(
                    Icons.Outlined.MonetizationOn,
                    contentDescription = null
                )
            },
            title = {
                Text(
                    text = stringResource(
                        id = if (dialogState == FinancialActionDialogState.Add) {
                            R.string.title_add_record
                        } else {
                            R.string.title_edit_record
                        }
                    ),
                    maxLines = 1
                )
            },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    OutlinedTextField(
                        label = { Text(text = stringResource(id = R.string.title_title)) },
                        value = model.title ?: "",
                        onValueChange = { text ->
                            model = model.copy(title = text)
                        },
                        singleLine = true
                    )
                    OutlinedTextField(
                        label = { Text(text = stringResource(id = R.string.title_amount)) },
                        modifier = Modifier.padding(top = 8.dp),
                        value = model.amount ?: "",
                        onValueChange = { text ->
                            model = model.copy(amount = text)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true
                    )
                    OutlinedTextField(
                        label = { Text(text = stringResource(id = R.string.title_description)) },
                        modifier = Modifier.padding(top = 8.dp),
                        value = model.description ?: "",
                        onValueChange = { text ->
                            model = model.copy(description = text)
                        },
                        singleLine = false,
                        maxLines = 4
                    )
                    Spacer(modifier = Modifier.padding(top = 24.dp))
                    SingleChoiceSegmentedButtonRow {
                        SegmentedButton(
                            selected = isExpense ?: false,
                            onClick = {
                                model = model.copy(type = FinancialActionType.EXPENSE)
                            },
                            shape = SegmentedButtonDefaults.itemShape(
                                index = 0,
                                count = 2
                            ),
                            colors = SegmentedButtonDefaults.colors(
                                activeContainerColor = MaterialTheme.colorScheme.redContainer,
                                activeContentColor = MaterialTheme.colorScheme.onRedContainer,
                                inactiveContentColor = MaterialTheme.colorScheme.red
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
                            selected = isExpense?.let { !it } ?: false,
                            onClick = {
                                model = model.copy(type = FinancialActionType.INCOME)
                            },
                            shape = SegmentedButtonDefaults.itemShape(
                                index = 1,
                                count = 2
                            ),
                            colors = SegmentedButtonDefaults.colors(
                                activeContainerColor = MaterialTheme.colorScheme.greenContainer,
                                activeContentColor = MaterialTheme.colorScheme.onGreenContainer,
                                inactiveContentColor = MaterialTheme.colorScheme.green
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
            onDismissRequest = { setShowDialog(false) },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (model.isValid()) {
                            setShowDialog(false)
                            onConfirm(
                                model.toFinancialAction(),
                                dialogState
                            )
                        }
                    }
                ) {
                    Text(text = stringResource(id = android.R.string.ok))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { setShowDialog(false) }
                ) {
                    Text(text = stringResource(id = android.R.string.cancel))
                }
            }
        )
    }
}

@PreviewTheme
@Composable
fun PreviewFinancialActionDialog() {
    PreviewTheme {
        val action = FinancialAction(
            title = LoremIpsum().values.joinToString(),
            description = LoremIpsum().values.joinToString(),
            amount = 0.0,
            type = FinancialActionType.EXPENSE
        )

        FinancialActionDialog(
            dialogState = FinancialActionDialogState.Edit(action),
            onConfirm = { _, _ -> },
            showDialog = true,
            setShowDialog = {}
        )
    }
}
