import java.util.Scanner;

public class StarbucksBad {
    // Calculates total for a drink with many options
    public static double calcTotal(String size, boolean customizeOptions, String studentCode, String tip) {
        double total = 0;
        Scanner scanner = new Scanner(System.in);

        if (size == "TALL") { total = 2.5; }
        else if (size == "VENTI") { total = 3.75; }
        else {total = 3.25;}

        //Lets user decide to customize coffee or stick with default
        if(customizeOptions){
            System.out.println("Choose milk: Type WHOLE, OAT, or ALMOND");
            String milk = scanner.nextLine();

            System.out.println("Add whip? yes/no");
            String addWhip = scanner.nextLine();

            System.out.println("Enter digit for shot amount");
            int shots = scanner.nextInt(); 

            System.out.println("Enter digit for syrup pumps");
            int syrupPumps = scanner.nextInt();  
            
            total += calcAddons(milk, shots, syrupPumps, addWhip);
        }
        
        if (studentCode != null && studentCode.contains("STUDENT")) {
            //Student Discount
            total -= (total * 0.1); 
        }
        
        //Adds Tax
        total += (total * 0.0825);

        //Cleaned up tip
        if (tip != null) {
            try {
                int tp = Integer.parseInt(tip);
                total *= 1 + (tp / 100);

            } catch (Exception e) {  }

        }


        //Edit: Math.round is used to shorten code and round down to two digits
        return Math.round(total * 100.0) / 100.0;
    }
    //Helper function to help calculate total of addons. Removes bulk params from calc total and speeds up process.
    public static double calcAddons(String milk, int shots, int syrupPumps, String whip){
        double addonTotal = 0.0;

        if (milk.equals("OAT")){
                addonTotal =+ 0.8;
        }else if(milk.equals("ALMOND")){
            addonTotal += + 0.7;
        }

        for (int i = 0; i < shots; i++){
            addonTotal += 0.9;
        }

        for (int i = 0; i < syrupPumps; i++) {
            addonTotal += 0.5;
        }

        if (whip != null && whip.equals("yes")) { 
            addonTotal += 0.3; 
        }

        return addonTotal;
    }

    public static void main(String[] args) {
        System.out.println("Test 1: Tall, no options, no discount, no tip");
        System.out.println(StarbucksBad.calcTotal("TALL", false, null, null)); 
        // expected ≈ 2.71

        System.out.println("Test 2: Venti with student discount");
        System.out.println(StarbucksBad.calcTotal("VENTI", false, "STUDENT123", null)); 
        // expected ≈ 3.65

        System.out.println("Test 3: Grande with 20% tip");
        System.out.println(StarbucksBad.calcTotal("GRANDE", false, null, "20")); 
        // expected ≈ 4.22

        System.out.println("Test 4: Addons directly");
        System.out.println(StarbucksBad.calcAddons("OAT", 2, 3, "yes")); 
        // expected 4.9

        System.out.println("Test 5: Invalid tip ignored");
        System.out.println(StarbucksBad.calcTotal("TALL", false, null, "abc")); 
        // expected ≈ 2.71

        System.out.println("Test 6: Student code without number (still gives discount in your version)");
        System.out.println(StarbucksBad.calcTotal("VENTI", false, "STUDENT", null)); 
        // expected ≈ 3.65
    }
}
