package org.cemiranda.junit5app.ejemplos.models;

import org.cemiranda.junit5app.ejemplos.exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

  @Test
  void testNombreCuenta() {
    Cuenta cuenta = new Cuenta("Andres", new BigDecimal("1000.12345"));
//    cuenta.setPersona("Andres");
    String esperado = "Andres";
    String real = cuenta.getPersona();
    assertNotNull(real);
    assertEquals(esperado, real);
  }

  @Test
  void testSaldoCuenta() {
    Cuenta cuenta = new Cuenta("Andres", new BigDecimal("1000.12345"));
    assertNotNull(cuenta.getSaldo());
    assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
    assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
    assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
  }

  @Test
  void testReferenciaCuenta() {
    Cuenta cuenta = new Cuenta("John Doe", new BigDecimal("8900.9997"));
    Cuenta cuenta2 = new Cuenta("John Doe", new BigDecimal("8900.9997"));

//    assertNotEquals(cuenta, cuenta2);
    assertEquals(cuenta2, cuenta);
  }

  @Test
  void testDebitoCuenta() {
    Cuenta cuenta = new Cuenta("Cesar", new BigDecimal("1000.12345"));
    cuenta.debito(new BigDecimal(100));
    assertNotNull(cuenta.getSaldo());
    assertEquals(900, cuenta.getSaldo().intValue());
    assertEquals("900.12345", cuenta.getSaldo().toPlainString());
  }

  @Test
  void testCreditoCuenta() {
    Cuenta cuenta = new Cuenta("Cesar", new BigDecimal("1000.12345"));
    cuenta.credito(new BigDecimal(100));
    assertNotNull(cuenta.getSaldo());
    assertEquals(1100, cuenta.getSaldo().intValue());
    assertEquals("1100.12345", cuenta.getSaldo().toPlainString());
  }

  @Test
  void dineroInsuficienteException() {
    Cuenta cuenta = new Cuenta("Cesar", new BigDecimal("1000.12345"));
    Exception exception = assertThrows(DineroInsuficienteException.class, () -> {
      cuenta.debito(new BigDecimal(2000));
    });
    String actual = exception.getMessage();
    String esperado = "Dinero insuficiente";
    assertEquals(esperado, actual);
  }

  @Test
  void testTransferirDineroCuentas() {

    Cuenta cuentaOrigen = new Cuenta("Jhon Doe donador", new BigDecimal("2500"));
    Cuenta cuentaDestino = new Cuenta("Gina Doe receptora", new BigDecimal("1500"));

    Banco bbva = new Banco();
    bbva.setNombre("BBVA");
    bbva.transferir(cuentaOrigen, cuentaDestino, new BigDecimal(400));
    assertEquals("1900", cuentaDestino.getSaldo().toPlainString());
    assertEquals("2100", cuentaOrigen.getSaldo().toPlainString());

  }

  @Test
  void testRelacionBancoCuentas() {

    Cuenta cuentaOrigen = new Cuenta("Jhon Doe donador", new BigDecimal("2500"));
    Cuenta cuentaDestino = new Cuenta("Cesar", new BigDecimal("1500"));

    Banco bbva = new Banco();
    bbva.addCuenta(cuentaOrigen);
    bbva.addCuenta(cuentaDestino);
    bbva.setNombre("BBVA");
    bbva.transferir(cuentaOrigen, cuentaDestino, new BigDecimal(400));
    assertEquals("1900", cuentaDestino.getSaldo().toPlainString());
    assertEquals("2100", cuentaOrigen.getSaldo().toPlainString());

    assertEquals(2, bbva.getCuentas().size());
    assertEquals("BBVA", cuentaOrigen.getBanco().getNombre());
    assertEquals("Cesar", bbva.getCuentas().stream()
        .filter(c -> c.getPersona().equals("Cesar"))
        .findFirst()
        .get().getPersona());
  }
}