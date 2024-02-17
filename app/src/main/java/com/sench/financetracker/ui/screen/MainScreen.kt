package com.sench.financetracker.ui.screen

import android.annotation.TargetApi
import android.os.Build
import android.view.HapticFeedbackConstants
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sench.financetracker.R
import com.sench.financetracker.data.financialaction.FinancialAction
import com.sench.financetracker.data.financialaction.FinancialActionType
import com.sench.financetracker.data.financialaction.util.swap
import com.sench.financetracker.ui.component.FinancialActionCard
import com.sench.financetracker.ui.component.dialog.FinancialActionDialog
import com.sench.financetracker.ui.component.dialog.FinancialActionDialogState
import com.sench.financetracker.ui.component.model.FinancialActionModel.Companion.toModel
import com.sench.financetracker.ui.util.PreviewTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyColumnState

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    when (state) {
        MainUiState.Loading -> LoadingScreen()
        is MainUiState.Error -> ErrorScreen(state as MainUiState.Error)
        is MainUiState.Success -> SuccessScreen(
            state as MainUiState.Success,
            onRemoveItem = { financialAction ->
                viewModel.sendIntent(MainIntent.DeleteFinancialAction(financialAction))
            },
            onUpdateItem = { financialAction ->
                viewModel.sendIntent(MainIntent.UpdateFinancialAction(financialAction))
            },
            onAddItem = { financialAction ->
                viewModel.sendIntent(MainIntent.CreateFinancialAction(financialAction))
            },
            onUpdateFinancialActions = { from, to ->
                viewModel.sendIntent(
                    MainIntent.UpdateFinancialActionsPositions(
                        from,
                        to
                    )
                )
            }
        )
    }
}

@Composable
private fun LoadingScreen() {
    Text(text = "Loading")
}

