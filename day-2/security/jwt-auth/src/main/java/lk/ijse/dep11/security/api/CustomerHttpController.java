package lk.ijse.dep11.security.api;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@CrossOrigin
public class CustomerHttpController {

    @GetMapping
    public String getAllCustomers(){
        return "<h1>Get All Customers</h1>";
    }

    @PostMapping
    public String saveCustomer(){
        return "<h1>Save Customer</h1>";
    }
}
