import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Scanner;
import java.util.*;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SoccernetParser {

	private static HashMap leagueURL;
	
	public static void init(){
		leagueURL = new HashMap();
		leagueURL.put("EPL", "/barclays-premier-league/23/table?season=");
		leagueURL.put("LaLiga", "/spanish-primera-division/15/table?season=");
		leagueURL.put("Bundesliga", "/german-bundesliga/10/table?season=");
		leagueURL.put("Ligue1", "/french-ligue-1/9/table?season=");
		leagueURL.put("SerieA", "/italian-serie-a/12/table?season=");
		leagueURL.put("MLS", "/major-league-soccer/19/table?season=");
	}
	
	
	
	public static void ParseTable(Elements links, String year){
		Elements rows = links.select("tr");

		try(PrintWriter out = new PrintWriter( year+".csv" ) ){
			
			Iterator<Element> iter = rows.iterator();
			while( iter.hasNext() ){
				Element tempElement = iter.next();
				Iterator<Element> secondIter = tempElement.children().iterator();
				while( secondIter.hasNext() ){
					out.printf( secondIter.next().text() );
					if(secondIter.hasNext())
						out.printf(",");
				}
				out.printf("\n");
			}
			out.close();
		} catch (FileNotFoundException e){
			System.out.println("Unable to load order from "+ year+".csv"+".");
		} catch ( Exception e){
			System.out.println("Unable to load order from "+ year+".");
		}

	}
	
	
	public static void ParseWebsite(String url, String year){
		try{
		Document doc = Jsoup.connect(url).get();
		Elements links = doc.select("table");
		ParseTable(links, year);
		}catch (Exception e){
			System.out.println("Error in ParseWebsite");
		}
	}
	
	
	public static void main(String[] args) {
		SoccernetParser.init();
		String baseURL = "http://www.espnfc.us";
		
		Set set = leagueURL.entrySet();
		Iterator myIter = set.iterator();
		while( myIter.hasNext() ){
			Map.Entry me = (Map.Entry) myIter.next();
			for(int i=2001; i < 2014; i++){
				System.out.println("Fetching: "+baseURL+ me.getValue().toString() +Integer.valueOf(i).toString() );
				ParseWebsite(baseURL+ me.getValue().toString() +Integer.valueOf(i).toString() , me.getKey().toString() +Integer.valueOf(i).toString()  );
			}
		}
		
	}

}
