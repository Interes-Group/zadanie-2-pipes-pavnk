package sk.stuba.fei.uim.oop.board;

import sk.stuba.fei.uim.oop.blocks.EmptyPipe;
import sk.stuba.fei.uim.oop.blocks.Pipe;
import sk.stuba.fei.uim.oop.blocks.StartPipe;
import sk.stuba.fei.uim.oop.blocks.StraightPipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Board {
    private Pipe[][] grid;
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private int pipeSize;
    private int size;

    public Board(int size) {
        this.size = size;
        pipeSize = 600/size;
        this.grid = new Pipe[size][size];
        for(int i=0;i<size;++i){
            for(int j=0;j<size;++j){
                grid[i][j]=new EmptyPipe(j,i,pipeSize);
            }
        }
        setStartAndGoal(size);
        generatePath();
        setPipeOrientations();
    }
    public Pipe getPipe(int x, int y){
        return grid[y][x];
    }
    public int getSize(){
        return this.size;
    }

    private void generatePath() {
        boolean[][] visited = new boolean[size][size];
        for(int i=0;i<size;++i){
            for(int j=0;j<size;++j){
                visited[i][j] = false;
            }
        }
        List<Integer[]> path = new ArrayList<>();
        generatePathRecursive(startX,startY,visited, path);

    }

    private boolean generatePathRecursive(int currentX, int currentY,boolean[][] visited, List<Integer[]> path){
        visited[currentY][currentX] = true;
        if(currentX == endX && currentY == endY) {
            path.add(new Integer[]{currentX,currentY});
            return true;
        }
        Integer[] neighbour = chooseRandomNeighbour(currentX,currentY, visited);
        while(neighbour[0]!=-1 && neighbour[1]!=-1){
            if(generatePathRecursive(neighbour[0],neighbour[1],visited, path)) {
                grid[currentY][currentX] = new StraightPipe(currentX,currentY, pipeSize, true);
                path.add(new Integer[]{currentX,currentY});
                return true;
            }
            neighbour = chooseRandomNeighbour(currentX,currentY, visited);
        }
        return false;
    }

    private Integer[] chooseRandomNeighbour(int currentX, int currentY,boolean[][] visited){
        List<Integer[]> neighbors = new ArrayList<>();
        if(checkValid(currentX,currentY-1,visited)){
            neighbors.add(new Integer[]{currentX,currentY-1});
        }
        if(checkValid(currentX+1,currentY,visited)){
            neighbors.add(new Integer[]{currentX+1,currentY});
        }
        if(checkValid(currentX,currentY+1,visited)){
            neighbors.add(new Integer[]{currentX,currentY+1});
        }
        if(checkValid(currentX-1,currentY,visited)){
            neighbors.add(new Integer[]{currentX-1,currentY});
        }
        if(neighbors.size() == 0){
            return new Integer[]{-1,-1};
        }
        Random random = new Random();
        return neighbors.get(random.nextInt(neighbors.size()));
    }
    private boolean checkValid(int x, int y, boolean[][] visited){
        return ((x>=0 && x<size) && (y>=0 && y<size) && !visited[y][x]);
    }
    private void setPipeOrientations() {
    }

    private void setStartAndGoal(int size) {
        Random random = new Random();
        startX = random.nextInt(size);
        if(startX == 0 || startX == size-1){
            startY = random.nextInt(size);
            if(startY== 0 || startY == size-1){
                if(startX == 0 && startY == 0){
                    endX = size-1;
                    endY = size-1;
                } else if(startX == 0 && startY == size-1){
                    endX = size-1;
                    endY = 0;
                } else if(startX == size-1 && startY == 0){
                    endX = 0;
                    endY = size-1;
                } else if(startX == size-1 && startY == size-1){
                    endX = 0;
                    endY = 0;
                }
            } else {
                if(startX == 0){
                    endX = size-1;
                    endY = random.nextInt(size);
                } else if(startX == size-1){
                    endX = 0;
                    endY = random.nextInt(size);
                }
            }
        } else{
            startY = random.nextInt(2) * (size-1);
            if(startY == 0){
                endY = size-1;
                endX = random.nextInt(size);
            } else if(startY == size-1){
                endY = 0;
                endX = random.nextInt(size);
            }
        }

        grid[startY][startX] = new StartPipe(startX,startY, pipeSize, random.nextInt(4));
        grid[endY][endX] = new StartPipe(endX,endY, pipeSize, random.nextInt(4));
    }

}
