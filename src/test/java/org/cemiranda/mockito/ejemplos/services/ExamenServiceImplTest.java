package org.cemiranda.mockito.ejemplos.services;

import org.cemiranda.mockito.ejemplos.models.Examen;
import org.cemiranda.mockito.ejemplos.repositories.ExamenRepository;
import org.cemiranda.mockito.ejemplos.repositories.ExamenRepositoryImpl;
import org.cemiranda.mockito.ejemplos.repositories.ExamenRepositoryOtro;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExamenServiceImplTest {

  @Test
  void findExamenPorNombre() throws InterruptedException {
    ExamenRepository repository = mock(ExamenRepositoryOtro.class);
    ExamenService service = new ExamenServiceImpl(repository);

    // se define lal ista de datos que retornará el mock
    List<Examen> datos = Arrays.asList(new Examen(5L, "Matemáticas"), new Examen(6L, "Idiomas"),
        new Examen(7L, "Historia"));

    // se indica que cuando se llame a findAll() en el mock, se devuelven los datos definidos
    when(repository.findAll()).thenReturn(datos);

    // se ejecuta la prueba buscar un examenp or nombre
    Examen examen = service.findExamenPorNombre("Matemáticas");

    assertNotNull(examen);
    assertEquals(5L, examen.getId());
    assertEquals("Matemáticas", examen.getNombre());

  }
}