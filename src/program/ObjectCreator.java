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
        String result = "\n[a: " + Integer.toString(getA()) + "] [b: " + Double.toString(getB()) + "] [c: " + Boolean.toString(isC()) + "]";
        return result;
    }
}

/**
 * A class that has instances of AllPrimitive and ArrayPrimitives
 */
class ComplexWithReferences {
    private AllPrimitive obj1;
    private AllPrimitive obj2;

    private ArrayPrimitives arr1;

    public ComplexWithReferences() {
        obj1 = new AllPrimitive(1,2,true);
        obj2 = new AllPrimitive(2228, 102947.9754, false);
        arr1 = new ArrayPrimitives(new int[] {9,8,7,6,5,4,3,2,1});
    }

    public ComplexWithReferences(AllPrimitive obj1, AllPrimitive obj2, ArrayPrimitives arr1) {
        this.setObj1(obj1);
        this.setObj2(obj2);
        this.setArr1(arr1);
    }

    public AllPrimitive getObj1() {
        return obj1;
    }

    public AllPrimitive getObj2() {
        return obj2;
    }

    public ArrayPrimitives getArr1() {
        return arr1;
    }

    public void setObj1(AllPrimitive obj1) {
        this.obj1 = obj1;
    }

    public void setObj2(AllPrimitive obj2) {
        this.obj2 = obj2;
    }

    public void setArr1(ArrayPrimitives arr1) {
        this.arr1 = arr1;
    }

    public String toString() {
        String result = getObj1().toString() + "\n" + getObj2().toString() + "\n" + getArr1().toString();
        return result;
    }
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

    public String toString() {
        String result = "\n";

        for(int i = 0; i < myArr.length; i++) {
            result += "[Index: " + i + "]: " + Integer.toString(myArr[i]) + "\n";
        }

        return result;
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

    public ArrayPrimitives createArrayPrimitives(int[] inputArray) {
        return new ArrayPrimitives(inputArray);
    }

    public ComplexWithReferences createComplexWithReferences(AllPrimitive a, AllPrimitive b, ArrayPrimitives c) {
        return new ComplexWithReferences(a, b, c);
    }

}