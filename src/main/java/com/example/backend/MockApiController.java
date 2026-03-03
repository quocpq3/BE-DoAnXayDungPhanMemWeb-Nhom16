package com.example.backend;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mock")
@CrossOrigin(origins = "*")
public class MockApiController {

    @GetMapping("/products")
    public Map<String, Object> getMockProducts() {
        List<Map<String, Object>> products = List.of(
                Map.of(
                        "id", 1,
                        "name", "Laptop Pro 14",
                        "price", 28990000,
                        "inStock", true
                ),
                Map.of(
                        "id", 2,
                        "name", "Wireless Mouse M2",
                        "price", 490000,
                        "inStock", true
                ),
                Map.of(
                        "id", 3,
                        "name", "Mechanical Keyboard K87",
                        "price", 1590000,
                        "inStock", false
                )
        );

        return Map.of(
                "success", true,
                "message", "Mock products loaded",
                "data", products
        );
    }
}
