package org.cemiranda.mockito.ejemplos.repositories;

import org.cemiranda.mockito.ejemplos.models.Examen;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ExamenRepositoryImpl implements ExamenRepository{
  @Override
  public List<Examen> findAll() {
    return
        Collections.emptyList();
        /**/
  }
}
