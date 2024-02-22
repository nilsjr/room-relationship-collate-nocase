package de.nilsdruyen.roomcollatenocasebug.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey
    @ColumnInfo("id", collate = ColumnInfo.NOCASE)
    val id: String,
    val name: String,
)
