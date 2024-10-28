package org.cemiranda.junit5app.ejemplos.models;

import org.cemiranda.junit5app.ejemplos.exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
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

  @Nested
  @DisplayName("probando atributos de cuenta corriente")
  class CuentaTestNombreSaldo{
    @Test
    @DisplayName("el nombre")
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
    @DisplayName("el saldo que no sea null, mayor que cero, valor esperado")
    void testSaldoCuenta() {
      cuenta = new Cuenta("César", new BigDecimal("1000.12345"));
      assertNotNull(cuenta.getSaldo());
      assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
      assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
      assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    @DisplayName("testeando referencias que no sean iguales con el método equals")
    void testReferenciaCuenta() {
      cuenta = new Cuenta("John Doe", new BigDecimal("8900.9997"));
      Cuenta cuenta2 = new Cuenta("John Doe", new BigDecimal("8900.9997"));

//    assertNotEquals(cuenta, cuenta2);
      assertEquals(cuenta2, cuenta);
    }
  }

  @Nested
  class CuentaOperacionesTest {
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
    void testTransferirDineroCuentas() {

      Cuenta cuentaOrigen = new Cuenta("Jhon Doe donador", new BigDecimal("2500"));
      Cuenta cuentaDestino = new Cuenta("Gina Doe receptora", new BigDecimal("1500"));

      Banco bbva = new Banco();
      bbva.setNombre("BBVA");
      bbva.transferir(cuentaOrigen, cuentaDestino, new BigDecimal(400));
      assertEquals("1900", cuentaDestino.getSaldo().toPlainString());
      assertEquals("2100", cuentaOrigen.getSaldo().toPlainString());

    }
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


  @Nested
  class SistemaOperativoTest {
    @Test
    @EnabledOnOs(OS.WINDOWS)
    void testSoloWindows() {
    }

    @Test
    @EnabledOnOs({OS.LINUX, OS.MAC})
    void testSoloLinuxMac() {
    }

    @DisabledOnOs(OS.WINDOWS)
    void testNoWindows(){
    }

  }

  @Nested
  class JavaVersionTest {
    @Test
    @EnabledOnJre(JRE.JAVA_8)
    void soloJdk8(){
    }

    @Test
    @EnabledOnJre(JRE.JAVA_11)
    void soloJdk11() {
    }

    @Test
    @DisabledOnJre(JRE.JAVA_15)
    void testNoJdK15() {
    }
  }

  @Nested
  class SystemPropertiesTest {
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
  }


  class VariableAmbienteTest {
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
  }

  // ejecutar esta prueba cuando estemos en desarrollo
  @Test
  @DisplayName("test Saldo Cuenta Dev")
  void testSaldoCuentaDev() {
    boolean esDev = "qa".equals(System.getProperty("ENV"));
    assumeTrue(esDev);
    cuenta = new Cuenta("César", new BigDecimal("1000.12345"));
    assertNotNull(cuenta.getSaldo());
    assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
    assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
    assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
  }

  @Test
  @DisplayName("test Saldo Cuenta Dev 2")
  void testSaldoCuentaDev2() {
    boolean esDev = "dev".equals(System.getProperty("ENV"));
    assumingThat(esDev, ()-> {
//      cuenta = new Cuenta("César", new BigDecimal("1000.12345"));
      assertNotNull(cuenta.getSaldo());
      assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
      assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
      assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    });
  }

  @DisplayName("Probando Debito Cuenta Repetir")
  @RepeatedTest(value=5, name = "Repetición número {currentRepetition} de {totalRepetitions}")
  void testDebitoCuentaRepetir(RepetitionInfo info) {
    if (info.getCurrentRepetition() == 3){
      System.out.println("repeticion 3");
    }
    cuenta.debito(new BigDecimal(100));
    assertNotNull(cuenta.getSaldo());
    assertEquals(900, cuenta.getSaldo().intValue());
    assertEquals("900.12345", cuenta.getSaldo().toPlainString());
  }

  @Nested
  class PruebasParametrizadasTest {
    @ParameterizedTest
//      (name = "numero {index} ejecutando con valor {0} - {argumentsWithNames}")
    @ValueSource(strings = {"100", "200", "300", "500", "700", "1000"})
    void testDebitoCuentaValueSource(String monto) {
      cuenta.debito(new BigDecimal(monto) );
      assertNotNull(cuenta.getSaldo());
      assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

    @ParameterizedTest
//      (name = "numero {index} ejecutando con valor {0} - {argumentsWithNames}")
    @CsvSource({"1,100", "2,200", "3,300", "4,500", "5,700", "6,1000"})
    void testDebitoCuentaCsvSource(String index, String monto) {
      System.out.println(index + " -> " + monto);
      cuenta.debito(new BigDecimal(monto) );
      assertNotNull(cuenta.getSaldo());
      assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

    @ParameterizedTest
//      (name = "numero {index} ejecutando con valor {0} - {argumentsWithNames}")
    @CsvSource({"200,100,John,Andres", "250,200,Pepe,Pepe", "301,300,Maria,Maria", "400,500,Cesar,Cesar", "750,700,Luci,Luci", "1000.12345,1000.12345,Lili,Lili"})
    void testDebitoCuentaCsvSource2(String saldo, String monto, String esperado, String actual) {
      System.out.println(saldo + " -> " + monto);
      cuenta.setSaldo(new BigDecimal(saldo));
      cuenta.debito(new BigDecimal(monto) );
      cuenta.setPersona(actual);
      assertNotNull(cuenta.getPersona());
      assertEquals(esperado, actual);
      assertNotNull(cuenta.getSaldo());
      assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

    @ParameterizedTest
//      (name = "numero {index} ejecutando con valor {0} - {argumentsWithNames}")
    @CsvFileSource(resources = "/data.csv")
    void testDebitoCuentaCsvFileSource( String monto) {
      cuenta.debito(new BigDecimal(monto) );
      assertNotNull(cuenta.getSaldo());
      assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

    @ParameterizedTest
//      (name = "numero {index} ejecutando con valor {0} - {argumentsWithNames}")
    @MethodSource("montoList")
    void testDebitoCuentaMethodSource( String monto) {
      cuenta.debito(new BigDecimal(monto) );
      assertNotNull(cuenta.getSaldo());
      assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }
  }

  static List<String> montoList () {
    return Arrays.asList("100", "200", "300", "500", "700", "1000");
  }


}