package de.nilsdruyen.roomcollatenocasebug.data.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import de.nilsdruyen.roomcollatenocasebug.data.entities.Book
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Upsert
    suspend fun set(book: Book)

    @Transaction
    @Query("SELECT * FROM Book WHERE uid = :userId")
    fun getForUser(userId: String): Flow<List<Book>>
}