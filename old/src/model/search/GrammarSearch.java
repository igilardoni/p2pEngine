package model.search;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.Advertisable;
import model.Objet;
import model.RemoteSearch;
import model.SearchListener;
import net.jxta.discovery.DiscoveryService;

import com.itextpdf.text.log.SysoCounter;

public class GrammarSearch implements ListenerTalker {
	
	class Input {
		boolean offre = false, demande = false, username = false, userFilter = false;
		boolean titre = false;
		String query;
	}
	
	class Search {
		RemoteSearch<Objet> rs;
		String query;
		
		Search(RemoteSearch<Objet> rs, String query) {
			this.rs = rs;
			this.query = query;
		}
	}
	
	enum OP {
		OR,
		AND
	}
	
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
	
	private String regexMot = "([^ \"]+)|(\".+\")";
	
	public GrammarSearch(DiscoveryService discovery) {
		setTags();
		this.discovery = discovery;
	}
	
	private void initTags() {
		for(String s: tags.keySet()) {
			tags.put(s, false);
		}
	}
	
	public void addTag(String tag) {
		tags.put(tag, false);
	}
	
	protected void setTags() {
		addTag(userTag);
		addTag(titleTag);
		addTag(propositionTag);
		addTag(souhaitTag);
	}
	
	public void setRegexMot(String regex) {
		this.regexMot = regex;
	}
	
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
	
	private void activeTag(String tag) {
		String realTag = tag.subSequence(0, tag.length()-1).toString();
		tags.put(realTag, true);
	}
	
	private boolean tagSelected(String s) {
		return tags.get(s);
	}
	
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
		
		/*for(Input in: inputs) {
			getFilters(in);
		} */
		
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
			System.out.println("username = " + in.query);
			
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
	
	public static void main(String[] args) {
		GrammarSearch gs = new GrammarSearch(null);
		gs.search("besoin:title:patate user:demande:title:carotte OR patate");
		
		
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
