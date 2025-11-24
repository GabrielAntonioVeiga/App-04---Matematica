package ufpr.veiga.matematica.controller

import ufpr.veiga.matematica.constants.AppConstants
import ufpr.veiga.matematica.model.Rodada
import kotlin.random.Random

class MaiorNumeroController {

    private var rodadaAtual: Rodada? = null
    private var numeroRodadaAtual = 0
    private var acertos = 0

    fun gerarRodada(): Rodada {
        numeroRodadaAtual++

        val digito1 = Random.nextInt(AppConstants.NUMERO_MINIMO, AppConstants.NUMERO_MAXIMO + 1)
        val digito2 = Random.nextInt(AppConstants.NUMERO_MINIMO, AppConstants.NUMERO_MAXIMO + 1)
        val digito3 = Random.nextInt(AppConstants.NUMERO_MINIMO, AppConstants.NUMERO_MAXIMO + 1)

        val respostaCorreta = calcularMaiorNumero(digito1, digito2, digito3)

        rodadaAtual = Rodada(digito1, digito2, digito3, respostaCorreta)
        return rodadaAtual!!
    }

    private fun calcularMaiorNumero(d1: Int, d2: Int, d3: Int): Int {
        val digitos = listOf(d1, d2, d3).sortedDescending()
        return digitos.joinToString("").toInt()
    }

    fun validarResposta(respostaUsuario: Int): Boolean {
        val correto = rodadaAtual?.respostaCorreta == respostaUsuario
        if (correto) {
            acertos++
        }
        return correto
    }

    fun calcularNota(): Int {
        return acertos * AppConstants.PONTOS_POR_QUESTAO
    }

    fun getRodadaAtual(): Int = numeroRodadaAtual

    fun getTotalRodadas(): Int = AppConstants.TOTAL_QUESTOES

    fun getAcertos(): Int = acertos

    fun isUltimaRodada(): Boolean = numeroRodadaAtual >= AppConstants.TOTAL_QUESTOES

    fun getRespostaCorreta(): Int? = rodadaAtual?.respostaCorreta
}
