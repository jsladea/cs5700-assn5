import org.example.float.FloatDetector
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class FloatDetectorTests {
    private val d = FloatDetector()

    @ParameterizedTest
    @ValueSource(strings = ["1.0", "123.34", "0.20000", ".123", "9.9", "12349871234.12340981234098"])
    fun validFloats(s: String) = assertEquals(true, d.detect(s))

    @ParameterizedTest
    @ValueSource(strings = [
        "",          // empty
        "123",       // no dot
        "123.123.",  // too many dots (late)
        "012.4",     // 0 not followed by '.'
        "0",         // just zero
        ".",         // dot not followed by digit
        "123.",      // nothing after dot
        "a.1",       // invalid first
        "1.a",       // invalid after dot
        "1.0.0",     // too many dots (middle)
        " .1",       // space
        "1. ",       // trailing space
    ])
    fun invalidFloats(s: String) = assertEquals(false, d.detect(s))

    @Test
    fun earlyDotPath() {
        // Starts with '.', must go to FRACTION
        assertEquals(true, d.detect(".5"))
        // Fails if not followed by digit
        assertEquals(false, d.detect(".x"))
    }

    @Test
    fun zeroThenDotBranch() {
        assertEquals(true, d.detect("0.1"))
        assertEquals(false, d.detect("0x1"))
    }

    @Test
    fun leadingZeros() {
        assertEquals(false, d.detect("00.1")) // invalid: multiple leading zeros
        assertEquals(false, d.detect("01.2")) // invalid: 0 not followed by '.'
    }

    @Test
    fun onlyDot() {
        assertEquals(false, d.detect(".")) // just a dot, no digits
    }

    @Test
    fun dotAtEnd() {
        assertEquals(false, d.detect("1.")) // dot at end, no fraction
    }

    @Test
    fun multipleDots() {
        assertEquals(false, d.detect("1..2")) // two dots in a row
        assertEquals(false, d.detect("1.2.3")) // more than one dot
    }

    @Test
    fun negativeCases() {
        assertEquals(false, d.detect("-1.2")) // negative not allowed
        assertEquals(false, d.detect("+1.2")) // plus not allowed
        assertEquals(false, d.detect("1.2e3")) // exponent not allowed
    }

    @Test
    fun validEdgeCases() {
        assertEquals(true, d.detect("0.1")) // minimal valid with leading zero
        assertEquals(true, d.detect(".1")) // minimal valid with leading dot
        assertEquals(true, d.detect("1.0")) // minimal valid with trailing zero
    }

    @Test
    fun invalidCharacters() {
        assertEquals(false, d.detect("1a.2")) // letter in integer part
        assertEquals(false, d.detect("1.a2")) // letter in fraction part
        assertEquals(false, d.detect("1.2a")) // letter after valid float
    }

    @Test
    fun whitespace() {
        assertEquals(false, d.detect(" 1.2")) // leading space
        assertEquals(false, d.detect("1.2 ")) // trailing space
        assertEquals(false, d.detect("1 .2")) // space in the middle
    }

    @Test
    fun emptyString() {
        assertEquals(false, d.detect("")) // already covered, but for completeness
    }
}
