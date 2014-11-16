package model.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.Objet;
import model.advertisements.Advertisable;
import net.jxta.discovery.DiscoveryService;

/**
 * Classe pour la recherche suivant une synthaxe précise
 * Plusieurs recherches se lancerons, les résultats sont récupérer au fur et a mesure et transmis aux listeners.
 * @author Prudhomme Julien
 *
 */
public class GrammarSearch implements ListenerTalker {
	
	/**
	 * Classe qui garde une trace d'une requete (un seul mot clé avec ses tags)
	 * @author Prudhomme Julien
	 *
	 */
	class Input {
		boolean offre = false, demande = false, username = false, userFilter = false;
		boolean titre = false;
		String query;
	}
	
	/**
	 * Sert a définir les recherches afin de les lancés une fois que toute la requete aura été parsé
	 * @author Prudhomme Julien
	 *
	 */
	class Search {
		RemoteSearch<Objet> rs; //L'objet qui nous sert a rechercher
		String query; //La requete
		
		Search(RemoteSearch<Objet> rs, String query) {
			this.rs = rs;
			this.query = query;
		}
	}
	
	/**
	 * Sert a savoir si on utilise l'union ou l'intersection entre chaque mot clé
	 * Par défaut OP = AND
	 * @author Prudhomme Julien
	 *
	 */
	enum OP {
		OR,
		AND
	}
	
	/*
	 * On garde les tags en static
	 */
	public static String propositionTag = "offer";
	public static String souhaitTag = "wish";
	public static String userTag = "user";
	public static String titleTag = "title";
	
	private DiscoveryService discovery;
	private HashMap<String, Boolean> tags = new HashMap<String, Boolean>();
	private ArrayList<Input> inputs = new ArrayList<Input>();
	private ArrayList<Filter> filters = new ArrayList<Filter>();
	private BaseListenerTalker lastFilter = null;
	private ArrayList<Search> searchs = new ArrayList<Search>();
	private OP currentOP = OP.AND;
	private ArrayList<SearchListener> listeners = new ArrayList<SearchListener>();
	
	/*
	 * C'est la regex qui reconnait un mot
	 * Un mot est une suite de caractère sans espaces, ou une suite de caractère entre guillemet :
	 * Mots simple : a, b, ab
	 * Mots entre guillement : "a", "ab", "a b", "ab ba"s
	 */
	private String regexMot = "([^ \"]+)|(\".+\")";
	
	/**
	 * Initialise la recherche
	 * @param discovery
	 */
	public GrammarSearch(DiscoveryService discovery) {
		setTags();
		this.discovery = discovery;
	}
	
	/**
	 * Met tout les tags a false
	 */
	private void initTags() {
		for(String s: tags.keySet()) {
			tags.put(s, false);
		}
	}
	
	/**
	 * Permet d'ajouter un tag au reconnaisseur
	 * @param tag
	 */
	public void addTag(String tag) {
		tags.put(tag, false);
	}
	
	/**
	 * Définitions des tags par défaut
	 */
	protected void setTags() {
		addTag(userTag);
		addTag(titleTag);
		addTag(propositionTag);
		addTag(souhaitTag);
	}
	
	public void setRegexMot(String regex) {
		this.regexMot = regex;
	}
	
	/**
	 * Permet de construire la regex qui reconnait un tag
	 * à partir des tags définis
	 * "(tag1|tag2|tag3):"
	 * @return la regex qui reconnait un tag défini
	 */
	private String getTagsRegex() {
		String res = "";
		int i = 0;
		for(String s: tags.keySet()) {
			if(i == 0) res = s;
			else res = res + "|" + s;
			i++;
		}
		res = "(" + res + "):";
		return res;
	}
	
	/**
	 * Active un tag: le mot en cours d'analyse contient le tag "tag"
	 * @param tag
	 */
	private void activeTag(String tag) {
		String realTag = tag.subSequence(0, tag.length()-1).toString();
		tags.put(realTag, true);
	}
	
