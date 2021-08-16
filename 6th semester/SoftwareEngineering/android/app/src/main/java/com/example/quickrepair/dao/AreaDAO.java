package com.example.quickrepair.dao;

import java.util.List;

public interface AreaDAO
{
    /**
     * Return all areas. Each area is a String.
     * @return Areas
     */
    public List<String> getAreas();
}
