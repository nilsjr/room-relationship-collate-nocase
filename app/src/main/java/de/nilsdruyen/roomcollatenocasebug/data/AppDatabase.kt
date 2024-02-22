package de.nilsdruyen.roomcollatenocasebug.data

import androidx.room.Database
import androidx.room.RoomDatabase
import de.nilsdruyen.roomcollatenocasebug.data.daos.BookDao
import de.nilsdruyen.roomcollatenocasebug.data.daos.UserDao
import de.nilsdruyen.roomcollatenocasebug.data.entities.Book
import de.nilsdruyen.roomcollatenocasebug.data.entities.User

@Database(entities = [User::class, Book::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun bookDao(): BookDao
}