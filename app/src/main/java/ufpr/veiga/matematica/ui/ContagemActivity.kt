package ufpr.veiga.matematica.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ufpr.veiga.matematica.databinding.ActivityContagemBinding

class ContagemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContagemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContagemBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
