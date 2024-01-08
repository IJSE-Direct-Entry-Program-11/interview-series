package lk.ijse.dep11.ds;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

public class ComparableAndComparatorDemo {

    public static void main(String[] args) {
        Customer[] customers = {
                new Customer(10, "Kasun"),
                new Customer(5, "Kasun"),
                new Customer(2, "Kasun"),
                new Customer(4, "Kasun"),
                new Customer(8, "Kasun")
        };
        Arrays.sort(customers, (o1, o2) -> Integer.compare(o1.id, o2.id));
        for (Customer customer : customers) System.out.println(customer);
    }

    // Arrays.sort, Collections.sort, TreeMap, TreeSet

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Customer{
        private int id;
        private String name;

//        @Override
//        public int compareTo(Customer o) {
//            return Integer.compare(id, o.id);
//        }
    }
}
