package com.softwaretesting.robotsimulator.project;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.Arrays;

@Data
public class Matrix {

    @Setter(AccessLevel.NONE)
    private Integer xPosition;
    @Setter(AccessLevel.NONE)
    private Integer yPosition;

    private DIRECTION direction;
    private PEN_POSITION penPosition;
    private Integer size;
    private int[][] matrix = null;

    public void show() {
        for (int i = size - 1 ; i >= 0 ; i--) {
            int[] subArray = matrix[i];
            System.out.format("%3d| " , i);
            for (int j = 0 ; j < size ; j++) {
                if (subArray[j] == 0) {
                    System.out.print("   ");
                } else if (subArray[j] == 1) {
                    System.out.print("*  ");
                }
            }
            System.out.println();
        }
        System.out.print("  ");
        for (int i = 0 ; i < size ; i++) {
            System.out.print("---");
        }
        System.out.println();
        System.out.print("   ");
        for (int i = 0 ; i < size ; i++) {
            System.out.format("%3d" , i);
        }
        System.out.println();
    }

    public void rotate(ROTATION rotation) {
        switch (rotation) {
            case RIGHT -> moveRight();
            case LEFT -> moveLeft();
        }
    }

	private void moveRight() {
        if (DIRECTION.NORTH.equals(this.direction)) {
            this.direction = DIRECTION.EAST;
        } else if (DIRECTION.EAST.equals(this.direction)) {
            this.direction = DIRECTION.SOUTH;
        } else if (DIRECTION.SOUTH.equals(this.direction)) {
            this.direction = DIRECTION.WEST;
        } else if (DIRECTION.WEST.equals(this.direction)) {
            this.direction = DIRECTION.NORTH;
        }
    }
	
    private void moveLeft() {
        if (DIRECTION.NORTH.equals(this.direction)) {
            this.direction = DIRECTION.WEST;
        } else if (DIRECTION.WEST.equals(this.direction)) {
            this.direction = DIRECTION.SOUTH;
        } else if (DIRECTION.SOUTH.equals(this.direction)) {
            this.direction = DIRECTION.EAST;
        } else if (DIRECTION.EAST.equals(this.direction)) {
            this.direction = DIRECTION.NORTH;
        }
    }

    public void move(Integer steps) {
        switch (this.direction) {
            case EAST -> moveEast(steps);
            case WEST -> moveWest(steps);
            case NORTH -> moveNorth(steps);
            case SOUTH -> moveSouth(steps);
        }
    }

    private void moveSouth(Integer steps) {
        if (this.yPosition - steps < 0) {
            throw new IllegalArgumentException("Robot exceeding the matrix boundaries");
        }
        if (PEN_POSITION.DOWN.equals(this.penPosition)) {
            for (int i = this.yPosition ; i >= this.yPosition - steps ; i--) {
                this.matrix[i][this.xPosition] = 1;
            }
        }
        this.yPosition = this.yPosition - steps;
    }

    private void moveNorth(Integer steps) {
        if (this.yPosition + steps >= size) {
            throw new IllegalArgumentException("Robot exceeding the matrix boundaries");
        }
        if (PEN_POSITION.DOWN.equals(this.penPosition)) {
            for (int i = this.yPosition ; i <= this.yPosition + steps ; i++) {
                this.matrix[i][this.xPosition] = 1;
            }
        }
        this.yPosition = this.yPosition + steps;

    }

    private void moveWest(Integer steps) {
        if (this.xPosition - steps < 0) {
            throw new IllegalArgumentException("Robot exceeding the matrix boundaries");
        }
        if (PEN_POSITION.DOWN.equals(this.penPosition)) {
            for (int i = this.xPosition ; i >= this.xPosition - steps ; i--) {
                this.matrix[this.yPosition][i] = 1;
            }
        }
        this.xPosition = this.xPosition - steps;

    }

    private void moveEast(Integer steps) {
        if (this.xPosition + steps >= size) {
            throw new IllegalArgumentException("Robot exceeding the matrix boundaries");
        }
        if (PEN_POSITION.DOWN.equals(this.penPosition)) {
            for (int i = this.xPosition ; i <= this.xPosition + steps ; i++) {
                this.matrix[this.yPosition][i] = 1;
            }
        }
        this.xPosition = this.xPosition + steps;
    }

    public void printPosition() {
        System.out.println("Position: (" + this.xPosition + ", " + this.yPosition + ") - Pen: " + (this.penPosition.getPosition()) + " - Facing: "
                + (this.direction.getDirection()));
    }

    public void changePenPosition(PEN_POSITION penPosition) {
        this.penPosition = penPosition;
    }

    public void initializeMatrix(int size) {
        int[][] arrayOfArray = new int[size][size];
        for (int[] array : arrayOfArray) {
            Arrays.fill(array , 0);
        }
        this.size = size;
        this.xPosition = 0;
        this.yPosition = 0;
        this.penPosition = PEN_POSITION.UP;
        this.direction = DIRECTION.NORTH;
        this.matrix = arrayOfArray;
    }

    public void setXPosition(Integer xPosition) {
        if (xPosition < 0 || xPosition > size) {
            throw new IllegalArgumentException("Illegal value for X position.");
        }
        this.xPosition = xPosition;
    }

    public void setYPosition(Integer yPosition) {
        if (yPosition < 0 || yPosition > size) {
            throw new IllegalArgumentException("Illegal value for Y position.");
        }
        this.yPosition = yPosition;
    }

}