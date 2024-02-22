package de.nilsdruyen.roomcollatenocasebug.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity
data class Book(
    @PrimaryKey
    val id: String,
    @ColumnInfo("uid", index = true, collate = ColumnInfo.NOCASE)
    val uid: String,
    val name: String,
)
