package com.shop.mappersAndFiles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class GenericMapper<T> {
    public List<T> mapJsonToList(Class<T> exampleObj) {
        try (InputStream reader = new FileInputStream("inputFile.json")) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(reader, objectMapper.getTypeFactory().constructCollectionType(List.class, exampleObj));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public void mapListToJson(List<T> objectsList) {
        try (FileWriter file = new FileWriter("outputFile.json")) {
            file.write(new ObjectMapper().writeValueAsString(objectsList));
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
