package com.cinema;

/**
 * Clase que representa una sala de cine.
 */
public class Cinema {

    private Seat[][] seats;

    /**
     * Construye una sala de cine. Se le pasa como dato un arreglo cuyo tamaño
     * es la cantidad de filas y los enteros que tiene son el número de butacas en cada fila.
     */
    public Cinema(int[] rows) {
        seats = new Seat[rows.length][];
        initSeats(rows);
    }

    /**
     * Inicializa las butacas de la sala de cine.
     *
     * @param rows arreglo que contiene la cantidad de butacas en cada fila
     */
    private void initSeats(int[] rows) {
        for (int i = 0; i < rows.length; i++) {
            seats[i] = new Seat[rows[i]];
        }
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                seats[i][j] = new Seat(i, j);
            }
        }
    }

    /**
     * Cuenta la cantidad de seats disponibles en el cine.
     */
    public int countAvailableSeats() {
        int availableSeatsCounter = 0;
        for (Seat[] row : seats){
            for (Seat s : row) {
                if (s.isAvailable()){
                    availableSeatsCounter ++;
                }
            }
        }
        return availableSeatsCounter;
    }

    /**
     * Busca la primera butaca libre dentro de una fila o null si no encuentra.
     */
    public Seat findFirstAvailableSeatInRow(int row) {
        Seat retStatement = null;
        if (row >= 0 && row <= seats.length) {
            for (int i = 0; i <= seats[row].length; i++) {
                if (i != seats[row].length) {
                    if (seats[row][i].isAvailable()) {
                        retStatement = seats[row][i];
                        break;
                    }
                } else {
                    retStatement = null;

                }
            }
        }
        else {
            return null;
        }
        return retStatement;
    }

    /**
     * Busca la primera butaca libre o null si no encuentra.
     */
    public Seat findFirstAvailableSeat() {
        Seat retStatement = null;
        for (int i = 0; i < seats.length; i++) {
            retStatement = findFirstAvailableSeatInRow(i);
            if (retStatement != null) {
                retStatement = findFirstAvailableSeatInRow(i);
                break;
            }
            
        } 
        return retStatement;
    }

    /**
     * Busca las N butacas libres consecutivas en una fila. Si no hay, retorna null.
     *
     * @param row    fila en la que buscará las butacas.
     * @param amount el número de butacas necesarias (N).
     * @return La primer butaca de la serie de N butacas, si no hay retorna null.
     */
    public Seat getAvailableSeatsInRow(int row, int amount) {
        Seat retStatement = null;
        int availableCounter = 0;
        for (int i = 0; i < seats[row].length; i++) {
            if (seats[row][i].isAvailable()) {
                availableCounter ++;
            }
            else {
                availableCounter = 0;
            }
            if (availableCounter == amount) {
                retStatement = seats[row][i-2];
                break;
            }
        }
        return retStatement;
    }

    /**
     * Busca en toda la sala N butacas libres consecutivas. Si las encuentra
     * retorna la primer butaca de la serie, si no retorna null.
     *
     * @param amount el número de butacas pedidas.
     */
    public Seat getAvailableSeats(int amount) {
        Seat retStatement = null;
        for (int i = 0; i < seats.length; i++) {
            retStatement = getAvailableSeatsInRow(i, amount);
            if (retStatement != null) {
                break;
            }
        }
        return retStatement;
    }

    /**
     * Marca como ocupadas la cantidad de butacas empezando por la que se le pasa.
     *
     * @param seat   butaca inicial de la serie.
     * @param amount la cantidad de butacas a reservar.
     */
    public void takeSeats(Seat seat, int amount) {
        int counter = 0;
        Seat s = seats[1][amount-1];
        int[] indexOfSeat = {0,0};
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (seats[i][j].equals(seat)) {
                    indexOfSeat[0] = i;
                    indexOfSeat[1] = j;
                }
            }
        }
        for (int i = indexOfSeat[1]; i < seats[indexOfSeat[0]].length; i++) {
            seats[indexOfSeat[0]][i].takeSeat();
            counter ++;
            if (counter == amount){
                break;
            }

        }


    }

    /**
     * Libera la cantidad de butacas consecutivas empezando por la que se le pasa.
     *
     * @param seat   butaca inicial de la serie.
     * @param amount la cantidad de butacas a liberar.
     */
    public void releaseSeats(Seat seat, int amount) {
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (seats[i][j].equals(seat)) {
                    for (int k = j; k <= j+amount ; k++) {
                        seats[i][k].releaseSeat();
                    }

                }
            }
        }
    }

    public static void main(String[] args) {
        int[] row= {5,5,5};
        Cinema myCinema = new Cinema(row);
        System.out.println(myCinema.seats);
    }
}