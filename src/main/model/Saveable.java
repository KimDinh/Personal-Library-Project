package model;

import java.io.FileWriter;
import java.io.IOException;

public interface Saveable {
    void save(FileWriter outFile) throws IOException;
}
