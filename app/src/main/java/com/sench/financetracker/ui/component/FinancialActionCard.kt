package com.sench.financetracker.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import com.sench.financetracker.ui.component.model.FinancialActionModel
import com.sench.financetracker.ui.component.model.FinancialActionTypeModel
import com.sench.financetracker.ui.theme.greenContainer
import com.sench.financetracker.ui.theme.redContainer
import com.sench.financetracker.ui.util.PreviewTheme

@Composable
fun FinancialActionCard(
    financialAction: FinancialActionModel,
    onClickActionOpen: () -> Unit,
    onClickActionRemove: () -> Unit
) {
    val isIncome = financialAction.isIncome()
    val type = if (isIncome) "+" else "-"

    Card(
        colors = CardDefaults.cardColors(
            if (isIncome) MaterialTheme.colorScheme.greenContainer else MaterialTheme.colorScheme.redContainer
        ),
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = onClickActionOpen)
    ) {
        Row(
            modifier = Modifier.padding(
                top = 12.dp,
                end = 4.dp,
                start = 16.dp,
                bottom = 16.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(0.dp)
            ) {
                Text(
                    text = financialAction.title,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = "$type ${financialAction.sum}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(
                onClick = onClickActionRemove,
                colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.onSurfaceVariant)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
@PreviewTheme
private fun PreviewFinancialActionCardIncome() {
    PreviewTheme {
        val action = FinancialActionModel(
            title = LoremIpsum().values.joinToString(),
            description = LoremIpsum().values.joinToString(),
            sum = "1000",
            type = FinancialActionTypeModel.INCOME
        )

        FinancialActionCard(
            financialAction = action,
            onClickActionOpen = {},
            onClickActionRemove = {}
        )
    }
}

@Composable
@PreviewTheme
private fun PreviewFinancialActionCardExpense() {
    PreviewTheme {
        val action = FinancialActionModel(
            title = LoremIpsum().values.joinToString(),
            description = LoremIpsum().values.joinToString(),
            sum = "1000",
            type = FinancialActionTypeModel.EXPENSE
        )

        FinancialActionCard(
            financialAction = action,
            onClickActionOpen = {},
            onClickActionRemove = {}
        )
    }
}