package com.walmarttechmexico.challengecuponvalido;

// Reto para el desarrollador:
// Completa la función 'validarCupon' para que determine si un cupón es válido.
// Considera los siguientes criterios:
// 1. El cupón no debe haber sido usado previamente.
// 2. La fecha de expiración del cupón no debe haber pasado.
// 3. Si la fecha de expiración no es válida, el cupón se considera inválido.

public class Main {
    public static void main(String[] args) {
        // Datos del cupón (ejemplo) - ¡Puedes modificarlos para probar tu solución!
        String codigoCupon = "DESCUENTO20";
        String fechaExpiracion = "2025-07-15"; // Formato AAAA-MM-DD
        boolean usadoPreviamente = false;

        // Validar el cupón (¡Aquí es donde el desarrollador debe trabajar!)
        boolean esValido = validarCupon(codigoCupon, fechaExpiracion, usadoPreviamente);

        // Imprimir el resultado
        if (esValido) {
            System.out.println("El cupón es válido.");
        } else {
            System.out.println("El cupón no es válido.");
        }
    }

    public static boolean validarCupon(String codigo, String fechaExpiracion, boolean usado) {
        return new CouponValidator()
                .validate(codigo, fechaExpiracion, usado);
    }
}
