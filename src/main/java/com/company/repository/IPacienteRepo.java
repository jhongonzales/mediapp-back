package com.company.repository;

import com.company.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPacienteRepo extends JpaRepository<Paciente, Integer> {


}
