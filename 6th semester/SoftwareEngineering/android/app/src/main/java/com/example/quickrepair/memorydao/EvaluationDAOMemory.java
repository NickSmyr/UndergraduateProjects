package com.example.quickrepair.memorydao;

import com.example.quickrepair.dao.EvaluationDAO;
import com.example.quickrepair.domain.Evaluation;

import java.util.ArrayList;
import java.util.List;

public class EvaluationDAOMemory implements EvaluationDAO {
    protected static ArrayList<Evaluation> entities = new ArrayList<Evaluation>();

    /**
     * Delete an Evaluation
     *
     * @param entity Evaluation
     */
    @Override
    public void delete(Evaluation entity) {
        entities.remove(entity);
    }

    /**
     * Find all Evaluations
     *
     * @return Evaluations
     */
    @Override
    public List<Evaluation> findAll() {
        ArrayList<Evaluation> result = new ArrayList<Evaluation>();
        result.addAll(entities);
        return result;
    }

    /**
     * Save a Evaluation
     *
     * @param entity Evaluation
     */
    @Override
    public void save(Evaluation entity) {
        entities.add(entity);
    }

    /**
     * Find the Evaluation with the given id
     *
     * @param evaluationID Evaluation's id
     * @return Evaluation or null
     */
    @Override
    public Evaluation find(int evaluationID) {
        for(Evaluation technician : entities)
            if(technician.getUid() == evaluationID)
                return technician;
        return null;
    }

    /**
     * Return the next id for an Evaluation.
     *
     * @return Evaluation's id
     */
    @Override
    public int nextId() {
        return (entities.size() > 0 ? entities.get(entities.size()-1).getUid()+1 : 1);
    }
}
