package ufpr.veiga.matematica.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import ufpr.veiga.matematica.controller.AritmeticaController
import ufpr.veiga.matematica.databinding.ActivityAritmeticaBinding
import ufpr.veiga.matematica.model.Questao

class AritmeticaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAritmeticaBinding
    private lateinit var controller: AritmeticaController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAritmeticaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        controller = AritmeticaController()

        carregarQuestao()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnResponder.setOnClickListener {
            validarResposta()
        }
    }

    private fun carregarQuestao() {
        val questao = controller.gerarQuestao()
        exibirQuestao(questao)
        atualizarProgresso()
        binding.etResposta.text?.clear()
        binding.etResposta.requestFocus()
    }

    private fun exibirQuestao(questao: Questao) {
        val contaTexto = "${questao.numero1} ${questao.operador.simbolo} ${questao.numero2}"
        binding.tvConta.text = contaTexto
    }

    private fun atualizarProgresso() {
        binding.tvProgresso.text = "Questão ${controller.getQuestaoAtual()}/${controller.getTotalQuestoes()}"
    }

    private fun validarResposta() {
        val respostaTexto = binding.etResposta.text.toString()

        if (respostaTexto.isEmpty()) {
            Toast.makeText(this, "Digite uma resposta", Toast.LENGTH_SHORT).show()
            return
        }

        val respostaUsuario = respostaTexto.toInt()
        val correto = controller.validarResposta(respostaUsuario)

        if (controller.isUltimaQuestao()) {
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
            builder.setMessage("A resposta correta era ${controller.getResultadoCorreto()}")
        }

        builder.setPositiveButton("Próxima") { dialog, _ ->
            dialog.dismiss()
            carregarQuestao()
        }

        builder.setCancelable(false)
        builder.show()
    }

    private fun mostrarResultadoFinal(ultimaRespostaCorreta: Boolean) {
        val builder = AlertDialog.Builder(this)

        val mensagemFeedback = if (ultimaRespostaCorreta) {
            "Correto!\n\n"
        } else {
            "Errou! A resposta era ${controller.getResultadoCorreto()}\n\n"
        }

        val nota = controller.calcularNota()
        val acertos = controller.getAcertos()
        val total = controller.getTotalQuestoes()

        builder.setTitle("Jogo Finalizado!")
        builder.setMessage(
            "${mensagemFeedback}Sua nota: $nota/100\n" +
            "Você acertou $acertos de $total questões"
        )

        builder.setPositiveButton("Fechar") { dialog, _ ->
            dialog.dismiss()
            finish()
        }

        builder.setCancelable(false)
        builder.show()
    }
}
