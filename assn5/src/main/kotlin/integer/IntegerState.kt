package org.example.integer

import org.example.common.Token

interface IntegerState {
    fun next(tok: String): IntegerState
    fun isAccepting(): Boolean
}

object IntegerInvalid : IntegerState {
    override fun next(tok: String) = this
    override fun isAccepting() = false
}

object IntegerStart : IntegerState {
    override fun next(tok: String): IntegerState =
        if (Token.isNonZeroDigit(tok)) IntegerInNumber else IntegerInvalid
    override fun isAccepting() = false
}

object IntegerInNumber : IntegerState {
    override fun next(tok: String): IntegerState =
        if (Token.isDigit(tok)) this else IntegerInvalid
    override fun isAccepting() = true
}
