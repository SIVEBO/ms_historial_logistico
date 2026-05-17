package com.sivebo.ms_historial_logistico.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sivebo.ms_historial_logistico.model.HistorialLogistico;


public interface HistorialLogisticoRepository extends JpaRepository<HistorialLogistico, Object>{
        
        List<HistorialLogistico> findByIdGuia(Long idGuia);

        List<HistorialLogistico> findByIdEstado(Long idEstado);

        List<HistorialLogistico> findByIdSucursalActual(Long idGuia);

        List<HistorialLogistico> findByFechaHoraBetween(LocalDateTime comienzo, LocalDateTime fin);
}
