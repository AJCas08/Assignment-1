import java.util.Scanner;

public class StarbucksGood {
    // Calculates total for a drink with many options
    public static double calcTotal(String size, boolean customizeOptions, String studentCode, String tip) {
        double total = 0;
        Scanner scanner = new Scanner(System.in);

        if (size == "TALL") { total = 2.5; }
        else if (size == "VENTI") { total = 3.75; }
        else {total = 3.25;}

        //Lets user decide to customize coffee or stick with default
        if(customizeOptions){
            boolean finished = false;
            while(!finished){
                System.out.println("Choose milk: Type WHOLE, OAT, or ALMOND");
                String milk = scanner.nextLine();
                if(!(milk.equalsIgnoreCase("whole") || milk.equalsIgnoreCase("oat") || milk.equalsIgnoreCase("almond"))){
                    System.err.println("Invalid selection for milk, please try again");
                }
                else{
                    try{
                        boolean addingWhip = false;
                        while(true){
                            System.out.println("Add whip? true/false");
                            String addWhip = scanner.nextLine();
                            if(!(addWhip.equalsIgnoreCase("true") || addWhip.equalsIgnoreCase("false"))){
                                System.err.println("Invalid selection for whip, please try again");
                            }
                            else{
                                addingWhip = Boolean.parseBoolean(addWhip);
                                break;
                            }
                        }

                        System.out.println("Enter digit for shot amount");
                        int shots = Integer.parseInt(scanner.nextLine()); 
            
                        System.out.println("Enter digit for syrup pumps");
                        int syrupPumps = Integer.parseInt(scanner.nextLine());  
                        
                        total += calcAddons(milk, shots, syrupPumps, addingWhip);
                        finished = true;
                    }
                    catch(Exception e){
                        System.err.println("Exception thrown: "+e);
                        System.out.println("Please Try again");
                    }
                }
            }
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
    public static double calcAddons(String milk, int shots, int syrupPumps, boolean whip){
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

        if (whip) { 
            addonTotal += 0.3; 
        }

        return addonTotal;
    }

    public static void main(String[] args) {
        double total = 0;
        //Base Cases (No Discounts or Customizations)
        total = calcTotal("TALL", false, null, "0");
        System.out.println("Total = $" + total);
        total = calcTotal("VENTI", false, null, "0");
        System.out.println("Total = $" + total);
        total = calcTotal("GRANDE", false, null, "0");
        System.out.println("Total = $" + total);
        
        //Discount Cases
        total = calcTotal("TALL", false, "STUDENT123", "0");
        System.out.println("Total = $" + total);
        total = calcTotal("VENTI", false, "STudentCode", "0");
        System.out.println("Total = $" + total);
        total = calcTotal("GRANDE", false, "NOTASTUDENT", "0");
        System.out.println("Total = $" + total);
        
        //Tip Cases
        total = calcTotal("TALL", false, null, "10");
        System.out.println("Total = $" + total);
        total = calcTotal("VENTI", false, null, "20");
        System.out.println("Total = $" + total);
        total = calcTotal("TALL", false, null, "notanumber");
        System.out.println("Total = $" + total);
        
        //Combined Cases (Discounts and Tips)
        total = calcTotal("VENTI", false, "STUDENT10", "15");
        System.out.println("Total = $" + total);
        
        //Edge and Error Cases
        total = calcTotal(null, false, null, "0");
        System.out.println("Total = $" + total);
        total = calcTotal("SMALL", false, null, "0");
        System.out.println("Total = $" + total);
    }
}
