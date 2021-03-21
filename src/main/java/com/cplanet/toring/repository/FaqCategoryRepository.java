package com.cplanet.toring.repository;

import com.cplanet.toring.domain.FaqCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FaqCategoryRepository extends JpaRepository<FaqCategory, Long> {

    @Query(value = "select c from FaqCategory c " +
            "join  c.faqInfos f " +
            "where c.displayStatus = 'OK' " +
            "and f.displayStatus = 'OK' " +
            "order by c.ordering desc, f.ordering desc ",
            countQuery = "select count(c) from FaqCategory c " +
                    "join  c.faqInfos f " +
                    "where c.displayStatus = 'OK' " +
                    "  and  f.displayStatus = 'OK' ")
    Page<FaqCategory> findAll(Pageable page);


}
