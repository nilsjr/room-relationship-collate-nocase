package de.nilsdruyen.roomcollatenocasebug.data.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import de.nilsdruyen.roomcollatenocasebug.data.entities.User
import de.nilsdruyen.roomcollatenocasebug.data.entities.UserWithBooks
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Transaction
    @Query("SELECT * FROM User")
    fun getWithBooks(): Flow<List<UserWithBooks>>

    @Transaction
    @Query("SELECT * FROM User WHERE id IN (:userId)")
    fun getWithBooks(userId: String): Flow<List<UserWithBooks>>

    @Upsert
    suspend fun set(user: User)
}