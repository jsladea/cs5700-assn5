package org.example.binary

interface BinaryState {
    fun next(tok: String): BinaryState
    fun isAccepting(): Boolean
}

object BinInvalid : BinaryState {
    override fun next(tok: String) = this
    override fun isAccepting() = false
}

object BinStart : BinaryState {
    override fun next(tok: String): BinaryState =
        if (tok == "1") BinOne else BinInvalid
    override fun isAccepting() = false
}

object BinOne : BinaryState {
    override fun next(tok: String): BinaryState = when (tok) {
        "1" -> this
        "0" -> BinZero
        else -> BinInvalid
    }
    override fun isAccepting() = true
}

object BinZero : BinaryState {
    override fun next(tok: String): BinaryState = when (tok) {
        "0" -> this
        "1" -> BinOne
        else -> BinInvalid
    }
    override fun isAccepting() = false
}
