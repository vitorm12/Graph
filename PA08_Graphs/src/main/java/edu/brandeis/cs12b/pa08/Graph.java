package edu.brandeis.cs12b.pa08;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
/**
 * @author Vitor Mouzinho
 * Class: Cosi 12B
 * Date: March 31,2019
 * Reads in Json file with values of a graph
 * and then uses 
 * Simple implementation of a Graph data structure
 * uses Node classes as data and Graph as wrapper class
 * 
 * */

public class Graph {

	/**
	 * Build the graph from a json file
	 * @param the json filename
	 */
	HashMap<String, Node> verticeM = new HashMap<>();
	HashMap<String, Node> edgeM = new HashMap<>();

	public Graph(String filename) {
		JSONParser parser = new JSONParser();
		Object vertices = null;
		Object edges = null;
		Object obj = null;
		try {
			obj = parser.parse(new FileReader(filename));
			JSONObject file = (JSONObject) obj;
			vertices = file.get("vertices");
			edges = file.get("edges");

		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		parseVerticesA(vertices);
		parseEdges(edges);
	}
	/**
	 * @param Object containing edges
	 * method to populate add proper connections 
	 * */
	public void parseEdges(Object edges) {
		JSONArray edgesA = (JSONArray) edges;
		for (Object z : edgesA) {
			JSONObject x = (JSONObject) z;
			Node v1 = verticeM.get(x.get("v1"));
			Node v2 = verticeM.get(x.get("v2"));
			int weight = Integer.valueOf(x.get("weight").toString());
			v1.addConection(v2, weight);
			v2.addConection(v1, weight);
			v2.parent = v1;
		}
	}
	/**
	 * @param Object vertices 
	 * to populate verticeM with verticies
	 * */
	public void parseVerticesA(Object vertices) {
		JSONArray verticesA = (JSONArray) vertices;
		for (Object x : verticesA) {
			verticeM.put(x.toString(), new Node(x.toString()));
		}
	}

	/**
	 * Returns a Set of all vertices in the graph.
	 * @return the Set of vertices.
	 */
	public Set<String> getVertices() {return verticeM.keySet();}

	/**
	 * Return a Set of Vertices that are directly connected to vertex v
	 * 
	 * @param v the vertex
	 * @return A Set that contains all the vertices that are connected to v
	 */
	public Set<String> getNeighbors(String v) {
		Set<String> neighbors = new HashSet<String>();
		if (!verticeM.containsKey(v)) {
			return neighbors;
		}
		Node z = verticeM.get(v);
		for (Node name : z.paths.keySet()) {
			neighbors.add(name.vertice);
		}
		return neighbors;
	}

	/**
	 * Get the weight of the edge between vertex a and vertex b.
	 * 
	 * @param a one vertex
	 * @param b another vertex
	 * @return the edge weight or -1 if the edge doesn't exist in the graph.
	 */
	public int getEdgeWeight(String a, String b) {
		if (!(verticeM.containsKey(a) && verticeM.containsKey(b))) {
			return -1;
		}
		Node aN = verticeM.get(a);
		Node bN = verticeM.get(b);
		int x = aN.getWeight(bN);
		return x;
	}

	/**
	 * Gets the cost of the path (sum of the edge weights) represented by the passed
	 * ArrayList of vertices. If the path is invalid, (jumps between non-connected
	 * vertices or contains a vertex that doesn't exist) return -1
	 * 
	 * @param path a List of vertices
	 * @return the path length
	 */
	public int getPathCost(List<String> path) {
		int w = 0;
		for (int i = 0; i < path.size() - 1; i++) {
			Node x = verticeM.get(path.get(i));
			Node z = verticeM.get(path.get(i + 1));
			int m = x.getWeight(z);
			if (m == -1) {
				return -1;
			} else {
				w += m;
			}
		}
		return w;
	}

	/**
	 * @param start the name of the first vertex in the route
	 * @param end the name of the last vertex in the route
	 * @return Any route from start to end. If no route exists, return null
	 */
	public List<String> getRoute(String start, String end) {
		Node starter = verticeM.get(start);
		Node ender = verticeM.get(end);
		HashSet<Node> found = starter.hasPathDfs(starter, ender);
		List<Node> visited = new ArrayList<Node>(found);
		visited.remove(starter);
		visited.remove(ender);
		if (visited.isEmpty() && !(starter.paths.keySet().contains(ender))) {
			return null;
		}
		List<String> finalPath = new ArrayList<String>();
		finalPath.add(starter.vertice);
		for (Node x : visited) {
			finalPath.add(x.vertice);
		}
		finalPath.add(end);
		return finalPath;
	}

	/**
	 * Determines if the graph is a tree or not. * @return true if the graph is a
	 * tree and false if not.
	 */
	public boolean isTree() {
		List<Node> list = new ArrayList<Node>(verticeM.values());
		int count = 0;
		for (int i = 0; i < list.size(); i++) {
			Node root = list.get(i);
			if (root.parent == null) {
				count++;
			}
			if (count > 1) {
				return false;
			}
			for (Node x : root.getChildren()) {
				if (!(x.parent.getVertice().equals(root.vertice))) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Use Dijkstra's algorithm to find the shortest path from start to end
	 * @param start the name of the first vertex in the route
	 * @param end   the name of the last vertex in the route
	 * @return the shortest route from start to end. If no route exists, return null
	 */
	public List<String> getShortestRoute(String start, String end) {
		// TODO Implement me!
		return null;
	}

}
