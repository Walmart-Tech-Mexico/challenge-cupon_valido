package com.walmarttechmexico.challengecuponvalido;

import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CouponValidatorTest {

    // ------- helpers -------
    private Clock fixedClockAt(String isoDate) {
        return Clock.fixed(
                Instant.parse(isoDate + "T00:00:00Z"),
                ZoneId.of("UTC")
        );
    }

    private String today(Clock clock) {
        return LocalDate.now(clock).toString();
    }

    private String toPlainDate(Instant instant, Clock clock) {
        return LocalDate.ofInstant(instant, clock.getZone()).toString();
    }

    // ------- valid coupons -------
    @Test
    void shouldValidateCouponWhenBeforeExpirationDate() {
        Clock currentDate = fixedClockAt("2025-07-15");

        CouponValidator validator = new CouponValidator(
                currentDate
        );

        String tomorrow = toPlainDate(
                currentDate.instant().plus(1, ChronoUnit.DAYS), currentDate
        );

        assertTrue(validator.validate("DESCUENTO20", tomorrow, false));
    }

    @Test
    void shouldValidateCouponWhenExpirationDateIsToday() {
        Clock currentDate = fixedClockAt("2025-07-14");
        CouponValidator validator = new CouponValidator(currentDate);

        String sameDay = today(currentDate);

        assertTrue(validator.validate("DESCUENTO20", sameDay, false));
    }

    // ------- invalid coupons -------
    @Test
    void shouldInvalidateWhenCouponIsExpired() {
        Clock currentDate = fixedClockAt("2025-07-14");
        CouponValidator validator = new CouponValidator(currentDate);

        String yesterday = toPlainDate(
                currentDate.instant().minus(1, ChronoUnit.DAYS), currentDate
        );

        assertFalse(validator.validate("DESCUENTO20", yesterday, false));
    }

    // ------- EDGE CASES: invalid data -------
    @Test
    void shouldInvalidateWhenCouponCodeIsInvalid() {
        CouponValidator validator = new CouponValidator(fixedClockAt("2025-07-14"));

        assertFalse(validator.validate("  ", "2025-07-24", false));
        assertFalse(validator.validate(null, "2025-07-24", false));
    }

    @Test
    void shouldInvalidateWhenCouponExpirationDateHasInvalidFormat() {
        CouponValidator validator = new CouponValidator(fixedClockAt("2025-07-14"));

        assertFalse(validator.validate("DESCUENTO20", "2025/07/24", false));
        assertFalse(validator.validate("DESCUENTO20", "2025-07-24T00:00:00Z", false));
    }

    @Test
    void shouldInvalidateWhenCouponExpirationDateIsNullOrBlank() {
        CouponValidator validator = new CouponValidator(fixedClockAt("2025-07-14"));

        assertFalse(validator.validate("DESCUENTO20", null, false));
        assertFalse(validator.validate("DESCUENTO20", " ", false));
    }

    @Test
    void shouldInvalidateWhenCouponHasBeenUsed() {
        CouponValidator validator = new CouponValidator(fixedClockAt("2025-07-14"));

        assertFalse(validator.validate("DESCUENTO20", "2025-07-24", true));
    }
}
