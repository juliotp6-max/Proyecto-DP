import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Comparator;
/**
 * Model the common elements of an Electric bike (EB) that operates 
 * within the simulation, moving towards a target and potentially recharging.
 * @author David J. Barnes and Michael Kölling
 * @author DP classes 
 * @version 2026.10.13
 */
public class ElectricBike 
{
    // Atributos de la clase
    private EBCompany company;
    private Location location;
    private Location targetLocation;
    private Location rechargingLocation;
    private String id;
    private int batteryCapacity;
    private int batteryLevel;
    private int numberOfCharges;
    private float chargesCost;
    private int idleCount;
    private List<Dock> docks;
    private double totalWhCharged; // Acumulador de wh cargados en total
    private int turn; //Variable que se utiliza para comprobar el turno de llegada entre dos EBike que van a un mismo destino.

    public static final Comparator<ElectricBike> COMPARADOR_EBIKE = Comparator.comparing(ElectricBike::getIdBike);
    
    /**
     * Constructor of class ElectricBike.
     * @param company The EB's operating company. Must not be null.
     * @param location The EB's starting {@link Location}. Must not be null.
     * @param targetLocation The EB's final destination {@link Location}. Must not be null.
     * @param id The identificator of the bike.
     * @param batteryCapacity The maximum capacity of the battery.
     * @throws NullPointerException If company, location, or targetLocation is null.
     */
    public ElectricBike(EBCompany company, Location location, Location targetLocation, String id, int batteryCapacity)
    {
        if (company == null || location == null || targetLocation == null) {
            throw new NullPointerException("Company, location, and targetLocation must not be null.");
        }
        this.company = company;
        this.location = location;
        this.targetLocation = targetLocation;
        this.id = id;
        this.batteryCapacity = batteryCapacity;
        this.batteryLevel = batteryCapacity;
        this.rechargingLocation = null;
        this.numberOfCharges = 0;
        this.chargesCost = 0.0f;
        this.idleCount = 0;
        this.docks = new ArrayList<>();
        this.totalWhCharged = 0;
        this.turn = Integer.MAX_VALUE;
    }

    /**
     * @return The id of the electric bike.
     */
    public String getIdBike()
    {
        return id;    
    }

    /**
     * Set the current location.
     * @param location Where it is. Must not be null.
     * @throws NullPointerException If location is null.
     */
    public void setLocation(Location location)
    {
        if (location == null) {
            throw new NullPointerException("Location must not be null.");
        }
        this.location = location;
    }

    /**
     * Get the final target location.
     * @return Where this bike is ultimately headed.
     */
    public Location getTargetLocation()
    {
        return targetLocation;  
    }

    /**
     * Get the temporary recharging location.
     * @return The {@link Location} of the next {@link BikeStation} to visit, or null if no recharge is planned.
     */
    public Location getRechargingLocation()
    {
        return rechargingLocation;
    }

    /**
     * Set the required final target location.
     * @param location Where to go. Must not be null.
     * @throws NullPointerException If location is null.
     */
    public void setTargetLocation(Location location)
    {
        if (location == null) {
            throw new NullPointerException("Location must not be null.");
        }
        this.targetLocation = location;
    }

    /**
     * @return The current location of the electric bike.
     */
    public Location getLocation()
    {
        return location;
    }
    
    /**
     * Calculates the optimal route for the bike. 
     * If there isn't enough battery to reach the target, it attempts to find an intermediate 
     * {@link BikeStation} and sets it as the {@code rechargingLocation}.
     */
    public void calculateRoute()
    {
        if (enoughBattery(targetLocation)) {
            rechargingLocation = null;
        } else {
            calculateRechargingPosition();
        }
    }

    /**
     * Gets a string representation of the planned route, including the recharging stop if one exists.
     * @return A string showing the route: {@code currentLocation -> [rechargingLocation ->] targetLocation}.
     */
    public String getStringRoute()
    {
       if (rechargingLocation != null) {
           return location + " -> " + rechargingLocation + " -> " + targetLocation;
       } else {
           return location + " -> " + targetLocation;
       }
    }

    public int getTurn(){
        return turn;
    }
    /**
     * Checks if the current battery level is sufficient to cover a given distance to a location.
     * @param dest The destination location to check.
     * @return true if battery is enough, false otherwise.
     */
    public boolean enoughBattery(Location dest)
    {
        if (location == null || dest == null) {
            return false;
        }
        int distance = location.distance(dest);
        int requiredEnergy = distance * 5; // 5 wh por cada paso
        return batteryLevel >= requiredEnergy;
    }

