import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Comparator;

/**
 * Modela los elementos comunes de una bicicleta eléctrica (EB) que opera
 * dentro de la simulación, desplazándose hacia un destino y recargándose potencialmente.
 * @author David J. Barnes y Michael Kölling
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
    private int turn; // Variable que se utiliza para comprobar el turno de llegada entre dos EBike que van a un mismo destino.

    public static final Comparator<ElectricBike> COMPARADOR_EBIKE = Comparator.comparing(ElectricBike::getIdBike);
    
    /**
     * Constructor de la clase ElectricBike.
     * @param company La compañía operadora de la bicicleta eléctrica. No debe ser nula.
     * @param location La {@link Location} inicial de la bicicleta eléctrica. No debe ser nula.
     * @param targetLocation La {@link Location} de destino final de la bicicleta eléctrica. No debe ser nula.
     * @param id El identificador de la bicicleta.
     * @param batteryCapacity La capacidad máxima de la batería.
     * @throws NullPointerException Si company, location o targetLocation son nulos.
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
     * @return El identificador de la bicicleta eléctrica.
     */
    public String getIdBike()
    {
        return id;    
    }

    /**
     * Establece la ubicación actual.
     * @param location Dónde se encuentra. No debe ser nula.
     * @throws NullPointerException Si location es nula.
     */
    public void setLocation(Location location)
    {
        if (location == null) {
            throw new NullPointerException("Location must not be null.");
        }
        this.location = location;
    }

    /**
     * Obtiene la ubicación de destino final.
     * @return Hacia dónde se dirige en última instancia esta bicicleta.
     */
    public Location getTargetLocation()
    {
        return targetLocation;  
    }

    /**
     * Obtiene la ubicación de recarga temporal.
     * @return La {@link Location} de la siguiente {@link BikeStation} a visitar, o null si no hay recarga planeada.
     */
    public Location getRechargingLocation()
    {
        return rechargingLocation;
    }

    /**
     * Establece la ubicación de destino final requerida.
     * @param location Dónde ir. No debe ser nula.
     * @throws NullPointerException Si location es nula.
     */
    public void setTargetLocation(Location location)
    {
        if (location == null) {
            throw new NullPointerException("Location must not be null.");
        }
        this.targetLocation = location;
    }

    /**
     * @return La ubicación actual de la bicicleta eléctrica.
     */
    public Location getLocation()
    {
        return location;
    }
    
    /**
     * Calcula la ruta óptima para la bicicleta. 
     * Si no hay suficiente batería para alcanzar el destino, intenta encontrar una 
     * {@link BikeStation} intermedia y la establece como {@code rechargingLocation}.
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
     * Obtiene una representación en cadena de la ruta planificada, incluyendo la parada de recarga si existe alguna.
     * @return Una cadena que muestra la ruta: {@code currentLocation -> [rechargingLocation ->] targetLocation}.
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
     * Comprueba si el nivel actual de batería es suficiente para cubrir una distancia dada hacia una ubicación.
     * @param dest La ubicación de destino a comprobar.
     * @return true si la batería es suficiente, false en caso contrario.
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
     * Muestra la estación de bicicletas ({@link BikeStation}) más cercana a la
     * que dirigirse para recargar cuando no dispone de suficiente batería para 
     * llegar al destino fijado.
     * Recorre todas las estaciones de la empresa a través de un {@link Iterator}
     * evaluando las distancias y la batería disponible.
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
                if(totalDist < minTotalDistance){
                    minTotalDistance = totalDist;
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
     * Obtiene la distancia estilo Manhattan hacia la ubicación de destino final desde la ubicación actual.
     * @return La distancia al destino.
     */
    public int distanceToTheTargetLocation()
    {
        if (location != null && targetLocation != null) {
            return location.distance(targetLocation);
        }
        return 0;
    }

    /**
     * Simula el proceso de recarga cuando la bicicleta llega a una {@code rechargingLocation}.
     * La batería se carga a su capacidad máxima, se calcula el coste y se recalcula la ruta.
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
     * Incrementa el contador de recargas realizadas por esta bicicleta.
     */
    public void incrementCharges()
    {
        numberOfCharges++;
    }

    /**
     * Añade una cantidad de coste al coste total de las recargas.
     * @param cost El coste a añadir.
     */
    public void incrementChargesCost(float cost)
    {
        if (cost > 0) {
            chargesCost += cost;
        }
    }    

    /**
     * Ejecuta un único paso de las acciones de la bicicleta.
     * Se desplaza un paso hacia el destino (de recarga o final) o permanece inactiva.
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
            // Actualizamos la posición con setLocation usando nextLocation
            setLocation(location.nextLocation(destination));
            reduceBatteryLevel();

            // Si al movernos hemos alcanzado la estación de recarga
            if (location.equals(rechargingLocation)) {
                recharge();
            }
        }
    }

    /**
     * Reduce el nivel de batería por el coste de un paso de movimiento (5 wh).
     * Asegura que el nivel de batería no baje de cero.
     */
    public void reduceBatteryLevel(){
        batteryLevel -= 5;
        if (batteryLevel < 0) {
            batteryLevel = 0;
        }
    }

    /**
     * Devuelve una representación detallada en cadena de la bicicleta eléctrica.
     * @return Una cadena que contiene el nombre/id de la bicicleta, información de batería, conteo de cargas, costes, inactividad y ruta.
     */
    @Override
    public String toString() {
        String cad = "(ElectricBike: " + getIdBike() + ", " + batteryLevel + "wh, ";
        cad = cad + batteryCapacity + "wh, " + numberOfCharges + ", " + chargesCost + ", " + idleCount + ")";
        return cad;
    }
}