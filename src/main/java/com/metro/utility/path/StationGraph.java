package com.metro.utility.path;

import java.util.ArrayList;

public class StationGraph {
	private ArrayList<ArrayList<Node>> graph;

	static class Node {		// 다음 노드의 index와 그 노드로 가는데 필요한 cost(가중치)
		int dest;
		int cost;
		
		@Override
		public String toString() {
			return "Node [dest=" + dest + ", cost=" + cost + "]";
		}

		Node(int dest, int cost) {
			this.dest = dest;
			this.cost = cost;
		}
	}
	
	StationGraph(int v) {
		graph = new ArrayList<>(v);
		for(int i = 0; i < v + 1; i++) {
			graph.add(new ArrayList<>());
		}		
	}
	
	void addNode(int src, int dest, int cost) {
		graph.get(src).add(new Node(dest, cost));
		graph.get(dest).add(new Node(src, cost));	// 양방향 간선 추가
	}
}
