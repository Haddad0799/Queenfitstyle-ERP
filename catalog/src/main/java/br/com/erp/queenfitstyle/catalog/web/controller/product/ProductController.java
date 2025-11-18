package br.com.erp.queenfitstyle.catalog.web.controller.product;

import br.com.erp.queenfitstyle.catalog.application.command.*;
import br.com.erp.queenfitstyle.catalog.application.port.in.*;
import br.com.erp.queenfitstyle.catalog.domain.entity.Image;
import br.com.erp.queenfitstyle.catalog.domain.entity.Product;
import br.com.erp.queenfitstyle.catalog.domain.entity.Sku;
import br.com.erp.queenfitstyle.catalog.domain.valueobject.SkuCode;
import br.com.erp.queenfitstyle.catalog.web.collector.ImportErrorCollector;
import br.com.erp.queenfitstyle.catalog.web.collector.SkuErrorCollector;
import br.com.erp.queenfitstyle.catalog.web.dto.error.ProductImportError;
import br.com.erp.queenfitstyle.catalog.web.dto.presign.request.GeneratePresignedUrlsRequest;
import br.com.erp.queenfitstyle.catalog.web.dto.presign.request.ImageUploadRequest;
import br.com.erp.queenfitstyle.catalog.web.dto.presign.response.PresignedUrl;
import br.com.erp.queenfitstyle.catalog.web.dto.presign.response.PresignedUrlsResponse;
import br.com.erp.queenfitstyle.catalog.web.dto.product.request.CreateProductDTO;
import br.com.erp.queenfitstyle.catalog.web.dto.product.request.ImportProductsDTO;
import br.com.erp.queenfitstyle.catalog.web.dto.product.request.UpdateProductDTO;
import br.com.erp.queenfitstyle.catalog.web.dto.product.response.CreateProductResumeDTO;
import br.com.erp.queenfitstyle.catalog.web.dto.product.response.ImportResumeDTO;
import br.com.erp.queenfitstyle.catalog.web.dto.product.response.ProductDetailsDTO;
import br.com.erp.queenfitstyle.catalog.web.dto.product.response.ProductSkusDTO;
import br.com.erp.queenfitstyle.catalog.web.dto.sku.request.CreateProductSkuDTO;
import br.com.erp.queenfitstyle.catalog.web.dto.sku.request.SaveSkuImagesDTO;
import br.com.erp.queenfitstyle.catalog.web.dto.sku.request.UpdateSkuDto;
import br.com.erp.queenfitstyle.catalog.web.dto.sku.response.SkuDetailsDTO;
import br.com.erp.queenfitstyle.catalog.web.dto.sku.response.SkuImageResponseDto;
import br.com.erp.queenfitstyle.catalog.web.dto.sku.response.SkuImagesResponseDto;
import br.com.erp.queenfitstyle.catalog.web.exception.SkuNotFoundException;
import br.com.erp.queenfitstyle.catalog.web.factory.ProductCommandFactory;
import br.com.erp.queenfitstyle.catalog.web.mapper.ProductMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    //Product UseCases
    private final CreateProductUseCase createProductUseCase;
    private final GetProductByIdUseCase getProductByIdUseCase;
    private final FindAllProductsFilteredUseCase findAllProductsFilteredUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final ImportProductsUseCase importProductsUseCase;

    //SKUs UseCases
    private final FindAllSkusByProductUseCase findAllSkusByProductUseCase;
    private final UpdateProductSkuUseCase updateProductSkuUseCase;
    private final CreateSkuToProductUseCase createSkuToProductUseCase;
    private final SaveProductSkuImagesUseCase saveProductSkuImagesUseCase;
    private final GetSkuImagesUseCase getSkuImagesUseCase;

    //Upload sku image useCase
    private final UploadImageSkuUseCase uploadImageSkuUseCase;

    //EventListenners
    private final ImportErrorCollector errorCollector;
    private final SkuErrorCollector skuErrorCollector;



    public ProductController(CreateProductUseCase createProductUseCase, GetProductByIdUseCase getProductByIdUseCase, FindAllProductsFilteredUseCase findAllProductsFilteredUseCase, UpdateProductUseCase updateProductUseCase, CreateSkuToProductUseCase createSkuToProductUseCase, SaveProductSkuImagesUseCase saveProductSkuImagesUseCase, GetSkuImagesUseCase getSkuImagesUseCase, UploadImageSkuUseCase uploadImageSkuUseCase,FindAllSkusByProductUseCase findAllSkusByProductUseCase, UpdateProductSkuUseCase updateProductSkuUseCase, ImportProductsUseCase importProductsUseCase, ImportErrorCollector errorCollector, SkuErrorCollector skuErrorCollector) {
        this.createProductUseCase = createProductUseCase;
        this.getProductByIdUseCase = getProductByIdUseCase;
        this.findAllProductsFilteredUseCase = findAllProductsFilteredUseCase;
        this.updateProductUseCase = updateProductUseCase;
        this.createSkuToProductUseCase = createSkuToProductUseCase;
        this.saveProductSkuImagesUseCase = saveProductSkuImagesUseCase;
        this.getSkuImagesUseCase = getSkuImagesUseCase;
        this.uploadImageSkuUseCase = uploadImageSkuUseCase;
        this.skuErrorCollector = skuErrorCollector;
        this.findAllSkusByProductUseCase = findAllSkusByProductUseCase;
        this.updateProductSkuUseCase = updateProductSkuUseCase;
        this.importProductsUseCase = importProductsUseCase;
        this.errorCollector = errorCollector;
    }


    @PostMapping
    public ResponseEntity<CreateProductResumeDTO> createNewProduct(@RequestBody CreateProductDTO dto) {

        CreateProductCommand command = ProductCommandFactory.createFromDTO(dto);

        Product product = createProductUseCase.execute(command);

        List<String> errors = skuErrorCollector.getErrors();

        CreateProductResumeDTO response = ProductMapper.toResumeDTO(product, errors);

        skuErrorCollector.clear();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product.getId())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @PostMapping("/import")
    public ResponseEntity<ImportResumeDTO> importProducts(@RequestBody ImportProductsDTO dto) {

        List<ImportProductCommand> commands = ProductCommandFactory.fromImportDTO(dto);

        List<Product> savedProducts = importProductsUseCase.execute(commands);

        List<ProductImportError> errors = errorCollector.getErrors();

        errorCollector.clear();

        ImportResumeDTO resume = ProductMapper.toImportResume(commands, savedProducts, errors);

        return ResponseEntity.ok(resume);
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

    @PostMapping("/{id}/skus")
    public ResponseEntity<SkuDetailsDTO> createSkuToProduct(
            @PathVariable Long id,
            @RequestBody CreateProductSkuDTO dto) {

        CreateSkuToProductCommand command = ProductCommandFactory.createProductSkuDTO(id, dto);
        Sku createdSku = createSkuToProductUseCase.execute(command);

       SkuDetailsDTO response = ProductMapper.toSkuDetailsDTO(createdSku);

        URI location = URI.create(String.format("/products/%d/skus/%s", id, createdSku.getCode()));

        return ResponseEntity.created(location).body(response);
    }

    @PostMapping("/{id}/skus/{skuCode}/images/presigned-urls")
    public PresignedUrlsResponse generatePresignedUrls(
            @PathVariable Long id,
            @PathVariable String skuCode,
            @RequestBody GeneratePresignedUrlsRequest request
    ) {
        List<String> urls = uploadImageSkuUseCase.execute(
                id,
                skuCode,
                request.images().stream().map(ImageUploadRequest::filename).toList()
        );

        List<PresignedUrl> presignedList = new ArrayList<>();
        for (int i = 0; i < request.images().size(); i++) {
            presignedList.add(new PresignedUrl(request.images().get(i).filename(), urls.get(i)));
        }

        return new PresignedUrlsResponse(presignedList);
    }

    @PostMapping("/{id}/skus/{skuCode}/images")
    public ResponseEntity<Void> saveSkuImages (@PathVariable Long id,
                                               @PathVariable String skuCode,
                                               @RequestBody SaveSkuImagesDTO dto) {

        SaveProductSkuImagesCommand command = ProductCommandFactory.from(id, skuCode, dto);
        saveProductSkuImagesUseCase.execute(command);
        return ResponseEntity.noContent().build();


    }

    @GetMapping("/{id}/skus/{skuCode}/images")
    public ResponseEntity<SkuImagesResponseDto> getSkuImages(@PathVariable Long id,
                                                            @PathVariable String skuCode) {


        List<Image> images = getSkuImagesUseCase.execute(id,skuCode);

        List<SkuImageResponseDto> imageDtos = images.stream()
                .map(i-> new SkuImageResponseDto(i.getId()
                        .toString(),
                        i.getPublicUrl(),
                        i.getDisplayOrder()))
                .toList();

        return ResponseEntity.ok(new SkuImagesResponseDto(imageDtos));

    }


}
