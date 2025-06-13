package com.cali.config;

import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class CoveredArea {

    private final List<Pair<Integer, Integer>> area;

    public CoveredArea(List<Pair<Integer, Integer>> area) {
        this.area = area;
    }

    public List<Pair<Integer, Integer>> getArea() {
        return area;
    }
}
