package com.thiagomdo.ba.challenge.msproducts.common;

import com.thiagomdo.ba.challenge.msproducts.model.dto.ProductDTO;
import com.thiagomdo.ba.challenge.msproducts.model.entities.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductConstants {

    public static final Product PRODUCT = new Product("asdaf2", "Sapiens, Uma breve história da humanidade", "O planeta Terra tem cerca de 4,5 bilhões de anos.", 56.90);

    public static final ProductDTO PRODUCT_DTO = new ProductDTO("asdaf2", "Sapiens, Uma breve história da humanidade", "O planeta Terra tem cerca de 4,5 bilhões de anos.", 56.90);


    public static final Product SAPIENS_BOOK = new Product("asdaf2", "Sapiens, Uma breve história da humanidade", "O planeta Terra tem cerca de 4,5 bilhões de anos.", 56.90);
    public static final ProductDTO SAPIENS_BOOK_DTO = new ProductDTO("asdaf2", "Sapiens, Uma breve história da humanidade", "O planeta Terra tem cerca de 4,5 bilhões de anos.", 56.90);
    public static final Product ALERTA_VERMELHO_BOOK = new Product("dasfx3", "Alerta Vermelho", "Bill Browder iniciou sua vida adulta como um rebelde de Wall Street cujos instintos...", 88.90);
    public static final ProductDTO ALERTA_VERMELHO_BOOK_DTO = new ProductDTO("dasfx3", "Alerta Vermelho", "Bill Browder iniciou sua vida adulta como um rebelde de Wall Street cujos instintos...", 88.90);


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
