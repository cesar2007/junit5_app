package org.cemiranda.junit5app.ejemplos.models;

import org.cemiranda.junit5app.ejemplos.exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;

import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CuentaTest {

  Cuenta cuenta;

  @BeforeEach
  void initMetodoTest() {
    this.cuenta = new Cuenta("Cesar", new BigDecimal("1000.12345"));
    System.out.println("iniciando el método.");
  }

  @AfterEach
  void tearDown() {
    System.out.println("finalizando método de prueba");
  }

  @BeforeAll
  static void beforeAll() {
    System.out.println("inicializando el test");
  }

  @AfterAll
  static void afterAll() {
    System.out.println("finalizando el test");
  }

  @Test
  @DisplayName("probando nombre de la cuenta")
  void testNombreCuenta() {
    Cuenta cuenta = new Cuenta("César", new BigDecimal("1000.12345"));
    cuenta.setPersona("César");
    String esperado = "César";
    String real = cuenta.getPersona();
//    assertNotNull(real, "La cuenta no puede ser nula");
    assertNotNull(real, () -> "La cuenta no puede ser nula");
    assertEquals(esperado, real, "el nombre de la cuenta no es el que se esperaba");
    assertTrue(real.equals("César"), "esperado debe ser igual al real");
  }

  @Test
  @DisplayName("probando saldo de la cuenta corriente, que no sea null, mayor que cero, valor esperado")
  void testSaldoCuenta() {
    cuenta = new Cuenta("César", new BigDecimal("1000.12345"));
    assertNotNull(cuenta.getSaldo());
    assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
    assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
    assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
  }

  @Test
  void testReferenciaCuenta() {
    cuenta = new Cuenta("John Doe", new BigDecimal("8900.9997"));
    Cuenta cuenta2 = new Cuenta("John Doe", new BigDecimal("8900.9997"));

//    assertNotEquals(cuenta, cuenta2);
    assertEquals(cuenta2, cuenta);
  }

  @Test
  void testDebitoCuenta() {
    cuenta.debito(new BigDecimal(100));
    assertNotNull(cuenta.getSaldo());
    assertEquals(900, cuenta.getSaldo().intValue());
    assertEquals("900.12345", cuenta.getSaldo().toPlainString());
  }

  @Test
  void testCreditoCuenta() {
    cuenta.credito(new BigDecimal(100));
    assertNotNull(cuenta.getSaldo());
    assertEquals(1100, cuenta.getSaldo().intValue());
    assertEquals("1100.12345", cuenta.getSaldo().toPlainString());
  }

  @Test
  void dineroInsuficienteException() {
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
//  @Disabled
  @DisplayName("probando relaciones entre las cuentas y el banco con assertAll")
  void testRelacionBancoCuentas() {
//    fail();
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

    assertTrue(bbva.getCuentas().stream()
        .anyMatch(c -> c.getPersona().equals("Cesar")));

//    assertAll(
//        () -> assertEquals("2200", cuentaOrigen.getSaldo().toPlainString()),
//        () -> assertEquals(2, bbva.getCuentas().size()),
//        () -> assertEquals("BBVA.", cuentaOrigen.getBanco().getNombre()),
//        () -> assertEquals("Cesar", bbva.getCuentas().stream()
//            .filter(c -> c.getPersona().equals("Cesar")).findFirst()
//            .get().getPersona()),
//        () -> assertTrue(bbva.getCuentas().stream().anyMatch(c -> c.getPersona()
//            .equals("Cesar")))
//    );
  }

  @Test
  @EnabledOnOs(OS.WINDOWS)
  void testSoloWindows() {
  }

  @Test
  @EnabledOnOs({OS.LINUX, OS.MAC})
  void testSoloLinuxMac() {
  }

  @Test
  @DisabledOnOs(OS.WINDOWS)
  void testNoWindows(){
  }

  @Test
  @EnabledOnJre(JRE.JAVA_8)
  void soloJdk8(){

  }

  // habilitar si existe cierta propiedad de sistema
  @Test
  void imprimirSystemProperties() {
    Properties properties = System.getProperties();
    properties.forEach((k, v) -> System.out.println(k + ":" + v));
  }

  @Test
  @EnabledIfSystemProperty(named = "java.version", matches="21.0.4")
  void testJavaVersion() {

  }

  @Test
  @DisabledIfSystemProperty(named = "os.arch", matches = ".*32.*")
  void testSolo64(){

  }

  @Test
  @EnabledIfSystemProperty(named = "os.arch", matches = ".*32.*")
  void testNo64(){

  }

  @Test
  @EnabledIfSystemProperty(named = "user.country", matches = "US")
  void name() {
  }

  @Test
  @EnabledIfSystemProperty(named = "ENV", matches = "dev")
  void testDev() {
  }

  @Test
  void imprimirVariablesAmbiente() {
    Map<String, String> getenv = System.getenv();
    getenv.forEach((k, v) -> System.out.println(k + " = " + v ));
  }

  @Test
  @EnabledIfEnvironmentVariable(named = "JAVA_HOME", matches = ".*jdk-17.*")
  void testJavaHome(){
  }

  @Test
  @EnabledIfEnvironmentVariable(named = "NUMBER_OF_PROCESSORS", matches = "13")
  void testProcesadores(){
  }

  @Test
  @EnabledIfEnvironmentVariable(named = "ENVIRONMENT", matches = "dev")
  void testEnv() {
  }

  // ejecutar esta prueba cuando estemos en desarrollo
  @Test
  @DisplayName("test Saldo Cuenta Dev")
  void testSaldoCuentaDev() {
    boolean esDev = "dev".equals(System.getProperty("ENV"));
    assumeTrue(esDev);
    cuenta = new Cuenta("César", new BigDecimal("1000.12345"));
    assertNotNull(cuenta.getSaldo());
    assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
    assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
    assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
  }
}