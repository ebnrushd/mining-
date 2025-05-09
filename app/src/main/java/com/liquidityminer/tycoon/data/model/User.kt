package com.liquidityminer.tycoon.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val walletAddress: String,
    val energyLevel: Int = 100,
    val lastMiningTimestamp: Long = 0,
    val totalMined: Double = 0.0,
    val stakingBalance: String = "0",
    val stakingStartTime: Long = 0,
    val miningMultiplier: Double = 1.0,
    val achievementPoints: Int = 0
)