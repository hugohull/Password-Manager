package group4.passwordmanager.manager;

import group4.passwordmanager.model.Master;
import group4.passwordmanager.service.MasterService;

public class MasterManager {

    private static MasterService masterService;

    private static Master master;

    public MasterManager(MasterService masterService, Master master){
        this.masterService = masterService;
        this.master = master;
    }

    // Method to handle the creation or generation of the master password
    public static void createMasterPassword() {
        if (!master.hasMasterPassword()) {
            String randomPassword = master.generateRandomPassword(16);
            master.setMasterPassword(randomPassword);
            masterService.displayMessage("Generated Random Master Password: " + randomPassword);
        } else {
            masterService.displayMessage("Master Password already exists.");
        }
    }

    public static void editMasterPassword() {
        if (master.hasMasterPassword()) {
            String newPassword = master.generateRandomPassword(16);
            master.updateMasterPassword(newPassword);
            masterService.displayMessage("Master Password updated successfully to " + newPassword);
        } else {
            masterService.displayMessage("No existing Master Password found. Please create one first.");
        }
    }

    public static void deleteMasterPassword() {
        if (master.hasMasterPassword()) {
            if (masterService.confirmDeletion()) {
                master.deleteMasterPassword();
                masterService.displayMessage("Master Password deleted successfully.");
            } else {
                masterService.displayMessage("Master Password deletion cancelled.");
            }
        } else {
            masterService.displayMessage("No existing Master Password found.");
        }
    }

    public static void lockAccount() {
        master.lock();
        masterService.displayMessage("Account locked successfully.");
    }

    public void unlockAccount() {
        if (master.isLocked()) {
            String password = masterService.promptForUnlocking();
            if (master.unlock(password)) {
                masterService.displayMessage("Account unlocked successfully.");
            } else {
                masterService.displayMessage("Incorrect Master Password. Account remains locked.");
            }
        } else {
            masterService.displayMessage("Account is already unlocked.");
        }
    }
}