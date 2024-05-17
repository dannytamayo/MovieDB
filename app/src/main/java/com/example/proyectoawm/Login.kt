package com.example.proyectoawm

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.proyectoawm.Interface.Servicios
import com.example.proyectoawm.Models.ResponseLogin
import com.example.proyectoawm.databinding.ActivityLoginBinding
import com.example.proyectoawm.databinding.ActivityShowMovieBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Aquí es donde debes realizar la petición con retrofit

                val retrofit = Retrofit.Builder()
                    .baseUrl("http://10.79.7.111/APIMovie/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val service = retrofit.create(Servicios::class.java)

                val call = service.login(email, password)

                call.enqueue(object : Callback<ResponseLogin> {
                    override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                        if (response.isSuccessful && response.body() != null) {
                            val loginResponse = response.body()!!
                            if (loginResponse.success) {
                                val editor = sharedPreferences.edit()
                                editor.putInt("userId", loginResponse.userId)
                                editor.apply()

                                val intent = Intent(this@Login, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this@Login, "Login failed: ${loginResponse.message}", Toast.LENGTH_LONG).show()
                            }
                        } else {
                            Toast.makeText(this@Login, "Error: ${response.code()} ${response.message()}", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                        Toast.makeText(this@Login, "Error " + t, Toast.LENGTH_LONG ).show()
//                        Log.d("error", t.toString())
                    }

                })


            } else {
                // Aquí debes mostrar un mensaje de error si el email o la contraseña están vacíos
            }
        }
    }
}