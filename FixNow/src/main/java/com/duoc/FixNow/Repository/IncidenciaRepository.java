package com.duoc.FixNow.Repository;

import com.duoc.FixNow.Model.Estado;
import com.duoc.FixNow.Model.Incidencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncidenciaRepository extends JpaRepository<Incidencia,Long> {
    List<Incidencia> findByEstado(Estado estado);
}
