package ufpr.veiga.matematica.controller

import ufpr.veiga.matematica.R
import ufpr.veiga.matematica.constants.AppConstants
import ufpr.veiga.matematica.model.RodadaContagem
import kotlin.random.Random

class ContagemController {

    private val imagensDisponiveis = listOf(
        R.drawable.masha_e_o_urso,
        R.drawable.pele_xuxa,
        R.drawable.peppa_pig_aquatica,
        R.drawable.coxa,
        R.drawable.nokia,
        R.drawable.baruch_spinoza,
        R.drawable.flamengo,
        R.drawable.pista_tubarao,
        R.drawable.sigma,
        R.drawable.virginia_e_vini_junior
    )

    private var rodadaAtual: RodadaContagem? = null
    private var numeroRodadaAtual = 0
    private var acertos = 0

    private var paresPerguntas: List<Pair<Int, Int>> = emptyList()


    fun iniciarJogo() {
        val imagensSorteadasUnicas = imagensDisponiveis.shuffled().take(5)
        val quantidadesSorteadasUnicas = (1..9).toList().shuffled().take(5)

        paresPerguntas = imagensSorteadasUnicas.zip(quantidadesSorteadasUnicas)
    }
    fun gerarRodada(): RodadaContagem {
        numeroRodadaAtual++

        val pergunta = paresPerguntas[numeroRodadaAtual - 1]
        val quantidade = pergunta.second
        val opcoes = gerarOpcoes(quantidade)

        val imagemSorteada = pergunta.first

        rodadaAtual = RodadaContagem(imagemSorteada, quantidade, opcoes, quantidade)
        return rodadaAtual!!
    }

    private fun gerarOpcoes(respostaCorreta: Int): List<Int> {
        val opcoes = mutableSetOf<Int>()
        opcoes.add(respostaCorreta)

        while (opcoes.size < 3) {
            val opcaoErrada = Random.nextInt(1, 10)
            if (opcaoErrada != respostaCorreta) {
                opcoes.add(opcaoErrada)
            }
        }

        return opcoes.shuffled()
    }

    fun validarResposta(opcaoEscolhida: Int): Boolean {
        val correto = rodadaAtual?.respostaCorreta == opcaoEscolhida
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
