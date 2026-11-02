import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Models an Electric Vehicle Charging Station.
 * A charging station contains multiple {@link Dock} units.
 * @author DP classes 
 * @version 2026.10.13
 */
public class BikeStation
{
    private String idStation;
    private Location location;
    private List<Dock> docks;

    /**
     * Constructor for objects of class BikeStation (default).
     */
    public BikeStation()
    {
        this.idStation = "CC01";
        this.location = new Location(0, 0);
        this.docks = new ArrayList<>();
    }

    /**
     * Constructor for objects of class BikeStation.
     * @param id The unique identifier of the station.
     * @param location The location of the station.
     */
    public BikeStation(String id, Location location)
    {
        this.idStation = id;
        this.location = location;
        this.docks = new ArrayList<>();
    }
    
    /**
     * Retrieves the first free {@link Dock} found at the station.
     * @return A free {@link Dock}, or null if none are found.
     */
    public Dock getFreeDock()
    {
        for (Dock dock : docks) {
            if (dock.isFree()) {
                return dock;
            }
        }
        return null;        
    }

    /**
     * @return The unique identifier of the charging station.
     */
    public String getId()
    {
        return idStation;
    }
    
    /**
     * Método que devuelve la localización donde está la estación de bicicletas.
     * @return La localización de la estación.
     */
    public Location getLocation()
    {
        return location;
    }

    /**
     * Método que devuelve la colección docks.
     * @return La lista de dársenas de la estación.
     */
    public List<Dock> getDocks()
    {
        return docks;
    }
    
    /**
     * Método que permite añadir un nuevo dock de tipo Dock a la colección docks,
     * manteniendo el orden establecido (decreciente por velocidad, creciente por tarifa, creciente por ID).
     * @param dock El nuevo Dock a añadir.
     */
    public void addDock(Dock dock)
    {
        if (dock != null && !docks.contains(dock)) {
            docks.add(dock);
            sortDocks();
        }
    }
    
    /**
     * Ordena la colección de docks según los criterios de la Entrega Inicial:
     * - Decreciente por velocidad de carga.
     * - Creciente por tarifa de carga.
     * - Creciente por identificador.
     */
    public void sortDocks()
    {
        Collections.sort(docks, new Comparator<Dock>() {
            @Override
            public int compare(Dock d1, Dock d2) {
                // 1. Decreciente por velocidad de carga
                int speedCmp = Integer.compare(d2.getChargingSpeed(), d1.getChargingSpeed());
                if (speedCmp != 0) {
                    return speedCmp;
                }
                // 2. Creciente por tarifa de carga
                int feeCmp = Float.compare(d1.getChargingFee(), d2.getChargingFee());
                if (feeCmp != 0) {
                    return feeCmp;
                }
                // 3. Creciente por identificador
                return d1.getId().compareTo(d2.getId());
            }
        });
    }
    
    /**
     * Set the current location of the charging station.
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
     * Returns a string representation of the bike station.
     * @return A string containing station details.
     */
    @Override
    public String toString() {
        return "BikeStation [id=" + idStation + ", location=" + location + ", docks=" + docks.size() + "]";
    }
}