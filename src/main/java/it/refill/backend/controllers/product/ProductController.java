package it.refill.backend.controllers.product;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.refill.backend.models.product.Product;
import it.refill.backend.models.users.Supplier;
import it.refill.backend.payload.request.ProductPost;
import it.refill.backend.repository.products.CategoryRepository;
import it.refill.backend.repository.products.ProductRepository;
import it.refill.backend.repository.users.SupplierRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/product")
public class ProductController {

        Logger logger = LoggerFactory.getLogger(ProductController.class);

        @Autowired
        ProductRepository productRepository;

        @Autowired
        SupplierRepository supplierRepository;

        @Autowired
        CategoryRepository categoryRepository;

        /* ############### ROLE SUPPLIER ################ */

        @Operation(summary = "Create product", description = "Create a new product from some supplier")
        @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Succesfully operation"),
                        @ApiResponse(responseCode = "500", description = "Something went wrong"), })
        @Transactional
        @PostMapping("")
        @PreAuthorize("hasRole('SUPPLIER')")
        public ResponseEntity<?> addProduct(@Valid @RequestBody ProductPost newProduct, HttpServletRequest req) {
 
                // get the suppleier associated with the new product
                Supplier supplier = supplierRepository.getOne((Long) req.getAttribute("user_id"));

                // create the new product
                Product product = new Product(newProduct.getType(), newProduct.getProductName(),
                                newProduct.getProductDescription(), newProduct.getImgLink(), newProduct.getPrice(),
                                supplier, true, newProduct.getCategory());

                Product test = productRepository.saveAndFlush(product);

                return new ResponseEntity<Product>(test, HttpStatus.ACCEPTED);

        }

        @Operation(summary = "Patch product", description = "Modify some existing product product from some supplier")
        @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Succesfully operation"),
                        @ApiResponse(responseCode = "500", description = "Something went wrong"), })
        @Transactional
        @PatchMapping("/{id}")
        @PreAuthorize("hasRole('SUPPLIER')")
        public ResponseEntity<?> patchProduct(@RequestBody ProductPost partialPost, @PathVariable("id") Long productId,
                        HttpServletRequest req) {

                logger.error(partialPost.getProductName());

                Product product = productRepository.getOneByIdAndSupplierId(productId,
                                (Long) req.getAttribute("user_id"));

                product.setPrice(partialPost.getPrice());
                product.setProductDescription(partialPost.getProductDescription());
                product.setProductName(partialPost.getProductName());
                product.setImgLink(partialPost.getImgLink());
                product.setCategory(partialPost.getCategory());

                return new ResponseEntity<>(productRepository.saveAndFlush(product), HttpStatus.ACCEPTED);
        }

        @Operation(summary = "Delete product", description = "Delete a product given the ID")
        @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Succesfully operation"),
                        @ApiResponse(responseCode = "500", description = "Something went wrong"), })
        @Transactional
        @DeleteMapping("/{id}")
        @PreAuthorize("hasRole('SUPPLIER')")
        public ResponseEntity<?> deleteProduct(@PathVariable("id") Long productId, HttpServletRequest req) {

                try {
                        Product product = productRepository.getOneByIdAndSupplierId(productId,
                                        (Long) req.getAttribute("user_id"));
                        productRepository.delete(product);
                        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

                } catch (Exception e) {
                        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
        }
}