package xyz.deseteral.pudeuko.clients

import android.content.Context
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import xyz.deseteral.pudeuko.R
import xyz.deseteral.pudeuko.domain.ContentDTO

interface PudeukoClient {
    @POST("items")
    @Headers("Content-Type: application/json")
    fun postItem(@Body content: ContentDTO) : Call<Unit>
}

fun buildPudeukoClient(context: Context) : PudeukoClient {
    val serviceUrl = context.getString(R.string.pudeuko_service_url)
    val retrofit = Retrofit.Builder()
        .baseUrl(serviceUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(PudeukoClient::class.java)
}