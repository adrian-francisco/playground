/*
 * Name: App.java
 * Author: Adrian Francisco
 * Created: Oct 12, 2017
 */
package fauxtel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fauxtel.dao.PromoDAO;
import fauxtel.dao.ReservationDAO;
import fauxtel.dao.RoomDAO;
import fauxtel.model.Promo;
import fauxtel.model.Reservation;
import fauxtel.model.Room;
import fauxtel.model.Room.Type;

/**
 * Main runner for Fauxtel.
 */
public class App {

    /** Set the timezone to UTC. */
    static {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    /** The logging. */
    private static final Log LOG = LogFactory.getLog(Reservation.class);

    /** The room DAO. */
    private static RoomDAO roomDAO = new RoomDAO();

    /** The promo DAO. */
    private static PromoDAO promoDAO = new PromoDAO();

    /** The reservation DAO. */
    private static ReservationDAO reservationDAO = new ReservationDAO();

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        createInitial();

        System.out.println();
        System.out.println("Welcome to Fauxtels.com Reservations!");

        try (Scanner in = new Scanner(System.in, "UTF-8")) {

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            while (true) {
                System.out.println();
                printMenu();

                System.out.print("Selection: ");
                System.out.flush();
                String selection = in.next();
                System.out.println();

                if ("q".equals(selection)) {
                    System.out.println("Thank you!");
                    System.exit(0);
                }
                else if ("1".equals(selection)) {
                    Room.print(roomDAO.read());
                }
                else if ("2".equals(selection)) {
                    Reservation.print(reservationDAO.read());
                }
                else if ("3".equals(selection)) {
                    Promo.print(promoDAO.read());
                }
                else if ("4".equals(selection)) {
                    Room.print(roomDAO.read());

                    System.out.print("Enter the room name you wish to reserve ('0' to cancel): ");
                    System.out.flush();
                    String roomName = in.next();

                    if ("0".equals(roomName)) {
                        continue;
                    }

                    if (roomDAO.read(roomName) == null) {
                        System.out.println("Invalid room, please try again: " + roomName);
                    }

                    Date checkIn = null;
                    Date checkOut = null;
                    try {
                        System.out.print("Enter check in date (YYYY-MM-DD): ");
                        System.out.flush();
                        checkIn = df.parse(in.next());

                        System.out.print("Enter check out date (YYYY-MM-DD): ");
                        System.out.flush();
                        checkOut = df.parse(in.next());
                    }
                    catch (ParseException e) {
                        System.out.println("Invalid date pattern, please try again: " + e.getMessage());
                        continue;
                    }

                    System.out.print("Enter a guest name: ");
                    System.out.flush();
                    String guestName = in.next();

                    System.out.println();

                    Reservation reservation = new Reservation();
                    reservation.setRoom(roomDAO.read(roomName));
                    reservation.setName(guestName);

                    reservationDAO.create(reservation, checkIn, checkOut);

                    System.out.println("Reservation confirmed.");
                }
                else if ("5".equals(selection)) {
                    Room.Type.print();
                    System.out.println();

                    System.out.print("Enter the room type: ");
                    System.out.flush();
                    Room.Type type = Room.Type.values()[in.nextInt()];

                    System.out.print("Enter a date (YYYY-MM-DD): ");
                    System.out.flush();
                    Date date = null;
                    try {
                        date = df.parse(in.next());
                    }
                    catch (ParseException e) {
                        System.out.println("Invalid date pattern, please try again: " + e.getMessage());
                        continue;
                    }

                    System.out.println();

                    Collection<Room> rooms = roomDAO.read(type, date);
                    if (rooms.isEmpty()) {
                        System.out.println("No available rooms.");
                    }
                    else {
                        System.out.println("The following rooms are available:");
                        Room.print(rooms);
                    }
                }
                else if ("6".equals(selection)) {
                    Room.print(roomDAO.read());

                    System.out.print("Enter the room names (comma delimited, no spaces) for price change: ");
                    System.out.flush();
                    String line = in.next();

                    System.out.print("Enter the new price: ");
                    System.out.flush();
                    double price = in.nextDouble();

                    Collection<Room> roomsToUpdate = new ArrayList<>();

                    for (String roomName : line.split(",")) {
                        Room room = roomDAO.read(roomName);
                        if (room != null) {
                            roomsToUpdate.add(room);
                        }
                    }

                    roomDAO.update(roomsToUpdate, price);

                    System.out.println();
                    System.out.println("Successfully changed the price.");
                }
                else if ("7".equals(selection)) {
                    Room.print(roomDAO.read());

                    System.out.print("Enter the room names (comma delimited, no spaces) for special offer: ");
                    System.out.flush();
                    String line = in.next();

                    System.out.print("Enter the offer price: ");
                    System.out.flush();
                    double price = in.nextDouble();

                    Date startDate = null;
                    Date endDate = null;
                    try {
                        System.out.print("Enter the offer start date (YYYY-MM-DD): ");
                        System.out.flush();
                        startDate = df.parse(in.next());

                        System.out.print("Enter the offer end date (YYYY-MM-DD): ");
                        System.out.flush();
                        endDate = df.parse(in.next());
                    }
                    catch (ParseException e) {
                        System.out.println("Invalid date pattern, please try again: " + e.getMessage());
                        continue;
                    }

                    Collection<Room> rooms = new ArrayList<>();

                    for (String roomName : line.split(",")) {
                        Room room = roomDAO.read(roomName);
                        if (room != null) {
                            rooms.add(room);
                        }
                    }

                    promoDAO.create(rooms, startDate, endDate, price);

                    System.out.println();
                    System.out.println("Successfully applied special offer price.");
                }
                else {
                    System.out.println("Invalid selection, please try again.");
                }
            }
        }
    }

