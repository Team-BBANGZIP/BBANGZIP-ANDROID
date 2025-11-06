package org.android.bbangzip.data.auth.interceptor

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class AuthEventManager
    @Inject
    constructor() {
        private val _authEvent =
            MutableSharedFlow<AuthEvent>(
                extraBufferCapacity = 1,
                onBufferOverflow = BufferOverflow.DROP_OLDEST,
            )
        val authEvent: SharedFlow<AuthEvent> = _authEvent.asSharedFlow()

        suspend fun emitEvent(event: AuthEvent) {
            _authEvent.emit(event)
        }
    }

sealed class AuthEvent {
    data object ForceLogout : AuthEvent()
}
