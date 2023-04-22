package playground;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.ls.LSOutput;

import java.io.*;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) throws IOException, ParseException {
        readJsonFile();
    }

    public static void readJsonFile() throws IOException, ParseException {
        FileReader reader = new FileReader("ground/src/main/resources/data.json");
        JSONParser parser = new JSONParser();

        Object obj = parser.parse(reader);

        JSONArray people = (JSONArray) obj;

        System.out.println(people);
    }

    public void parsePerson(JSONObject person) {

    }

    public static void readFileBytes() {
        try {
            FileInputStream dataFile = new FileInputStream("ground/src/main/resources/data.json");

            int i = 0;

            while((i = dataFile.read()) != -1) {
                System.out.println((char)i);
            }

            dataFile.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeFile() {
        try {
            FileOutputStream output = new FileOutputStream("output.txt");
            String howdy = "hello world";

            byte[] bytes = howdy.getBytes();
            output.write(bytes);

            output.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
