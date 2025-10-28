package br.com.erp.queenfitstyle.catalog.infra.adapter.generator;

import br.com.erp.queenfitstyle.catalog.application.service.ProductCodeGenerator;
import br.com.erp.queenfitstyle.catalog.domain.valueobject.ProductCode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MySqlJdbcProductCodeGenerator implements ProductCodeGenerator {

    private static final String PREFIX = "QFS";
    private static final int LENGTH = 4;

    private final JdbcTemplate jdbcTemplate;

    public MySqlJdbcProductCodeGenerator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public ProductCode nextCode() {
        // 🔹 Incrementa a sequência
        jdbcTemplate.update("UPDATE product_code_sequence SET next_val = next_val + 1");

        // 🔹 Lê o valor atualizado
        Long nextVal = jdbcTemplate.queryForObject(
                "SELECT next_val FROM product_code_sequence",
                Long.class
        );

        String code = PREFIX + String.format("%0" + LENGTH + "d", nextVal);
        return ProductCode.of(code);
    }

}
