package de.nilsdruyen.roomcollatenocasebug

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import de.nilsdruyen.roomcollatenocasebug.data.AppDatabase
import de.nilsdruyen.roomcollatenocasebug.data.entities.Book
import de.nilsdruyen.roomcollatenocasebug.data.entities.User
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28]) // This config setting is the key to make things work
class DatabaseTest {

    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .build()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun `Test all users with books are returned`() = runTest {
        db.userDao().set(User("nils", "Name"))
        db.userDao().set(User("nils2", "Name"))

        db.userDao().getWithBooks().test {
            assert(awaitItem().size == 2)
        }
    }

    @Test
    fun `Test userWithBooks is fetched case insensitive`() = runTest {
        db.userDao().set(User("nils", "Name"))
        db.bookDao().set(Book("book1", "nils", "Book1"))
        db.bookDao().set(Book("book2", "NILS", "Book2"))

        // should be 2 with collate = ColumnInfo.NOCASE
        val expected = 1

        db.userDao().getWithBooks("nils").test {
            assert(awaitItem().first().books.size == expected)
        }
        db.userDao().getWithBooks("NILS").test {
            assert(awaitItem().first().books.size == expected)
        }
        db.userDao().getWithBooks("Nils").test {
            assert(awaitItem().first().books.size == expected)
        }
    }

    @Test
    fun `Test books for user are fetched case insensitive`() = runTest {
        db.bookDao().set(Book("book1", "nils", "Book1"))
        db.bookDao().set(Book("book2", "NILS", "Book2"))

        val expected = 2

        db.bookDao().getForUser("nils").test {
            assert(awaitItem().size == expected)
        }
        db.bookDao().getForUser("NILS").test {
            assert(awaitItem().size == expected)
        }
        db.bookDao().getForUser("Nils").test {
            assert(awaitItem().size == expected)
        }
    }
}