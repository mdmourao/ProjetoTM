package tm2021.fcul.node.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class LogFile {

   public static File ficheiro;
    FileWriter myWriter;

    public LogFile() throws IOException {
        File myObj = new File("logTemp.txt");
        myWriter = new FileWriter("logTemp.txt");
        ficheiro = myObj;
    }

    public void writetoLogFile(String text){
        try {
            myWriter.write(text + "\n");
        } catch (IOException e) {

        }
    }

    public void readFile(){
        try {
            Scanner myReader = new Scanner(ficheiro);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {

        }
    }


}
