package ru.aasmc.nowinandroidclone

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import ru.aasmc.nowinandroid.sync.initializers.Sync

@HiltAndroidApp
class NiaApp: Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize Sync; the system responsible for keeping data in the app up to date.
        Sync.initialize(context = this)
    }
}