/**
 * Class {@code Shape} represents shape
 * @author Lukas Runt
 * @version 1.0 (16-04-2022)
 */
public class Shape {
    /** Name of shape*/
    public String name;
    /** Number of edges*/
    public int edges;
    /** Number of vertices*/
    public int vertices;
    /** Number of arks*/
    public int ark;
    /** Has the shape aspectRation 1:1? 1 - YES; 0 - NO*/
    public int aspectRatio;

    /**
     * Constructor of shape for basic shapes, created in init
     * @param name Name of shape
     * @param edges Number of edges
     * @param vertices Number of vertices
     * @param ark Number of arks
     * @param aspectRatio Has the shape aspectRation 1:1? 1 - YES; 0 - NO
     */
    public Shape(String name, int edges, int vertices, int ark, int aspectRatio){
        this.name = name;
        this.edges = edges;
        this.vertices = vertices;
        this.ark = ark;
        this.aspectRatio = aspectRatio;
    }

    /**
     * Constructor of shape for input by user
     * @param edges Number of edges
     * @param vertices Number of vertices
     * @param ark Number of arks
     * @param aspectRatio Has the shape aspectRation 1:1? 1 - YES; 0 - NO
     */
    public Shape(int edges, int vertices, int ark, int aspectRatio){
        this("User shape", edges, vertices, ark, aspectRatio);
    }
}
