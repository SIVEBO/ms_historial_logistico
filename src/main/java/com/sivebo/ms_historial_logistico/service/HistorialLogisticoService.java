package com.sivebo.ms_historial_logistico.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.sivebo.ms_historial_logistico.dto.HistorialLogisticoResponseDTO;
import com.sivebo.ms_historial_logistico.model.HistorialLogistico;
import com.sivebo.ms_historial_logistico.repository.HistorialLogisticoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistorialLogisticoService {
        
        private final HistorialLogisticoRepository historialLogisticoRepository;

        @Qualifier("guiaDespachoWebClient")
        private final WebClient guiaDespachoWebClient;

        @Qualifier("sucursalWebClient")
        private final WebClient sucursalWebClient;

        private HistorialLogisticoResponseDTO mapToDTO(HistorialLogistico historialLogistico){
                return new HistorialLogisticoResponseDTO(
                        historialLogistico.getId(),
                        historialLogistico.getIdGuia(),
                        historialLogistico.getIdEstado(),
                        historialLogistico.getIdSucursalActual(),
                        historialLogistico.getFechaHora(),
                        historialLogistico.getComentario()
                );
        }

        public List<HistorialLogisticoResponseDTO> getAll(){
                return historialLogisticoRepository.findAll()
                        .stream().map(this::mapToDTO).toList();
        }

        
        
}
