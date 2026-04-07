package com.duoc.FixNow.Service;

import com.duoc.FixNow.Model.Estado;
import com.duoc.FixNow.Model.Incidencia;
import com.duoc.FixNow.Repository.IncidenciaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IncidenciaService {

    private final IncidenciaRepository incidenciaRepository;

    public IncidenciaService(IncidenciaRepository incidenciaRepository) {
        this.incidenciaRepository = incidenciaRepository;
    }

    public List<Incidencia> listarTIncidencias() {
        return incidenciaRepository.findAll();
    }

    public Incidencia crear(Incidencia incidencia) {
        incidencia.setFechaRegistro(LocalDate.now());
        return incidenciaRepository.save(incidencia);
    }

    public Incidencia obtenerPorId(Long id) {

        return incidenciaRepository.findById(id).orElse(null);
    }

    public Incidencia actualizar(Long id, Incidencia nuevosDatos) {

        return incidenciaRepository.findById(id).map(existente -> {
            existente.setTitulo(nuevosDatos.getTitulo());
            existente.setDescripcion(nuevosDatos.getDescripcion());
            existente.setEstado(nuevosDatos.getEstado());
            existente.setPrioridad(nuevosDatos.getPrioridad());
            return incidenciaRepository.save(existente);
        }).orElse (null);
    }

    public boolean eliminarIncidencia(Long id) {
        boolean incidenciaBuscada = incidenciaRepository.existsById(id);
        if (incidenciaBuscada) {
            incidenciaRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public List<Incidencia> incidenciaPorEstado(Estado estado){
        return incidenciaRepository.findByEstado(estado);
    }

}