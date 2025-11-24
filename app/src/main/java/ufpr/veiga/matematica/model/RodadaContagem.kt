package ufpr.veiga.matematica.model

data class RodadaContagem(
    val imagemResId: Int,
    val quantidade: Int,
    val opcoes: List<Int>,
    val respostaCorreta: Int
)
