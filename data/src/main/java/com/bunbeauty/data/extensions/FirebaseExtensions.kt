package com.bunbeauty.data.extensions

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map

@ExperimentalCoroutinesApi
fun DatabaseReference.getSnapshot(): Flow<DataSnapshot> = callbackFlow {
    val listener = object : ValueEventListener {

        override fun onDataChange(snapshot: DataSnapshot) {
            trySend(snapshot)
        }

        override fun onCancelled(error: DatabaseError) {
            close(error.toException())
        }
    }
    addListenerForSingleValueEvent(listener)
    awaitClose { removeEventListener(listener) }
}

@ExperimentalCoroutinesApi
inline fun <reified T> DatabaseReference.getValue(): Flow<T?> {
    return getSnapshot().map { snapshot ->
        snapshot.getValue(T::class.java)
    }
}

@ExperimentalCoroutinesApi
inline fun <reified T> DatabaseReference.getValueList(): Flow<List<T>> {
    return getSnapshot().map { snapshot ->
        snapshot.children.mapNotNull { childSnapshot ->
            childSnapshot.getValue(T::class.java)
        }
    }
}

@ExperimentalCoroutinesApi
fun DatabaseReference.observeSnapshot(): Flow<DataSnapshot> = callbackFlow {
    val listener = object : ValueEventListener {

        override fun onDataChange(snapshot: DataSnapshot) {
            trySend(snapshot)
        }

        override fun onCancelled(error: DatabaseError) {
            close(error.toException())
        }
    }
    addValueEventListener(listener)
    awaitClose { removeEventListener(listener) }
}

@ExperimentalCoroutinesApi
inline fun <reified T> DatabaseReference.observeValue(): Flow<T?> {
    return observeSnapshot().map { snapshot ->
        snapshot.getValue(T::class.java)
    }
}