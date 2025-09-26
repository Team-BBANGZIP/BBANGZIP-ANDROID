package org.android.bbangzip.data.source.local.util.context

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import org.android.bbangzip.UserPreferences
import org.android.bbangzip.data.source.local.datasource.serializer.UserSerializer

val Context.userDataSource: DataStore<UserPreferences> by dataStore(
    fileName = "user_prefs.proto",
    serializer = UserSerializer,
)
