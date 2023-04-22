package playground.algorithms.graph;

import playground.Node;

import java.util.*;
import java.util.stream.Collectors;

public class CloneGraph {

    public void makeCopyAndCheckNeighbors(Node node, Node copy, Map<Integer, Node> visited) {
        visited.put(copy.val, copy);

        // add neighbors to copy
        for (Node originalNeighbor : node.neighbors) {
            // if (visited[originalNeighbor.val] == null) {   // if haven't seen this one yet, make a copy of it and add as originalNeighbor to copy
            if (visited.get(originalNeighbor.val) == null) {
                Node copyNeighbor = new Node(originalNeighbor.val);
                copy.neighbors.add(copyNeighbor);

                makeCopyAndCheckNeighbors(originalNeighbor, copyNeighbor, visited);
            } else {
                // have seen this one, already copy out there. Get the copy. set it as a originalNeighbor if the current copy
                copy.neighbors.add(visited.get(originalNeighbor.val));
            }
        }
    }

    public Node performClone(Node node) {
        Map<Integer, Node> visited = new HashMap<>();
        Node copy = new Node(node.val);

        makeCopyAndCheckNeighbors(node, copy, visited);

        return copy;
    }



    private Node copyNode(Node node, Map<Integer, Node> copiesByVal) {
        Node copy = new Node(node.val);
        copiesByVal.put(copy.val, copy);

        return copy;
    }

    private void processQueue(Queue<Node> nodesYetToProcessNeighbors, Map<Integer, Node> copiesByVal) {
        while (!nodesYetToProcessNeighbors.isEmpty()) {
            Node curr = nodesYetToProcessNeighbors.poll();

            for (Node neighbor : curr.neighbors) {
                if (!copiesByVal.containsKey(neighbor.val)) {
                    copyNode(neighbor, copiesByVal);
                    nodesYetToProcessNeighbors.offer(neighbor);
                }

                Node copyOfNeighbor = copiesByVal.get(neighbor.val);
                Node copyOfCurr = copiesByVal.get(curr.val);

                copyOfCurr.neighbors.add(copyOfNeighbor);
            }
        }
    }

    public Node performBfsClone(Node node) {
        if (node == null) {
            return null;
        }

        Map<Integer, Node> copiesByVal = new HashMap<>();
        Queue<Node> nodesYetToProcessNeighbors = new LinkedList<>();

        Node newNode = copyNode(node, copiesByVal);
        nodesYetToProcessNeighbors.offer(node);

        processQueue(nodesYetToProcessNeighbors, copiesByVal);

        return newNode;
    }


    // make preMap
    // build up preMap
    // build up total course list (Set w/ unique items)
    // build visitingSet
    // for each course, check if it can be complete (dfs):
    // if already visiting course, return false (detected loop)
    // if course doesn't have any prereqs, return true
    // mark as visiting
    // look at this courses neighbors (prereqs) "do dfs on them"
    // get return val and try to return false if one is false (contains a loop)

    // if make it past, remove from visiting & set course's prereqs as empty list since we were able
    // return true for this course

    private boolean dfs(Map<Integer, List<Integer>> preMap, Set<Integer> visiting, Integer course) {
        if (visiting.contains(course)) return false;
        if (preMap.get(course).isEmpty()) return true;

        visiting.add(course);

        for (Integer prereq: preMap.get(course)) {
            if (!dfs(preMap, visiting, prereq)) return false;
        }

        visiting.remove(course);
        preMap.put(course, new ArrayList<>());

        return true;
    }

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        Map<Integer, List<Integer>> preMap = new HashMap<>();
        Set<Integer> courses = new HashSet<>();
        Set<Integer> visiting = new HashSet<>();

        for (int[] pair : prerequisites) {
            preMap.computeIfAbsent(pair[0], k -> new ArrayList<>());
            preMap.computeIfAbsent(pair[1], k -> new ArrayList<>());
            preMap.get(pair[0]).add(pair[1]);

            courses.add(pair[0]);
            courses.add(pair[1]);
        }

        for (Integer course : courses) {
            if (!dfs(preMap, visiting, course)) return false;
        }

        return true;
    }


























}
