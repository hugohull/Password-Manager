package group4.passwordmanager.service;

import group4.passwordmanager.model.Credential;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SearchService {

    private final CredentialService credentialService;

    public SearchService(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    public void searchCredentialsAndPrintDetails(Scanner scanner, String searchTerm) {
        while (true) {
            List<Credential> credentials = searchCredentials(searchTerm.trim());

            if (credentials.isEmpty()) {
                System.out.println("No matching credentials found.");
                return;
            } else {
                System.out.println("Matching credentials:");

                for (int i = 0; i < credentials.size(); i++) {
                    Credential credential = credentials.get(i);
                    System.out.println((i + 1) + ": Email/Username: " + credential.getEmailOrUsername() +
                            ", Website: " + credential.getWebsite() + ", Tags: " + credential.getTags());
                }

                System.out.println("Enter the number of the credential to view its details or leave blank to go back:");
                String selection = scanner.nextLine();

                if (selection.equalsIgnoreCase("")) {
                    return;  // Go back to choosing an option
                }

                try {
                    int index = Integer.parseInt(selection);
                    if (index >= 1 && index <= credentials.size()) {
                        viewPasswordOnly(credentials.get(index - 1));
                    } else {
                        System.out.println("Invalid selection.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid selection.");
                }
            }
        }
    }

    public static void viewPasswordOnly(Credential credential) {
        System.out.println("Password: " + credential.getPassword());
    }

    private List<Credential> searchCredentials(String searchTerm) {
        List<Credential> matchingCredentials = new ArrayList<>();

        // Retrieve all credentials from the CredentialService
        List<Credential> credentials = credentialService.getAllCredentials();

        for (Credential credential : credentials) {
            if (credential.getEmailOrUsername().toLowerCase().contains(searchTerm.toLowerCase()) ||
                    credential.getWebsite().toLowerCase().contains(searchTerm.toLowerCase()) ||
                    (credential.getTags() != null && credential.getTags().stream().anyMatch(tag -> tag.toLowerCase().contains(searchTerm.toLowerCase())))) {
                matchingCredentials.add(credential);
            }
        }

        return matchingCredentials;
    }
}
