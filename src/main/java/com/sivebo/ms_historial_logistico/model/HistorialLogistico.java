package com.sivebo.ms_historial_logistico.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="historial_logistico")
public class HistorialLogistico {
        
        @Id
        @GeneratedValue(strategy=GenerationType.IDENTITY)
        Long id;

        @Column(name="id_guia", nullable=false)
        Long idGuia;

        @Column(name="id_estado", nullable=false)
        Long idEstado;

        @Column(name="id_sucursal_actual", nullable=false)
        Long idSucursalActual;

        @Column(name="fecha_hora", nullable=false)
        LocalDateTime fechaHora;

        @Column(name="comentario", nullable=false)
        String comentario;
}
