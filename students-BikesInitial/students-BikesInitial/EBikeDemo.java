import java.util.*;

/**
 * Provides a simple demonstration and simulation environment for the 
 * Electric Bike (EB) and Bike Station model.
 * It sets up the environment and runs the simulation for a fixed number of steps.
 *
 * Scenarios: 
 * <ul>
 * <li>Demo SIMPLE (demo=DemoType.SIMPLE): Two bikes are created.</li>
 * <li>Demo MEDIUM (demo=DemoType.MEDIUM): Five bikes are created.</li>
 * <li>Demo ADVANCED(demo=DemoType.ADVANCED): Eight bikes are created.</li>
 * </ul>
 * @author DP Classes
 * @version 2026
 */
public class EBikeDemo
{
    /** The maximum X coordinate for the grid (number of rows). */
    public static final int MAXX = 20; 
    /** The maximum Y coordinate for the grid (number of columns). */
    public static final int MAXY = 20; 
    
    /** The maximum number of turns. */
    public static final int MAXSTEPS = 50;
    
    /** The company managing the EBs and Bike stations. */
    private EBCompany company;
    
    /** The simulation's bikes; they are the electric bikes of the company. 
     * @see ElectricBike 
     */
    private List<ElectricBike> bikes; 
    
    /** The bike stations available in the city. 
     * @see BikeStation 
     */
    private List<BikeStation> stations;
    
    /** Constant for selecting the demo scenario, using the {@link DemoType} enumeration. */
    private static final DemoType DEMO = DemoType.SIMPLE;
        
    /**
     * Constructor for objects of class EBikeDemo.
     * Initializes the company, bikes, and stations lists, and sets the number of 
     * bikes based on the {@code DEMO} constant before calling {@code reset()}.
     */
    public EBikeDemo()
    {
        company = new EBCompany("Compañía EBBike Cáceres");
        bikes = new ArrayList<>();
        stations = new ArrayList<>();
        
        reset();
    }

    /**
     * Run the demo for a fixed number of steps (50 steps).
     * In each step, all bikes perform their action.
     */
    public void run()
    {         
        for(int step = 0; step < MAXSTEPS; step++) {
            step(step);
        }
        // Mostramos la información final al terminar los pasos
        showFinalInfo();
    }

    /**
     * Run the demo for one step by requesting all bikes to act.
     * @param step The current step number of the simulation.
     */
    public void step(int step)
    {
        // Para cada bicicleta, ejecutamos su acción en este paso
        for (ElectricBike bike : bikes) {
            bike.act();
        }
    }

    /**
     * Reset the demo to a starting point.
     * Clears existing bikes and stations, resets the company, and re-creates
     * the initial setup.
     */
    public void reset()
    {
        bikes.clear();
        stations.clear();
        
        // Reseteamos la información de la compañía si procede
        company.reset(); 
        
        createElectricBikes();
        createStations(); 
        createDocks();
        configureRoutes();
        showInitialInfo();
    }
    
    /**
     * Creates the {@link ElectricBike}s based on {@code numBikesCreated}, assigns them
     * starting and target {@link Location}s, and adds them to the company.
     * The bikes list is sorted using the corresponding comparator.
     */
    private void createElectricBikes() {
        Location [] locations = {new Location(10,13), new Location(8,4), new Location(8,4), new Location(15,10), 
                                new Location(1,1), new Location(2,2), new Location(11,13), new Location(14,16)};
        Location [] targetLocations = {new Location(1,1), new Location(19,19), new Location(12,17), new Location(4,4), 
                                        new Location(1,10), new Location(5,5), new Location(8,7), new Location(19,19)};
                                        
        for (int i = 0; i < DEMO.getNumBikesToCreate(); i++){
            ElectricBike eb = new ElectricBike(company, locations[i], targetLocations[i], ("EBCC" + i), (i + 1) * 15);
            company.addElectricBike(eb); 
            bikes.add(eb);
        }
        // Ordenamos la lista de bicicletas por ID
        Collections.sort(bikes, new ComparatorEBIdBike());
    }
    
    /**
     * Creates predefined {@link BikeStation}s and adds them to the company.
     */
    private void createStations() {  
        Location [] locations = {new Location(10,5), new Location(10,11), new Location(14,16), new Location(8,4)};
                            
        for (int i = 0; i < DEMO.getNumStationsToCreate(); i++){
            BikeStation b = new BikeStation("CC0" + i, locations[i]);
            company.addBikeStation(b);
            stations.add(b);
        }
    }

    /**
     * Creates a fixed number of {@link Dock} units for each {@link BikeStation}
     * and orders the Docks within each station.
     */
    private void createDocks() {  
        for (BikeStation station : stations){
            for (int i = 0; i < DEMO.getNumDocksToCreate(); i++){
                Dock d = new Dock(station.getId() + "_00" + i, ((i + 1) * 20), ((i + 1) * 0.20f));
                station.addDock(d);
            }
        }    
    }
    
     /**
     * Instructs each {@link ElectricBike} to calculate its initial route.
     */
     private void configureRoutes() {
         for (ElectricBike bike : bikes) {
             bike.calculateRoute(); 
         }
     }

    /**
     * Displays the initial information for the simulation.
     */
    public void showInitialInfo() {
        System.out.println("(-------------------)");
        System.out.println("( Electric Bikes    )");
        System.out.println("(-------------------)");

        for(ElectricBike bike : bikes){
            System.out.println(bike);
        }
        
        System.out.println("(-------------------)");
        System.out.println("( Bike Stations     )");
        System.out.println("(-------------------)");
       
        for(BikeStation station : stations){
            System.out.println(station);
        }
        
        System.out.println("(------------------)");
        System.out.println("( Simulation start )");
        System.out.println("(------------------)");
    }

    /**
     * Displays the final information after the simulation has run.
     */
    private void showFinalInfo() {
        System.out.println("(-------------------)");
        System.out.println("( Final information )");        
        System.out.println("(-------------------)");
        
        System.out.println("(-------------------)");
        System.out.println("( Electric Bikes    )");
        System.out.println("(-------------------)");
        
        // Ordenamos las bicis usando el comparador por ID disponible
        Collections.sort(bikes, new ComparatorEBIdBike());
        for (ElectricBike bike : bikes) {
            System.out.println(bike);
        }
        
        System.out.println("(-------------------)");
        System.out.println("( Bike Stations     )");
        System.out.println("(-------------------)");
        
        // Ordenamos las estaciones usando el comparador por ID disponible
        Collections.sort(stations, new ComparatorBikeStationId());
        for (BikeStation station : stations) {
            System.out.println(station);
        }
    }
    
    /**
     * The main entry point for running the EBikeDemo simulation.
     */
    public static void main() {
        EBikeDemo demo = new EBikeDemo();
        demo.run();
    }
}