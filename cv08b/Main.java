import java.util.Scanner;

/**
 * Program for clasification of shapes
 * @author Lukar Runt
 * @version 1.0 (16-04-2022)
 */
public class Main {

    /** Array of shapes what user can think about*/
    public static Shape[] shapes;
    /** Scanner for user input*/
    public static Scanner sc = new Scanner(System.in);

    /**
     * Method initialize shapes
     */
    public static void initShapes(){
        shapes = new Shape[6];
        shapes[0] = new Shape("Circle",0, 0, 2, 1);
        shapes[1] = new Shape("Oval", 2, 0, 2, 0);
        shapes[2] = new Shape("Square", 4, 4, 0, 1);
        shapes[3] = new Shape("Rectangle", 4, 4, 0, 0);
        shapes[4] = new Shape("Triangle", 3, 3, 0, 1);
        shapes[5] = new Shape("Pentagon", 5, 5, 0, 0);
    }

    /**
     * Method gave instructions to user, and returns shape what is user think about
     */
    public static void userInput(){
        System.out.println("Think of a shape and answer the questions.");
        int edges = inputNumber("Number of edges: ");
        int vertices = inputNumber("Number of vertices: ");
        int arks = inputNumber("Number of arks: ");
        int sameLengthOfEdges = inputYesNo("Has the shape same length of edges (1 - YES, 0 - NO): ");
        Shape userShape = new Shape(edges, vertices, arks, sameLengthOfEdges);
        Shape assignedShape = assignShape(userShape);
        System.out.println("You think about: " + assignedShape.name);
    }

    /**
     * Method provides user input of numbers
     * @param instructions instructions for user
     * @return number what user writes
     */
    public static int inputNumber(String instructions){
        System.out.print(instructions);
        int number = 0;
        try{
            String input = sc.next();
            number = Integer.parseInt(input);
        }catch(Exception ex){
            System.out.println("Enter number!");
            number = inputNumber(instructions);
        }
        return number;
    }

    /**
     * Method provides user input for yes or no
     * @param instructions instructions to user
     * @return 1 (YES) or 0 (NO)
     */
    public static int inputYesNo(String instructions){
        System.out.print(instructions);
        int number = 0;
        try{
            String input = sc.next();
            number = Integer.parseInt(input);
            if(number < 0 || 1 < number){
                throw new Exception();
            }
        }catch(Exception ex){
            System.out.println("Enter 1 for Yes or 0 for No!");
            number = inputYesNo(instructions);
        }
        return number;
    }

    /**
     * Method find out which shape is the closest
     * @param userShape shape specified by the user
     * @return shape which is the closest to user shape
     */
    public static Shape assignShape(Shape userShape){
        double distance = Double.MAX_VALUE;
        Shape closestShape = null;
        for(int i = 0; i < shapes.length; i++){
            double eucDist = euclideanDistance(shapes[i], userShape);
            if(eucDist < distance){
                distance = eucDist;
                closestShape = shapes[i];
            }
        }
        return closestShape;
    }

    /**
     * Method counts euclideanDistance between two shapes
     * @param a first shape
     * @param b second shape
     * @return distance between two shapes
     */
    public static double euclideanDistance(Shape a, Shape b) {
        int edgesDifference = a.edges - b.edges;
        int verticesDiference = a.vertices - b.vertices;
        int arksDifference = a.ark - b.ark;
        int sameEdgesDifference = a.aspectRatio - b.aspectRatio;
        return Math.sqrt(edgesDifference * edgesDifference + verticesDiference * verticesDiference + arksDifference * arksDifference + sameEdgesDifference * sameEdgesDifference);
    }

    /**
     * Starting point of program
     * @param args input arguments
     */
    public static void main(String[] args){
        initShapes();
        userInput();
    }
}
