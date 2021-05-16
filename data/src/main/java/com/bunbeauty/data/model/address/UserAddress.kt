package com.bunbeauty.data.model.address

import android.os.Parcelable
import androidx.room.*
import com.bunbeauty.data.model.cafe.CafeEntity
import com.bunbeauty.data.model.user.User
import kotlinx.parcelize.Parcelize

/*
indices = [Index(unique = true, value = ["userId"])],
foreignKeys = [ForeignKey(
entity = User::class,
parentColumns = ["userId"],
childColumns = ["user_address_user_id"],
onDelete = ForeignKey.CASCADE
)]
*/

@Parcelize
@Entity
data class UserAddress(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var userId: String? = null,
) : Address(), Parcelable
