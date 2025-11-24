package ufpr.veiga.matematica.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ufpr.veiga.matematica.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnJogoContagem.setOnClickListener {
            val intent = Intent(this, ContagemActivity::class.java)
            startActivity(intent)
        }

        binding.btnAritmetica.setOnClickListener {
            val intent = Intent(this, AritmeticaActivity::class.java)
            startActivity(intent)
        }

        binding.btnMaiorNumero.setOnClickListener {
            val intent = Intent(this, MaiorNumeroActivity::class.java)
            startActivity(intent)
        }
    }
}
