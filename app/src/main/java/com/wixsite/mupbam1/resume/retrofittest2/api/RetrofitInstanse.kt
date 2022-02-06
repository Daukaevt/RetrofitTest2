package com.wixsite.mupbam1.resume.retrofittest2.api

import com.wixsite.mupbam1.resume.retrofittest2.data.Fox
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitInstanse {


        @GET("/floof")
        suspend fun getEmployee(): Response<Fox>


}