    /**
     *Muestra la estacion de biciclitas ({@link BikeStation}) mas cercana a la
     *que dirigirse para recargar cuando no dispone de suficiente bateria para 
     *llegar al destino fijado.
     *Recorre todas las estaciones de la empresa a traves de un {@Link Iterator}
     *las distancias y la bateria que se dispone
     *
     */
    public void calculateRechargingPosition()
    {
        BikeStation bestStation = null;
        int minTotalDistance = Integer.MAX_VALUE;

        Iterator<BikeStation> it= company.getBikeStations().iterator();

        while(it.hasNext()){
            BikeStation station= it.next();
            
            if(enoughBattery(station.getLocation())) {
                int distToStation = location.distance(station.getLocation());
                int distStationToTarget = station.getLocation().distance(targetLocation);
                int totalDist = distToStation + distStationToTarget;
                if(totalDist<minTotalDistance){
                    minTotalDistance=totalDist;
                    bestStation = station;
                }
            }
        }
        if(bestStation != null){
            rechargingLocation = bestStation.getLocation();
        }else{
            rechargingLocation = null;
        }
    }

    /**
     * Get the Manhattan-like distance to the final target location from the current location.
     * @return The distance to the target location.
     */
    public int distanceToTheTargetLocation()
    {
        if (location != null && targetLocation != null) {
            return location.distance(targetLocation);
        }
        return 0;
    }

    /**
     * Simulates the recharging process when the bike arrives at a {@code rechargingLocation}.
     * The battery is charged to full capacity, the cost is calculated, and the route is recalculated.
     */
    public void recharge()
    {
        // Buscamos la estación asociada a la ubicación de recarga actual
        BikeStation station = company.getBikeStationAt(rechargingLocation);
        if (station != null) {
            Dock freeDock = station.getFreeDock();
            if (freeDock != null) {
                int whNeeded = batteryCapacity - batteryLevel;
                if (whNeeded > 0) {
                    float cost = freeDock.recharge(this, whNeeded);
                    incrementCharges();
                    incrementChargesCost(cost);
                    totalWhCharged += whNeeded;
                    batteryLevel = batteryCapacity;
                }
                if (!docks.contains(freeDock)) {
                    docks.add(freeDock);
                }
            }
        }
        rechargingLocation = null;
        calculateRoute();
    }  

    /**
     * Increments the count of recharges performed by this bike.
     */
    public void incrementCharges()
    {
        numberOfCharges++;
    }

    /**
     * Adds a cost amount to the total charges cost.
     * @param cost The cost to add.
     */
    public void incrementChargesCost(float cost)
    {
        if (cost > 0) {
            chargesCost += cost;
        }
    }   

    /**
     * Carries out a single step of the bike's actions.
     * Moves one step towards the target (recharging or final) or stays idle.
     */
    public void act()
    {
        Location destination = (rechargingLocation != null) ? rechargingLocation : targetLocation;

        if (location.equals(destination)) {
            if (location.equals(targetLocation)) {
                
                if(turn == Integer.MAX_VALUE){
                    turn = company.getCurrentTurn();
                }
                idleCount++;
            } else if (location.equals(rechargingLocation)) {
                recharge();
            }
        } else {
            // CORREGIDO: Actualizamos la posición con setLocation usando nextLocation
            setLocation(location.nextLocation(destination));
            reduceBatteryLevel();

            // Si al movernos hemos alcanzado la estación de recarga
            if (location.equals(rechargingLocation)) {
                recharge();
            }
        }
    }

    /**
     * Reduces the battery level by the cost of one movement step (5 wh).
     * Ensures the battery level does not go below zero.
     */
    public void reduceBatteryLevel(){
        batteryLevel -= 5;
        if (batteryLevel < 0) {
            batteryLevel = 0;
        }
    }

    /**
     * Returns a detailed string representation of the electric bike.
     * @return A string containing the bike's name, plate, battery info, charge counts, costs, idle count, and route.
     */
    @Override
    public String toString() {
        String cad = "(ElectricBike: " + getIdBike() + ", " + batteryLevel + "wh, ";
        cad = cad + batteryCapacity + "wh, " + numberOfCharges + ", " + chargesCost + ", " + idleCount + ")";
        return cad;
    }
    
    
}