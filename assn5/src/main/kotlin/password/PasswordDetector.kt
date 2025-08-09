package org.example.password

import org.example.common.Detector
import org.example.common.Token
import org.example.common.tokenize

class PasswordDetector : Detector {
    override fun detect(input: String): Boolean {
        if (input.length < 8) return false
        val ctx = PassContext()
        var state: PasswordState = PassStart
        val toks = tokenize(input)
        for ((idx, t) in toks.withIndex()) {
            ctx.endedWithSpecial = (Token.isSpecial(t) && idx == toks.lastIndex)
            state = state.next(t, ctx)
            if (state === PassInvalid) return false
        }
        return state.isAccepting(ctx)
    }
}
