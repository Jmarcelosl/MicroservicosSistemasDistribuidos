package com.ecommerce.shopping_api.repository;

import com.ecommerce.shopping_api.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long>, ReportRepository {

    public List<Shop> findAllByUserIdentifier(String userIdentifier);

    public List<Shop> findAllByTotalGreaterThan( Float total);
    //busca apenas valores maiores do que o valor passado por parametro
    List<Shop> findAllByDateGreaterThan( LocalDateTime date);

}

