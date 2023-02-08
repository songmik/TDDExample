package com.songmi.test

import android.app.Application
import com.songmi.test.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class TDDApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        // TODO Koin Trigger
        startKoin {
            // androidLogger : 에러 발생시 로깅할 수 있게 추가해줌
            androidLogger(Level.ERROR)
            androidContext(this@TDDApplication)
            modules(appModule)
        }
    }
}