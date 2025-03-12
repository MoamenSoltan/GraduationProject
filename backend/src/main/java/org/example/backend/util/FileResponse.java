package org.example.backend.util;

import org.springframework.beans.factory.annotation.Value;

import java.nio.file.Paths;

public class FileResponse {
    private  String BASE_URL="http://localhost:8080/api/files/uploads/" ;
    public  String getFileName(String filePath) {
        return filePath != null ?BASE_URL+ Paths.get(filePath).getFileName().toString() : null;
    }
}
