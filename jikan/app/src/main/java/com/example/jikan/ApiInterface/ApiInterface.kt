package com.example.jikan.ApiInterface

import com.example.jikan.Model.AnimeListResponse
import com.example.jikan.Model.AnimeResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
interface ApiInterface {

    @GET("top/anime")
    suspend fun getTopAnime(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 25
    ): Response<AnimeListResponse>

    @GET("anime/{id}/full")
    suspend fun getAnimeById(
        @Path("id") animeId: Int
    ): Response<AnimeResponse>

    companion object {
        private const val BASE_URL = "https://api.jikan.moe/v4/"

        fun create(): ApiInterface {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            // Crear OkHttpClient que acepta todos los certificados SSL (solo para desarrollo)
            val client = getUnsafeOkHttpClient()
                .newBuilder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiInterface::class.java)
        }

        // SOLO PARA DESARROLLO - NO USAR EN PRODUCCIÓN
        private fun getUnsafeOkHttpClient(): OkHttpClient {
            try {
                val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                    override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
                    override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
                    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
                })

                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, SecureRandom())

                val sslSocketFactory = sslContext.socketFactory

                return OkHttpClient.Builder()
                    .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                    .hostnameVerifier { _, _ -> true }
                    .build()
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
    }
}