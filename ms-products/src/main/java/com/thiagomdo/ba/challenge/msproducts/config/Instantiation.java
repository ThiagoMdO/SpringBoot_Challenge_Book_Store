package com.thiagomdo.ba.challenge.msproducts.config;

import com.thiagomdo.ba.challenge.msproducts.model.entities.Product;
import com.thiagomdo.ba.challenge.msproducts.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class Instantiation implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        productRepository.deleteAll();

        Product product1 = new Product("6616929eb63057298df31490", "Sapiens, Uma breve história da humanidade", "O planeta Terra tem cerca de 4,5 bilhões de anos.", 100.00);
        Product product2 = new Product("6616929eb63057298df31491", "Alerta Vermelho", "Bill Browder iniciou sua vida adulta como um rebelde de Wall Street cujos instintos...", 50.00);
        Product product3 = new Product("6616929eb63057298df31492", "Origem (Robert Langdon - Livro 5)", "Em vez de se limitar a escrever um thriller de fuga e perseguição, Dan Brown está determinado a...", 45.90);

        productRepository.saveAll(Arrays.asList(product1, product2, product3));
    }
}
