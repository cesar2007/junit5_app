# JUnit 5 y Mockito en Java 21 🚀

Este repositorio contiene ejemplos prácticos de **pruebas unitarias** utilizando **JUnit 5** y **Mockito** en un entorno de Java 21. A través de diferentes casos de prueba, se muestra cómo integrar JUnit para validar la lógica de negocio y cómo emplear Mockito para crear **mocks** y realizar pruebas aisladas de dependencias.

El objetivo es demostrar cómo estas herramientas facilitan el desarrollo orientado a pruebas (TDD) en aplicaciones empresariales y simplifican el ciclo de vida de las pruebas.

---

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
- **Mockito**: 4.x o superior
- **Maven** o **Gradle** (opcional para gestionar dependencias)
- **Git**: Para clonar y administrar el repositorio

---

## 🌟 Características principales

- **JUnit 5**:
    - Uso del ciclo de vida completo (`@BeforeAll`, `@AfterEach`).
    - Validación de excepciones y condiciones múltiples con `assertThrows` y `assertAll`.
    - Pruebas parametrizadas para validar diferentes casos con un solo método.
    - Mejora en la documentación de pruebas con `@DisplayName`.

- **Mockito**:
    - Creación de mocks para aislar dependencias en las pruebas.
    - Configuración de comportamiento con `when(...).thenReturn(...)`.
    - Verificación de interacciones con `verify(...)`.

---

## 📌 ¿Qué es JUnit?

JUnit es un framework de pruebas unitarias para Java, utilizado para validar que cada unidad del código (por ejemplo, métodos y clases) funcione correctamente. Facilita la creación de pruebas automatizadas, mejorando la calidad del software y detectando errores de manera temprana en el desarrollo.

El ciclo de vida de las pruebas en JUnit se organiza con **anotaciones** como:

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

## 🔍 ¿Qué es Mockito?

Mockito es una librería para **simular objetos** en las pruebas unitarias. Su propósito principal es aislar el componente bajo prueba al crear **mocks** de sus dependencias, asegurando que solo se evalúe la lógica interna del componente sin involucrar otras partes del sistema.

Mockito se utiliza para:

- **Simular dependencias** complejas o lentas, como bases de datos o servicios externos.
- **Definir comportamiento** con `when(...).thenReturn(...)` para devolver valores esperados.
- **Verificar interacciones** entre objetos con `verify(...)`.

---


## 📂 Ejemplos de pruebas

1. **Prueba con JUnit:**  
   Validación de un método que filtra resultados en base al nombre, asegurando que las búsquedas devuelvan los resultados esperados.

2. **Uso de Mockito:**  
   Simulación de una dependencia (repositorio) para aislar la lógica de negocio del servicio. En este ejemplo, se utiliza un **mock** para evitar llamadas reales a la base de datos y se verifica que el servicio interactúe correctamente con la dependencia.

---

## ▶️ Cómo ejecutar las pruebas

1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/cesar2007/junit5_app.git
   cd tu-repositorio

