package org.example;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {

    private final String[] state;
    private final static char aliveCell = 'X';
    private final static char deadCell = '.';

    private final static String lineBreak = "\n";

    public Board(String[] state) {this.state = state != null ? state : new String[0];}
    public boolean isEmpty() {
        for (String row : state){
            if(row.contains(String.valueOf(aliveCell))){
                return false;
            }
        }
        return true;
    }

    public Board next() {return new Board(calculate());}

    private String[] calculate() {
        return IntStream.range(0, rows())
                .mapToObj(i->calculateRow(i))
                .toArray(String[]::new);
    }

    private String calculateRow(int i) {
        return IntStream.range(0,cols())
                .mapToObj(j-> format(calculateCell(i,j)))
                .collect(Collectors.joining());
    }

    private String format(char state) { return String.valueOf(state);}

    private int cols() { return state[0].length();}

    private char calculateCell(int i, int j) { return shouldBeAlive(i,j) ? aliveCell : deadCell;}

    private boolean shouldBeAlive(int i, int j) {
        int aliveNeighbors = countAliveNeighbors(i,j);
        boolean currentCellAlive = isAlive(i,j);
        System.out.println("Cell at (" +i+", "+j+ "): Alive=" +currentCellAlive+", Alive Neighbors=" + aliveNeighbors);
        return currentCellAlive ?
                is(aliveNeighbors, 2,3) :
                is(aliveNeighbors, 3);
    }

    private boolean is(int value, int... options) {
         return IntStream.of(options).anyMatch(o-> o == value);
    }

    private int countAliveNeighbors(int i, int j) {
        return (int) neighborsOf(i,j).stream()
                .filter(CheckAlive::check)
                .count();
    }

    private List<CheckAlive> neighborsOf(int i, int j) {
        return List.of(
                () -> isAlive(i-1, j-1),
                () -> isAlive(i-1, j),
                () -> isAlive(i-1, j+1),
                () -> isAlive(i, j-1),
                () -> isAlive(i, j+1),
                () -> isAlive(i+1, j-1),
                () -> isAlive(i+1, j),
                () -> isAlive(i+1, j+1)
        );
    }

    public void createCell(int i, int j) {
        if(isInBounds(i,j)){
            StringBuilder newRow= new StringBuilder(state[i]);
            newRow.setCharAt(j,aliveCell);
            state[i] = newRow.toString();
        }
    }

    private interface CheckAlive {
        boolean check();
    }
    private int rows() { return state.length;}

    public boolean isAlive(int i, int j) {return isInBounds(i,j) && cellAt(i,j) == aliveCell;}

    private char cellAt(int i, int j) { return state[i].charAt(j);}

    private boolean isInBounds(int i, int j) {return i>=0 && i < rows() && j>= 0 && j<cols();}
}
