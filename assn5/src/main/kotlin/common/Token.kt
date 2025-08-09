package org.example.common

object Token {
    val DIGITS = setOf("0","1","2","3","4","5","6","7","8","9")
    val NONZERO_DIGITS = setOf("1","2","3","4","5","6","7","8","9")
    val BINARY = setOf("0", "1")
    val SPECIALS = setOf("!", "@", "#", "$", "%", "&", "*")

    val UPPER = ('A'..'Z').map { it.toString() }.toSet()
    val LOWER = ('a'..'z').map { it.toString() }.toSet()

    fun isDigit(s: String) = s in DIGITS
    fun isNonZeroDigit(s: String) = s in NONZERO_DIGITS
    fun isBinary(s: String) = s in BINARY
    fun isUpper(s: String) = s in UPPER
    fun isLower(s: String) = s in LOWER
    fun isLetter(s: String) = isUpper(s) || isLower(s)
    fun isSpecial(s: String) = s in SPECIALS
}
