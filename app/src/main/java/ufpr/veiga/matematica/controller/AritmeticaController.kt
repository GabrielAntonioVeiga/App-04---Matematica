package ufpr.veiga.matematica.controller

import ufpr.veiga.matematica.constants.AppConstants
import ufpr.veiga.matematica.model.Operador
import ufpr.veiga.matematica.model.Questao
import kotlin.random.Random

class AritmeticaController {

    private var questaoAtual: Questao? = null
    private var numeroQuestaoAtual = 0
    private var acertos = 0

    fun gerarQuestao(): Questao {
        numeroQuestaoAtual++

        val operador = if (Random.nextBoolean()) Operador.SOMA else Operador.SUBTRACAO

        val numero1: Int
        val numero2: Int

        if (operador == Operador.SUBTRACAO) {
            numero1 = Random.nextInt(AppConstants.NUMERO_MINIMO, AppConstants.NUMERO_MAXIMO + 1)
            numero2 = Random.nextInt(AppConstants.NUMERO_MINIMO, numero1 + 1)
        } else {
            numero1 = Random.nextInt(AppConstants.NUMERO_MINIMO, AppConstants.NUMERO_MAXIMO + 1)
            numero2 = Random.nextInt(AppConstants.NUMERO_MINIMO, AppConstants.NUMERO_MAXIMO + 1)
        }

        val resultado = when (operador) {
            Operador.SOMA -> numero1 + numero2
            Operador.SUBTRACAO -> numero1 - numero2
        }

        questaoAtual = Questao(numero1, numero2, operador, resultado)
        return questaoAtual!!
    }

    fun validarResposta(respostaUsuario: Int): Boolean {
        val correto = questaoAtual?.resultado == respostaUsuario
        if (correto) {
            acertos++
        }
        return correto
    }

    fun calcularNota(): Int {
        return acertos * AppConstants.PONTOS_POR_QUESTAO
    }

    fun getQuestaoAtual(): Int = numeroQuestaoAtual

    fun getTotalQuestoes(): Int = AppConstants.TOTAL_QUESTOES

    fun getAcertos(): Int = acertos

    fun isUltimaQuestao(): Boolean = numeroQuestaoAtual >= AppConstants.TOTAL_QUESTOES

    fun getResultadoCorreto(): Int? = questaoAtual?.resultado
}
