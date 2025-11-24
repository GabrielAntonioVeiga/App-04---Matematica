package ufpr.veiga.matematica.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ufpr.veiga.matematica.databinding.ActivityAritmeticaBinding

class AritmeticaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAritmeticaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAritmeticaBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
