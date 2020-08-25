
import java.util.*;
import java.math.*;
class Gym
{
    //corrections for excost, ledunits and acunits included
    public static double values(double temp, double mol) //for different Cv values
    {
        double q=0;
        //Cv values as per experiment
        int ch=0;
        if(temp<273.2)
            ch=1;
        else if(temp>=273.2 && temp<280)
            ch=2;
        else if(temp>=280 && temp<288.7)
            ch=3;
        else if(temp>=288.7 && temp<300)
            ch=4;
        else if(temp>=300 && temp<320)
            ch=5;
        switch(ch)  //case wise calculation of heat required for a small change in temperature (Cv remaining constant for the same)
        {
            case 1: // 273.2K
            q+= mol*0.02077*(273.2-temp);
            //T=280;
            break;
            case 2: //280K
            q+= mol*0.02078*(280-temp);
            //T=288.7;
            break;
            case 3: //288.7K
            q+= mol*0.02078*(288.7-temp);
            //T=300;
            break;
            case 4: //300K
            q+= mol*0.02080*(300-temp);
            //T=320;
            break;
            case 5: //320K
            q+= mol*0.02083*(320-temp);
            //T=360;
            break;
        }
        return(q);
    }

    public static double Heat(double Ti, double Tf, double mol) // calculating heat at constant volume
    {
        double temp=Ti;
        double heat=0;
        while(temp<Tf)
        {
            heat+=values(temp, mol);
            //temp=T;
            if(temp<273.2)
                temp=Ti;
            else if(temp>=273.2 && temp<280)
                temp=273.2;
            else if(temp>=280 && temp<288.7)
                temp=280;
            else if(temp>=288.7 && temp<300)
                temp=288.7;
            else if(temp>=300 && temp<320)
                temp=300; 
        }
        return(heat); 
    }

    public static double cost(double kwh)  //slab function for the cost calculation for units of electricity in the state of Goa
    {
        //double engy=100; //obtained from other modules (energy consumption per second)
        //double kwh=(engy/1000)*3600;
        //cost in Rs for the state of Goa
        double cost=0;
        if(kwh>=0 && kwh<=100)
            cost=kwh*1.4;
        else if(kwh>100 && kwh<=200)
            cost=100*1.4 + (kwh-100)*2.1;
        else if(kwh>200 && kwh<=300)
            cost=100*1.4 + 100*2.1 + (kwh-200)*2.65;
        else if(kwh>300 && kwh<=400)
            cost=100*1.4 + 100*2.1 + 100*2.65 + (kwh-300)*3.45;
        else if(kwh>400)
            cost=100*1.4 + 100*2.1 + 100*2.65 + 100*3.45 + (kwh-400)*4;
        //System.out.print("Cost of electricity= Rs. ");
        return(cost);
    }

