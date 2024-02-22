package de.nilsdruyen.roomcollatenocasebug

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import de.nilsdruyen.roomcollatenocasebug.data.AppDatabase
import de.nilsdruyen.roomcollatenocasebug.data.entities.Book
import de.nilsdruyen.roomcollatenocasebug.data.entities.User
import de.nilsdruyen.roomcollatenocasebug.ui.theme.RoomCollateNoCaseBugTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val db: AppDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        populateDb()

        setContent {
            RoomCollateNoCaseBugTheme {
                val users by db.userDao().getWithBooks()
                    .collectAsStateWithLifecycle(initialValue = emptyList())
                val users2 by db.userDao().getWithBooks("Nils")
                    .collectAsStateWithLifecycle(initialValue = emptyList())
                val books by db.bookDao().getForUser("Nils")
                    .collectAsStateWithLifecycle(initialValue = emptyList())

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Spacer(Modifier.height(0.dp))
                        Text("All users", modifier = Modifier.padding(horizontal = 16.dp))
                        users.forEach { (user, books) ->
                            Text(
                                "${user.id} - ${user.name} / ${books.size}",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                            )
                        }
                        HorizontalDivider()
                        Text("Select NILS", modifier = Modifier.padding(horizontal = 16.dp))
                        users2.forEach { (user, books) ->
                            Text(
                                "${user.id} - ${user.name} / ${books.size}",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                            )
                        }
                        HorizontalDivider()
                        Text("Books NILS", modifier = Modifier.padding(horizontal = 16.dp))
                        books.forEach {
                            Text(
                                "${it.id} - ${it.uid}",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                            )
                        }
                        Spacer(Modifier.height(0.dp))
                    }
                }
            }
        }
    }

    private fun populateDb() {
        lifecycleScope.launch {
            db.userDao().set(User("nils", "Nils"))
            db.userDao().set(User("test", "Test"))

            db.bookDao().set(Book("test", "test", "TestBook"))
            db.bookDao().set(Book("test2", "test", "TestBook"))
            db.bookDao().set(Book("test3", "Nils", "TestBook"))
            db.bookDao().set(Book("test4", "Nils", "TestBook2"))
            db.bookDao().set(Book("test5", "nils", "TestBook3"))
        }
    }
}