import java.util.*;

/**
 * Models the operation of an Electric Bike (EB) Company. 
 * It manages a fleet of {@link ElectricBike}s and a network of {@link BikeStation}s.
 * @author David J. Barnes and Michael Kölling
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
     * Constructor for objects of class EBCompany.
     * @param name The name of the company.
     */
    public EBCompany(String name)
    {
        this.name = name;
        this.subscribedBikes = new ArrayList<>();
        this.stations = new ArrayList<>();
        this.currentTurn = 0;
    }

    /**
     * Adds an {@link ElectricBike} to the company's fleet.
     * @param bike The electric bike to add.
     */
    public void addElectricBike(ElectricBike bike)
    {       
        if (bike != null && !subscribedBikes.contains(bike)) {
            subscribedBikes.add(bike);
        }
    }

    /**
     * Adds a {@link BikeStation} to the company's network.
     * @param station The bike station to add.
     */
    public void addBikeStation(BikeStation station)
    {       
        if (station != null && !stations.contains(station)) {
            stations.add(station);
        }
    }
    
    /**
     * Retrieves a {@link BikeStation} by its unique ID.
     * @param id The ID of the station to find.
     * @return The {@link BikeStation} with the matching ID, or {@code null} if not found.
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

    public int getCurrentTurn(){
        return currentTurn;
    }
    
    //Simula que avanza un turno.
    public void step(){
        currentTurn++;
        for(ElectricBike bike : subscribedBikes){
            bike.act();
        }
    }
    
    /**
     * Retrieves a {@link BikeStation} by its {@link Location} using an Iterator.
     * @param location The {@link Location} of the station to find.
     * @return The {@link BikeStation} at the matching location, or {@code null} if not found.
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
     * Retrieves a {@link BikeStation} at a given location (alias used by ElectricBike).
     * @param location The location to check.
     * @return The BikeStation at that location, or null if none.
     */
    public BikeStation getBikeStationAt(Location location)
    {
        return getBikeStation(location);
    }

    /**
     * Returns the full list of bike stations managed by the company.
     * @return List of bike stations.
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
     * @return The total number of managed {@link BikeStation}s.
     */
    public int getNumberOfStations(){
        return stations.size();
    }
    
    /**
     * Clears all managed bikes and stations, resetting the company to an empty state.
     */
    public void reset(){
        subscribedBikes.clear();
        stations.clear();
    }
    
}