import java.sql.*;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Main {

    private  static  final String url = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String username = "system";
    private  static  final String password = "tiger";

    public static void main(String[] args)throws  ClassNotFoundException, SQLException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Connect Successfully");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            Connection con = DriverManager.getConnection(url, username, password);
            Statement stmt = con.createStatement();
            while (true) {
                try {
                    System.out.println();
                    System.out.println("\t\t\t HOTEL MANAGEMENT SYSTEM");
                    System.out.println("1. Reserve a room");
                    System.out.println("2. View Reservation");
                    System.out.println("3. Get Room Number");
                    System.out.println("4. Update Reservation");
                    System.out.println("5. Delete Reservation");
                    System.out.println("0. Exit");
                    System.out.println("Choose an option :");
                    int choice = sc.nextInt();

                    switch (choice) {

                        case 1:
                            reserveRoom(con, sc, stmt);
                            break;
                        case 2:
                            viewReservation(con, stmt);
                            break;
                        case 3:
                            getRoomNumber(con, sc, stmt);
                            break;
                        case 4:
                            updateReservation(con, sc, stmt);
                            break;
                        case 5:
                            deleteReservation(con, sc, stmt);
                            break;
                        case 0:
                            sc.close();
                            exit();
                            return;
                        default:
                            System.out.println("Inter valid option :");
                    }
                }catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please Valid input.");
                    sc.nextLine(); // Clear the invalid input
                }
            }

        } catch(Exception e){
                System.out.println(e.getMessage());
            }




    }

    public static void reserveRoom(Connection con, Scanner sc, Statement stmt){

        System.out.print("Enter Room Number :");
        int room_number = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Guest Name :");
        String name = sc.nextLine();
        System.out.print("Enter Contact Details :");
        String contact = sc.next();


        String sql = "Insert into reservation(guest_name,room_number,contact_number)"+ "values('" +name+ "', " +room_number+ ", '"+ contact +"')";

        try {
            int affectedRows = stmt.executeUpdate(sql);

            if(affectedRows>0){
                System.out.println("Reservation successfully");
            }else {
                System.out.println("Reservation failed");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void viewReservation(Connection con, Statement stmt) throws SQLException {

        String sql = "select reservation_id, guest_name, room_number, contact_number,  reservation_date  from reservation";

        try{
            ResultSet rs = stmt.executeQuery(sql);
            String sqli = "select count(*) from reservation";
//            int count = stmt.executeUpdate(sqli);
//            System.out.println("Current Reservation "+count);

            System.out.println("+------------------------+----------------------------+------------------+-----------------------+-----------------------------+");
            System.out.println("| Reservation Id         |  Guest                     |  Room Number     | Contact               | Reservation Date            |");
            System.out.println("+------------------------+----------------------------+------------------+-----------------------+-----------------------------+");

            while(rs.next()){
                int id = rs.getInt("reservation_id");
                String name  = rs.getString("guest_name");
                int room_number = rs.getInt("room_number");
                String contact = rs.getString("contact_number");
                String date = rs.getTimestamp("reservation_date").toString();

                // Display Details
                System.out.printf("| %-22d | %-26s | %-16d | %-21s | %-19s     |\n",id,name,room_number,contact,date);
                System.out.println("+------------------------+----------------------------+------------------+-----------------------+-----------------------------+");


            }
            }catch (Exception e){
            System.out.println(e.getMessage());

            }
    }

    public  static  void getRoomNumber(Connection con,Scanner sc, Statement stmt){
        System.out.println("Enter Reservation Id :");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter Guest Name :");
        String name = sc.nextLine();

        String sql = "SELECT room_number FROM reservation WHERE reservation_id = " + id + " AND guest_name = '" + name + "'";

        try{
            ResultSet rs = stmt.executeQuery(sql);

            if(rs.next()){
                int roomNumber = rs.getInt("room_number");
                System.out.println("Room number for Reservation ID :" +id+ "\nGuest Name :" +name+ "\nRoom Number is :"+roomNumber);
            }else {
                System.out.println("Reservation not found");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public  static void updateReservation(Connection con,Scanner sc, Statement stmt){
        System.out.println("What you want to update Select:");
        System.out.println("1. Name Update :");
        System.out.println("2. Contact Update :");
        System.out.println("3. Room Update :");
        System.out.println("4. Main Menu :");
        int choose  = sc.nextInt();

        switch (choose){

            case 1:
                nameUpdate(con, sc, stmt);
                break;
            case 2:
                contactUpdate(con, sc, stmt);
                break;
            case 3:
                roomUpdate(con, sc, stmt);
                break;
            case 4:
                return;
            default:
                System.out.println("Enter valid option ");
        }



        }
    // ========================================== Update Details Start ===============================================
    public static  void nameUpdate(Connection con, Scanner sc, Statement stmt){
        System.out.println("Enter Id : ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter Name : ");
        String name = sc.nextLine();

        String sql = "update reservation set guest_name = '" + name + "' where reservation_id = "+id+" ";

        try{
            int affectedRows = stmt.executeUpdate(sql);

            if(affectedRows>0){
                System.out.println("Update name successfully");
            }else {
                System.out.println("Update failed");
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

    }
    public  static void contactUpdate(Connection con, Scanner sc, Statement stmt){
        System.out.println("Enter Id : ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter Room Number : ");
        String contact = sc.nextLine();

        String sql = "update reservation set contact_number = '"+contact+"' where reservation_id = "+id+" ";

        try{
            int affectedRows = stmt.executeUpdate(sql);

            if(affectedRows>0){
                System.out.println("Update Contact successfully");
            }else {
                System.out.println("Update failed");
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

    }
    public static void roomUpdate(Connection con, Scanner sc, Statement stmt){
        System.out.println("Enter Id : ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter Room Number : ");
        int roomNumber = sc.nextInt();

        String sql = "update reservation set room_number = "+roomNumber+" where reservation_id = "+id+" ";

        try{
            int affectedRows = stmt.executeUpdate(sql);

            if(affectedRows>0){
                System.out.println("Update Room successfully");
            }else {
                System.out.println("Update failed");
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    // ============================================= Update Details End ===============================================

    public static  void deleteReservation(Connection con,Scanner sc, Statement stmt){

        System.out.println("Enter Id : ");
        int id = sc.nextInt();

        String sql = "delete from reservation where reservation_id = "+id+" ";

        try{
            int affectedRows = stmt.executeUpdate(sql);
           if(affectedRows>0){
               System.out.println("Deletion successfully");
           }else{
               System.out.println("Delete failed");
           }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
    // ========================================== Exit =================================================================
    public static  void exit() throws InterruptedException {
        System.out.print("Exiting System");

            int i= 5;
            while(i!=0){
                System.out.print(".");
                Thread.sleep(450);
                i--;
            }
        System.out.println();
        System.out.println("Thanks for visiting!!!");
    }

}
