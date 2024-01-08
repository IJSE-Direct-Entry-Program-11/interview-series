package lk.ijse.dep11.ds;

public class LinkedList<T> {

    public void add(int index, T value) {

    }

    public T remove(int index) {
        return null;
    }

    @Override
    public String toString() {
        return "[]";
    }

    private static class Node<T> {
        T next;
        T value;
    }
}