    /**
     * Create initial test data.
     */
    private static void createInitial() {
        Room room1A = new Room();
        room1A.setName("1A");
        room1A.setPrice(130.93);
        room1A.addType(Type.SINGLE_PERSON);
        room1A.addType(Type.DOUBLE_BED);
        roomDAO.create(room1A);

        Reservation reservation1A = new Reservation();
        reservation1A.setRoom(room1A);
        reservation1A.setDate(createDate(2017, Calendar.OCTOBER, 15));
        reservation1A.setName("Adrian");
        reservationDAO.create(reservation1A);

        Promo promo1A = new Promo();
        promo1A.setRoom(room1A);
        promo1A.setDate(createDate(2017, Calendar.OCTOBER, 10));
        promo1A.setDiscountPrice(120.93);
        promoDAO.create(promo1A);

        Room room1B = new Room();
        room1B.setName("1B");
        room1B.setPrice(150.00);
        room1B.addType(Type.SINGLE_PERSON);
        room1B.addType(Type.KING_BED);
        roomDAO.create(room1B);

        Room room2B = new Room();
        room2B.setName("2B");
        room2B.setPrice(200.00);
        room2B.addType(Type.DOUBLE_PERSON);
        room2B.addType(Type.KING_BED);
        roomDAO.create(room2B);

        Promo promo2B1 = new Promo();
        promo2B1.setRoom(room2B);
        promo2B1.setDate(createDate(2017, Calendar.OCTOBER, 23));
        promo2B1.setDiscountPrice(100.00);
        promoDAO.create(promo2B1);

        Promo promo2B2 = new Promo();
        promo2B2.setRoom(room2B);
        promo2B2.setDate(createDate(2017, Calendar.OCTOBER, 20));
        promo2B2.setDiscountPrice(100.00);
        promoDAO.create(promo2B2);

        Reservation reservation2BDay1 = new Reservation();
        reservation2BDay1.setRoom(room2B);
        reservation2BDay1.setDate(createDate(2017, Calendar.OCTOBER, 21));
        reservation2BDay1.setName("Nico");
        reservationDAO.create(reservation2BDay1);

        Reservation reservation2BDay2 = new Reservation();
        reservation2BDay2.setRoom(room2B);
        reservation2BDay2.setDate(createDate(2017, Calendar.OCTOBER, 20));
        reservation2BDay2.setName("Nico");
        reservationDAO.create(reservation2BDay2);
    }

    /**
     * Creates the date.
     *
     * @param year the year
     * @param month the month
     * @param day the day
     * @return the date
     */
    private static Date createDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }

    /**
     * Prints the menu.
     */
    private static void printMenu() {
        System.out.println("Please select one of the following options:");
        System.out.println();
        System.out.println("1. List all rooms.");
        System.out.println("2. List all reservations.");
        System.out.println("3. List all special offers.");
        System.out.println("4. Reserve a room.");
        System.out.println("5. Check availability by room type for a given date.");
        System.out.println("6. Change price of rooms.");
        System.out.println("7. Apply special offer price for rooms.");
        System.out.println("q. Quit.");
        System.out.println();
    }
}
