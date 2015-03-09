import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Scanner;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SoccerNetBot {

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
		Scanner myScan = new Scanner(System.in);
		System.out.println("Press Enter Mate");
		myScan.nextLine();
		}catch (Exception e){
			System.out.println("Error in ParseWebsite");
		}
	}
	
	
	public static void main(String[] args) {
		String baseURL = "http://www.espnfc.us/barclays-premier-league/23/table?season=";
		
		
		for(int i=2001; i < 2014; i++){
			System.out.println("Fetching: "+baseURL+ Integer.valueOf(i).toString() );
			ParseWebsite(baseURL+ Integer.valueOf(i).toString() , Integer.valueOf(i).toString()  );
		}
		
		
	}

}
