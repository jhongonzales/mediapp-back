package com.company.serviceImp;

import com.company.model.Medico;
import com.company.repository.IGenericRepo;
import com.company.repository.IMedicoRepo;
import com.company.service.IMedicoService;
import org.springframework.stereotype.Service;

@Service
public class MedicoServiceImpl extends CRUDImpl<Medico, Integer> implements IMedicoService {
    private final IMedicoRepo repo;

    public MedicoServiceImpl(IMedicoRepo repo) {
        this.repo = repo;
    }
    @Override
    protected IGenericRepo<Medico, Integer> getRepository() {
        return this.repo;
    }
}


