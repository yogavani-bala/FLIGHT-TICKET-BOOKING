import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FlightBookingSystem {
    public static class Flight {
        public int flightID;
        public int economySeats;
        public int businessSeats;
        public double economyPrice;
        public double businessPrice;
        public List<String> economyPassengerDetails;
        public List<String> businessPassengerDetails;

        public Flight(int flightID, int economySeats, int businessSeats, double economyPrice, double businessPrice) {
            this.flightID = flightID;
            this.economySeats = economySeats;
            this.businessSeats = businessSeats;
            this.economyPrice = economyPrice;
            this.businessPrice = businessPrice;
            this.economyPassengerDetails = new ArrayList<>();
            this.businessPassengerDetails = new ArrayList<>();
        }

        public void bookTicket(String passengerName, int tickets, String classType) {
            if (classType.equalsIgnoreCase("economy")) {
                if (tickets > economySeats) {
                    System.out.println("Not Enough Economy Class Seats Available.");
                    return;
                }
                String passengerDetail = "Passenger: " + passengerName + " -- Class: Economy" +
                        " -- Number of Tickets: " + tickets + " -- Total Fare: Rs" + (economyPrice * tickets);
                economyPassengerDetails.add(passengerDetail);
                economySeats -= tickets; // Reduce available seats
            } else if (classType.equalsIgnoreCase("business")) {
                if (tickets > businessSeats) {
                    System.out.println("Not Enough Business Class Seats Available.");
                    return;
                }
                String passengerDetail = "Passenger: " + passengerName + " -- Class: Business" +
                        " -- Number of Tickets: " + tickets + " -- Total Fare: Rs" + (businessPrice * tickets);
                businessPassengerDetails.add(passengerDetail);
                businessSeats -= tickets; // Reduce available seats
            } else {
                System.out.println("Invalid Class Type.");
                return;
            }

            System.out.println("Booking Successful!");
            flightSummary();
            printDetails(classType);
        }

        public void cancelBooking(String passengerName, String classType) {
            boolean found = false;
            if (classType.equalsIgnoreCase("economy")) {
                for (String details : economyPassengerDetails) {
                    if (details.contains(passengerName)) {
                        economyPassengerDetails.remove(details);
                        economySeats += getTicketsFromDetails(details); // Increase available seats
                        System.out.println("Booking for Passenger: " + passengerName + " in Economy Class canceled.");
                        found = true;
                        break;
                    }
                }
            } else if (classType.equalsIgnoreCase("business")) {
                for (String details : businessPassengerDetails) {
                    if (details.contains(passengerName)) {
                        businessPassengerDetails.remove(details);
                        businessSeats += getTicketsFromDetails(details); // Increase available seats
                        System.out.println("Booking for Passenger: " + passengerName + " in Business Class canceled.");
                        found = true;
                        break;
                    }
                }
            } else {
                System.out.println("Invalid Class Type.");
                return;
            }

            if (!found) {
                System.out.println("No booking found for Passenger: " + passengerName + " in " + classType + " Class.");
            }

            flightSummary();
            printDetails(classType);
        }

        private int getTicketsFromDetails(String details) {
            String[] parts = details.split("--");
            String ticketsPart = parts[2].trim(); // "Number of Tickets: X"
            String ticketsValue = ticketsPart.split(":")[1].trim(); // "X"
            return Integer.parseInt(ticketsValue);
        }

        public void flightSummary() {
            System.out.println("\nFlight ID: " + flightID);
            System.out.println("Economy Class - Seats Available: " + economySeats + ", Price per Ticket: Rs" + economyPrice);
            System.out.println("Business Class - Seats Available: " + businessSeats + ", Price per Ticket: Rs" + businessPrice);
        }

        public void printDetails(String classType) {
            if (classType.equalsIgnoreCase("economy")) {
                System.out.println("\nEconomy Class Details for Flight ID " + flightID + ":");
                if (economyPassengerDetails.isEmpty()) {
                    System.out.println("No passenger details available.");
                } else {
                    for (String details : economyPassengerDetails) {
                        System.out.println(details);
                    }
                }
            } else if (classType.equalsIgnoreCase("business")) {
                System.out.println("\nBusiness Class Details for Flight ID " + flightID + ":");
                if (businessPassengerDetails.isEmpty()) {
                    System.out.println("No passenger details available.");
                } else {
                    for (String details : businessPassengerDetails) {
                        System.out.println(details);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        ArrayList<Flight> flights = new ArrayList<>();
        flights.add(new Flight(1, 100, 50, 3000.0, 6000.0)); // Example flight with ID 1, 100 economy seats, 50 business seats, Rs3000 economy price, Rs6000 business price
        flights.add(new Flight(2, 150, 75, 4000.0, 8000.0)); // Example flight with ID 2, 150 economy seats, 75 business seats, Rs4000 economy price, Rs8000 business price

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\nFLIGHT BOOKING");
            System.out.println("\nMenu:");
            System.out.println("1. Book Tickets");
            System.out.println("2. Cancel Booking");
            System.out.println("3. Print Flight Details");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    System.out.print("Enter Flight ID: ");
                    int flightID = sc.nextInt();

                    if (flightID < 1 || flightID > flights.size()) {
                        System.out.println("Invalid Flight ID.");
                        break;
                    }

                    Flight currentFlight = flights.get(flightID - 1);

                    System.out.print("Enter passenger name: ");
                    String passengerName = sc.nextLine();

                    System.out.print("Enter class type (Economy/Business): ");
                    String classType = sc.nextLine();

                    System.out.print("Enter number of tickets to book: ");
                    int ticketsToBook = sc.nextInt();

                    currentFlight.bookTicket(passengerName, ticketsToBook, classType.toLowerCase());
                    break;

                case 2:
                    System.out.print("Enter Flight ID: ");
                    int cancelFlightID = sc.nextInt();

                    if (cancelFlightID < 1 || cancelFlightID > flights.size()) {
                        System.out.println("Invalid Flight ID.");
                        break;
                    }

                    Flight flightToCancel = flights.get(cancelFlightID - 1);

                    System.out.print("Enter passenger name: ");
                    String cancelPassengerName = sc.next();

                    System.out.print("Enter class type (Economy/Business): ");
                    String cancelClassType = sc.next();

                    flightToCancel.cancelBooking(cancelPassengerName, cancelClassType.toLowerCase());
                    break;

                case 3:
                    for (Flight flight : flights) {
                        flight.flightSummary();
                        flight.printDetails("economy");
                        flight.printDetails("business");
                    }
                    break;

                case 4:
                    System.out.println("Exiting Program.");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice. Please enter a valid choice.");
                    break;
            }
        }
    }
}
