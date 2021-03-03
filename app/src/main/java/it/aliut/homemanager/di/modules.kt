package it.aliut.homemanager.di

import com.google.gson.GsonBuilder
import it.aliut.homemanager.BuildConfig
import it.aliut.homemanager.net.HomeManagerApi
import it.aliut.homemanager.repository.DataRepository
import it.aliut.homemanager.repository.DeviceRepository
import it.aliut.homemanager.ui.devicedetails.DeviceDetailsViewModel
import it.aliut.homemanager.ui.deviceslist.DevicesListViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager


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

//    factory {
//        OkHttpClient.Builder()
//            .addInterceptor(get<Interceptor>())
//            .build()
//    }

    factory {
        val trustAllCerts = arrayOf(object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()

        })

        // Install the all-trusting trust manager
        val sslContext: SSLContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())
        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory: SSLSocketFactory = sslContext.getSocketFactory()
        val builder = OkHttpClient.Builder()
        builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
        builder.hostnameVerifier(HostnameVerifier { _, _ -> true })
        builder.build()
    }

    single {
        GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create()
    }

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(BuildConfig.HM_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

    factory { get<Retrofit>().create(HomeManagerApi::class.java) }
}

val viewModelModule = module {
    viewModel { DevicesListViewModel(get()) }
    viewModel { (id: String) -> DeviceDetailsViewModel(id, get(), get()) }
}