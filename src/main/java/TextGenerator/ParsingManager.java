package TextGenerator;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class ParsingManager {
    StatManager db;
    HashMap<String, HashMap<String, Integer>> stat;


    public ParsingManager(String statPath, String author){
        db = new StatManager(statPath, author);
        stat = new HashMap<>();
    }

    public void parse(String author){
        String input = author.concat("Text.txt");
        try (FileReader reader = new FileReader(input)){
            int c;
            char buf;
            String buf_s = "start ";
            String[] sentence ;
            while((c=reader.read()) != -1){
                buf = (char)c;
                if (buf == '.'){
                    buf_s = buf_s.concat(" "+buf);
                    buf_s = buf_s.replaceAll("([^А-Яа-я.ёstar ])"," ");
                    buf_s = buf_s.replaceAll("([?:!])",".");
                    buf_s = buf_s.replaceAll(" +"," ");
                    buf_s = buf_s.toLowerCase();
                    sentence = buf_s.split(" ");
                    //DEBUG-----
                    //System.out.println(buf_s);
                    //for (String s:sentence) {
                    //System.out.print(s + "|__|");
                    //}
                    //------
                    buf_s = "start ";
                    String word;
                    Integer number;
                    for (int i = 0; i < sentence.length; i++) {
                        word = sentence[i];
                        if (stat.containsKey(word)){
                            if (i+1 < sentence.length){
                                if (stat.get(word).containsKey(sentence[i + 1])) {
                                    number = stat.get(word).get(sentence[i + 1]);
                                    stat.get(word).replace(sentence[i + 1], ++number);
                                }else{
                                    stat.get(word).put(sentence[i + 1], 1);
                                }
                            }
                        }else{
                            stat.put(sentence[i], new HashMap<>());
                            if (i+1 < sentence.length){
                                if (stat.get(word).containsKey(sentence[i + 1])) {
                                    number = stat.get(word).get(sentence[i + 1]);
                                    stat.get(word).replace(sentence[i + 1], ++number);
                                }else{
                                    stat.get(word).put(sentence[i + 1], 1);
                                }
                            }
                        }
                    }
                }else{
                    buf_s = buf_s.concat(buf+"");
                }

            }
            System.out.println();
            //DEBUG----
            for (String s: stat.keySet()) {
                System.out.println(s + stat.get(s).toString());
            }
            //-------
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public HashMap<String, HashMap<String, Integer>> getStatistics() {
        return stat;
    }

    public void setStatistics(HashMap<String, HashMap<String, Integer>> stats) {
        this.stat = stats;
    }
}