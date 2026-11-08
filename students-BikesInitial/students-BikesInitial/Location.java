import java.util.*;

/**
 * Modela una ubicación en una ciudad con cuadrícula bidimensional utilizando coordenadas (x, y).
 * @author David J. Barnes y Michael Kölling
 * @author DP classes 
 * @version 2026.10.13
 */
public class Location
{
    private int x;
    private int y;

    /**
     * Modela una ubicación en la ciudad.
     * @param x La coordenada x. Debe ser no negativa.
     * @param y La coordenada y. Debe ser no negativa.
     * @throws IllegalArgumentException Si alguna coordenada es negativa.
     */
    public Location(int x, int y)
    {
        if(x < 0) {
            throw new IllegalArgumentException(
                        "Negative x-coordinate: " + x);
        }
        if(y < 0) {
            throw new IllegalArgumentException(
                        "Negative y-coordinate: " + y);
        }        
        this.x = x;
        this.y = y;
    }
    
    /**
     * Genera la siguiente ubicación a visitar en línea recta (movimiento diagonal/recto al estilo Manhattan) 
     * desde esta ubicación hasta el destino.
     * @param destination La {@link Location} a la que queremos llegar.
     * @return La siguiente {@link Location} un paso más cerca del destino. Devuelve el destino
     * si esta ubicación ya es el destino.
     */
    public Location nextLocation(Location destination)
    {
        if (destination == null) {
            return this;
        }
        
        int nextX = x;
        int nextY = y;
        
        if(destination.getX() > x) {
            nextX++;
        } else if(destination.getX() < x) {
            nextX--;
        }
        
        if(destination.getY() > y) {
            nextY++;
        } else if(destination.getY() < y) {
            nextY--;
        }
        
        return new Location(nextX, nextY);
    }

    /**
     * Determina el número de movimientos requeridos para llegar desde aquí hasta el destino, 
     * utilizando la distancia de Chebyshev (máximo de las diferencias absolutas de las coordenadas).
     * @param destination La {@link Location} de destino requerida.
     * @return El número de pasos de movimiento.
     */
    public int distance(Location destination)
    {
        if (destination == null) {
            return 0;
        }
        int deltaX = Math.abs(destination.getX() - x);
        int deltaY = Math.abs(destination.getY() - y);
        return Math.max(deltaX, deltaY);
    }
    
    /**
     * Implementa la igualdad de contenido para las ubicaciones.
     * @param other El objeto con el que comparar.
     * @return {@code true} si el otro objeto es una {@link Location} y tiene las mismas coordenadas x e y, {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object other)
    {
        if(other instanceof Location) {
            Location otherLocation = (Location) other;
            return x == otherLocation.getX() && y == otherLocation.getY();
        }
        return false;
    }
    
    /**
     * @return Una representación en cadena de la ubicación en el formato "x-y".
     */
    @Override
    public String toString()
    {
        return x + "-" + y;
    }

    /**
     * Genera un código hash para la ubicación.
     * Utiliza los 16 bits superiores para el valor de y y los 16 bits inferiores para el valor de x
     * para asegurar un código hash único para la mayoría de los tamaños de cuadrícula.
     * @return Un código hash para la ubicación.
     */
    @Override
    public int hashCode()
    {
        return (y << 16) + x;
    }

    /**
     * @return La coordenada x.
     */
    public int getX()
    {
        return x;
    }

    /**
     * @return La coordenada y.
     */
    public int getY()
    {
        return y;
    }
}