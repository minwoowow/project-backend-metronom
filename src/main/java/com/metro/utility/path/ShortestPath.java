package com.metro.utility.path;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class ShortestPath {
	
	static final int INF = 200000000;
	static int V;
	static int[] prev;
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
	
	public ShortestPath() {
	}
	
	ShortestPath(int v) {
		V = v;
		graph = new ArrayList<>(v + 1);
		for(int i = 0; i < v + 1; i++) {
			graph.add(new ArrayList<>());
		}		
	}
	

	void addNode(int src, int dest, int cost) {
		graph.get(src).add(new Node(dest, cost));
		graph.get(dest).add(new Node(src, cost));	// 양방향 간선 추가
	}
	
	public void dijkstra(int start, int end) {
		// 우선순위큐 사용, 가중치를 기준으로 오름차순화한다 => 가장 낮은 cost 부터 Deque
		PriorityQueue<Node> pq = new PriorityQueue<>((o1, o2) -> o1.cost - o2.cost);	// Node Class에 @override 하는 방법도 있음
																						// public int comparTo(Node o) { return Integer.compare(this.cost, o.cost);}
		
		int[] dist = new int[V + 1];	// 각 노드별 가중치 배열
		prev = new int[V + 1];			// 경로 역추적 -> 큐에 add 할때 이전 노드의 index를 저장
		Arrays.fill(dist, INF);
		Arrays.fill(prev, -1);
		dist[start] = 0;
		pq.add(new Node(start, 0));
		
		while(!pq.isEmpty()) {
			Node curNode = pq.poll();
			int u = curNode.dest;
			
			if(u == end) {
				return;
			}
			
			// Queue에서 poll한 노드의 index에 해당하는 cost와 현재기록되어있는 dist배열의 index의 cost와 비교하여 방문처리
			// 현재 꺼낸 노드의 가중치가 dist의 가중치보다 크다면 해당 노드는 이전에 방문된 노드임 
			if (dist[curNode.dest] < curNode.cost) {
				continue;
			}			
			
			for(Node node : graph.get(u)) {
				int v = node.dest;
				int cost = node.cost;
				if(dist[v] > dist[u] + cost) {
					prev[v] = u;
					dist[v] = dist[u] + cost;
					pq.add(new Node(v, dist[v]));
				}
			}
		}
	}
	
	public List<Integer> getStationList(int from, int to) {
		// read할 csv파일 이름
		String fileName = "dijkstra3.csv";
		
		Map<String, Integer> stationMap = new HashMap<>();
		List<String[]> edges = new ArrayList<>();		
		
		// 초기화
		try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			br.readLine();	// 첫행(header) 스킵
			String line;	// BufferedReader로 읽어온 행을 저장할 변수
			int index = 0;		// station ID와 맵핑해줄 index
			StringTokenizer tokenizer;	// csv에서 읽어들인 행을 ','로 분리할 토크나이저
			
			while((line = br.readLine()) != null) {
				tokenizer = new StringTokenizer(line, ",");
				
				String src = tokenizer.nextToken();
				String dest = tokenizer.nextToken();
				String cost = tokenizer.nextToken();
				
				if(!stationMap.containsKey(src)) {
					stationMap.put(src, index++);
				}
				
				if(!stationMap.containsKey(dest)) {
					stationMap.put(dest, index++);
				}
				
				edges.add(new String[] {src, dest, cost});
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ShortestPath addGraph = new ShortestPath(stationMap.size());
		
		for(String[] edge : edges) {
			int src = stationMap.get(edge[0]);
			int dest = stationMap.get(edge[1]);
			int cost = Integer.parseInt(edge[2]);
			
			addGraph.addNode(src, dest, cost);
		}
		
		if(!stationMap.containsKey(String.valueOf(from)) || !stationMap.containsKey(String.valueOf(to))) {
			System.out.println("[Error] Invalid start or end station code");
			return null;
		}
		
		int sNode = stationMap.get(String.valueOf(from));
		int eNode = stationMap.get(String.valueOf(to));
		
		
		addGraph.dijkstra(sNode, eNode);
		
		int cur = eNode;
		List<Integer> result = new ArrayList<>();
		
		while(cur != -1) {
			for(Map.Entry<String, Integer> entry : stationMap.entrySet()) {
				int key = Integer.parseInt(entry.getKey());
				int value = entry.getValue();
				if(value == cur) {
					result.add(key);
					System.out.print(key + " <- ");
				}
			}
			cur = prev[cur];
		}
		System.out.println();
		Collections.reverse(result);
		return result;
	}
	
	public static void main(String[] args) {
		
//		int st = 95;
//		int en = 203;
//		
//		List<Integer> list = new ArrayList<>();
//		
//		TransferStations ts = new TransferStations();
//		
//		list = ts.findTransferStation(getStationList(st, en));
//		
//		System.out.println(list.toString());
//		
//		List<Integer> resultPath = new ArrayList<>();
//		resultPath.addAll(Arrays.asList(st, en));
//		resultPath.addAll(1, list);
//		
//		for(int a : resultPath)
//			System.out.println(a);
//		
//		for(int i = 0; i < resultPath.size() - 1; i += 2) {
//			int s = resultPath.get(i);
//			int e = resultPath.get(i+1);
//			int t = 0;
//			
//			switch (s / 100) {
//			case 0:
//			case 1:
//				t = ((s - e) < 0) ? 134 : 95;
//				
//				break;
//			case 2:
//				t = ((s - e) < 0) ? 243 : 201;
//				
//				break;
//			case 3:
//				t = ((s - e) < 0) ? 317 : 301;				
//				
//				break;
//			case 4:
//				t = ((s - e) < 0) ? 414 : 401;
//				
//				break;
//			default:
//				break;				
//			}
//			
//			System.out.println("[Departure Station : " + s + "] [Arrival Station : " + e + "] [Last Stop : " + t + "]");
//		}
	}
}
