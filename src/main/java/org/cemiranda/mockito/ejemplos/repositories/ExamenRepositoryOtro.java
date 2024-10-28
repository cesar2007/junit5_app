package org.cemiranda.mockito.ejemplos.repositories;


import org.cemiranda.mockito.ejemplos.models.Examen;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExamenRepositoryOtro implements ExamenRepository{
  @Override
  public List<Examen> findAll() throws InterruptedException {
    try {
      TimeUnit.SECONDS.sleep(5);
    } catch(InterruptedException e) {
      e.printStackTrace();
    }

    return null;
  }
}
