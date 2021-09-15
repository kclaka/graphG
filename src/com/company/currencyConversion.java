package com.company;

import java.util.*;

class currencyConversion{

    public static double calculate(String start, String end,  List<Node> data){

        HashMap<String, HashMap<String, Double>> graph = new HashMap<>();

        for(Node node : data) {
            if(!graph.containsKey(node.dividend)){
                graph.put(node.dividend, new HashMap<>());
            }

            if(!graph.containsKey(node.divisor)){
                graph.put(node.divisor, new HashMap<>());
            }

            graph.get(node.dividend).put(node.divisor, node.value);
            graph.get(node.divisor).put(node.dividend, 1/node.value);
        }

        double result ;
        HashSet<String> visited = new HashSet<>();

        if (!graph.containsKey(start) || !graph.containsKey(end)){
            result = -1.0;
        } else if (start.equals(end)){
            result = 1.0;
        }else{
            result = dfs(graph, start, end, 1, visited);
        }


        return result;
    }

    private static double dfs(HashMap<String, HashMap<String, Double>> graph, String start, String end, double actualProduct, HashSet<String> visited) {
        visited.add(start);

        double res = -1.0;

        Map<String, Double> neighbors = graph.get(start);

        if(neighbors.containsKey(end)){
            res = actualProduct * neighbors.get(end);
        }else{
            for(Map.Entry<String, Double> pair : neighbors.entrySet()){
                String nextNode = pair.getKey();
                if(visited.contains(nextNode)){
                    continue;
                }

                res = dfs(graph, nextNode, end, actualProduct * pair.getValue(), visited);

                if(res != -1.0) break;
            }
        }

        visited.remove(start);
        return res;
    }

    static class Node{
        String dividend;
        String divisor;
        double value;

        public Node(String d, String di, double v){
            this.dividend = d;
            this.divisor = di;
            this.value = v;
        }
    }

    public static void main(String[] args) {
        List<Node> data = new ArrayList<>();

        data.add(new Node("USD", "JPY", 110));
        data.add(new Node("USD", "AUD", 1.45));
        data.add(new Node("JPY", "GBP", 0.0070));

        System.out.println(calculate("GBP", "JPY", data));


    }


}




