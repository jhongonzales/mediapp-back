package com.company.service;

import com.company.model.Consulta;
import com.company.model.Examen;

import java.util.List;

public interface IConsultaService extends ICRUD<Consulta, Integer> {

    Consulta registrarTransaccional(Consulta consulta, List<Examen> examenes);
}