	/**
	 * Permet de savoir si un tag est activé
	 * @param s
	 * @return true si le tag s est activé
	 */
	private boolean tagSelected(String s) {
		return tags.get(s);
	}
	
	/**
	 * Analyse une partie de la chaine
	 * Une chaine peut contenir une sous chaine, l'analyse est récursive
	 * @param s la (sous)chaine
	 */
	protected void handle(String s) {
		Matcher m = Pattern.compile(getTagsRegex()).matcher(s);
		if(m.find()) {
			activeTag(m.group());
			handle(s.substring(m.group().length()));
		}
		else {
			Input in = new Input();
			in.query = s.replaceAll("\"", "");
			if(tagSelected(titleTag)) {
				in.titre = true;
			}
			if(tagSelected(propositionTag)) {
				in.offre = true;
			}
			if(tagSelected(souhaitTag)) {
				in.demande = true;
			}
			if(tagSelected(userTag) && !tagSelected(propositionTag) && !tagSelected(souhaitTag) && !tagSelected(titleTag)) {
				in.username = true;
				
			}
			else if (tagSelected(userTag)) in.userFilter = true;
			this.inputs.add(in);
			getFilters(in);
		}
	}
	
	/**
	 * Ajoute et connecte un filtre (suivant l'état de currentOP)
	 * @param f
	 */
	private void addFilter(BaseListenerTalker f) {
		if(lastFilter == null) lastFilter = f;
		else {
			switch(currentOP) {
			case AND: 
				Intersection i = new Intersection(lastFilter, f);
				lastFilter = i; break;
			case OR:
				Union u = new Union(lastFilter, f);
				lastFilter = u; break;
			}
		}
	}
	
	/**
	 * Lance la recherche. Les résultats sont transmis aux listeners
	 * @param query
	 */
	public void search(String query) {
		
		String requete = "((" + getTagsRegex() + ")*" + regexMot + ")+";
		Matcher m = Pattern.compile(requete).matcher(query);
		this.inputs.removeAll(inputs);
		while(m.find()) {
			initTags();
			if(m.group().equals("OR")) currentOP = OP.OR;
			else {
				handle(m.group());
				currentOP = OP.AND;
			}
		}
		
		lastFilter.addListener(this);
		lauchSearchs();
	}
	

	
	private void getFilters(Input in) {
		if(in.userFilter) {
			final Filter f = new Filter(discovery);
			filters.add(f);
			f.setDemandeFilter(in.demande);
			f.setOffreFilter(in.offre);
			
			RemoteSearch<Objet> rs = new RemoteSearch<Objet>(discovery, "titre");
			rs.addListener(f);
			searchs.add(new Search(rs, in.query));
			if(lastFilter != null) {
				UserIntersection ui = new UserIntersection(lastFilter, f);
				lastFilter = ui;
			}
			else lastFilter = f;
		}
		else if(in.username) {
			Filter f = new Filter(discovery);
			f.setUserFilter(in.query);
			
			if(lastFilter == null) {
				RemoteSearch<Objet> rs = new RemoteSearch<Objet>(discovery, "user");
				rs.addListener(f);
				searchs.add(new Search(rs, in.query));
			}
			
			if(lastFilter != null) {
				lastFilter.addListener(f);
			}
			lastFilter = f;
			
		}
		else {
			final Filter f = new Filter(discovery);
			f.setOffreFilter(in.offre);
			f.setDemandeFilter(in.demande);
			RemoteSearch<Objet> rs = new RemoteSearch<Objet>(discovery, "titre");
			rs.addListener(f);
			searchs.add(new Search(rs, in.query));
			addFilter(f);
		}
	}
	
	private void lauchSearchs() {
		for(Search s: searchs) {
			s.rs.search(s.query);
		}
	}

	@Override
	public void searchEvent(Advertisable adv) {
		notifyListener(adv);
	}

	@Override
	public void addListener(SearchListener l) {
		listeners.add(l);
	}

	@Override
	public void notifyListener(Advertisable adv) {
		for(SearchListener l: listeners) {
			l.searchEvent(adv);
		}
	}
	
}
