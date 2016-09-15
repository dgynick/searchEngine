package Parser;

import DataManager.DbManager;

import java.sql.ResultSet;
import java.util.*;

/**
 * CS 121 Team BEES
 * Dongguang You - 80122555
 * Robert Long - 74151707
 */

public class Paser {
    private static HashMap<String,Integer> url_words = new HashMap<String,Integer>();
    private static HashMap<String,Integer> word_Count = new HashMap<String,Integer>();
    private static HashMap<String, Integer> subdomains = new HashMap<>();
    private static ArrayList<String> list = new ArrayList<>();

    static <K,V extends Comparable<? super V>> List<Map.Entry<K, V>> entriesSortedByValues(Map<K,V> map) {
        List<Map.Entry<K,V>> sortedEntries = new ArrayList<Map.Entry<K,V>>(map.entrySet());

        Collections.sort(sortedEntries,
                new Comparator<Map.Entry<K, V>>() {
                    @Override
                    public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
                        return e2.getValue().compareTo(e1.getValue());
                    }
                }
        );

        return sortedEntries;
    }

    public static void main(String args[]) throws Exception{
       ResultSet rs= DbManager.getURLContent().executeQuery();
        while(rs.next()){
            String url= rs.getString("Url");
            String text= rs.getString("Text_Content");
            parse(url,text);
            text="";
            url="";
        }

        List<Map.Entry<String, Integer>> sortedWords = entriesSortedByValues(word_Count);
        List<Map.Entry<String, Integer>> sortedUrls = entriesSortedByValues(url_words);
        Collections.sort(list);

        int i = 1, sum = 0;
        for (String s : list) {
            System.out.printf("%d. %s.ics.uci.edu (Total Unique %d)\n", i++, s, subdomains.get(s));
            sum += subdomains.get(s);
        }
        System.out.printf("Totalling %d unique pages.\n", sum);

        //System.out.println(url_words);
        //System.out.println(word_Count);
/*        for (int i=0; i<500; i++){
            System.out.printf("%d. %s, %d\n", i+1, sortedWords.get(i).getKey(), subdomains.get(sortedWords.get(i).getKey()));
        }*/

    }
    private static void parse(String url,String text){
           url_words.put(url,0);
        String subdomain = url.substring(0, url.indexOf(".ics.uci.edu"));

        if (!subdomains.containsKey(subdomain)) {
            subdomains.put(subdomain, 1);
            list.add(subdomain);
        } else {
            subdomains.put(subdomain, subdomains.get(subdomain) + 1);
        }
           String temp="";
           char c;
           for(int i=0;i<text.length();i++){
               c= text.charAt(i);
               if(c>=48&&c<58 || c>=65 && c<91 || c>=97&& c<123){
                   temp+=c;
               }
               else if(c == ' '){
                   if(temp.length()>0){
                      updateUW(url);
                       updateWC(temp);
                       temp="";
                   }
               }
               else{
                   temp="";
               }
           }
           temp="";
    }
    private static void updateUW(String url){
         url_words.put(url,url_words.get(url)+1);
    }
    private static void updateWC(String word){
         if(word_Count.containsKey(word)){
             word_Count.put(word,word_Count.get(word)+1);
         }
        else{
             word_Count.put(word,1);
         }
    }



}
