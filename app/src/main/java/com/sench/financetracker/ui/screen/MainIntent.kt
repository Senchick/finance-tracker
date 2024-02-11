package com.sench.financetracker.ui.screen

sealed class MainIntent {
    data object UpdateData : MainIntent()
}