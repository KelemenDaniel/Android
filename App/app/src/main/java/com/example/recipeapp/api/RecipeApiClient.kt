package com.example.recipeapp.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecipeApiClient {
    companion object{
        private const val BASE_URL = "https://recipe-appservice-cthjbdfafnhfdtes.germanywestcentral-01.azurewebsites.net\n"
        private const val AUTH_TOKEN = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjM2MjgyNTg2MDExMTNlNjU3NmE0NTMzNzM2NWZlOGI4OTczZDE2NzEiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiIxNjI1ODkxMzM3NDgtcWpndWZzNnJ2NDRmY3J0NHE4ZHN0cmU2djFlbG80Y3MuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiIxNjI1ODkxMzM3NDgtcWpndWZzNnJ2NDRmY3J0NHE4ZHN0cmU2djFlbG80Y3MuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDkzMDI2MjI1MjEyMTk3OTUwNzMiLCJoZCI6InN0dWRlbnQubXMuc2FwaWVudGlhLnJvIiwiZW1haWwiOiJrZWxlbWVuLmRhbmllbEBzdHVkZW50Lm1zLnNhcGllbnRpYS5ybyIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJhdF9oYXNoIjoiV1BxSUEzdVBMOWMxeG9pNFlVbXROZyIsIm5hbWUiOiJLZWxlbWVuIETDoW5pZWwiLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNvbnRlbnQuY29tL2EvQUNnOG9jSVRsT2cxWGhUYXd6UkVkSVU0dEt6c0hXTDhDSUdWNDZzaGhjd1pnRzYxR3ZNUlNBPXM5Ni1jIiwiZ2l2ZW5fbmFtZSI6IktlbGVtZW4iLCJmYW1pbHlfbmFtZSI6IkTDoW5pZWwiLCJpYXQiOjE3MzMzMjQ0NDgsImV4cCI6MTczMzMyODA0OH0.vFPY4wl2W_RZM66sCnlwSIGWgUVDx3CLFK5Hdsy82g_j25uCbGHpghNfWMSMZK5NiHY8IWFkfrGtKDUSIMqqaqO39NrFBrLwOPZZHv993r1Jhn26tz1Y4pBcXruxFM8G_V-Q-ErsQeneTRzoEzwQyMtBmvEDxjz7bk9tTjvgp_MrjR-AeLfWee1yJvl333_SDqJGvWcWTHRYksSM8ya_bkyoJ75oICj2V2jOKTNAccB-GX8kSr1kFjNqfBBG0UIptfTACP5SxmteyYrGVZkR8pdy5WQxdVUz2V1LmIDuSL09rmqQ1fFd8BR5z_qOoMlzghTQN-a6dfXLjuK364h1pg"
    }

    private val okHttpClient = OkHttpClient.Builder().addInterceptor(AuthInterceptor(AUTH_TOKEN)).build()

//    val recipeService: RecipeService

    init{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
}