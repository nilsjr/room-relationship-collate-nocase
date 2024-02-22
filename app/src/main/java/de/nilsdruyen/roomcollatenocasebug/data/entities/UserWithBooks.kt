package de.nilsdruyen.roomcollatenocasebug.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithBooks(
    @Embedded val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "uid"
    )
    val books: List<Book>
)