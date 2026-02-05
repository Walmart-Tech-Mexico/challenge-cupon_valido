package com.walmarttechmexico.challengecuponvalido;

import java.time.Clock;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CouponValidator {

    private final Clock clock;

    public CouponValidator() {
        this.clock = Clock.systemDefaultZone();
    }

    public CouponValidator(Clock clock) {
        this.clock = clock;
    }

    /**
     * Valida un cupon
     *
     * @param couponCode        codigo del cupon, no puede ser vacio o nulo
     * @param expirationDate    solo acepta YYYY-MM-DD, por la especificación del reto
     * @param used              true: usado, false: no usado.
     * @return boolean          true si es valido, false si es invalido.
     *                          Un Cupon invalido puede ser por:
     *                              - datos inválidos
     *                              - el cupon ya fue usado
     *                              - el cupon ya expiró
     */
    public boolean validate(String couponCode, String expirationDate, boolean used) {
        if (used) return false;
        if (couponCode == null || couponCode.isBlank()) return false;
        if (expirationDate == null || expirationDate.isBlank()) return false;

        try {
            return !LocalDate
                    .parse(expirationDate, DateTimeFormatter.ISO_LOCAL_DATE)
                    .isBefore(LocalDate.now(clock));
        } catch (DateTimeException e) {
            return false;
        }
    }
}