@Composable
private fun ColumnScope.EmptyScreen(onShowDialogClick: () -> Unit) {
    Text(
        text = stringResource(id = R.string.title_list_empty),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.onSurface,
        textAlign = TextAlign.Center
    )

    Text(
        text = stringResource(id = R.string.title_list_empty_description),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center
    )

    Button(
        onClick = { onShowDialogClick() },
        modifier = Modifier
            .padding(top = 16.dp)
            .align(alignment = Alignment.CenterHorizontally)
    ) {
        Icon(
            imageVector = Icons.Outlined.Add,
            contentDescription = null,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(text = stringResource(id = R.string.title_add_record))
    }
}

@Composable
private fun ErrorScreen(state: MainUiState.Error) {
    Text(text = "Error: ${state.error.message}")
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
@TargetApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
private fun SuccessScreen(
    state: MainUiState.Success,
    onRemoveItem: (FinancialAction) -> Unit,
    onAddItem: (FinancialAction) -> Unit,
    onUpdateItem: (FinancialAction) -> Unit,
    onUpdateFinancialActions: (from: FinancialAction, to: FinancialAction) -> Unit
) {
    val view = LocalView.current
    val financialActions = remember {
        state.financialActions.toMutableStateList()
    }

    val actionsIsEmpty by remember {
        derivedStateOf { financialActions.isEmpty() }
    }

    var financialActionsPositionsPair by remember {
        mutableStateOf<Pair<FinancialAction, FinancialAction>?>(null)
    }
    val coroutineScope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    var dialogState by remember {
        mutableStateOf<FinancialActionDialogState?>(null)
    }
    var showDialog by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(state.financialActions.hashCode()) {
        if (state.financialActions != financialActions) {
            val conditionMore = state.financialActions.size > financialActions.size

            financialActions.clear()
            financialActions.addAll(state.financialActions)

            if (conditionMore) {
                coroutineScope.launch {
                    delay(500)
                    lazyListState.animateScrollToItem(0)
                }
            }
        }
    }

    if (dialogState != null) {
        FinancialActionDialog(
            dialogState = dialogState!!,
            showDialog = showDialog,
            setShowDialog = { showDialog = it },
            onConfirm = { financialAction, financialActionDialogState ->
                when (financialActionDialogState) {
                    FinancialActionDialogState.Add -> onAddItem(financialAction)
                    is FinancialActionDialogState.Edit -> onUpdateItem(financialAction)
                }
            }
        )
    }

    val openAddDialog = {
        showDialog = true
        dialogState = FinancialActionDialogState.Add
    }

    val firstVisible by remember {
        derivedStateOf {
            lazyListState.canScrollBackward
        }
    }

    val animatedSum by animateFloatAsState(
        targetValue = state.sum.toFloat(),
        label = "animate sum"
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Column {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = stringResource(id = R.string.title_balance) + ": $animatedSum",
                    style = MaterialTheme.typography.headlineLarge
                )
                AnimatedVisibility(
                    firstVisible,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    HorizontalDivider(
                        modifier = Modifier
                            .height(1.dp)
                            .background(MaterialTheme.colorScheme.onSurfaceVariant)
                    )
                }
            }
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = !actionsIsEmpty,
                enter = slideInHorizontally(
                    initialOffsetX = {
                        (it * 1.2).toInt()
                    },
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                ),
                exit = slideOutHorizontally(
                    targetOffsetX = {
                        (it * 1.2).toInt()
                    },
                    animationSpec = spring(stiffness = Spring.StiffnessVeryLow)
                )
            ) {
                ExtendedFloatingActionButton(
                    text = { Text(text = stringResource(id = R.string.title_add_record)) },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Add,
                            contentDescription = null
                        )
                    },
                    onClick = openAddDialog
                )
            }
        }
    ) { paddingValues ->

        AnimatedVisibility(
            visible = actionsIsEmpty,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
            ) {
                EmptyScreen(onShowDialogClick = openAddDialog)
            }
        }

        AnimatedVisibility(
            visible = !actionsIsEmpty,
            enter = slideInVertically(animationSpec = spring(stiffness = Spring.StiffnessLow)),
            exit = fadeOut()
        ) {
            /**
             * TODO: Баг с перетаскиванием, position иногда может дублироваться при перетаскивании,
             * из-за этого сохранение позиции работает некорректно
             */
            val reorderableLazyColumnState = rememberReorderableLazyColumnState(lazyListState) { from, to ->
                financialActions.swap(
                    to.index,
                    from.index
                ) { financialActionFrom, financialActionTo ->
                    financialActionsPositionsPair = Pair(
                        first = financialActionFrom,
                        second = financialActionTo
                    )
                }

                view.performHapticFeedback(HapticFeedbackConstants.SEGMENT_FREQUENT_TICK)
            }

            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                state = lazyListState
            ) {
                items(
                    items = financialActions,
                    key = { it.id }
                ) { item ->
                    ReorderableItem(
                        reorderableLazyListState = reorderableLazyColumnState,
                        key = item.id
                    ) { isDragging ->
                        FinancialActionCard(
                            isElevated = isDragging,
                            modifier = Modifier
                                .longPressDraggableHandle(
                                    onDragStopped = {
                                        if (financialActionsPositionsPair != null) {
                                            onUpdateFinancialActions(
                                                financialActionsPositionsPair!!.first,
                                                financialActionsPositionsPair!!.second
                                            )
                                        }

                                        view.performHapticFeedback(HapticFeedbackConstants.GESTURE_END)
                                    },
                                    onDragStarted = {
                                        view.performHapticFeedback(HapticFeedbackConstants.DRAG_START)
                                    }
                                )
                                .animateItemPlacement(),
                            financialAction = item.toModel(),
                            onActionOpenClick = {
                                showDialog = true
                                dialogState = FinancialActionDialogState.Edit(financialAction = item)
                            },
                            onActionRemoveClick = { onRemoveItem(item) }
                        )
                    }
                }

                val extendedFABHeight = 56
                val extendedFABPaddings = 16
                val extendedFABOffset = ((extendedFABHeight + extendedFABPaddings) / 2).dp

                item { Spacer(modifier = Modifier.padding(extendedFABOffset)) }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
@PreviewTheme
private fun PreviewSuccessScreenEmpty() {
    PreviewTheme {
        SuccessScreen(
            MainUiState.Success(
                sum = 0.0,
                financialActions =
                mutableListOf(
                    FinancialAction(
                        amount = 100.0,
                        type = FinancialActionType.INCOME,
                        title = LoremIpsum().values.joinToString(),
                        description = LoremIpsum().values.joinToString(),
                        position = 0
                    )

                ),
            ),
            onRemoveItem = {},
            onUpdateItem = {},
            onAddItem = {},
            onUpdateFinancialActions = { _, _ -> }
        )
    }
}