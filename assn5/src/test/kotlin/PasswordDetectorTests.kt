import org.example.password.PasswordDetector
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.junit.jupiter.api.Test

class PasswordDetectorTest {

    private val d = PasswordDetector()

    // -------- Valid per assignment --------
    @ParameterizedTest
    @ValueSource(strings = [
        // Given valid examples
        "aaaaH!aa",
        "1234567*9J",
        "asdpoihj;loikjasdf;ijp;lij2309jasd;lfkm20ij@aH",

        // Spaces are allowed
        "Pass word*X",      // space in middle, special in middle, ends not special
        "     A!A",         // 5 leading spaces + 'A' + '!' + 'A' => length 8, ends with letter
        "Aaaaaa! a",        // special not last; space near end; has uppercase

        // Boundary: exactly 8 chars
        "Aaaaaa!a",         // length 8, has upper + special, does not end special

        // Mixed specials (in set): ends with non-special
        "AA##aaaa",         // has uppercase + valid specials, ends 'a'
        "X\$aaaaaa"          // length 8, special '$' present, uppercase 'X', ends 'a'
    ])
    fun validPasswords(s: String) = assertEquals(true, d.detect(s), "Expected valid: `$s`")

    // -------- Invalid per assignment --------
    @ParameterizedTest
    @ValueSource(strings = [
        // Given invalid examples
        "a",                // too short
        "aaaaaaa!",         // no uppercase + ends with special
        "aaaHaaaaa",        // no special
        "Abbbbbbb!",        // ends with special

        // Missing special (only uppercase present)
        "PasswrdX",         // length 8, uppercase yes, special no
        "AAAAAAA1",         // uppercase yes, digit yes, special no

        // Ends with special (disallowed)
        "Aaaaaaaa*",        // ends with '*'
        "Aaaaaaa@",         // ends with '@'

        // Boundary: length 7
        "AAAAA!a",          // 7 chars -> too short even though rest OK

        // Non-set punctuation doesn't satisfy "special"
        "Aaaaaaa?a",        // '?' is NOT in !@#$%&*, so no valid special -> invalid

        // Spaces allowed, but still must meet rules
        "      A!",         // 6 spaces + 'A' + '!' = 8, ends with '!' (special) -> invalid
        "Aaaaaaa ",         // ends with space OK, but has no special -> invalid
    ])
    fun invalidPasswords(s: String) = assertEquals(false, d.detect(s), "Expected invalid: `$s`")

    // -------- Focused edge tests --------
    @Test
    fun lastCharSpecialCheck() {
        // Ends with special -> invalid
        assertEquals(false, d.detect("aaaaaaaH!"))
        // Special before last is fine
        assertEquals(true, d.detect("aaaaaaa!H"))
        // Trailing space is allowed (space is not special)
        assertEquals(true, d.detect("Aaaaaa!a "))
    }

    @Test
    fun spacesAreCountedTowardLength() {
        // 5 spaces + A + ! + A = 8 characters -> valid (has upper + special, ends not special)
        assertEquals(true, d.detect("     A!A"))
        // 4 spaces + A + ! + A = 7 -> too short
        assertEquals(false, d.detect("    A!A"))
    }

    @Test
    fun specialSetIsExactlyBangAtHashDollarPercentAmpStar() {
        // '?' not in the allowed set, so without another allowed special, invalid
        assertEquals(false, d.detect("Abcde?fg"))   // 8 chars, upper yes, special (valid set) no
        // With a valid special from the set, it becomes valid
        assertEquals(true, d.detect("Abcde?*fg"))
        assertEquals(true, d.detect("Abcdefg@H"))   // valid: '*' present, uppercase, ends 'H'
    }
}


