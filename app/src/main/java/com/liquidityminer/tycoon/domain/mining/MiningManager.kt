package com.liquidityminer.tycoon.domain.mining

import com.liquidityminer.tycoon.data.model.User
import com.liquidityminer.tycoon.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MiningManager @Inject constructor(
    private val userRepository: UserRepository,
    private val miningCalculator: MiningCalculator
) {
    suspend fun performMining(walletAddress: String): Result<Double> = runCatching {
        val user = userRepository.getUser(walletAddress) ?: throw IllegalStateException("User not found")
        
        val cooldown = miningCalculator.calculateCooldownPeriod(user.lastMiningTimestamp)
        if (cooldown > 0) {
            throw IllegalStateException("Mining cooldown active: ${cooldown}s remaining")
        }

        if (user.energyLevel <= 0) {
            throw IllegalStateException("Insufficient energy")
        }

        val reward = miningCalculator.calculateMiningReward(
            stakingMultiplier = user.stakingBalance.toDoubleOrNull()?.div(1000) ?: 0.0,
            miningMultiplier = user.miningMultiplier,
            energyLevel = user.energyLevel
        )

        val updatedUser = user.copy(
            energyLevel = (user.energyLevel - 10).coerceAtLeast(0),
            lastMiningTimestamp = System.currentTimeMillis(),
            totalMined = user.totalMined + reward
        )
        
        userRepository.updateUser(updatedUser)
        reward
    }

    fun observeMiningStats(walletAddress: String): Flow<MiningStats> {
        return userRepository.observeUser(walletAddress).map { user ->
            user?.let {
                MiningStats(
                    energyLevel = it.energyLevel,
                    totalMined = it.totalMined,
                    cooldownRemaining = miningCalculator.calculateCooldownPeriod(it.lastMiningTimestamp),
                    miningMultiplier = it.miningMultiplier
                )
            } ?: MiningStats()
        }
    }
}