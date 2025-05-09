package com.liquidityminer.tycoon.data.repository

import com.liquidityminer.tycoon.data.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface UserRepository {
    suspend fun getUser(walletAddress: String): User?
    suspend fun updateUser(user: User)
    fun observeUser(walletAddress: String): Flow<User?>
    suspend fun deleteUser(walletAddress: String)
}