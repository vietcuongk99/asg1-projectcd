package Dictionary;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Dictionary {
   // public static ArrayList<Dictionary> dictionaries = new ArrayList<Dictionary>();
    private String english;
    private String vietnamese;

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getVietnamese() {
        return vietnamese;
    }

    public void setVietnamese(String vietnamese) {
        this.vietnamese = vietnamese;
    }

    public Dictionary(String english, String vietnamese) {
        this.english = english;
        this.vietnamese = vietnamese;
    }

    public Dictionary() {
    }
}
