package ufpr.veiga.matematica.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ufpr.veiga.matematica.databinding.ActivityMaiorNumeroBinding

class MaiorNumeroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMaiorNumeroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMaiorNumeroBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
