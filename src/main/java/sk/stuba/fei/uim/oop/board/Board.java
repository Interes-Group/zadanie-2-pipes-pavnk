package sk.stuba.fei.uim.oop.board;

import sk.stuba.fei.uim.oop.blocks.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    private final Pipe[][] grid;
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private final int pipeSize;
    private final int size;
    private final Random random;

    public Board(int size, Random random) {
        this.random = random;
        this.size = size;
        pipeSize = 600/size;
        this.grid = new Pipe[size][size];
        for(int i=0;i<size;++i){
            for(int j=0;j<size;++j){
                grid[i][j]=new EmptyPipe(j,i,pipeSize,false,false);
            }
        }
        setStartAndGoal(size);
        generatePath();
        setPipeOrientations();
    }
    public int getPipeSize(){
        return pipeSize;
    }

    public Pipe getPipeAt(int x, int y) {
        if (x < 0 || x >= grid[0].length || y < 0 || y >= grid.length) {
            return null;
        }
        return grid[y][x];
    }

    public Pipe getPipe(int x, int y){
        return grid[y][x];
    }
    public int getSize(){
        return this.size;
    }

    public boolean checkPath(){
        getPipeAt(startX,startY).setWaterFlows(true);
        if(checkConnected(startX, startY, Direction.UP)){
            return checkPathRecursive(startX,startY-1,Direction.DOWN);
        }
        if(checkConnected(startX, startY, Direction.RIGHT)){
            return checkPathRecursive(startX+1,startY,Direction.LEFT);
        }
        if(checkConnected(startX, startY, Direction.DOWN)){
            return checkPathRecursive(startX,startY+1,Direction.UP);
        }
        if(checkConnected(startX, startY, Direction.LEFT)){
            return checkPathRecursive(startX-1,startY,Direction.RIGHT);
        }
        return false;
    }

    private boolean checkPathRecursive(int x, int y, Direction previous){
        Pipe pipe = getPipeAt(x,y);
        pipe.setWaterFlows(true);
        if(x == endX && y == endY)
            return true;
        if(previous != Direction.UP && checkConnected(x, y, Direction.UP)){
            return checkPathRecursive(x,y-1,Direction.DOWN);
        }
        if(previous != Direction.RIGHT && checkConnected(x, y, Direction.RIGHT)){
            return checkPathRecursive(x+1,y,Direction.LEFT);
        }
        if(previous != Direction.DOWN && checkConnected(x, y, Direction.DOWN)){
            return checkPathRecursive(x,y+1,Direction.UP);
        }
        if(previous != Direction.LEFT && checkConnected(x, y, Direction.LEFT)){
            return checkPathRecursive(x-1,y,Direction.RIGHT);
        }
        return false;
    }

    private boolean checkConnected(int x, int y, Direction direction){
        switch (direction){
            case UP: {
                if (!grid[y][x].isFacingUp())
                    return false;
                Pipe nextPipe = getPipeAt(x, y - 1);
                if (nextPipe == null)
                    return false;
                return nextPipe.isFacingDown();
            }
            case RIGHT: {
                if (!grid[y][x].isFacingRight())
                    return false;
                Pipe nextPipe = getPipeAt(x + 1, y);
                if (nextPipe == null)
                    return false;
                return nextPipe.isFacingLeft();
            }
            case DOWN:{
                if (!grid[y][x].isFacingDown())
                    return false;
                Pipe nextPipe = getPipeAt(x, y+1);
                if (nextPipe == null)
                    return false;
                return nextPipe.isFacingUp();
            }
            case LEFT: {
                if (!grid[y][x].isFacingLeft())
                    return false;
                Pipe nextPipe = getPipeAt(x-1, y);
                if (nextPipe == null)
                    return false;
                return nextPipe.isFacingRight();
            }
            default:
                return false;
        }
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
        placePipes(path);
    }

    private void placePipes(List<Integer[]> path){
        for (int i = 1; i < path.size()-1; i++) {
            int deltaX  = Math.abs(path.get(i+1)[0] - path.get(i-1)[0]);
            int deltaY  = Math.abs(path.get(i+1)[1] - path.get(i-1)[1]);

            if (deltaX == 2 || deltaY == 2) {
                boolean orientation = random.nextBoolean();
                grid[path.get(i)[1]][path.get(i)[0]] = new StraightPipe(path.get(i)[0], path.get(i)[1],pipeSize,orientation, false,false);
            }
            else {
                int orientation = random.nextInt(4);
                grid[path.get(i)[1]][path.get(i)[0]] = new CurvedPipe(path.get(i)[0], path.get(i)[1],pipeSize, OrientationCurved.values()[orientation], false,false);
            }
        }
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
        return neighbors.get(random.nextInt(neighbors.size()));
    }
    private boolean checkValid(int x, int y, boolean[][] visited){
        return ((x>=0 && x<size) && (y>=0 && y<size) && !visited[y][x]);
    }
    private void setPipeOrientations() {
    }

    private void setStartAndGoal(int size) {
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

        grid[startY][startX] = new StartPipe(startX,startY, pipeSize, Direction.values()[random.nextInt(4)], true,false);
        grid[endY][endX] = new StartPipe(endX,endY, pipeSize, Direction.values()[random.nextInt(4)], false,false);
    }

    public void resetPipeFlow() {
        for (Pipe[] pipes : grid) {
            for (int j = 0; j < grid[0].length; ++j) {
                pipes[j].setWaterFlows(false);
            }
        }
    }
}
