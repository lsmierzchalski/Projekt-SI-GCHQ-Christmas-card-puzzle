package sample;

import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.IntConstraintFactory;
import org.chocosolver.solver.constraints.SatFactory;
import org.chocosolver.solver.constraints.nary.automata.FA.FiniteAutomaton;
import org.chocosolver.solver.search.solution.Solution;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.VariableFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.chocosolver.solver.trace.Chatterbox.printShortStatistics;

public class SolverPuzzle {


    public int[][] solvePuzzle(
            int grid_width,
            int grid_height,
            ArrayList<ArrayList<Integer>> know_positions,
            ArrayList<ArrayList<Integer>> column_constraints,
            ArrayList<ArrayList<Integer>> row_constraints){


        Solver solver = new Solver("GCHQ Christmas Nonogram");
        BoolVar[][] grid = VariableFactory.boolMatrix("grid", grid_width, grid_height, solver);

        for (ArrayList<Integer> coords : know_positions) {
            SatFactory.addTrue(grid[coords.get(0)][coords.get(1)]);
        }

        for (int col = 0; col < column_constraints.size(); ++col) {
            int[] tmp = new int[column_constraints.get(col).size()];
            for(int i=0; i<column_constraints.get(col).size();i++){
                tmp[i] = column_constraints.get(col).get(i);
            }
            String re = regularExpression(tmp);
            solver.post(IntConstraintFactory.regular(grid[col],
                    new FiniteAutomaton(re)));
        }

        for (int row = 0; row < row_constraints.size(); ++row) {
            int[] tmp = new int[row_constraints.get(row).size()];
            for(int i=0; i<row_constraints.get(row).size();i++){
                tmp[i] = row_constraints.get(row).get(i);
            }
            String re = regularExpression(tmp);
            BoolVar[] rowVars = new BoolVar[grid_width];
            for (int x = 0; x < grid_width; ++x) {
                rowVars[x] = grid[x][row];
            }
            solver.post(IntConstraintFactory.regular(rowVars,
                    new FiniteAutomaton(re)));
        }

        int[][] result = new int[grid_width][grid_height];

        if (solver.findSolution()) {
            Solution solution = solver.getSolutionRecorder().getLastSolution();
            for (int y = 0; y < grid_height; ++y) {
                for (int x = 0; x < grid_width; ++x) {
                    result[x][y] = solution.getIntVal(grid[x][y]) == 1 ? 1 : 0;
                }
            }
            return result;
        }

        return null;
    }

    private static String regularExpression(int[] runLengths) {
        return "0*"
                + Arrays.stream(runLengths)
                .mapToObj(x -> String.format("1{%d}", x))
                .collect(Collectors.joining( "0+"))
                + "0*";
    }

}
