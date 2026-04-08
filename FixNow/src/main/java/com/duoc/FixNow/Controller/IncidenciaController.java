package com.duoc.FixNow.Controller;


import com.duoc.FixNow.Model.Estado;
import com.duoc.FixNow.Model.Incidencia;
import com.duoc.FixNow.Service.IncidenciaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
    @RequestMapping("api/v1/incidencias")
    public class IncidenciaController {

        private final IncidenciaService incidenciaService;

        public IncidenciaController(IncidenciaService incidenciaService) {
            this.incidenciaService = incidenciaService;
        }

        @GetMapping
        public ResponseEntity<?> listarIncidencias() {
            try{
                List<Incidencia> incidencias = incidenciaService.listaDeIncidencias();
                return ResponseEntity.ok(incidencias); // Retorna estado 200;
            }catch(Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error al obtener las incidencias");
            }
        }

        @GetMapping("/{id}")
        public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
            try {
                Incidencia incidenciaBuscada = incidenciaService.obtenerPorId(id)
                        .orElseThrow(() -> new NoSuchElementException("Incidencia no encontrada"));
                return ResponseEntity.ok(incidenciaBuscada);
            } catch (NoSuchElementException e) {
                // Capturamos específicamente el 404 antes de la excepcion generica
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            } catch(Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error al obtener la incidencia por ID: " + id);
            }
        }


        @GetMapping("/estado/{estado}")
        public ResponseEntity<?> buscarIncidenciaPorEstado(@PathVariable Estado estado) {
            try{
                List<Incidencia> listaIncidencias = incidenciaService.incidenciaPorEstado(estado);
                return ResponseEntity.ok(listaIncidencias);
            }catch(Exception e){

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener las incidencias por estado: " + estado);

            }
        }

        @PostMapping
        public ResponseEntity<?> crear(@Valid @RequestBody Incidencia incidencia) {
            try{
                Incidencia nuevaIncidencia = incidenciaService.crear(incidencia);
                return ResponseEntity.status(HttpStatus.CREATED).body(nuevaIncidencia);
            }catch(Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al agregar incidencia: " + incidencia);
            }
        }

        @PutMapping("/{id}")
        public ResponseEntity<?> actualizar(@PathVariable Long id,@Valid @RequestBody Incidencia incidenciaActualizada) {
            try{
                Incidencia incidencia = incidenciaService.actualizar(id,incidenciaActualizada)
                        .orElseThrow(() -> new NoSuchElementException("Incidencia no encontrada por ID: " + id));
                return ResponseEntity.ok(incidencia);
            }catch (NoSuchElementException e){
                // Capturo el codigo 404
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Incidencia no encontrada por ID: " + id);
            }catch(Exception e){
                //Envio codigo 500 (generico)
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar incidencia ");
            }
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<?> eliminarIncidencia(@PathVariable Long id) {
            try{
                if(incidenciaService.eliminarIncidencia(id)){
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                }
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La Incidencia buscada no ha sido encontrada");
            }catch(Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar incidencia: " + id);
            }
        }

    }

    }
}
