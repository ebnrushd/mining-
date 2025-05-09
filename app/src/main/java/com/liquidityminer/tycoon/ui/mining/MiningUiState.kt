package com.liquidityminer.tycoon.ui.mining

sealed class MiningUiState {
    data object Loading : MiningUiState()
    data class Success(val reward: Double) : MiningUiState()
    data class Error(val message: String) : MiningUiState()
}