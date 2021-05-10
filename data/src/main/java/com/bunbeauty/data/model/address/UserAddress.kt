package com.bunbeauty.data.model.address

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.bunbeauty.data.model.cafe.CafeEntity
import com.bunbeauty.data.model.user.User
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    indices = [Index(unique = true, value = ["userId"])],
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["userId"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class UserAddress(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var userId: String? = null,
) : Address(), Parcelable
