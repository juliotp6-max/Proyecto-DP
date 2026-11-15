import java.util.*;

/**
 * Modela el funcionamiento de una compañía de bicicletas eléctricas (EB).
 * Gestiona una flota de {@link ElectricBike} y una red de {@link BikeStation}.
 * @author David J. Barnes y Michael Kölling
 * @author DP classes
 * @version 2026.10.13
 */
public class EBCompany  
{
    private String name;
    private List<ElectricBike> subscribedBikes;
    private List<BikeStation> stations;
    private int currentTurn;

    /**
     * Constructor para objetos de la clase EBCompany.
     * @param name El nombre de la compañía.
     */
    public EBCompany(String name)
    {
        this.name = name;
        this.subscribedBikes = new ArrayList<>();
        this.stations = new ArrayList<>();
        this.currentTurn = 0;
    }

    /**
     * Añade una {@link ElectricBike} a la flota de la compañía.
     * @param bike La bicicleta eléctrica a añadir.
     */
    public void addElectricBike(ElectricBike bike)
    {        
        if (bike != null && !subscribedBikes.contains(bike)) {
            subscribedBikes.add(bike);
        }
    }

    /**
     * Añade una {@link BikeStation} a la red de la compañía.
     * @param station La estación de bicicletas a añadir.
     */
    public void addBikeStation(BikeStation station)
    {        
        if (station != null && !stations.contains(station)) {
            stations.add(station);
        }
    }
    
    /**
     * Recupera una {@link BikeStation} por su ID único.
     * @param id El ID de la estación a encontrar.
     * @return La {@link BikeStation} con el ID coincidente, o {@code null} si no se encuentra.
     */
    public BikeStation getBikeStation(String id)
    {
        for (BikeStation station : stations) {
            if (station.getId().equals(id)) {
                return station;
            }
        }
        return null;
    }

    /**
     * @return El turno actual de la simulación.
     */
    public int getCurrentTurn(){
        return currentTurn;
    }
    
    /**
     * Simula que avanza un turno en la compañía y en todas sus bicicletas.
     */
    public void step(){
        currentTurn++;
        for(ElectricBike bike : subscribedBikes){
            bike.act();
        }
    }
    
    /**
     * Recupera una {@link BikeStation} por su {@link Location} utilizando un Iterator.
     * @param location La {@link Location} de la estación a encontrar.
     * @return La {@link BikeStation} en la ubicación coincidente, o {@code null} si no se encuentra.
     */
    public BikeStation getBikeStation(Location location)
    {
        Iterator<BikeStation> it = stations.iterator();
        while (it.hasNext()) {
            BikeStation station = it.next();
            if (station.getLocation().equals(location)) {
                return station;
            }
        }
        return null;
    }

    /**
     * Recupera una {@link BikeStation} en una ubicación dada (alias utilizado por ElectricBike).
     * @param location La ubicación a comprobar.
     * @return La BikeStation en esa ubicación, o null si no hay ninguna.
     */
    public BikeStation getBikeStationAt(Location location)
    {
        return getBikeStation(location);
    }

    /**
     * Devuelve la lista completa de estaciones de bicicletas gestionadas por la compañía.
     * @return Lista de estaciones de bicicletas.
     */
    public List<BikeStation> getBikeStations()
    {
        return stations;
    }
    
    /**
     * Ordena las bicicletas suscritas de forma creciente por su ID.
     */
    public void sortBikesById() {
        subscribedBikes.sort((b1, b2) -> b1.getIdBike().compareTo(b2.getIdBike()));
    }
    
    /**
     * @return El número total de {@link BikeStation} gestionadas.
     */
    public int getNumberOfStations(){
        return stations.size();
    }
    
    /**
     * Limpia todas las bicicletas y estaciones gestionadas, restableciendo la compañía a un estado vacío.
     */
    public void reset(){
        subscribedBikes.clear();
        stations.clear();
    }
}