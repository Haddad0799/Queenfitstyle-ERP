package br.com.erp.queenfitstyle.catalog.api.controller.product;

import br.com.erp.queenfitstyle.catalog.api.dto.error.ProductImportError;
import br.com.erp.queenfitstyle.catalog.api.dto.product.request.CreateProductDTO;
import br.com.erp.queenfitstyle.catalog.api.dto.product.request.ImportProductsDTO;
import br.com.erp.queenfitstyle.catalog.api.dto.product.request.UpdateProductDTO;
import br.com.erp.queenfitstyle.catalog.api.dto.product.response.CreateProductResumeDTO;
import br.com.erp.queenfitstyle.catalog.api.dto.product.response.ImportResumeDTO;
import br.com.erp.queenfitstyle.catalog.api.dto.product.response.ProductDetailsDTO;
import br.com.erp.queenfitstyle.catalog.api.dto.product.response.ProductSkusDTO;
import br.com.erp.queenfitstyle.catalog.api.dto.sku.request.UpdateSkuDto;
import br.com.erp.queenfitstyle.catalog.api.dto.sku.response.SkuDetailsDTO;
import br.com.erp.queenfitstyle.catalog.api.exception.SkuNotFoundException;
import br.com.erp.queenfitstyle.catalog.api.factory.ProductCommandFactory;
import br.com.erp.queenfitstyle.catalog.api.listener.ImportErrorListener;
import br.com.erp.queenfitstyle.catalog.api.listener.SkuErrorListener;
import br.com.erp.queenfitstyle.catalog.api.mapper.ProductMapper;
import br.com.erp.queenfitstyle.catalog.application.command.CreateProductCommand;
import br.com.erp.queenfitstyle.catalog.application.command.ImportProductCommand;
import br.com.erp.queenfitstyle.catalog.application.command.UpdateProductCommand;
import br.com.erp.queenfitstyle.catalog.application.command.UpdateSkuCommand;
import br.com.erp.queenfitstyle.catalog.domain.entity.Product;
import br.com.erp.queenfitstyle.catalog.domain.entity.Sku;
import br.com.erp.queenfitstyle.catalog.domain.port.in.*;
import br.com.erp.queenfitstyle.catalog.domain.valueobject.SkuCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    //UseCases
    private final CreateProductUseCase createProductUseCase;
    private final GetProductByIdUseCase getProductByIdUseCase;
    private final FindAllProductsFilteredUseCase findAllProductsFilteredUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final FindAllSkusByProductUseCase findAllSkusByProductUseCase;
    private final UpdateProductSkuUseCase updateProductSkuUseCase;
    private final ImportProductsUseCase importProductsUseCase;

    //EventListenners
    private final ImportErrorListener importErrorListener;
    private final SkuErrorListener errorListener;



    public ProductController(CreateProductUseCase createProductUseCase, GetProductByIdUseCase getProductByIdUseCase, FindAllProductsFilteredUseCase findAllProductsFilteredUseCase, UpdateProductUseCase updateProductUseCase, SkuErrorListener errorListener, FindAllSkusByProductUseCase findAllSkusByProductUseCase, UpdateProductSkuUseCase updateProductSkuUseCase, ImportProductsUseCase importProductsUseCase, ImportErrorListener importErrorListener) {
        this.createProductUseCase = createProductUseCase;
        this.getProductByIdUseCase = getProductByIdUseCase;
        this.findAllProductsFilteredUseCase = findAllProductsFilteredUseCase;
        this.updateProductUseCase = updateProductUseCase;
        this.errorListener = errorListener;
        this.findAllSkusByProductUseCase = findAllSkusByProductUseCase;
        this.updateProductSkuUseCase = updateProductSkuUseCase;
        this.importProductsUseCase = importProductsUseCase;
        this.importErrorListener = importErrorListener;
    }


    @PostMapping
    public ResponseEntity<CreateProductResumeDTO> createNewProduct(@RequestBody CreateProductDTO dto) {

        CreateProductCommand command = ProductCommandFactory.createFromDTO(dto);

        Product product = createProductUseCase.execute(command);

        List<String> errors = errorListener.getLastErrors();

        CreateProductResumeDTO response = ProductMapper.toResumeDTO(product, errors);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product.getId())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailsDTO> findById(@PathVariable Long id) {

        Product product = getProductByIdUseCase.execute(id);

        ProductDetailsDTO response = ProductMapper.toDetailsDTO(product);

        return ResponseEntity.ok(response);

    }

    @GetMapping
    public ResponseEntity<Page<ProductDetailsDTO>> findAllSortedAndPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long colorId,
            @RequestParam(required = false) String sizeFilter
    ) {
        Pageable pageable = PageRequest.of(page, size,
                direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

        Page<Product> products = findAllProductsFilteredUseCase
                .execute(categoryId, active, name, colorId, sizeFilter, pageable);

        Page<ProductDetailsDTO> response = products
                .map(ProductMapper::toDetailsDTO);

        return ResponseEntity.ok(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProductDetailsDTO> updateProduct(@PathVariable Long id, @RequestBody UpdateProductDTO dto) {

        UpdateProductCommand command = ProductCommandFactory.updateFromDTO(id,dto);

        Product updated = updateProductUseCase.execute(command);

        ProductDetailsDTO response = ProductMapper.toDetailsDTO(updated);

        return ResponseEntity.ok(response);

    }

    @GetMapping("/{id}/skus")
    public ResponseEntity<ProductSkusDTO> findSkusFromProduct(@PathVariable Long id) {

        List<Sku> skus = findAllSkusByProductUseCase.execute(id);

        List<SkuDetailsDTO> skuDetails = ProductMapper.toSkuDetailsDTO(skus);

        return ResponseEntity.ok(new ProductSkusDTO(skuDetails));
    }

    @PutMapping("/{id}/skus/{skuCode}")
    public ResponseEntity<SkuDetailsDTO> updateProductSku(@PathVariable Long id,
                                                          @PathVariable String skuCode,
                                                          @RequestBody UpdateSkuDto dto) {

        UpdateSkuCommand command = ProductCommandFactory.UpdateProductSkuFromDTO(id, skuCode, dto);

        Product updated = updateProductSkuUseCase.execute(command);

        SkuCode code = new SkuCode(skuCode);

        Sku sku = updated.findSkuByCode(code)
                .orElseThrow(() -> new SkuNotFoundException(
                        "SKU " + skuCode + " não encontrado para o produto " + updated.getCode()
                ));

        SkuDetailsDTO response = ProductMapper.toSkuDetailsDTO(sku);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/import")
    public ResponseEntity<ImportResumeDTO> importProducts(@RequestBody ImportProductsDTO dto) {

        List<ImportProductCommand> commands = ProductCommandFactory.fromImportDTO(dto);

        List<Product> savedProducts = importProductsUseCase.execute(commands);

        List<ProductImportError> errors = importErrorListener.getErrors();

        importErrorListener.clear();

        ImportResumeDTO resume = ProductMapper.toImportResume(commands, savedProducts, errors);

        return ResponseEntity.ok(resume);
    }

}
