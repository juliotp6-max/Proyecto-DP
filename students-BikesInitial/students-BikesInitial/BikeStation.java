import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Modela una estación de recarga de vehículos eléctricos.
 * Una estación de carga contiene múltiples unidades de dársenas ({@link Dock}).
 * @author DP classes 
 * @version 2026.10.13
 */
public class BikeStation
{
    private String idStation;
    private Location location;
    private List<Dock> docks;

    /**
     * Constructor para objetos de la clase BikeStation (por defecto).
     */
    public BikeStation()
    {
        this.idStation = "CC01";
        this.location = new Location(0, 0);
        this.docks = new ArrayList<>();
    }

    /**
     * Constructor para objetos de la clase BikeStation.
     * @param id El identificador único de la estación.
     * @param location La ubicación de la estación.
     */
    public BikeStation(String id, Location location)
    {
        this.idStation = id;
        this.location = location;
        this.docks = new ArrayList<>();
    }
    
    /**
     * Método con Iterator que devuelve un dock que esté vacío o nulo si están todos llenos.
     * @return Un {@link Dock} libre, o null si no se encuentra ninguno.
     */
    public Dock getFreeDock()
    {
        Iterator<Dock> it = docks.iterator();
        while(it.hasNext()){
            Dock dock = it.next();
            if(dock.isFree()){
                return dock;
            }
        }
        return null;
    }

    /**
     * @return El identificador único de la estación de carga.
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
     * Establece la ubicación actual de la estación de carga.
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
     * Devuelve una representación en cadena de la estación de bicicletas.
     * @return Una cadena que contiene los detalles de la estación.
     */
    @Override
    public String toString() {
        return "BikeStation [id=" + idStation + ", location=" + location + ", docks=" + docks.size() + "]";
    }
}