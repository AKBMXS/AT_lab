package com.example.at_project_final;

public class HistoryItem {
    private long id; // Unique ID for the history item
    private String extractedText; // The extracted text
    private String extractionTime; // The time when the text was extracted

    // Constructor
    public HistoryItem(long id, String extractedText, String extractionTime) {
        this.id = id;
        this.extractedText = extractedText;
        this.extractionTime = extractionTime;
    }

    // Getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getExtractedText() {
        return extractedText;
    }

    public void setExtractedText(String extractedText) {
        this.extractedText = extractedText;
    }

    public String getExtractionTime() {
        return extractionTime;
    }

    public void setExtractionTime(String extractionTime) {
        this.extractionTime = extractionTime;
    }
}

