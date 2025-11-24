package ufpr.veiga.matematica.ui

import android.os.Bundle
import android.util.TypedValue
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import ufpr.veiga.matematica.R
import ufpr.veiga.matematica.controller.ContagemController
import ufpr.veiga.matematica.databinding.ActivityContagemBinding
import ufpr.veiga.matematica.model.RodadaContagem

class ContagemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContagemBinding
    private lateinit var controller: ContagemController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContagemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        controller = ContagemController()

        carregarRodada()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnOpcao1.setOnClickListener {
            val opcaoEscolhida = binding.btnOpcao1.text.toString().toInt()
            validarResposta(opcaoEscolhida)
        }

        binding.btnOpcao2.setOnClickListener {
            val opcaoEscolhida = binding.btnOpcao2.text.toString().toInt()
            validarResposta(opcaoEscolhida)
        }

        binding.btnOpcao3.setOnClickListener {
            val opcaoEscolhida = binding.btnOpcao3.text.toString().toInt()
            validarResposta(opcaoEscolhida)
        }
    }

    private fun carregarRodada() {
        val rodada = controller.gerarRodada()
        exibirImagens(rodada)
        exibirOpcoes(rodada)
        atualizarProgresso()
    }

    private fun exibirImagens(rodada: RodadaContagem) {
        binding.containerImagens.removeAllViews()

        val tamanhoEmDp = 120
        val tamanhoEmPx = dpParaPx(tamanhoEmDp)

        for (i in 0 until rodada.quantidade) {
            val imageView = ImageView(this)
            imageView.setImageResource(rodada.imagemResId)
            imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
            imageView.maxWidth = tamanhoEmPx
            imageView.maxHeight = tamanhoEmPx

            val params = android.widget.GridLayout.LayoutParams()
            params.width = tamanhoEmPx
            params.height = tamanhoEmPx
            params.setMargins(4, 4, 4, 4)
            imageView.layoutParams = params

            binding.containerImagens.addView(imageView)
        }
    }

    private fun exibirOpcoes(rodada: RodadaContagem) {
        binding.btnOpcao1.text = rodada.opcoes[0].toString()
        binding.btnOpcao2.text = rodada.opcoes[1].toString()
        binding.btnOpcao3.text = rodada.opcoes[2].toString()
    }

    private fun atualizarProgresso() {
        binding.tvProgresso.text = getString(R.string.contagem_progresso, controller.getRodadaAtual(), controller.getTotalRodadas())
    }

    private fun dpParaPx(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            resources.displayMetrics
        ).toInt()
    }

    private fun validarResposta(opcaoEscolhida: Int) {
        val correto = controller.validarResposta(opcaoEscolhida)

        if (controller.isUltimaRodada()) {
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
            builder.setMessage(getString(R.string.feedback_errou_mensagem, controller.getRespostaCorreta()))
        }

        builder.setPositiveButton(getString(R.string.feedback_btn_proxima)) { dialog, _ ->
            dialog.dismiss()
            carregarRodada()
        }

        builder.setCancelable(false)
        builder.show()
    }

    private fun mostrarResultadoFinal(ultimaRespostaCorreta: Boolean) {
        val builder = AlertDialog.Builder(this)

        val mensagemFeedback = if (ultimaRespostaCorreta) {
            getString(R.string.resultado_correto_final)
        } else {
            getString(R.string.resultado_errou_final, controller.getRespostaCorreta())
        }

        val nota = controller.calcularNota()
        val acertos = controller.getAcertos()
        val total = controller.getTotalRodadas()

        builder.setTitle(getString(R.string.resultado_titulo))
        builder.setMessage(
            "$mensagemFeedback${getString(R.string.resultado_nota, nota)}\n" +
            getString(R.string.resultado_acertos_rodadas, acertos, total)
        )

        builder.setPositiveButton(getString(R.string.resultado_btn_fechar)) { dialog, _ ->
            dialog.dismiss()
            finish()
        }

        builder.setCancelable(false)
        builder.show()
    }
}
