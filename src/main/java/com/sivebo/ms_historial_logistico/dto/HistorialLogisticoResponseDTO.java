package com.sivebo.ms_historial_logistico.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialLogisticoResponseDTO {

        Long id;
        Long idGuia;
        Long idEstado;
        Long idSucursalActual;
        LocalDateTime fechaHora;
        String comentario;
}
