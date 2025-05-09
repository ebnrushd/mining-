package com.liquidityminer.tycoon.domain.mining

import com.liquidityminer.tycoon.data.Constants
import javax.inject.Inject
import kotlin.math.max

class MiningCalculator @Inject constructor() {
    fun calculateMiningReward(
        baseRate: Double = Constants.BASE_MINING_RATE,
        stakingMultiplier: Double,
        miningMultiplier: Double,
        energyLevel: Int
    ): Double {
        val energyMultiplier = max(energyLevel.toDouble() / Constants.MAX_ENERGY, 0.1)
        return baseRate * (1 + stakingMultiplier) * miningMultiplier * energyMultiplier
    }

    fun calculateCooldownPeriod(
        lastMiningTimestamp: Long,
        currentTime: Long = System.currentTimeMillis()
    ): Long {
        val timeDifference = currentTime - lastMiningTimestamp
        return max(Constants.MIN_COOLDOWN_PERIOD - (timeDifference / 1000), 0)
    }

    fun calculateEnergyRegeneration(
        currentEnergy: Int,
        lastUpdateTime: Long,
        currentTime: Long = System.currentTimeMillis()
    ): Int {
        val minutesPassed = ((currentTime - lastUpdateTime) / (1000 * 60)).toInt()
        val regeneratedEnergy = minutesPassed * Constants.ENERGY_REGEN_RATE
        return (currentEnergy + regeneratedEnergy).coerceIn(0, Constants.MAX_ENERGY)
    }
}