    public static void main()
    {

        // BASIC + LIGHTING

        int pplh=0; // no. of gymers per hour
        double sqft=0;  // plot area
        Scanner sc = new Scanner(System.in);
        System.out.println("**********INPUT**********"); // menu driven program for the input data
        System.out.println();
        System.out.println("1. Choose 1 if you have an estimate of the number of gymers per hour.");
        System.out.println("2. Choose 2 if you have the plot size for the proposed gym.");
        System.out.println();
        System.out.println("*************************");
        System.out.print("Your choice: ");
        int ch; // stores the choice
        ch=sc.nextInt();
        System.out.println();
        //Industry thumb rule: 10-12 square feet for each member 
        switch(ch)
        {
            case 1:
            System.out.print("Enter an estimate of the number of gymers per hour: ");
            pplh=sc.nextInt();
            System.out.println();
            sqft=pplh*11*0.3048*0.3048; //square footage (m^2) of the gym
            break;

            case 2:
            //System.out.println("$");
            System.out.print("Enter the plot area in square feet: ");
            sqft=sc.nextDouble();

            System.out.println();
            //System.out.println("$");
            pplh+=(int)(sqft/11.0);
            //System.out.print(pplh);
            sqft=sqft*0.3048*0.3048; // converting to (m^2)
            //System.out.println("$");
            break;

            default:
            System.out.println("Wrong choice made. Please start again.");
        }
        if(ch==1 || ch==2)
        {
            //need to add some more area as this is the minimum floor area
            //Industry thumb rule: 500 lu/m^2 for gyms
            System.out.println();
            System.out.println("Industry thumb rule: A minimum of 10-12 square feet is a must for each member in any commercial gym. We will take 11 sq.ft.");
            System.out.println();
            //System.out.println("1. A minimum of 10-12 square feet is a must for each member in any commercial gym. We will take 11 sq.ft.");
            //System.out.println("2. The");
            System.out.print("Floor area of the gym (in m^2): ");
            System.out.println(sqft);
            System.out.print("Number of gymers per hour: ");
            System.out.println(pplh);
            System.out.println();
            System.out.println("Let the gym be open for 12 hours (8 am to 8 pm) everyday. Assuming the number of gymers to be constant at the previous value: ");
            int ppl= pplh*12;
            System.out.print("Number of gymers per day: ");
            System.out.println(ppl);
            System.out.println();
            System.out.print("LIGHTING:");
            System.out.println();
            double lu=sqft*500; //lumens required
            /* 1 lux= 1 lumen/(m^2) 
             * LED is rated at 90 lumen/watt
             * wattage consume by 1 LED =lumen required/90
             * units consumed by 1 LED= (wattage*3600)/1000
             */
            //double ledunits=((lu/15)*3600)/1000;
            double ledcount= lu/90; // wattage of LEDs
            double ledunits=(ledcount*3600)/1000;
            //ledunits/=10; //correction
            System.out.println("Industry thumb rule: 500 lu/m^2");
            System.out.println("Rating of LEDs used: 90 lumen/watt");
            System.out.println();
            System.out.print("Units of electricity consumed by LED lights(kWh): ");
            System.out.println(ledunits);

            //AIR CONDITIONING 

            // standard room height at 2.4m
            double vol=sqft*2.4; // gym volume
            double ambtemp;
            System.out.println();
            System.out.println("AIR CONDITIONING:");
            System.out.println();
            //Scanner sc=new Scanner(System.in);
            System.out.println("Standard room height: 2.4 m");
            System.out.print("Volume of the room(m): ");
            System.out.println(vol);
            System.out.println();
            System.out.println("Ambient temperature: ");
            ambtemp=sc.nextDouble();
            //Electric water purifiers use negligible energy (around 1.5 units a year)
            //Ministry of Power Mandate on AC temp at 24 degree celsius which is good both financially and environmentally
            double Tf=24+273;
            double Ti=ambtemp+273;
            System.out.println();
            System.out.println("Ministry of Power Mandate on AC temp requires the room temperature (any commercial set up) \nto be 24 degree celsius as it is good both financially and environmentally.");
            /* call recurring function to calculate heat at constant volume required 
             * Cv values change with temperature. 
             * Total heat required = summation of the heat required for small changes in temperature (for which Cv can be considered to be constant)
             * No. of moles = (total volume of air (=volume of the room) in (m^3) * 1000(to convert to L))/22.4 [for ideal gases and air can be considered ideal]
             * kmol=mol/1000= volume of room/22.4
             */
            double mol=(vol*1000)/22.4;
            //double heat=Heat(Ti,Tf,mol);//heat required for the change in temperature
            /* 1.5 tonne AC converts 1.5 tonne of ice at 0 degree celsius to water at same temp in 24 hours
             * Lets assume 1.5 tonne AC is used and once 24 degrees is attained, the temperature doesn't change
             * 1.5 tonne AC consumes 1.5 units every hour
             * 1.5 tonne =1500 kg
             * latent heat of fusion of ice= 334.4 J/g= 334400 J/kg 
             */
            //math.pow(Ti,2)
            //double cvdt=(-1)*((8.314-28.11)*(Ti-Tf)-((0.5*0.1967*(10^(-2)))*((Math.pow(Ti,2))-(Math.pow(Tf,2))))-(((0.4802*(10^(-5)))/3)*((Math.pow(Ti,3))-(Math.pow(Tf,3))))-((((-1)*(1.966)*(10^(-9)))/4)*((Math.pow(Ti,4))-(Math.pow(Tf,4)))));
            //double cvdt=(8.314-28.11)*(Ti-Tf)-((0.5*0.1967*(10^(-2)))*((Ti^2.0)-(Tf^2.0)))-(((0.4802*(10^(-5)))/3)*((Ti^3.0)-(Tf^3.0)))-((((-1)*(1.966)*(10^(-9)))/4)*((Ti^4.0)-(Tf^4.0)));
            double cvdt= (8.314-28.11)*(Ti-Tf);
            cvdt-=(0.5*0.1967*(10^(-2)))*((Math.pow(Ti,2))-(Math.pow(Tf,2)));
            cvdt-=((0.4802*(10^(-5)))/3)*((Math.pow(Ti,3))-(Math.pow(Tf,3)));
            cvdt+=(1.966)*((10^(-9))/4)*((Math.pow(Ti,4))-(Math.pow(Tf,4)));
            double heat=mol*cvdt;  // heat for change in temp in J
            System.out.print("Heat required (in J) for bringing the temperature of the room to 24 degree celsius: ");
            System.out.println(heat);
            double acheat=1500*334400; //heat withdrawn by AC in 24 hours in J
            double acheath=acheat/24; //heat withdrawn by AC in 1 hour
            double time=heat/acheath;  //time in hours required to attain 24 degree celsius
            System.out.println();
            System.out.println("Lets use a 1.5 tonne AC.");
            System.out.println("A 1.5 tonne AC converts 1.5 tonnes of ice at 0 degree celsius to water at same temp in 24 hours.");
            System.out.println("A 1.5 tonne AC uses 1.5 units of energy every hour that it is running.");
            double acunits=time*1.5; //units of electricity consumed by AC
            System.out.println("This cooling is done before 8 am.");
            System.out.println("Beyond 8 am, the heating in the gym is due to the heat given out by the people working out.");
            System.out.println("An average human being produces 200 calories of heat per hour.");
            double bodyh=ppl*200*4.182; //body heat produced in J
            acunits+=((bodyh/acheath)*1.5);
            acunits/=1000; // correction
            System.out.print("Units of electricity consumed by AC(kWh)= ");
            System.out.println(acunits);
            System.out.println();
            
            
            //WATER PURIFIERS
            System.out.println("WATER PURIFIERS:");
            System.out.println();
            System.out.println("A single water purifier is sufficient for a gym of any floor area.");
            System.out.println("Electric water purifiers use negligible energy (around 1.5 units a year).");
            System.out.println();

            // WORK OUT
            System.out.println();
            System.out.println("*******WORKOUT PLAN*******");
            System.out.println();
            System.out.println("1. 30 minutes on treadmill");
            System.out.println("2. 30 minutes on crosstrainer");
            System.out.println("3. 30 minutes on electric bike");
            System.out.println();
            System.out.println("**************************");
            System.out.println();
            System.out.println("On an average, regular gym equipments require 650 W every hour.");
            System.out.println("Eco-friendly equipment: ");
            System.out.println("SportsArt Treadmill produces 200 Wh of electrical energy(DC).");
            System.out.println("Imaginarium Cycle produces 160 Wh of electrical energy(DC).");
            System.out.println("Lasell Gym Crosstrainer produces 50 Wh of electrical energy(DC).");
            System.out.println();
            System.out.println("For the purpose of this gym, we will use a dynamo of 80% efficiency of conversion from DC to AC.");
            System.out.println("As we might need to reverse engineer the equipments, we shall assume an efficiency of 50% of the above mentioned models.");
            double tdl= 200;   //SportsArt Treadmill
            double cyc= 160;   //Imaginarium cycle
            double ct= 50;   //Lasell Gym 
            //System.out.println(tdl);
            double wpp=(0.5*0.5*(tdl+cyc+ct))/1000; // electricity produced per person, 50% efficiency of the reverse engineered equipment
            wpp*=0.8; //80% efficiency of conversion from DC to AC current
            double unitearn= ppl*wpp; // electricity unit benefit due to the energy produced
            System.out.println();
            System.out.print("Total units of electicity produced due to the eco friendly equipment(kWh): ");
            System.out.println(unitearn);
            //Regular equipment:
            tdl=650;
            cyc=650;
            ct=650;
            wpp=(0.5*(tdl+cyc+ct))/1000;
            double unitcons= ppl*wpp; // units consumed by regular instruments
            System.out.println();
            System.out.print("Total units of electicity consumed due to the regular equipment(kWh): ");
            System.out.println(unitcons);
            double unitben; //to store the total electricity unit benefit due to swapping of equipments
            unitben=unitcons+unitearn;
            System.out.println();
            System.out.print("Total benefit in terms of units of electicity(kWh) due to the swapping of regular equipments by eco friendly ones: ");
            System.out.println(unitben);
            System.out.println();

            // COST CALCULATIONS
            System.out.println("COST CALCULATIONS:");
            System.out.println();
            System.out.println("All calculations are in accordance with the cost values for the state of Goa.");
            double useunits= ledunits+acunits;
            double utilcost=cost(useunits); //cost for lighting and air conditioning
            System.out.print("Total cost for the lighting and the AC: Rs. ");
            System.out.println(utilcost);
            double regunits= useunits+unitcons;
            double ecounits= useunits-unitearn;
            double regcost=cost(regunits); //regular gym cost
            double ecocost=cost(ecounits); //eco gym cost
            double costben=regcost-ecocost; //cost benefit due to use of eco friendly equipment
            double brkevnunits=ecounits;
            System.out.println();
            System.out.print("Regular gym's running cost: Rs. ");
            System.out.println(regcost);
            System.out.print("Proposed gym's running cost: Rs. ");
            System.out.println(ecocost);
            System.out.println();
            System.out.print("Total cost benefit: Rs. ");
            System.out.println(costben);
            //double brkevn= brkevnunits
            System.out.println();
            if(brkevnunits!=0)
            {
                // SOLAR PANEL
                System.out.println("SOLAR PANELS:");
                System.out.println();
                System.out.println("As the energy consumed and produced are not breaking even, we must use solar panels.");

                double scap; // stores the system capacity
                //Scanner sc = new Scanner (System.in);
                System.out.println("System capacity of the solar panels (usually between 250 W to 400 W). ");
                System.out.print("Your choice: ");
                scap=sc.nextDouble();
                System.out.println();
                double scapunits=(scap*3600)/1000;

                //double brkevn =1000; //amount of electricity required to break even (obtained from other function)
                double suntime;
                suntime= (brkevnunits/scapunits); // exposure time in hours
                System.out.println();
                System.out.println("The cost of the solar panels is a  one time investment.");
                System.out.println("The average cost of setting up a single solar panel= Rs. 40,000");
                System.out.println();
                System.out.println("Although an average of 9 hours of sunlight is available per day, various factors affect this resulting in an average of 5kWh produced everyday.");
                System.out.println("Thus, we are reducing the sun exposure time to fit this value.");
                System.out.println();
                if(suntime>5) //taking 5 hours because an average of 5KWh is produced per day
                {
                    System.out.print("Number of panels: ");
                    double nopanels=(int)(suntime/5); //calculating the number of panels required
                    System.out.println(nopanels);

                    System.out.println("Sun exposure time for the solar panels: 5 hours ");
                    double excess= (5*nopanels)-suntime;  //excess sun exposure
                    System.out.print("Excess suntime(h): ");
                    System.out.println(excess);
                    double exseng=excess*scapunits; //excess electrical energy produced (kWh)
                    double exscost=cost(exseng); //cost of the excess energy obtained
                    
                    System.out.print("Cost of the excess energy obtained (additional benefit): ");
                    System.out.println(exscost/10);
                    double spcost=40000*nopanels;
                    System.out.println();
                    System.out.print("Cost of solar panelling (one time investment): Rs. ");
                    System.out.println(spcost);
                }
                else
                {
                    System.out.print("Sun exposure time for the solar panels: ");
                    System.out.print(suntime); // [[[[ CAN USE THE EXTRA SUNTIME ENERGY FOR LIGHTING WHEN EQUIPMENT IS NOT IN USE]]]]
                    System.out.println(" hours");
                    System.out.println("The energy obtained due to any additional sun exposure time can be used for lighting when equipments are not in use.");
                    double excess=5-suntime;
                    System.out.print("Excess suntime(h): ");
                    System.out.println(excess);
                    
                    double exseng=excess*scapunits; //excess electrical energy produced (kWh)
                    double exscost=cost(exseng); //cost of the excess energy obtained
                    System.out.print("Cost of the excess energy obtained (additional benefit): Rs. ");
                    System.out.println(exscost/10); // cost with correction
                    System.out.println();
                    System.out.print("Cost of solar panelling (one time investment): Rs. 40,000");
                }
            }
        }
    }
}

