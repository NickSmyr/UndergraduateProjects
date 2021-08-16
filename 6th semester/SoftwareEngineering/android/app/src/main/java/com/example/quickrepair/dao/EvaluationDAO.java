package com.example.quickrepair.dao;


import com.example.quickrepair.domain.Evaluation;

import java.util.List;

public interface EvaluationDAO {
    /**
     * Delete an Evaluation
     * @param entity Evaluation
     */
    void delete(Evaluation entity);

    /**
     * Find all Evaluations
     * @return Evaluations
     */
    List<Evaluation> findAll();

    /**
     * Save a Evaluation
     * @param entity Evaluation
     */
    void save(Evaluation entity);

    /**
     * Find the Evaluation with the given id
     * @param evaluationID Evaluation's id
     * @return Evaluation or null
     */
    Evaluation find(int evaluationID);

    /**
     * Return the next id for an Evaluation.
     * @return Evaluation's id
     */
    int nextId();
}
