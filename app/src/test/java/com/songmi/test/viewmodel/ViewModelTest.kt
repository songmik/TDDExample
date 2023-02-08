package com.songmi.test.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import com.songmi.test.di.appTestModule
import com.songmi.test.livedata.LiveDataTestObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

internal class ViewModelTest: KoinTest {

    @get:Rule
    val mockitoRule : MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var context: Application

    // TestCoroutineDispatcher -> StandardTestDispatcher
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        startKoin {
            androidContext(context)
            modules(appTestModule)
        }
        Dispatchers.setMain(dispatcher)
    }

    // Test가 끝나고 Coroutines를 사용해줄 예정
    @After
    fun tearDown() {
        stopKoin()
        // resetMain -> MainDispatcher를 초기화 해주어야 메모리 누수가 발생하지 않음
        Dispatchers.resetMain()
    }

    // LiveData 내부에 어떠한 인스턴스를 담을 수 있는 형태를 만들어줘야 함
    protected fun <T> LiveData<T>.test(): LiveDataTestObserver<T> {
        val testObserver = LiveDataTestObserver<T>()
        observeForever(testObserver)
        return testObserver
    }

}