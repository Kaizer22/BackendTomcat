package TextGenerator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Generator {
        private final String output = "OutputText.txt";

        public Generator(){ }

        public String generateText(HashMap<String, HashMap<String, Integer>> stat, int len){
            System.out.println("Количество слов:" + stat.size());
            String text = "";
            String buf = getWord(stat.get("start"));
            String next_word = buf.substring(0,1).toUpperCase() + buf.substring(1);
            text = text.concat(next_word + " ");
            while (text.length() < len){
                if (next_word.equals(".") || next_word.equals("") || next_word.equals("\n")){
                    buf = getWord(stat.get("start"));
                    next_word  = buf.substring(0,1).toUpperCase() + buf.substring(1);
                    text = text.concat("\n" + next_word + " ");
                }else{
                    next_word = getWord(stat.get(next_word.toLowerCase()));
                    text = text.concat(next_word + " ");
                }

            }

            //writeToFile(text);
            return text;
        }

        private void writeToFile(String text) {
            try (FileWriter writer = new FileWriter(output, false)){
                writer.write(text);
                writer.append('\n');

                writer.flush();
            }catch (IOException ex){
                System.out.println(ex.getMessage());
            }
        }

        private String getWord(HashMap<String, Integer> freq) {
            Random r = new Random();
            int sum = 0;
            double buf_sum = 0;
            //System.out.println("FREQ" + freq.toString());
            String [] words = new String[freq.keySet().size()];

            int y = 0;
            for (String k: freq.keySet()) {
                words[y++] = k;
            }
            Integer[] frequencies = freq.values().toArray(new Integer[0]);
            for (int f: frequencies) {
                sum += f;
            }
            double[] probabilities = new double[frequencies.length];
            for (int i = 0; i < probabilities.length; i++) {
                probabilities[i] = (double)frequencies[i]/sum + buf_sum;
                //System.out.println(probabilities[i]);
                buf_sum = probabilities[i];
            }
            double randomNumber = r.nextDouble();
            for (int i = 0; i < probabilities.length ; i++) {
                if (randomNumber < probabilities[i]){
                    return words[i];
                }
            }
            return "BRUH";
        }
}

