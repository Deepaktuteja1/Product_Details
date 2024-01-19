//package com.example.demo.controller;
//
//import com.example.demo.model.ProductDTO;
//import com.example.demo.model.UserInfo;
//import com.example.demo.service.ProductService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.annotation.security.RolesAllowed;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.annotation.Secured;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/products")
//@Tag(name = "Product Details", description = "Endpoints for managing product details")
//public class ProductController {
//
//    @Autowired
//    private  ProductService productService;
//
//
//    @Operation(summary = "Get all products", description = "Get a list of all products", tags = {"Product Details"})
//    @GetMapping
//    @RolesAllowed("ROLE_ADMIN")
//    public ResponseEntity<List<ProductDTO>> getAllProducts() {
//        List<ProductDTO> products = productService.getAllProducts();
//        return new ResponseEntity<>(products, HttpStatus.OK);
//    }
//
//    @Operation(summary = "Get a product by ID", description = "Get a product based on its ID", tags = {"Product Details"})
//    @GetMapping("/{id}")
//    @RolesAllowed("ROLE_ADMIN")
//    public ResponseEntity<ProductDTO> getProductById(@PathVariable @Parameter(description = "ID of the product") Long id) {
//        Optional<ProductDTO> product = productService.getProductById(id);
//        return product.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
//                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
//
//    @Operation(summary = "Save a new product", description = "Save a new product", tags = {"Product Details"})
//    @PostMapping
//    @RolesAllowed("ROLE_ADMIN")
//    public ResponseEntity<ProductDTO> saveProduct(@RequestBody ProductDTO productDTO) {
//        ProductDTO savedProduct = productService.saveProduct(productDTO);
//        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
//    }
//    @PostMapping("/New")
//    public String addNewUser (@RequestBody UserInfo userInfo){
//        return productService.addUser(userInfo);
//    }
//
//    @Operation(summary = "Delete a product by ID", description = "Delete a product based on its ID", tags = {"Product Details"})
//    @DeleteMapping("/{id}")
//    @RolesAllowed("ROLE_ADMIN")
//    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
//        productService.deleteProduct(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//}
//

package com.example.demo.controller;

import com.example.demo.model.AuthRequest;
import com.example.demo.model.Product;
import com.example.demo.model.ProductDTO;
import com.example.demo.model.UserInfo;
import com.example.demo.service.JwtService;
import com.example.demo.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/products")
@Tag(name = "Product Details", description = "Endpoints for managing product details")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Operation(summary = "Get all products", description = "Get a list of all products", tags = {"Product Details"})
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @Operation(summary = "Get a product by ID", description = "Get a product based on its ID", tags = {"Product Details"})
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable @Parameter(description = "ID of the product") Long id) {
        Optional<ProductDTO> productDTO = productService.getProductById(id);
        return productDTO.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Save a new product", description = "Save a new product", tags = {"Product Details"})
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ProductDTO> saveProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO savedProductDTO = productService.saveProduct(productDTO);
        return new ResponseEntity<>(savedProductDTO, HttpStatus.CREATED);
    }

    @PostMapping("/new")
    public String addNewUser(@RequestBody UserInfo userInfo) {
        return productService.addUser(userInfo);
    }

    @Operation(summary = "Delete a product by ID", description = "Delete a product based on its ID", tags = {"Product Details"})
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }


    }
}



