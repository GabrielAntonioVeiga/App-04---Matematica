package ufpr.veiga.matematica.controller

import ufpr.veiga.matematica.constants.AppConstants
import ufpr.veiga.matematica.model.Operador
import ufpr.veiga.matematica.model.Questao
import kotlin.random.Random

class AritmeticaController {

    private var questaoAtual: Questao? = null
    private var numeroQuestaoAtual = 0
    private var acertos = 0

    private var questoesDaPartida: List<Questao> = emptyList()
    fun gerarTodasAsQuestoesValidas(): List<Questao> {
        val todasQuestoes = mutableListOf<Questao>()
        val min = AppConstants.NUMERO_MINIMO // 0
        val max = AppConstants.NUMERO_MAXIMO // 9

        for (n1 in min..max) {
            for (n2 in min..max) {
                // SOMA
                val resultadoSoma = n1 + n2

                if (n1 <= n2) {
                    todasQuestoes.add(Questao(n1, n2, Operador.SOMA, resultadoSoma))
                }

                if (n1 >= n2) {
                    val resultadoSubtracao = n1 - n2
                    todasQuestoes.add(Questao(n1, n2, Operador.SUBTRACAO, resultadoSubtracao))
                }
            }
        }
        return todasQuestoes
    }

    fun iniciarJogo() {
        questoesDaPartida = gerarTodasAsQuestoesValidas().shuffled().take(5)
    }

    fun gerarQuestao(): Questao {
        numeroQuestaoAtual++

       val questao = questoesDaPartida[numeroQuestaoAtual - 1]

        questaoAtual = Questao(questao.numero1, questao.numero2, questao.operador, questao.resultado)
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
