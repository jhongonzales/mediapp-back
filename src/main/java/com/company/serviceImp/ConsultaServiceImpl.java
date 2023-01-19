package com.company.serviceImp;

import com.company.model.Consulta;
import com.company.model.Examen;
import com.company.repository.IConsultaExamenRepo;
import com.company.repository.IConsultaRepo;
import com.company.repository.IGenericRepo;
import com.company.service.IConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConsultaServiceImpl extends CRUDImpl<Consulta, Integer> implements IConsultaService {

    private final IConsultaRepo repo;

    public ConsultaServiceImpl(IConsultaRepo repo) {
        this.repo = repo;
    }

    @Autowired
    private IConsultaExamenRepo consultaExamenRepo;

    @Override
    protected IGenericRepo<Consulta, Integer> getRepository() {
        return this.repo;
    }

    @Transactional
    @Override
    public Consulta registrarTransaccional(Consulta consulta, List<Examen> examenes) {
        consulta.getDetalleConsulta().forEach(det -> det.setConsulta(consulta));

        repo.save(consulta);

        examenes.forEach(ex -> consultaExamenRepo.registrar(consulta.getIdConsulta(), ex.getIdExamen()));

        return consulta;
    }
}
