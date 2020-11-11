package program;

/**
 * Creates an object based off of user input and 5 premade objects. User can specify the type, amount and values
 * for any primitive in the premade object. Once the user is finished, they can specify to send the object
 * which will enable the sender and serializer functionality.
 */

//below are the 5 object types described on page 3 of the assignment description in order

/**
 * A class with only primitives as its instance variables
  */
class AllPrimitive {
    private int a;
    private double b;
    private boolean c;

    AllPrimitive() {
        setA(5);
        setB(1.24);
        setC(false);
    }

    AllPrimitive(int i, double j, boolean k) {
        setA(i);
        setB(j);
        setC(k);
    }

    public int getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public boolean isC() {
        return c;
    }

    public void setA(int a) {
        this.a = a;
    }

    public void setB(double b) {
        this.b = b;
    }

    public void setC(boolean c) {
        this.c = c;
    }

    public String toString() {
        String result = "a: " + Integer.toString(getA()) + " b: " + Double.toString(getB()) + " c: " + Boolean.toString(isC());
        return result;
    }
}

class ComplexWithReferences {

}

/**
 * A class with an int array
 */
class ArrayPrimitives {
    private int myArr[];

    public ArrayPrimitives() {
        setMyArr(new int[]{1, 2, 3, 4, 5, 6});
    }

    public ArrayPrimitives(int[] input) {
        setMyArr(input);
    }

    public int[] getMyArr() {
        return myArr;
    }

    public void setMyArr(int[] input) {
        myArr = new int[input.length];
        for(int i = 0; i < input.length; i++) {
            myArr[i] = input[i];
        }
    }
}

class ArrayReferences {

}

class InstanceJavaCollection {

}

public class ObjectCreator {
    /* TODO
    classify 5 object types based of the 5 specified in the assignment description
    Implement method to create each type with parameters and return it

     */

    public AllPrimitive createAllPrimitive(int a, double b, boolean c) {
        return new AllPrimitive(a, b, c);
    }

}