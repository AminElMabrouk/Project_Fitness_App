package com.fitnessapp.fitness.Classes;

public class Workout {
    private String name;
    private String description;
    private String documentId;  // Field to store Firestore document ID

    // Default constructor required for Firestore
    public Workout() {}

    // Constructor for other fields
    public Workout(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getter and setter methods for the fields
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter and setter methods for documentId
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}
