package br.com.erp.queenfitstyle.catalog.api.controller.category;

import br.com.erp.queenfitstyle.catalog.api.dto.category.request.CreateCategoryDTO;
import br.com.erp.queenfitstyle.catalog.api.dto.category.request.UpdateCategoryNameRequest;
import br.com.erp.queenfitstyle.catalog.api.dto.category.response.CategoryCreatedDTO;
import br.com.erp.queenfitstyle.catalog.api.dto.category.response.CategoryUpdatedDTO;
import br.com.erp.queenfitstyle.catalog.domain.entity.Category;
import br.com.erp.queenfitstyle.catalog.domain.port.in.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CreateCategoryUseCase createCategoryUseCase;
    private final GetCategoryByidUseCase getCategoryByIdUsecase;
    private final GetAllCategoriesPaginatedUseCase getAllCategoriesPaginatedUseCase;
    private final DeleteCategoryUseCase deleteCategoryUseCase;
    private final UpdateCategoryNameUseCase updadeCategoryNameUseCase;

    public CategoryController(CreateCategoryUseCase createCategoryUseCase, GetCategoryByidUseCase getCategoryByIdUsecase, GetAllCategoriesPaginatedUseCase getAllCategoriesPaginatedUseCase, DeleteCategoryUseCase deleteCategoryUseCase, UpdateCategoryNameUseCase updadeCategoryNameUseCase) {
        this.createCategoryUseCase = createCategoryUseCase;
        this.getCategoryByIdUsecase = getCategoryByIdUsecase;
        this.getAllCategoriesPaginatedUseCase = getAllCategoriesPaginatedUseCase;
        this.deleteCategoryUseCase = deleteCategoryUseCase;
        this.updadeCategoryNameUseCase = updadeCategoryNameUseCase;
    }

    @PostMapping
    public ResponseEntity<CategoryCreatedDTO> createNewCategory(@RequestBody CreateCategoryDTO dto) {
        // Executa o caso de uso
        Category category = createCategoryUseCase.execute(dto.name());

        // Cria DTO de resposta
        CategoryCreatedDTO responseDTO = new CategoryCreatedDTO(
                category.getId(),
                category.getDisplayName(),
                category.getNormalizedName(),
                category.isActive()
        );

        // Monta URI da nova categoria
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(category.getId())
                .toUri();

        // Retorna 201 Created com Location header e corpo
        return ResponseEntity
                .created(location)
                .body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryCreatedDTO> findById(@PathVariable Long id) {
        Category category = getCategoryByIdUsecase.execute(id);

        CategoryCreatedDTO responseDTO = new CategoryCreatedDTO(category.getId(),
                category.getDisplayName(),
                category.getNormalizedName(),
                category.isActive());

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<CategoryCreatedDTO>> findAll(
            Pageable pageable,
            @RequestParam(required = false) Boolean active) {

        Page<Category> categories = getAllCategoriesPaginatedUseCase.execute(pageable, active);

        Page<CategoryCreatedDTO> dtoPage = categories.map(c ->
                new CategoryCreatedDTO(
                        c.getId(),
                        c.getDisplayName(),
                        c.getNormalizedName(),
                        c.isActive()
                )
        );

        return ResponseEntity.ok(dtoPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryUpdatedDTO> updateName(
            @PathVariable Long id,
            @RequestBody UpdateCategoryNameRequest request
    ) {
       Category category = updadeCategoryNameUseCase.execute(id, request.newName());

       CategoryUpdatedDTO responseDTO = new CategoryUpdatedDTO(category.getDisplayName(), category.getNormalizedName());

        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        deleteCategoryUseCase.execute(id);

        return ResponseEntity.noContent().build();
    }

}
