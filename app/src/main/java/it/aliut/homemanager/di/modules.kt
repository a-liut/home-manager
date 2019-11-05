package it.aliut.homemanager.di

import it.aliut.homemanager.BuildConfig
import it.aliut.homemanager.net.HomeManagerApi
import it.aliut.homemanager.repository.DataRepository
import it.aliut.homemanager.repository.DeviceRepository
import it.aliut.homemanager.ui.deviceslist.DevicesListViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val repositoryModule = module {
    single { DeviceRepository(get()) }
    single { DataRepository(get()) }

}

val netModule = module {
    factory<Interceptor> {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
    }

    factory {
        OkHttpClient.Builder()
            .addInterceptor(get<Interceptor>())
            .build()
    }

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(BuildConfig.HM_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    factory { get<Retrofit>().create(HomeManagerApi::class.java) }
}

val viewModelModule = module {
    viewModel { DevicesListViewModel(get()) }
}