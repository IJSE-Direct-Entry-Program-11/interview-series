package lk.ijse.dep11.api;

import lk.ijse.dep11.db.MontisoriPool;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;

@RestController
@RequestMapping("/api/v1/pools")
public class PoolHttpController {

    private MontisoriPool pool = new MontisoriPool(3);

    @GetMapping("/random")
    public String getConnection(){
        Connection connection = pool.getConnection();
        return connection.toString();
    }

    @PatchMapping
    public void releaseAllConnections(){
        pool.releaseAllConnections();
    }
}
