import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

/**
 * Modela una unidad de dársena (dock) dentro de una {@link BikeStation}.
 * Realiza un seguimiento de sus capacidades de carga, tarifa y de las bicicletas eléctricas que ha recargado.
 * @author David J. Barnes y Michael Kölling
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
     * Compara los Dock por su velocidad de carga en orden decreciente,
     * en caso de empate por su tarifa en orden creciente y si hay empate por el id
     * en orden creciente.
     */
    public static final Comparator<Dock> COMPARADOR_DOCK = Comparator
    .comparing(Dock::getChargingSpeed)
    .reversed()
    .thenComparing(Dock::getChargingFee)
    .thenComparing(Dock::getId);
    
    /**
     * Constructor para objetos de la clase Dock.
     * @param id El identificador único de la dársena (ej. CC01_02).
     * @param speed La velocidad de carga de la dársena en wh.
     * @param fee La tarifa de carga en €/wh.
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
     * Devuelve el identificador único de la dársena.
     * @return El ID de la dársena.
     */
    public String getId() {
        return idDock;
    }

    /**
     * Devuelve la velocidad de carga de la dársena.
     * @return La velocidad de carga en wh.
     */
    public int getChargingSpeed() {
        return chargingSpeed;
    }

    /**
     * Devuelve la tarifa de carga de la dársena.
     * @return La tarifa de carga en €/wh.
     */
    public float getChargingFee() {
        return chargingFee;
    }

    /**
     * Indica si la dársena está libre u ocupada.
     * @return true si está libre, false si está ocupada.
     */
    public boolean isFree() {
        return free;
    }

    /**
     * Establece el estado de ocupación de la dársena.
     * @param free booleano que indica el estado de disponibilidad.
     */
    public void setFree(boolean free) {
        this.free = free;
    }

    /**
     * Devuelve la cantidad total recaudada por esta dársena.
     * @return Cantidad recaudada en €.
     */
    public float getAmountCollected() {
        return amountCollected;
    }

    /**
     * Devuelve una representación en cadena de la dársena, incluyendo su ID, velocidad, tarifa y el número de EVs recargados.
     * @return Una representación en cadena de la dársena.
     */
    public String toString()
    {
        return "Dock [id=" + idDock + ", speed=" + chargingSpeed + ", fee=" + chargingFee +  
               ", rechargedBikes=" + getNumerEVRecharged() + ", collected=" + amountCollected + "€]";
    }   

    /**
     * @return El número total de {@link ElectricBike}s que han sido recargadas por esta dársena.
     */
    public int getNumerEVRecharged(){
        return bikesRecharged.size();
    }
    
    /**
     * Añade un nuevo objeto bicicleta a la colección de bicicletas recargadas si no está presente ya.
     * @param bike La ElectricBike a añadir.
     */
    public void addBikeRecharged(ElectricBike bike) {
        if (bike != null && !bikesRecharged.contains(bike)) {
            bikesRecharged.add(bike);
        }
    }

    /**
     * Actualiza la cantidad recaudada por la dársena añadiendo los nuevos ingresos.
     * @param amount La cantidad a añadir en €.
     */
    public void updateAmountCollected(float amount) {
        if (amount > 0) {
            this.amountCollected += amount;
        }
    }

    /**
     * Simula el proceso de carga para una {@link ElectricBike}.
     * Incrementa la cantidad recaudada y registra la bicicleta como recargada.
     * @param bike La bicicleta eléctrica a recargar.
     * @param whCharged Vatios-hora cargados durante el proceso.
     * @return El coste de la carga en €.
     */
    public float recharge(ElectricBike bike, int whCharged)
    {
        float cost = whCharged * chargingFee;
        updateAmountCollected(cost);
        addBikeRecharged(bike);
        return cost;
    }

    /**
     * Simula el proceso de carga utilizando la velocidad de carga estándar de la dársena.
     * @param bike La bicicleta eléctrica a recargar.
     * @return El coste de la carga en €.
     */
    public float recharge(ElectricBike bike) {
        return recharge(bike, chargingSpeed);
    }
}