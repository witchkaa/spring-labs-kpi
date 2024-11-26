package ua.kpi.ist.springlab1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.kpi.ist.springlab1.model.Product;
import ua.kpi.ist.springlab1.repository.ProductRepository;

import java.net.URI;


@RequestMapping("/products")
@RestController
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    ObjectMapper objectMapper;
    @GetMapping
    Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<Product> getProductById(@PathVariable long id) {
        Product p = productRepository.findById(id);
        if (p == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(p);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteProductById(@PathVariable long id) {
        productRepository.deleteById(id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Product> save(@PathVariable Long id, @RequestBody Product productDto) {
        if (productDto.getId()!=null && !productDto.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Product product = new Product(id, productDto.getName(), productDto.getDescription(), productDto.getPrice());
        product = productRepository.save(product);
        return ResponseEntity.ok(product);
    }
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
    // One @Operation for one path+method combination
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
