import java.util.Comparator;

/**
 * Compara dos objetos {@link BikeStation} basándose en el número total de bicicletas 
 * recargadas en los docks de la estación en orden decreciente, y en caso de empate, 
 * por su ID en orden creciente.
 * 
 * @author DP classes
 * @version 2026.10.13
 * @param st1 La primera estación de bicicletas a comparar.
 * @param st2 La segunda estación de bicicletas a comparar.
 * @return Un entero negativo, cero o un entero positivo según el criterio de ordenación.
 */
public class ComparatorBikeStationBikes implements Comparator<BikeStation>
{
    public int compare(BikeStation st1, BikeStation st2) {
        // 1. Calculamos el total de bicicletas recargadas sumando las de todos sus docks
        int bikes1 = 0;
        for (Dock dock : st1.getDocks()) {
            bikes1 += dock.getNumerEVRecharged();
        }
        
        int bikes2 = 0;
        for (Dock dock : st2.getDocks()) {
            bikes2 += dock.getNumerEVRecharged();
        }
        
        // Orden decreciente por el número total de bicicletas recargadas
        if (bikes1 != bikes2) {
            return Integer.compare(bikes2, bikes1); // bikes2, bikes1 invierte el orden a decreciente
        }
        
        // 2. En caso de empate, orden creciente por ID de la estación
        return st1.getId().compareTo(st2.getId());
    }
}