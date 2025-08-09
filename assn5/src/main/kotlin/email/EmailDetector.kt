package org.example.email

import org.example.common.Detector
import org.example.common.tokenize

class EmailDetector : Detector {
    override fun detect(input: String): Boolean {
        if (input.isEmpty()) return false
        var state: EmailState = EmailStart
        var atCount = 0
        var dotAfterAtCount = 0
        var afterAt = false

        for (t in tokenize(input)) {
            val prev = state
            state = state.next(t)
            if (state === EmailInvalid) return false

            if (prev === EmailLocal && t == "@") {
                atCount++
                afterAt = true
                if (atCount > 1) return false
            } else if (afterAt && t == ".") {
                dotAfterAtCount++
                if (dotAfterAtCount > 1) return false
            }
        }
        return state === EmailSuffix && atCount == 1 && dotAfterAtCount == 1
    }
}
