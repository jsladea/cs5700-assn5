package org.example.float

import org.example.common.Token

interface FloatState {
    fun next(tok: String): FloatState
    fun isAccepting(): Boolean
}

object FPInvalid : FloatState {
    override fun next(tok: String) = this
    override fun isAccepting() = false
}

object FPStart : FloatState {
    override fun next(tok: String): FloatState = when {
        Token.isNonZeroDigit(tok) -> FPIntPart
        tok == "0" -> FPZero
        tok == "." -> FPDotStart
        else -> FPInvalid
    }
    override fun isAccepting() = false
}

object FPIntPart : FloatState {
    override fun next(tok: String): FloatState = when {
        Token.isDigit(tok) -> this
        tok == "." -> FPDotSeen
        else -> FPInvalid
    }
    override fun isAccepting() = false
}

object FPZero : FloatState {
    override fun next(tok: String): FloatState =
        if (tok == ".") FPDotSeen else FPInvalid
    override fun isAccepting() = false
}

object FPDotStart : FloatState {
    override fun next(tok: String): FloatState =
        if (Token.isDigit(tok)) FPFraction else FPInvalid
    override fun isAccepting() = false
}

object FPDotSeen : FloatState {
    override fun next(tok: String): FloatState =
        if (Token.isDigit(tok)) FPFraction else FPInvalid
    override fun isAccepting() = false
}

object FPFraction : FloatState {
    override fun next(tok: String): FloatState =
        if (Token.isDigit(tok)) this else FPInvalid
    override fun isAccepting() = true
}
