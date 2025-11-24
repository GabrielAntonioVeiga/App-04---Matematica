package ufpr.veiga.matematica.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import ufpr.veiga.matematica.controller.MaiorNumeroController
import ufpr.veiga.matematica.databinding.ActivityMaiorNumeroBinding
import ufpr.veiga.matematica.model.Rodada

class MaiorNumeroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMaiorNumeroBinding
    private lateinit var controller: MaiorNumeroController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMaiorNumeroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        controller = MaiorNumeroController()

        carregarRodada()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnConfirmar.setOnClickListener {
            validarResposta()
        }
    }

    private fun carregarRodada() {
        val rodada = controller.gerarRodada()
        exibirDigitos(rodada)
        atualizarProgresso()
        binding.etResposta.text?.clear()
        binding.etResposta.requestFocus()
    }

    private fun exibirDigitos(rodada: Rodada) {
        binding.tvDigito1.text = rodada.digito1.toString()
        binding.tvDigito2.text = rodada.digito2.toString()
        binding.tvDigito3.text = rodada.digito3.toString()
    }

    private fun atualizarProgresso() {
        binding.tvProgresso.text = "Rodada ${controller.getRodadaAtual()}/${controller.getTotalRodadas()}"
    }

    private fun validarResposta() {
        val respostaTexto = binding.etResposta.text.toString()

        if (respostaTexto.isEmpty()) {
            Toast.makeText(this, "Digite sua resposta", Toast.LENGTH_SHORT).show()
            return
        }

        if (respostaTexto.length != 3) {
            Toast.makeText(this, "Digite um número de 3 dígitos", Toast.LENGTH_SHORT).show()
            return
        }

        val respostaUsuario = respostaTexto.toInt()
        val correto = controller.validarResposta(respostaUsuario)

        if (controller.isUltimaRodada()) {
            mostrarResultadoFinal(correto)
        } else {
            mostrarFeedback(correto)
        }
    }

    private fun mostrarFeedback(correto: Boolean) {
        val builder = AlertDialog.Builder(this)

        if (correto) {
            builder.setTitle("Correto!")
            builder.setMessage("Parabéns! Você acertou!")
        } else {
            builder.setTitle("Errou!")
            builder.setMessage("A resposta correta era ${controller.getRespostaCorreta()}")
        }

        builder.setPositiveButton("Próxima") { dialog, _ ->
            dialog.dismiss()
            carregarRodada()
        }

        builder.setCancelable(false)
        builder.show()
    }

    private fun mostrarResultadoFinal(ultimaRespostaCorreta: Boolean) {
        val builder = AlertDialog.Builder(this)

        val mensagemFeedback = if (ultimaRespostaCorreta) {
            "Correto!\n\n"
        } else {
            "Errou! A resposta era ${controller.getRespostaCorreta()}\n\n"
        }

        val nota = controller.calcularNota()
        val acertos = controller.getAcertos()
        val total = controller.getTotalRodadas()

        builder.setTitle("Jogo Finalizado!")
        builder.setMessage(
            "${mensagemFeedback}Sua nota: $nota/100\n" +
            "Você acertou $acertos de $total rodadas"
        )

        builder.setPositiveButton("Fechar") { dialog, _ ->
            dialog.dismiss()
            finish()
        }

        builder.setCancelable(false)
        builder.show()
    }
}
