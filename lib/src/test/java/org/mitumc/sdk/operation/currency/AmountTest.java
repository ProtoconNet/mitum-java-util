package org.mitumc.sdk.operation.currency;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mitumc.sdk.exception.NumberRangeException;
import org.mitumc.sdk.exception.StringFormatException;
import org.mitumc.sdk.operation.Amount;

public class AmountTest {
    @DisplayName("Test amount")
    @Test
    void testAmount() {
        assertDoesNotThrow(() -> Amount.get("PEN", "100"));
        assertDoesNotThrow(() -> Amount.get("PEN",
                "99999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999"));
        
        assertThrows(StringFormatException.class, () -> Amount.get("", "100"));
        assertThrows(StringFormatException.class, () -> Amount.get("P", "100"));
        assertThrows(StringFormatException.class, () -> Amount.get("PE", "100"));
        assertThrows(StringFormatException.class, () -> Amount.get("01234567890", "100"));

        assertThrows(NumberFormatException.class, () -> Amount.get("PEN", ""));
        assertThrows(NumberFormatException.class, () -> Amount.get("PEN", "abc"));
        assertThrows(NumberFormatException.class, () -> Amount.get("PEN", "abc100"));

        assertThrows(NumberRangeException.class, () -> Amount.get("PEN", "0"));
    }
}
