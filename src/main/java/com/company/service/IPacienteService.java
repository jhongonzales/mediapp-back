package com.company.service;

import com.company.model.Paciente;

import java.util.List;

public interface IPacienteService {

    List<Paciente> buscarTodos();
    Paciente buscarPorId(Integer idPaciente);
    Paciente registrar(Paciente paciente);
    void eliminar(Integer idPaciente);

}
