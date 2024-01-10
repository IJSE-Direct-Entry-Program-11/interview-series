package lk.ijse.dep11.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MontisoriPool {
    private List<Connection> pool = new ArrayList<>();
    private List<Connection> consumerPool = new ArrayList<>();
    private static final int DEFAULT_POOL_SIZE = 10;

    public MontisoriPool() {
        this(DEFAULT_POOL_SIZE);
    }

    public MontisoriPool(int poolSize) {
        if (poolSize <= 0) throw new IllegalArgumentException("Pool size should be a positive integer");
        try {
            for (int i = 0; i < poolSize; i++) {
                pool.add(DriverManager.getConnection("jdbc:mysql://localhost:3306",
                        "root", "mysql"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized Connection getConnection(){
        while (pool.isEmpty()){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Connection connection = pool.get(0);
        consumerPool.add(connection);
        pool.remove(connection);
        return connection;
    }

    public synchronized void releaseConnection(Connection connection){
        pool.add(connection);
        consumerPool.remove(connection);
        notify();
    }

    public synchronized void releaseAllConnections(){
        consumerPool.forEach(c -> pool.add(c));
        consumerPool.clear();
        notifyAll();
    }
}
