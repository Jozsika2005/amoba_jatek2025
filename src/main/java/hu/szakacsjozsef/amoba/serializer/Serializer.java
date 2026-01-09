package hu.szakacsjozsef.amoba.serializer;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import hu.szakacsjozsef.amoba.model.Board;

public class Serializer {

    public static void save(char[][] board, File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(file, board);
    }

    public static char[][] load(File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(file, char[][].class);
    }
}
