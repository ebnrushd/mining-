package com.liquidityminer.tycoon.data

object Constants {
    const val CONTRACT_ADDRESS = "0x3b3009c8a9683827110e29b8edc6355b21e2cd70c6a7728367c"
    const val PAIR_ADDRESS = "0xf18993ab841d8edc6355b21e2cd70c6a7728367c"
    
    // Mining Constants
    const val BASE_MINING_RATE = 1.0
    const val MIN_COOLDOWN_PERIOD = 300L // 5 minutes in seconds
    const val MAX_ENERGY = 100
    const val ENERGY_REGEN_RATE = 1 // Energy points per minute
    
    // Staking Constants
    const val MIN_STAKE_AMOUNT = "0.01"
    const val MAX_STAKE_AMOUNT = "1000000"
    const val BASE_APY = 5.0 // 5% base APY
    
    // Security Constants
    const val MAX_REQUESTS_PER_MINUTE = 60
    const val TRANSACTION_TIMEOUT = 30000L // 30 seconds
}