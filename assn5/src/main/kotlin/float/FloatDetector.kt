package org.example.float

import org.example.common.Detector
import org.example.common.Token
import org.example.common.tokenize

class FloatDetector : Detector {
    override fun detect(input: String): Boolean {
        if (input.isEmpty()) return false
        var state: FloatState = FPStart
        var dotCount = 0
        for (t in tokenize(input)) {
            if (t == ".") {
                dotCount++
                if (dotCount > 1) return false
            } else if (!Token.isDigit(t)) {
                return false // only digits and '.'
            }
            state = state.next(t)
            if (state === FPInvalid) return false
        }
        return state.isAccepting() && dotCount == 1
    }
}
