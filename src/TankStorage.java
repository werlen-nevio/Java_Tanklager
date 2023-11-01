import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

class TankStorage {
    private List<Tank> tanks = new ArrayList<>();

    public void addTank(Tank tank) {
        tanks.add(tank);
    }

    public Tank removeTank(int tankNumber) {
        Tank removedTank = null;
        for (Tank tank : tanks) {
            if (tank.getNumber() == tankNumber) {
                double removedLiters = tank.getStoredLiters();
                boolean canRemove = true;

                double spaceAvailable = 0;
                for (Tank otherTank : tanks) {
                    if (otherTank != tank) {
                        spaceAvailable += otherTank.getCapacity() - otherTank.getStoredLiters();
                    }
                }
                if (spaceAvailable < removedLiters) {
                    canRemove = false;
                }

                if (canRemove) {
                    double remainingLiter = removedLiters;
                    for (Tank otherTank : tanks) {
                        if (otherTank != tank) {
                            remainingLiter = otherTank.deliver(remainingLiter);
                        }
                    }

                    tanks.remove(tank);
                    removedTank = tank;

                    logRedistributeContent(tankNumber, removedLiters);
                } else {
                    System.out.println("Der Tank kann nicht entfernt werden, da nicht genügend Platz zum Umverteilen vorhanden ist.");
                }
                logRemoval(tankNumber);
                break;
            }
        }
        return removedTank;
    }

    private void logRedistributeContent(int tankNumber, double redistributedLiters) {
        try {
            FileWriter fileWriter = new FileWriter("Logs/ContentRedistribution.txt", true);
            PrintWriter writer = new PrintWriter(fileWriter);

            Date currentDate = new Date();

            writer.println("Content Redistribution - From Tank: " + tankNumber + ", Liters: " + redistributedLiters + ", Date: " + currentDate);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double deliverToTanks(double liters) {
        if (liters <= 0) {
            generateStatusMessage("geliefert", 0);
            return 0;
        }

        double remainingLiters = liters;
        for (Tank tank : tanks) {
            remainingLiters = tank.deliver(remainingLiters);
        }
        generateStatusMessage("geliefert", liters - remainingLiters);
        return remainingLiters;
    }

    public double withdrawFromTanks(double liters) {
        if (liters <= 0) {
            generateStatusMessage("entnommen", 0);
            return 0;
        }
        double remainingLiters = liters;
        for (Tank tank : tanks) {
            remainingLiters = tank.withdraw(remainingLiters);
        }
        generateStatusMessage("entnommen", liters - remainingLiters);
        return remainingLiters;
    }

    public void startMaintenance(int tankNumber) {
        for (Tank tank : tanks) {
            if (tank.getNumber() == tankNumber) {
                double tankContent = tank.getStoredLiters();
                boolean canStartMaintenance = true;

                double spaceAvailable = 0;
                for (Tank otherTank : tanks) {
                    if (otherTank != tank) {
                        spaceAvailable += otherTank.getCapacity() - otherTank.getStoredLiters();
                    }
                }
                if (spaceAvailable < tankContent) {
                    canStartMaintenance = false;
                }

                if (canStartMaintenance) {
                    double remainingLiter = tankContent;
                    for (Tank otherTank : tanks) {
                        if (otherTank != tank) {
                            remainingLiter = otherTank.deliver(remainingLiter);
                        }
                    }

                    tank.setUnderMaintenance(true);

                    logstartMaintenance(tankNumber);

                    logRedistributeContent(tankNumber, tankContent);

                    System.out.println("Wartung für Tank " + tank.getName() + " (Nr." + tank.getNumber() + ") wurde gestartet.");
                } else {
                    System.out.println("Die Wartung kann nicht gestartet werden, da nicht genügend Platz zum Umverteilen vorhanden ist.");
                }
                break;
            }
        }
    }

    public void endMaintenance(int tankNumber) {
        for (Tank tank : tanks) {
            if (tank.getNumber() == tankNumber) {
                tank.setUnderMaintenance(false);
                tank.setStoredLiters(0);

                logendMaintenance(tankNumber);

                System.out.println("Wartung für Tank " + tank.getName() + " (Nr." + tank.getNumber() + ") wurde beendet.");
                break;
            }
        }
    }

    public String getStorageInfo() {
        int totalTanks = 0;
        double totalCapacity = 0;
        double totalStoredLiters = 0;

        for (Tank tank : tanks) {
            if (!tank.isUnderMaintenance()) {
                totalTanks++;
                totalCapacity += tank.getCapacity();
                totalStoredLiters += tank.getStoredLiters();
            }
        }

        double totalFreeCapacity = totalCapacity - totalStoredLiters;
        String message = "Das Tanklager umfasst " + totalTanks + " Tanks mit einer Gesamtkapazität von " + totalCapacity + " Litern. " +
                totalStoredLiters + " Liter Öl sind eingelagert. Für " + totalFreeCapacity + " Liter gibt es noch Platz.";
        logStorageInfo(message);

        return message;
    }

    public String getTankInfo(int tankNumber) {
        for (Tank tank : tanks) {
            if (tank.getNumber() == tankNumber) {
                logTankInfo(tank.getInfo());
                return tank.getInfo();
            }
        }
        String Message = "Tank mit Nummer " + tankNumber + " wurde nicht gefunden.";
        logTankInfo(Message);
        return Message;
    }

    public List<Tank> getTanks() {
        return tanks;
    }

    private void logRemoval(int tankNumber) {
        try {
            FileWriter fileWriter = new FileWriter("Logs/Tank.txt", true);
            PrintWriter writer = new PrintWriter(fileWriter);

            Date currentDate = new Date();

            writer.println("Tank deleted - Number: " + tankNumber + ", Date: " + currentDate);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logstartMaintenance(int tankNumber) {
        try {
            FileWriter fileWriter = new FileWriter("Logs/Maintenance.txt", true);
            PrintWriter writer = new PrintWriter(fileWriter);

            Date currentDate = new Date();

            writer.println("Maintenance start - Number: " + tankNumber + ", Date: " + currentDate);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logendMaintenance(int tankNumber) {
        try {
            FileWriter fileWriter = new FileWriter("Logs/Maintenance.txt", true);
            PrintWriter writer = new PrintWriter(fileWriter);

            Date currentDate = new Date();

            writer.println("Maintenance end - Number: " + tankNumber + ", Date: " + currentDate);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logStorageInfo(String Message) {
        try {
            FileWriter fileWriter = new FileWriter("Logs/Info.txt", true);
            PrintWriter writer = new PrintWriter(fileWriter);

            Date currentDate = new Date();

            writer.println("Get StorageInfo - Message: " + Message + ", Date: " + currentDate);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logTankInfo(String Message) {
        try {
            FileWriter fileWriter = new FileWriter("Logs/Info.txt", true);
            PrintWriter writer = new PrintWriter(fileWriter);

            Date currentDate = new Date();

            writer.println("Get TankInfo - Message: " + Message + ", Date: " + currentDate);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printStatusMessage(String message) {
        System.out.println(message);
    }

    private void generateStatusMessage(String operation, double liters) {
        StringBuilder message = new StringBuilder("Öl " + operation + ": " + liters + " Liter\n");

        for (Tank tank : tanks) {
            message.append(tank.getInfo()).append("\n");
        }

        printStatusMessage(message.toString());
    }
}
