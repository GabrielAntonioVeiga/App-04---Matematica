package ufpr.veiga.matematica.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import ufpr.veiga.matematica.R
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

        controller.iniciarJogo()
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
        binding.tvProgresso.text = getString(R.string.aritmetica_progresso, controller.getQuestaoAtual(), controller.getTotalQuestoes())
    }

    private fun validarResposta() {
        val respostaTexto = binding.etResposta.text.toString()

        if (respostaTexto.isEmpty()) {
            Toast.makeText(this, getString(R.string.validacao_digite_resposta), Toast.LENGTH_SHORT).show()
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
            builder.setTitle(getString(R.string.feedback_correto_titulo))
            builder.setMessage(getString(R.string.feedback_correto_mensagem))
        } else {
            builder.setTitle(getString(R.string.feedback_errou_titulo))
            builder.setMessage(getString(R.string.feedback_errou_mensagem, controller.getResultadoCorreto()))
        }

        builder.setPositiveButton(getString(R.string.feedback_btn_proxima)) { dialog, _ ->
            dialog.dismiss()
            carregarQuestao()
        }

        builder.setCancelable(false)
        builder.show()
    }

    private fun mostrarResultadoFinal(ultimaRespostaCorreta: Boolean) {
        val builder = AlertDialog.Builder(this)

        val mensagemFeedback = if (ultimaRespostaCorreta) {
            getString(R.string.resultado_correto_final)
        } else {
            getString(R.string.resultado_errou_final, controller.getResultadoCorreto())
        }

        val nota = controller.calcularNota()
        val acertos = controller.getAcertos()
        val total = controller.getTotalQuestoes()

        builder.setTitle(getString(R.string.resultado_titulo))
        builder.setMessage(
            "$mensagemFeedback${getString(R.string.resultado_nota, nota)}\n" +
            getString(R.string.resultado_acertos_questoes, acertos, total)
        )

        builder.setPositiveButton(getString(R.string.resultado_btn_fechar)) { dialog, _ ->
            dialog.dismiss()
            finish()
        }

        builder.setCancelable(false)
        builder.show()
    }
}
