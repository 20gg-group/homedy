package gggroup.com.baron.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CallAPI {

    companion object {
        private const val BASE_URL = "https://salty-brushlands-19787.herokuapp.com/"
        private const val BASE_URL_ADDRESS = "https://thongtindoanhnghiep.co/"

        private fun builder() : Retrofit {
            return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client())
                    .build()
        }

        private fun client(): OkHttpClient {
            return OkHttpClient.Builder().addNetworkInterceptor { chain ->
                var request = chain.request()
                val url = request.url().newBuilder()
                        .build()
                request = request.newBuilder().url(url).build()
                chain.proceed(request)
            }.build()
        }

        fun createService() : LinkAPI {
            return builder().create(LinkAPI::class.java)
        }

        private fun builderAddress() : Retrofit {
            return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client())
                    .build()
        }
        fun createServiceAddress() : LinkAPI{
            return builderAddress().create(LinkAPI::class.java)
        }
    }
}