package com.example.enums;

public enum File {
    PDF("pdf"),
    DOC("doc"),
    JPG("jpg"),
    PNG("png"),
    TXT("txt");

    private String extension;

    private File(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

    @Override
    public String toString() {
        return extension;
    }
}