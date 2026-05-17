package com.sivebo.ms_historial_logistico.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sivebo.ms_historial_logistico.dto.HistorialLogisticoRequestDTO;
import com.sivebo.ms_historial_logistico.dto.HistorialLogisticoResponseDTO;
import com.sivebo.ms_historial_logistico.service.HistorialLogisticoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/historial_logistico")
@RequiredArgsConstructor
public class HistorialLogisticoController {
        
        private final HistorialLogisticoService historialLogisticoService;

        @GetMapping
        public List<HistorialLogisticoResponseDTO> getAll() {
                return historialLogisticoService.getAll();
        }
        
        @GetMapping("{id}")
        public ResponseEntity<HistorialLogisticoResponseDTO> getById(@PathVariable Long id) {
                return historialLogisticoService.getById(id)
                        .map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
        }
        

        @GetMapping("/search")
        public ResponseEntity<?> getByAtribute(
                @RequestParam(required = false) String idGuia,
                @RequestParam(required = false) String idEstado,
                @RequestParam(required = false) String idSucursal,
                @RequestParam(required = false) String fecha){

                List<String> params = new ArrayList<>(List.of(idGuia, idEstado, idSucursal, fecha));

                int num_null = 0;
                for(String value: params){
                        if(value == null) num_null++;
                }
                if(num_null == params.size()) {
                        return ResponseEntity.badRequest().body("Debe proporcionar un atributo de búsqueda valido");
                }else if(num_null > 1) {
                        return ResponseEntity.badRequest().body("Solo se permite un atributo de búsqueda a la vez");
                }else if((params.size() - num_null) == 1){

                        if(idGuia != null) {
                                log.info(">>> Buscando historial logistico por id de guia: {}", idGuia);
                                return ResponseEntity.ok(historialLogisticoService.getByIdGuia(Long.valueOf(idGuia)));
                        }else if(idEstado != null){
                                log.info(">>> Buscando historial logistico por id de estado: {}", idEstado);
                                return ResponseEntity.ok(historialLogisticoService.getByIdEstado(Long.valueOf(idEstado)));
                        }else if(idSucursal != null){
                                log.info(">>> Buscando historial logistico por id de sucursal: {}", idSucursal);
                                return ResponseEntity.ok(historialLogisticoService.getByIdSucursalActual(Long.valueOf(idSucursal)));
                        }else if(fecha != null){
                                log.info(">>> Buscando historial logistico por fecha: {}", fecha);
                                LocalDate fecha_formateada = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd-MM-YY"));
                                return ResponseEntity.ok(historialLogisticoService.getByFecha(fecha_formateada));
                        }

                }
                return ResponseEntity.internalServerError().body("Error en el URL query");
        }

        @PostMapping 
        public ResponseEntity<HistorialLogisticoResponseDTO> create(@Valid @RequestBody HistorialLogisticoRequestDTO dto) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(historialLogisticoService.create(dto));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<String> delete(@PathVariable Long id) {
                if (historialLogisticoService.delete(id)) {
                        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("HistorialLogistico eliminada");
                } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("HistorialLogistico no encontrada o no se pudo eliminar");
                }
        }
}
