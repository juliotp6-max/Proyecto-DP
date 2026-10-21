import java.util.*;

/**
 * Models the operation of an Electric Bike (EB) Company. 
 * It manages a fleet of {@link ElectricBike}s and a network of {@link BikeStation}s.
 * * @author David J. Barnes and Michael Kölling
 * @author DP classes
 * @version 2026.10.13
 */
public class EBCompany  
{
    //TODO: Complete this code

    /**
     * Constructor for objects of class EVCompany.
     * @param name The name of the company.
     */
    public EBCompany(String name)
    {
        //TODO: Complete this code
    }

    /**
     * Adds an {@link ElectricBike} to the company's fleet.
     * @param bike The electric bike to add.
     */
    public void addElectricBike(ElectricBike bike)
    {       
        //TODO: Complete this code
    }

    
    /**
     * Adds a {@link BikeStation} to the company's network.
     * @param station The bike station to add.
     */
    public void addBikeStation(BikeStation station)
    {       
        //TODO: Complete this code
    }
    
    
    /**
     * Retrieves a {@link BikeStation} by its unique ID.
     * @param id The ID of the station to find.
     * @return The {@link BikeStation} with the matching ID, or {@code null} if not found.
     */
    public BikeStation getBikeStation(String id)
    {
        //TODO: Complete this code
        return null;
    }

    /**
     * Retrieves a {@link BikeStation} by its {@link Location}.
     * @param location The {@link Location} of the station to find.
     * @return The {@link BikeStation} at the matching location, or {@code null} if not found.
     */
    public BikeStation getBikeStation(Location location)
    {
        //TODO: Complete this code
        return null;
    }
    
    
    /**
     * @return The total number of managed {@link BikeStation}s.
     */
    public int getNumberOfStations(){
        //TODO: Complete this code
        return 0;
    }
    
    /**
     * Clears all managed bikes and stations, resetting the company to an empty state.
     */
    public void reset(){
        //TODO: Complete this code
    }
    
}