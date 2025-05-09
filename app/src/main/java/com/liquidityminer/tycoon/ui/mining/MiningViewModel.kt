package com.liquidityminer.tycoon.ui.mining

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liquidityminer.tycoon.domain.mining.MiningManager
import com.liquidityminer.tycoon.domain.mining.MiningStats
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MiningViewModel @Inject constructor(
    private val miningManager: MiningManager
) : ViewModel() {
    private val _uiState = MutableStateFlow<MiningUiState>(MiningUiState.Loading)
    val uiState: StateFlow<MiningUiState> = _uiState

    private val _miningStats = MutableStateFlow(MiningStats())
    val miningStats: StateFlow<MiningStats> = _miningStats

    fun startObservingMiningStats(walletAddress: String) {
        miningManager.observeMiningStats(walletAddress)
            .onEach { stats -> _miningStats.value = stats }
            .catch { _uiState.value = MiningUiState.Error(it.message ?: "Unknown error") }
            .launchIn(viewModelScope)
    }

    fun mine(walletAddress: String) {
        viewModelScope.launch {
            _uiState.value = MiningUiState.Loading
            miningManager.performMining(walletAddress)
                .onSuccess { reward ->
                    _uiState.value = MiningUiState.Success(reward)
                }
                .onFailure { error ->
                    _uiState.value = MiningUiState.Error(error.message ?: "Mining failed")
                }
        }
    }
}