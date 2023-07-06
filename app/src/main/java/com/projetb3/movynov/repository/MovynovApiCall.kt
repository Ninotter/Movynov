package com.projetb3.movynov.repository

import okhttp3.Call
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException

class MovynovApiCall {
    //used to connect to redirect the android virtual machine query to localhost
    private val devVirtualMachineBaseUrl: String = "http://10.0.2.2:8000"
    //unit test url
    private val devBaseUrl: String = "http://127.0.0.1:8000"

    private val physicalAndroidUrl: String = "http://192.168.99.76:8000"
    //prod url
    //todo : change this url to the prod url
    private val client = OkHttpClient()
    //Change accordingly to dev or prod
    private val currentUrl = physicalAndroidUrl

    public fun executeGet(optionalUrl : String) : String{
        val request = Request.Builder()
            .url(currentUrl + optionalUrl)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            else return response.body!!.string()
        }
    }

    public fun executeGetWithAuthorization(optionalUrl : String, token: String) : String{
        val request = Request.Builder()
            .url(currentUrl + optionalUrl)
            .addHeader("Authorization", "Bearer $token")
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            else return response.body!!.string()
        }
    }

    public fun executePost(url : String, body : RequestBody) : Response{
        val request: Request = Request.Builder()
            .url(currentUrl + url)
            .post(body)
            .build()

        val call: Call = client.newCall(request)
        val response: Response = call.execute()
        return response
    }
}