package com.example.amoba.util;


import com.example.amoba.model.Board;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;


import java.io.File;


public class Serializer {
public static void saveJson(Board board, File out) throws Exception {
ObjectMapper m = new ObjectMapper();
m.writeValue(out, board.getGridCopy());
}
public static char[][] loadJson(File in) throws Exception {
ObjectMapper m = new ObjectMapper();
return m.readValue(in, char[][].class);
}


public static void saveXml(Board board, File out) throws Exception {
XmlMapper m = new XmlMapper();
m.writeValue(out, board.getGridCopy());
}
public static char[][] loadXml(File in) throws Exception {
XmlMapper m = new XmlMapper();
return m.readValue(in, char[][].class);
}
}
