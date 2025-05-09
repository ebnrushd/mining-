package com.liquidityminer.tycoon.domain.mining

data class MiningStats(
    val energyLevel: Int = 0,
    val totalMined: Double = 0.0,
    val cooldownRemaining: Long = 0,
    val miningMultiplier: Double = 1.0
)