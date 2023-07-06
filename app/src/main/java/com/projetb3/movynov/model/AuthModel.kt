package com.projetb3.movynov.model

import com.google.gson.Gson
import com.projetb3.movynov.dataclasses.auth.LoginRegisterResult
import com.projetb3.movynov.repository.MovynovApiCall
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.FormBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject

class AuthModel {
    public fun register(email : String, password : String): Response{
        //TODO
        val json = JSONObject()
        json.put("email", email)
        json.put("password", password)
        json.put("pseudo", "TODO")
        json.put("spoilers", false)
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body = json.toString().toRequestBody(mediaType)

        return MovynovApiCall().executePost("/api/register", body)
    }

    public fun login(email : String, password : String) : Response{
        val json = JSONObject()
        json.put("email", email)
        json.put("password", password)
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body = json.toString().toRequestBody(mediaType)

        return MovynovApiCall().executePost("/api/login", body)
    }

    public fun extractTokenFromResponse(response: Response) : String{
        val loginRegisterResult = Gson().fromJson(response.body?.string(), LoginRegisterResult::class.java)
        return loginRegisterResult.token!!
    }
}