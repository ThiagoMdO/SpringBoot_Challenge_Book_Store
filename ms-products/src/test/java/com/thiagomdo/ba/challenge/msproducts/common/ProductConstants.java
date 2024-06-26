package com.thiagomdo.ba.challenge.msproducts.common;

import com.thiagomdo.ba.challenge.msproducts.model.dto.ProductDTO;
import com.thiagomdo.ba.challenge.msproducts.model.entities.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductConstants {

    public static final Product PRODUCT = new Product("asdaf2", "Sapiens, Uma breve história da humanidade", "O planeta Terra tem cerca de 4,5 bilhões de anos.", 56.90);
    public static final Product PRODUCT_DESCRIPTION_LESS_TEEN = new Product("asdaf2", "Sapiens, Uma breve história da humanidade", "curta", 56.90);
    public static final Product PRODUCT_VALUE_IS_NULL = new Product("asdaf2", "Sapiens, Uma breve história da humanidade", "O planeta Terra tem cerca de 4,5 bilhões de anos.", null);
    public static final Product PRODUCT_VALUE_LESS_ZERO = new Product("asdaf2", "Sapiens, Uma breve história da humanidade", "O planeta Terra tem cerca de 4,5 bilhões de anos.", -56.90);


    public static final ProductDTO PRODUCT_DTO = new ProductDTO("asdaf2", "Sapiens, Uma breve história da humanidade", "O planeta Terra tem cerca de 4,5 bilhões de anos.", 56.90);
    public static final ProductDTO PRODUCT_DESCRIPTION_LESS_TEEN_DTO = new ProductDTO("asdaf2", "Sapiens, Uma breve história da humanidade", "curta", 56.90);
    public static final ProductDTO PRODUCT_DESCRIPTION_LESS_TEEN_AVAILABLE_NAME_DTO = new ProductDTO("asdaf2", "AVAILABLE_NAME_DTO", "curta", 56.90);
    public static final ProductDTO PRODUCT_VALUE_LESS_ZERO_DTO = new ProductDTO("asdaf2", "Sapiens, Uma breve história da humanidade", "O planeta Terra tem cerca de 4,5 bilhões de anos.", -56.90);
    public static final ProductDTO PRODUCT_VALUE_LESS_ZERO_AVAILABLE_NAME_DTO = new ProductDTO("asdaf2", "AVAILABLE_NAME_DTO", "O planeta Terra tem cerca de 4,5 bilhões de anos.", -56.90);
    public static final ProductDTO PRODUCT_VALUE_IS_NULL_DTO = new ProductDTO("asdaf2", "Sapiens, Uma breve história da humanidade", "O planeta Terra tem cerca de 4,5 bilhões de anos.", null);
    public static final ProductDTO PRODUCT_VALUE_IS_NULL_AVAILABLE_NAME_DTO = new ProductDTO("asdaf2", "AVAILABLE_NAME_DTO", "O planeta Terra tem cerca de 4,5 bilhões de anos.", null);


    public static final Product SAPIENS_BOOK = new Product("asdaf2", "Sapiens, Uma breve história da humanidade", "O planeta Terra tem cerca de 4,5 bilhões de anos.", 56.90);
    public static final ProductDTO SAPIENS_BOOK_DTO = new ProductDTO("asdaf2", "Sapiens, Uma breve história da humanidade", "O planeta Terra tem cerca de 4,5 bilhões de anos.", 56.90);
    public static final Product ALERTA_VERMELHO_BOOK = new Product("dasfx3", "Alerta Vermelho", "Bill Browder iniciou sua vida adulta como um rebelde de Wall Street cujos instintos...", 88.90);
    public static final ProductDTO ALERTA_VERMELHO_BOOK_DTO = new ProductDTO("dasfx3", "Alerta Vermelho", "Bill Browder iniciou sua vida adulta como um rebelde de Wall Street cujos instintos...", 88.90);
    public static final Product O_CORACAO_DO_MUNDO_BOOK = new Product("dasfx33", "O coração do mundo", "Uma nova história universal a partir da rota da seda: o encontro do oriente com o ocidente", 0.0);
    public static final ProductDTO O_CORACAO_DO_MUNDO_BOOK_DTO = new ProductDTO("dasfx33", "O coração do mundo", "Uma nova história universal a partir da rota da seda: o encontro do oriente com o ocidente", 0.0);


    public static final List<Product> PRODUCT_LIST = new ArrayList<>(){
        {
            add(SAPIENS_BOOK);
            add(ALERTA_VERMELHO_BOOK);
        }
    };

    public static final List<ProductDTO> PRODUCT_DTO_LIST = new ArrayList<>(){
        {
            add(SAPIENS_BOOK_DTO);
            add(ALERTA_VERMELHO_BOOK_DTO);
        }
    };

}
