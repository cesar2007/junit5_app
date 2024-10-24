# JUnit 5 Unit Testing in Java 21 🚀

Este repositorio contiene ejemplos de **pruebas unitarias** utilizando **JUnit 5** y Java 21. A través de varios casos de prueba, se cubren aspectos esenciales de las funcionalidades de JUnit, como el ciclo de vida de pruebas, manejo de excepciones, y diversas anotaciones importantes.

## 📋 Contenido
- [Requisitos](#requisitos)
- [Características principales](#características-principales)
- [Anotaciones de JUnit 5](#anotaciones-de-junit-5)
- [Ejemplos de pruebas](#ejemplos-de-pruebas)
- [Ejecución de las pruebas](#ejecución-de-las-pruebas)
- [Referencias](#referencias)

---

## ✅ Requisitos
- **Java**: 21 o superior
- **JUnit**: 5.x
- **Maven** o **Gradle** (opcional, para la gestión de dependencias)
- **Git**: Para clonar este repositorio

---

## 🌟 Características principales
- Uso del ciclo de vida completo de JUnit: `@BeforeAll`, `@AfterAll`, `@BeforeEach`, `@AfterEach`.
- Pruebas parametrizadas y pruebas que dependen de propiedades del sistema.
- Manejo de excepciones con `assertThrows`.
- Validación de múltiples condiciones con `assertAll`.
- Uso de `@DisplayName` para una mejor documentación de las pruebas.
- Integración con GitHub como repositorio remoto.

---

## 📝 Anotaciones de JUnit 5
| Anotación                  | Descripción                                                             |
|----------------------------|-------------------------------------------------------------------------|
| `@Test`                    | Marca un método como caso de prueba.                                    |
| `@BeforeAll`               | Ejecuta código **una vez** antes de todos los tests.                    |
| `@AfterAll`                | Ejecuta código **una vez** después de todos los tests.                  |
| `@BeforeEach`              | Ejecuta código antes de **cada** test.                                  |
| `@AfterEach`               | Ejecuta código después de **cada** test.                                |
| `@DisplayName`             | Asigna un nombre amigable al test.                                      |
| `@Disabled`                | Desactiva temporalmente una prueba.                                     |
| `@EnabledIfSystemProperty` | Habilita la ejecución de un test si una propiedad del sistema coincide. |

---

## 📂 Ejemplos de pruebas

### 1. **Ciclo de vida de las pruebas**
```java
@BeforeAll
static void initAll() {
    System.out.println("Inicializando las pruebas...");
}

@AfterEach
void tearDown() {
    System.out.println("Finalizando cada prueba.");
}
