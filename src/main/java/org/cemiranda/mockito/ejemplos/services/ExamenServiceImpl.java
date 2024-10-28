package org.cemiranda.mockito.ejemplos.services;

import org.cemiranda.mockito.ejemplos.models.Examen;
import org.cemiranda.mockito.ejemplos.repositories.ExamenRepository;

import java.util.Optional;

public class ExamenServiceImpl implements ExamenService{
  private ExamenRepository examenRepository;

  public ExamenServiceImpl(ExamenRepository examenRepository) {
    this.examenRepository = examenRepository;
  }

  @Override
  public Examen findExamenPorNombre(String nombre) throws InterruptedException {
    Optional<Examen> examenOptional = examenRepository.findAll()
        .stream()
        .filter(e -> e.getNombre().contains(nombre))
        .findFirst();
    Examen examen = null;
    if(examenOptional.isPresent()) {
      examen = examenOptional.orElseThrow();
    }
    return examen;
  }
}
