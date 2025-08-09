package org.example.email

interface EmailState {
    fun next(tok: String): EmailState
    fun isAccepting(): Boolean
}

object EmailInvalid : EmailState {
    override fun next(tok: String) = this
    override fun isAccepting() = false
}

object EmailStart : EmailState {
    override fun next(tok: String): EmailState = when {
        tok == " " || tok == "@" -> EmailInvalid
        else -> EmailLocal
    }
    override fun isAccepting() = false
}

object EmailLocal : EmailState {
    override fun next(tok: String): EmailState = when (tok) {
        " " -> EmailInvalid
        "@" -> EmailAt
        else -> this
    }
    override fun isAccepting() = false
}

object EmailAt : EmailState {
    override fun next(tok: String): EmailState = when (tok) {
        " ", "@", "." -> EmailInvalid
        else -> EmailDomain
    }
    override fun isAccepting() = false
}

object EmailDomain : EmailState {
    override fun next(tok: String): EmailState = when (tok) {
        " " -> EmailInvalid
        "@" -> EmailInvalid
        "." -> EmailDot
        else -> this
    }
    override fun isAccepting() = false
}

object EmailDot : EmailState {
    override fun next(tok: String): EmailState = when (tok) {
        " ", "@", "." -> EmailInvalid
        else -> EmailSuffix
    }
    override fun isAccepting() = false
}

object EmailSuffix : EmailState {
    override fun next(tok: String): EmailState = when (tok) {
        " ", "@", "." -> EmailInvalid
        else -> this
    }
    override fun isAccepting() = true
}
