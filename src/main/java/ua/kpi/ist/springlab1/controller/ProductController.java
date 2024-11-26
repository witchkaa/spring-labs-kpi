package ua.kpi.ist.springlab1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.kpi.ist.springlab1.model.Product;
import ua.kpi.ist.springlab1.repository.ProductRepository;

import java.net.URI;


@RequestMapping("/products")
@RestController
@Tag(name = "Product", description = "Operations with Product")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    ObjectMapper objectMapper;

    @Operation(
            summary = "Get all",
            description = "Get a paginated and optionally filtered list of products",
            parameters = {
            @Parameter(name = "page", in = ParameterIn.QUERY, description = "Page number for pagination (0-based)", required = false, schema = @Schema(type = "integer", example = "0")),
            @Parameter(name = "size", in = ParameterIn.QUERY, description = "Number of products per page", required = false, schema = @Schema(type = "integer", example = "2")),
            @Parameter(name = "name", in = ParameterIn.QUERY, description = "Filter products by name (partial match)", required = false, schema = @Schema(type = "string", example = "shirt")),
            @Parameter(name = "description", in = ParameterIn.QUERY, description = "Filter products by description (partial match)", required = false, schema = @Schema(type = "string", example = "cotton")),
            @Parameter(name = "minPrice", in = ParameterIn.QUERY, description = "Filter products with price greater than or equal to", required = false, schema = @Schema(type = "number", format = "float", example = "80")),
            @Parameter(name = "maxPrice", in = ParameterIn.QUERY, description = "Filter products with price less than or equal to", required = false, schema = @Schema(type = "number", format = "float", example = "180"))
    }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
    })
    @GetMapping
    public Page<Product> getAllProducts(Pageable pageable,
                                        @RequestParam(required = false) String name,
                                        @RequestParam(required = false) String description,
                                        @RequestParam(required = false) Double minPrice,
                                        @RequestParam(required = false) Double maxPrice) {
        return productRepository.findAll(pageable, name, description, minPrice, maxPrice);
    }

    @Operation(
            summary = "Get By Id",
            description = "Get Product by identifier",
            parameters = {@Parameter(name = "id", description = "Product Id", example = "2")}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<Product> getProductById(@PathVariable long id) {
        Product p = productRepository.findById(id);
        if (p == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(p);
    }

    @Operation(
            summary = "Delete By Id",
            description = "Delete product by identifier",
            parameters = {@Parameter(name = "id", description = "Product Id", example = "2")}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No content"),
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteProductById(@PathVariable long id) {
        productRepository.deleteById(id);
    }

    @Operation(
            summary = "Put By Id",
            description = "Add or edit product by identifier",
            parameters = {@Parameter(name = "id", description = "Product Id", example = "2")}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Product> save(@PathVariable Long id, @RequestBody Product productDto) {
        if (productDto.getId()!=null && !productDto.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Product product = new Product(id, productDto.getName(), productDto.getDescription(), productDto.getPrice());
        product = productRepository.save(product);
        return ResponseEntity.ok(product);
    }

    @Operation(
            summary = "Add Product",
            description = "Add Product (id generated by server)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product productDto) {
        if (productDto.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        Product product = productRepository.save(productDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(product.getId()).toUri();

        return ResponseEntity.created(uri).body(product);
    }

    @Operation(summary = "RFC-6902 JSON Patch / RFC-7386 JSON Merge Patch",
            description = """
    **RFC-6902 JSON Patch:**<br>
    Content-type:**application/json-patch+json**<br>
    Body:<pre>
    [
    {"op":"replace", "path":"/name", "value":"Black jeans" },
    {"op":"replace", "path":"/price", "value":"500"},
    ...
    ]</pre>
    
    **RFC-7386 JSON Merge Patch:**<br>
    Content-type:**application/merge-patch+json**<br>
    Body:<pre>
    {
    "name":"Green skirt",
    ...
    }</pre>
    """)
    //RFC-6902 JSON Patch
    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    ResponseEntity<Product> jsonPatch(@PathVariable Long id, @RequestBody JsonPatch patch) {
        return patch(id, patch);
    }
    //RFC-7386 JSON Merge Patch
    @PatchMapping(path = "/{id}", consumes = "application/merge-patch+json")
    ResponseEntity<Product> jsonMergePatch(@PathVariable Long id, @RequestBody JsonMergePatch patch) {
        return patch(id, patch);
    }
    private ResponseEntity<Product> patch(Long id, JsonPatch patch) {
        Product product = productRepository.findById(id);
        if(product==null) return ResponseEntity.notFound().build();
        try {
            JsonNode json = objectMapper.convertValue(product, JsonNode.class);
            json = patch.apply(json);
            product = objectMapper.treeToValue(json, Product.class);
            product = productRepository.save(product);
            return ResponseEntity.ok(product);
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    private ResponseEntity<Product> patch(Long id, JsonMergePatch patch) {
        Product product = productRepository.findById(id);
        if(product==null) return ResponseEntity.notFound().build();
        try {
            JsonNode json = objectMapper.convertValue(product, JsonNode.class);
            json = patch.apply(json);
            product = objectMapper.treeToValue(json, Product.class);
            product = productRepository.save(product);
            return ResponseEntity.ok(product);
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
