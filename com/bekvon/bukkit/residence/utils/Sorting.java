/*
 * Decompiled with CFR 0_119.
 */
package com.bekvon.bukkit.residence.utils;

import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Sorting {
    public Map<String, Integer> sortByValueASC(Map<String, Integer> unsortMap) {
        LinkedList<Map.Entry<String, Integer>> list2 = new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());
        Collections.sort(list2, new Comparator<Map.Entry<String, Integer>>(){

            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list2) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    public Map<String, Integer> sortByValueDESC(Map<String, Integer> unsortMap) {
        LinkedList<Map.Entry<String, Integer>> list2 = new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());
        Collections.sort(list2, new Comparator<Map.Entry<String, Integer>>(){

            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list2) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    public Map<String, Integer> sortByKeyDESC(Map<String, Integer> unsortMap) {
        LinkedList<Map.Entry<String, Integer>> list2 = new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());
        Collections.sort(list2, new Comparator<Map.Entry<String, Integer>>(){

            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getKey().compareTo(o1.getKey());
            }
        });
        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list2) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    public List<ClaimedResidence> sortResidences(List<ClaimedResidence> residences) {
        Map map = new HashMap<String, Object>();
        for (ClaimedResidence one : residences) {
            if (one == null || one.getName() == null) continue;
            map.put(one.getName().toLowerCase(), one);
        }
        map = this.sortByKeyASC(map);
        residences.clear();
        for (Map.Entry one : map.entrySet()) {
            residences.add((ClaimedResidence)one.getValue());
        }
        return residences;
    }

    public Map<String, Object> sortByKeyASC(Map<String, Object> unsortMap) {
        LinkedList<Map.Entry<String, Object>> list2 = new LinkedList<Map.Entry<String, Object>>(unsortMap.entrySet());
        Collections.sort(list2, new Comparator<Map.Entry<String, Object>>(){

            @Override
            public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        LinkedHashMap<String, Object> sortedMap = new LinkedHashMap<String, Object>();
        for (Map.Entry<String, Object> entry : list2) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    public Map<String, String> sortStringByKeyASC(Map<String, String> unsortMap) {
        LinkedList<Map.Entry<String, String>> list2 = new LinkedList<Map.Entry<String, String>>(unsortMap.entrySet());
        Collections.sort(list2, new Comparator<Map.Entry<String, String>>(){

            @Override
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        LinkedHashMap<String, String> sortedMap = new LinkedHashMap<String, String>();
        for (Map.Entry<String, String> entry : list2) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    public Map<String, Double> sortDoubleDESC(Map<String, Double> unsortMap) {
        LinkedList<Map.Entry<String, Double>> list2 = new LinkedList<Map.Entry<String, Double>>(unsortMap.entrySet());
        Collections.sort(list2, new Comparator<Map.Entry<String, Double>>(){

            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        LinkedHashMap<String, Double> sortedMap = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> entry : list2) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    public Map<String, Integer> sortASC(Map<String, Integer> unsortMap) {
        LinkedList<Map.Entry<String, Integer>> list2 = new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());
        Collections.sort(list2, new Comparator<Map.Entry<String, Integer>>(){

            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list2) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

}

