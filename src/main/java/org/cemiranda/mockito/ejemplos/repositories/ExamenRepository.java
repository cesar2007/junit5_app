package org.cemiranda.mockito.ejemplos.repositories;

import org.cemiranda.mockito.ejemplos.models.Examen;

import java.util.List;

public interface ExamenRepository {
  List<Examen> findAll() throws InterruptedException;
}
