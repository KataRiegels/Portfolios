
public class Main {
    public static void main(String[] args) {
        conclear(10); //Temporary command to add space in console
        String db = "jdbc:sqlite:/home/xilas/Desktop/gits/Portfolios/Database/Main/StudentsDB.db";
        jdbc con = new jdbc(db);
    }
    public static void conclear(int times)
    {
        for (int i = 0 ; i < times ; i++)
        {
            System.out.println("\n");
        }
    }
}
