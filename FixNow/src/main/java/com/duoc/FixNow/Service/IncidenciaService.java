package com.duoc.FixNow.Service;

import com.duoc.FixNow.Model.Estado;
import com.duoc.FixNow.Model.Incidencia;
import com.duoc.FixNow.Repository.IncidenciaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IncidenciaService {

    private final IncidenciaRepository incidenciaRepository;

    public IncidenciaService(IncidenciaRepository incidenciaRepository) {
        this.incidenciaRepository = incidenciaRepository;
    }

    public List<Incidencia> listaDeIncidencias() {
        return incidenciaRepository.findAll();
    }

    public Incidencia crear(Incidencia incidencia) {
        incidencia.setFechaRegistro(LocalDate.now());
        return incidenciaRepository.save(incidencia);
    }

    public Incidencia obtenerPorId(Long id) {

        return incidenciaRepository.findById(id).orElse(null);
    }

    public Optional<Incidencia> actualizarIncidencia(Long id, Incidencia incidenciaActualizada) {
        // Utilizacion de una arrow Function para mapear directamente la entidad con los valores de sus atributos actualizados
        return incidenciaRepository.findById(id).map(incidencia ->{
            incidencia.setTitulo(incidenciaActualizada.getTitulo());
            incidencia.setDescripcion(incidenciaActualizada.getDescripcion());
            incidencia.setEstado(incidenciaActualizada.getEstado());
            incidencia.setPrioridad(incidenciaActualizada.getPrioridad());
            incidencia.setUsuarioReportante(incidenciaActualizada.getUsuarioReportante());
            return incidenciaRepository.save(incidencia);
        });
    }
    public Optional<Incidencia> buscarIncidenciaPorId(Long id) {
        return incidenciaRepository.findById(id);
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