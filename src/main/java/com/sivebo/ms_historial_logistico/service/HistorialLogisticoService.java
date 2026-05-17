package com.sivebo.ms_historial_logistico.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.sivebo.ms_historial_logistico.dto.HistorialLogisticoRequestDTO;
import com.sivebo.ms_historial_logistico.dto.HistorialLogisticoResponseDTO;
import com.sivebo.ms_historial_logistico.model.HistorialLogistico;
import com.sivebo.ms_historial_logistico.repository.HistorialLogisticoRepository;
import com.sivebo.ms_historial_logistico.utils.WebClientUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistorialLogisticoService {
        
        private final HistorialLogisticoRepository historialLogisticoRepository;
        private final WebClientUtil webClientUtil;

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

        public Optional<HistorialLogisticoResponseDTO> getById(Long id) {
                return historialLogisticoRepository.findById(id).map(this::mapToDTO);
        }
        
        public List<HistorialLogisticoResponseDTO> getByIdGuia(Long idGuia){
                return historialLogisticoRepository.findByIdGuia(idGuia)
                        .stream().map(this::mapToDTO).toList();
        }

        public List<HistorialLogisticoResponseDTO> getByIdEstado(Long idEstado){
                return historialLogisticoRepository.findByIdEstado(idEstado)
                        .stream().map(this::mapToDTO).toList();
        }

        public List<HistorialLogisticoResponseDTO> getByIdSucursalActual(Long idGuia){
                return historialLogisticoRepository.findByIdSucursalActual(idGuia)
                        .stream().map(this::mapToDTO).toList();
        }

        public List<HistorialLogisticoResponseDTO> getByFecha(LocalDate fecha){
                return historialLogisticoRepository.findByFechaHoraBetween(fecha.atStartOfDay(), fecha.atTime(LocalTime.MAX))
                        .stream().map(this::mapToDTO).toList();
        }
        

        public HistorialLogisticoResponseDTO create(HistorialLogisticoRequestDTO dto){
                webClientUtil.validateMicroServiceById(dto.getIdGuia(), "guias_despacho", guiaDespachoWebClient);
                webClientUtil.validateMicroServiceById(dto.getIdEstado(), "guias_despacho", guiaDespachoWebClient);
                webClientUtil.validateMicroServiceById(dto.getIdSucursalActual(), "sucursales", sucursalWebClient);
                
                return mapToDTO(historialLogisticoRepository.save(
                        new HistorialLogistico(
                        null,
                        dto.getIdGuia(),
                        dto.getIdEstado(),
                        dto.getIdSucursalActual(),
                        dto.getFechaHora(),
                        dto.getComentario()
                        )
                ));
        }
        
        public Boolean delete(Long id){
                historialLogisticoRepository.deleteById(id);
                return !historialLogisticoRepository.existsById(id);
        }
}
