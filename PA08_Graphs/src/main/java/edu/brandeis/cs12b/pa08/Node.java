package edu.brandeis.cs12b.pa08;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Node {

	HashMap<Node, Integer> paths = new HashMap<Node, Integer>();
	Node parent;
	Node children;
	String vertice;

	Node(String vertice) {
		this.vertice = vertice;
	}
	/**
	 * @return List<Node> returns the connections of specific node
	 * method attempts to try and transform Graph into tree
	 * 
	 * */
	public List<Node> getChildren() {
		paths.remove(parent);
		List<Node> children = new ArrayList<Node>(paths.keySet());
		return children;
	}
	/**
	 * @param node to connect
	 * @param int weight of connection
	 * adds node to path
	 * */
	public void addConection(Node connection, int weight) {
		paths.put(connection, weight);

	}
	/**
	 * @return HashSet<Node> returns path if there is one
	 *  uses depth first search to locate dest
	 *  and returns the path
	 * */
	public HashSet<Node> hasPathDfs(Node source, Node dest) {
		Node s = source;
		Node d = dest;
		HashSet<Node> visited = new HashSet<Node>();
		hasPathDfs(s, d, visited);
		return visited;
	}
	/**
	 * @param Node source
	 * @param Node destination
	 * @param HashSet<Node> visited elements
	 * s is updated each time on recursive call until there are no more values to
	 * travese through or when it has found the value needed
	 * updates visted elements each time 
	 * */
	private boolean hasPathDfs(Node s, Node d, HashSet<Node> v) {
		if (v.contains(s)) {
			return false;
		}
		v.add(s);
		if (s == d) {
			return true;
		}
		for (Node child : s.getPaths().keySet()) {
			if (hasPathDfs(child, d, v)) {
				return true;
			} else {
				v.remove(child);
			}
		}
		return false;
	}
	/**
	 * @param Node connecton
	 * returns the weight of the connection between two nodes
	 * or -1 if there is no connection
	 * */
	public int getWeight(Node connection) {
		if (paths.containsKey(connection)) {
			return paths.get(connection);
		} else {
			return -1;
		}
	}
	/**
	 * @return HashMap paths
	 * returns the connections of the node 
	 * */
	public HashMap<Node, Integer> getPaths() {
		return paths;
	}
	/**
	 * @param HashMap<Node, Integer> paths
	 * Sets new path for this node
	 * */
	public void setPaths(HashMap<Node, Integer> paths) {
		this.paths = paths;
	}
	/**
	 * @return vertice
	 * */
	public String getVertice() {
		return vertice;
	}
	/**
	 * @param String 
	 * */
	public void setVertice(String vertice) {
		this.vertice = vertice;
	}

}
