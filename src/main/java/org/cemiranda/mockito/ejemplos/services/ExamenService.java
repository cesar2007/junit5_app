package org.cemiranda.mockito.ejemplos.services;

import org.cemiranda.mockito.ejemplos.models.Examen;

public interface ExamenService {
  Examen findExamenPorNombre(String nombre) throws InterruptedException;
}
