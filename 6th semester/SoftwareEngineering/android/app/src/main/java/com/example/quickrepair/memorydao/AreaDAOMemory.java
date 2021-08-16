package com.example.quickrepair.memorydao;

import com.example.quickrepair.dao.AreaDAO;

import java.util.Arrays;
import java.util.List;

public class AreaDAOMemory implements AreaDAO
{
    protected static List<String> entities = Arrays.asList(
            "Acharnes",
            "Agia Paraskevi",
            "Agia Varvara",
            "Agioi Anargyroi",
            "Agios Dimitrios",
            "Agios Ioannis Rentis",
            "Agios Nikolaos",
            "Agrinio",
            "Aigio",
            "Alexandreia",
            "Alexandroupoli",
            "Alimos",
            "Amaliada",
            "Ampelokipoi",
            "Ano Liosia",
            "Argos",
            "Argyroupoli",
            "Arta",
            "Artemida",
            "Aspropyrgos",
            "Athens");
    /**
     * Return all areas. Each area is a String.
     * @return Areas
     */
    @Override
    public List<String> getAreas() {
        return entities;
    }
}
