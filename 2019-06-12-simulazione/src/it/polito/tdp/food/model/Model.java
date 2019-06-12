package it.polito.tdp.food.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.Condiment;
import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private Map<Integer, Condiment> idMap;
	private SimpleWeightedGraph<Condiment, DefaultWeightedEdge> grafo;
	
	public Model() {
		idMap = new HashMap<>();
	}

	public boolean isDigit(String numCalorie) {
		if(numCalorie.matches("\\d+(\\.|\\,){0,1}\\d*")) { //match per i numeri con virgola
			return true;
		}
		return false;
	}

	public String stampaCalorie(String numCalorie) {
		FoodDao dao = new FoodDao();
		String risultato="";
		double somma=0;
		grafo = new SimpleWeightedGraph<Condiment, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		dao.listAllCondiment(idMap);
		List<IngredientiConnessi> connessione = dao.getConnessioniIngredienti(idMap, numCalorie);
		for(IngredientiConnessi ic: connessione) {
			if(!grafo.containsVertex(ic.getC1())) {
				grafo.addVertex(ic.getC1());
			}
			if(!grafo.containsVertex(ic.getC2())) {
				grafo.addVertex(ic.getC2());
			}
			DefaultWeightedEdge edge = grafo.getEdge(ic.getC1(), ic.getC2());
			if(edge==null) {
				Graphs.addEdgeWithVertices(grafo, ic.getC1(), ic.getC2(), ic.getPeso());
			}
		}
		risultato+="Grafo creato! Vertici: "+grafo.vertexSet().size()+" Archi: "+grafo.edgeSet().size()+"\n";
		List<Condiment> list = new LinkedList<>(grafo.vertexSet());
		Collections.sort(list, new Comparator<Condiment>() {

			@Override
			public int compare(Condiment o1, Condiment o2) {
				return -(o1.getCondiment_calories().compareTo(o2.getCondiment_calories()));
			}
		});
				
		for(Condiment c: list) {
			List<Condiment> vicini = Graphs.neighborListOf(grafo, c);
			
			for(Condiment c2: vicini) {
				DefaultWeightedEdge edge = grafo.getEdge(c, c2);
				somma+=grafo.getEdgeWeight(edge);
			}
			risultato+=c.getDisplay_name()+" Calorie: "+c.getCondiment_calories()+" contenuto in: "+somma+" cibi\n";
		}
		return risultato;
	}

}
