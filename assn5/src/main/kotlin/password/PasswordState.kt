package org.example.password

import org.example.common.Token

interface PasswordState {
    fun next(tok: String, ctx: PassContext): PasswordState
    fun isAccepting(ctx: PassContext): Boolean
}

data class PassContext(
    var hasUpper: Boolean = false,
    var hasSpecial: Boolean = false,
    var endedWithSpecial: Boolean = false
)

object PassInvalid : PasswordState {
    override fun next(tok: String, ctx: PassContext) = this
    override fun isAccepting(ctx: PassContext) = false
}

object PassStart : PasswordState {
    override fun next(tok: String, ctx: PassContext): PasswordState {
        // Spaces and any other characters are allowed by the spec.
        if (Token.isUpper(tok)) ctx.hasUpper = true
        if (Token.isSpecial(tok)) ctx.hasSpecial = true
        return PassBody
    }
    override fun isAccepting(ctx: PassContext) = false
}

object PassBody : PasswordState {
    override fun next(tok: String, ctx: PassContext): PasswordState {
        // Accept any character; just track the two required features.
        if (Token.isUpper(tok)) ctx.hasUpper = true
        if (Token.isSpecial(tok)) ctx.hasSpecial = true
        return this
    }
    override fun isAccepting(ctx: PassContext): Boolean {
        // End-of-input acceptance is decided by PasswordDetector, which sets endedWithSpecial.
        return ctx.hasUpper && ctx.hasSpecial && !ctx.endedWithSpecial
    }
}
