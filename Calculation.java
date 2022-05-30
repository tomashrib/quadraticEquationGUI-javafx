public class Calculation{

    public static double calculationPlus(double a, double b, double c){
        double x1, x2, d;
        double result;

        d = b*b - (4 * a * c);
        
        result = -b + (Math.sqrt(d) / (2 * a));

        return result;
    }
    public static double calculationMinus(double a, double b, double c){
        double x1, x2, d;
        double result;

        d = b*b - (4 * a * c);
        
        result = -b - (Math.sqrt(d) / (2 * a));

        return result;
    }
    public static double calculationD(double a, double b, double c){
        double x1, x2, d;
        double result;

        d = b*b - (4 * a * c);
        result = d;
        return result;
    }
    
    // public class VertexXY{
    //     public double x;
    //     public double y;
    
    //     public VertexXY(double a, double b, double c){
    //         x = -b / (2 * a);
    //         y = ( (4 * a * c) - (b * b) ) / (4 * a);
    //     }
    // }


    public static double[] vertexXY(double a, double b, double c){
        double array[] = new double[2];

        array[0] = -b / (2 * a);
        array[1] = ( (4 * a * c) - (b * b) ) / (4 * a);
        return array;
    }
    
}
