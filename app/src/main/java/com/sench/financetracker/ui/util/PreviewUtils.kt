package com.sench.financetracker.ui.util

import android.content.res.Configuration
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.sench.financetracker.ui.theme.FinanceTrackerTheme

/*
 * apiLevel = 33, потому что ошибка Render Problem https://stackoverflow.com/questions/77611812/
 */
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, apiLevel = 33)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, apiLevel = 33)
annotation class PreviewTheme

@Composable
fun PreviewTheme(content: @Composable () -> Unit) {
    FinanceTrackerTheme {
        Surface {
            content()
        }
    }
}