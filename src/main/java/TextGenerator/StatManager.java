package TextGenerator;

import java.io.*;

import java.util.ArrayList;
import java.util.HashMap;

public class StatManager {
    private String statFile;

    public StatManager(String statPath, String author) {
        statFile = statPath.concat(author + "Stat.txt");
    }

    public void updateStat(HashMap<String, HashMap<String, Integer>> stat) {
        String[] generalKeys = new String[stat.keySet().size()];
        int i = 0;
        for (String k : stat.keySet()) {
            generalKeys[i++] = k;
        }

        ArrayList<ArrayList<String>> subKeys = new ArrayList<>();
        i = 0;
        for (HashMap<String, Integer> subWords : stat.values()) {
            subKeys.add(new ArrayList<String>());
            for (String sk : subWords.keySet()) {
                subKeys.get(i).add(sk);
            }
            i++;
        }

        ArrayList<ArrayList<Integer>> frequencies = new ArrayList<>();
        i = 0;
        for (HashMap<String, Integer> subWords : stat.values()) {
            frequencies.add(new ArrayList<Integer>());
            for (Integer fq : subWords.values()) {
                frequencies.get(i).add(fq);
            }
            i++;
        }

        System.out.println(subKeys.toString());
        System.out.println(frequencies.toString());
        int f = 0;
        try (FileWriter writer = new FileWriter(statFile, false)) {
            String text = "";
            String result_substring = "";
            int j = 0;
            int k = 0;
            for (String generalKey : generalKeys) {
                result_substring = result_substring.concat(generalKey + ":");
                for (String subKey : subKeys.get(j)) {
                    result_substring = result_substring.concat(subKey + "_" + frequencies.get(j).get(k++) + "*");
                }
                k = 0;
                j++;
                text = text.concat(result_substring + "\n");
                result_substring = "";
                //DEBUG----
                System.out.println(f++);
                //System.out.println(text);
                //-----
            }

            writer.write(text);
            writer.append('\n');

            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public HashMap<String, HashMap<String, Integer>> getStats() {
        HashMap<String, HashMap<String, Integer>> resultStat = new HashMap<>();
        HashMap<String, Integer> bufInfo = new HashMap<>();
        try {
            FileReader fileReader = new FileReader(statFile);
            BufferedReader reader = new BufferedReader(fileReader);
            String line = reader.readLine();
            String word = "";
            String[] nextWords;
            while (line != null) {
                //System.out.println(line);
                word = line.split(":")[0];
                //System.out.println(word);
                if (word.equals(".") || word.equals("")) {
                    nextWords = new String[0];
                } else {
                    nextWords = line.split(":")[1].split("[*]");
                }
                //System.out.println(nextWords);
                for (String info : nextWords) {
                    bufInfo.put(info.split("[_]")[0], Integer.valueOf(info.split("[_]")[1]));
                }
                resultStat.put(word, (HashMap<String, Integer>) bufInfo.clone());
                bufInfo.clear();

                line = reader.readLine();

            }

            //DEBUG----
            //for (String s: resultStat.keySet()) {
            //System.out.println(s + resultStat.get(s).toString());
            //}
            //System.out.println(resultStat.size());
            //-----
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultStat;
    }
}