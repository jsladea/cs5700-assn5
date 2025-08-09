import org.example.common.Token
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class TokenTests {
    @Test
    fun testIsDigit() {
        assertTrue(Token.isDigit("0"))
        assertTrue(Token.isDigit("5"))
        assertTrue(Token.isDigit("9"))
        assertFalse(Token.isDigit("a"))
        assertFalse(Token.isDigit("."))
        assertFalse(Token.isDigit("10"))
    }

    @Test
    fun testIsNonZeroDigit() {
        assertTrue(Token.isNonZeroDigit("1"))
        assertTrue(Token.isNonZeroDigit("9"))
        assertFalse(Token.isNonZeroDigit("0"))
        assertFalse(Token.isNonZeroDigit("a"))
        assertFalse(Token.isNonZeroDigit("."))
    }

    @Test
    fun testIsBinary() {
        assertTrue(Token.isBinary("0"))
        assertTrue(Token.isBinary("1"))
        assertFalse(Token.isBinary("2"))
        assertFalse(Token.isBinary("a"))
    }

    @Test
    fun testIsUpper() {
        assertTrue(Token.isUpper("A"))
        assertTrue(Token.isUpper("Z"))
        assertFalse(Token.isUpper("a"))
        assertFalse(Token.isUpper("1"))
        assertFalse(Token.isUpper("@"))
    }

    @Test
    fun testIsLower() {
        assertTrue(Token.isLower("a"))
        assertTrue(Token.isLower("z"))
        assertFalse(Token.isLower("A"))
        assertFalse(Token.isLower("1"))
        assertFalse(Token.isLower("@"))
    }

    @Test
    fun testIsLetter() {
        assertTrue(Token.isLetter("A"))
        assertTrue(Token.isLetter("z"))
        assertFalse(Token.isLetter("1"))
        assertFalse(Token.isLetter("@"))
    }

    @Test
    fun testIsSpecial() {
        for (s in Token.SPECIALS) {
            assertTrue(Token.isSpecial(s))
        }
        assertFalse(Token.isSpecial("a"))
        assertFalse(Token.isSpecial("1"))
        assertFalse(Token.isSpecial("."))
    }
}