package com.ima.myhealthydiary.utils.db.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user")
data class UserEntity(@ColumnInfo(name= "user_email") var name: String = "", @ColumnInfo(name="password") var password: String = "", @ColumnInfo(name="status") var status: Int = 0) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}