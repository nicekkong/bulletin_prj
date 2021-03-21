package com.cplanet.toring.repository;

import com.cplanet.toring.domain.FaqInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FaqInfoRepository extends JpaRepository<FaqInfo, Long> {

    @Query(value = "select f from FaqInfo f join fetch f.faqCategory c " +
                    "where f.displayStatus = 'OK' and c.displayStatus='OK' " +
                    "order by c.ordering desc, f.ordering desc, f.createDate desc",
        countQuery = "select count(f) from FaqInfo f where f.displayStatus='OK'")
    Page<FaqInfo> findAllFaq(Pageable page);


    @Query(value = "select f from FaqInfo f join fetch f.faqCategory c " +
            "where f.displayStatus = 'OK' and c.displayStatus='OK' " +
            " and  c.code = :category " +
            "order by c.ordering desc, f.ordering desc, f.createDate desc",
            countQuery = "select count(f) from FaqInfo f " +
                    "where f.displayStatus = 'OK' " +
                    " and  f.faqCategory = (select c from FaqCategory c where c.code= :category) ")
    Page<FaqInfo> findAllFaqByCategory(String category, Pageable page);
}
