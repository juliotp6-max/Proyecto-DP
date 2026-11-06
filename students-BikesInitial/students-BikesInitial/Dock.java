import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

/**
 * Model a dock unit within a {@link BikeStation}.
 * It tracks its charging capabilities, fee, and the electric bikes it has recharged.
 * @author David J. Barnes and Michael Kölling
 * @author DP classes 
 * @version 2026.10.13
 */
public class Dock
{
    // Campos requeridos por la especificación
    private String idDock;
    private int chargingSpeed;
    private float chargingFee;
    private List<ElectricBike> bikesRecharged;
    private float amountCollected;
    private boolean free;
    
    
    /**
     *Compara los Dock por su velocidad de carga en orden decreciente
     *en caso de empate por su tarifa, en orden creciente y si hay empate por el id
     *en orden creciente
        */
    public static final Comparator<Dock> COMPARADOR_DOCK = Comparator
    .comparing(Dock::getChargingSpeed)
    .reversed()
    .thenComparing(Dock::getChargingFee)
    .thenComparing(Dock::getId);
    
    /**
     * Constructor for objects of class Dock.
     * @param id The unique identifier of the dock (e.g. CC01_02).
     * @param speed The charging speed of the dock in wh.
     * @param fee The charging fee in €/wh.
     */
    public Dock(String id, int speed, float fee)
    {
        this.idDock = id;
        this.chargingSpeed = speed;
        this.chargingFee = fee;
        this.bikesRecharged = new ArrayList<>();
        this.amountCollected = 0.0f;
        this.free = true; // Por defecto el dock comienza libre
    }

    /**
     * Returns the unique identifier of the dock.
     * @return The dock's ID.
     */
    public String getId() {
        return idDock;
    }

    /**
     * Returns the charging speed of the dock.
     * @return The charging speed in wh.
     */
    public int getChargingSpeed() {
        return chargingSpeed;
    }

    /**
     * Returns the charging fee of the dock.
     * @return The charging fee in €/wh.
     */
    public float getChargingFee() {
        return chargingFee;
    }

    /**
     * Indicates if the dock is free or occupied.
     * @return true if free, false if occupied.
     */
    public boolean isFree() {
        return free;
    }

    /**
     * Sets the occupancy status of the dock.
     * @param free boolean indicating free status.
     */
    public void setFree(boolean free) {
        this.free = free;
    }

    /**
     * Returns the total amount collected by this dock.
     * @return Amount collected in €.
     */
    public float getAmountCollected() {
        return amountCollected;
    }

    /**
     * Returns a string representation of the dock, including its ID, speed, fee, and the number of EVs recharged.
     * @return A string representation of the dock.
     */
    public String toString()
    {
        return "Dock [id=" + idDock + ", speed=" + chargingSpeed + ", fee=" + chargingFee + 
               ", rechargedBikes=" + getNumerEVRecharged() + ", collected=" + amountCollected + "€]";
    }   

    /**
     * @return The total number of {@link ElectricBike}s that have been recharged by this dock.
     */
    public int getNumerEVRecharged(){
        return bikesRecharged.size();
    }
    
    /**
     * Adds a new bicycle object to the collection of recharged bikes if it is not already present.
     * @param bike The ElectricBike to add.
     */
    public void addBikeRecharged(ElectricBike bike) {
        if (bike != null && !bikesRecharged.contains(bike)) {
            bikesRecharged.add(bike);
        }
    }

    /**
     * Updates the amount collected by the dock by adding the new revenue.
     * @param amount The amount to add in €.
     */
    public void updateAmountCollected(float amount) {
        if (amount > 0) {
            this.amountCollected += amount;
        }
    }

    /**
     * Simulates the charging process for an {@link ElectricBike}.
     * Increases the amount collected and registers the bike as recharged.
     * @param bike The electric bike to recharge.
     * @param whCharged Watt-hours charged during the process.
     * @return The cost of the charge in €.
     */
    public float recharge(ElectricBike bike, int whCharged)
    {
        float cost = whCharged * chargingFee;
        updateAmountCollected(cost);
        addBikeRecharged(bike);
        return cost;
    }

    /**
     * Simulates the charging process using the standard charging speed capacity of the dock.
     * @param bike The electric bike to recharge.
     * @return The cost of the charge in €.
     */
    public float recharge(ElectricBike bike) {
        return recharge(bike, chargingSpeed);
    }
    

}