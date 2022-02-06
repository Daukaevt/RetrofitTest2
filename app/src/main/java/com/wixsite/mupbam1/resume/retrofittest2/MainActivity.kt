package com.wixsite.mupbam1.resume.retrofittest2


//https://www.youtube.com/watch?v=t6Sql3WMAnk
//https://johncodeos.com/how-to-parse-json-with-retrofit-converters-using-kotlin/

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.wixsite.mupbam1.resume.retrofittest2.api.RetrofitInstanse
import com.wixsite.mupbam1.resume.retrofittest2.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

const val TAG="MyLog"

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        parseJSON()
        binding.btRND.setOnClickListener {
           parseJSON()
        }
    }
    fun parseJSON() {

        // Create Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://randomfox.ca")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(RetrofitInstanse::class.java)


        lifecycleScope.launchWhenCreated {

            val response = try {
                service
            } catch(e: IOException) {
                Log.e(TAG, "IOException, you might not have internet connection")

                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(TAG, "HttpException, unexpected response")

                return@launchWhenCreated
            }
            if(response.getEmployee().isSuccessful && response.getEmployee().body() != null) {
                val item= response.getEmployee().body()!!.image
                Glide
                    .with(this@MainActivity)
                    .load(item)
                    .into(binding.ivFox);

            } else {
                Log.e(TAG, "Response not successful")
            }

        }

    }


}