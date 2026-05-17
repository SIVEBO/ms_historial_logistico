package com.sivebo.ms_historial_logistico.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialLogisticoRequest {

        @NotNull(message="el id de guia es obligatorio.")
        Long idGuia;

        @NotNull(message="el id de estado es obligatorio.")
        Long idEstado;

        @NotNull(message="el id de sucursal actual es obligatorio.")
        Long idSucursalActual;

        @NotNull(message="el id de fecha y hora es obligatorio.")
        LocalDateTime fechaHora;

        String comentario;

}
