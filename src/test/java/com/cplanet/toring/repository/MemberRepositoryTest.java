package com.cplanet.toring.repository;


import com.cplanet.toring.domain.Member;
import com.cplanet.toring.domain.enums.MemberStatus;
import com.cplanet.toring.domain.enums.Role;
import com.cplanet.toring.service.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ContentsRepository contentsRepository;

    @Autowired
    private ContentsReviewRepository contentsReviewRepository;

//    @Autowired
//    private FaqInfoRepository faqInfoRepository;
//
//    @Autowired
//    private FaqCategoryRepository faqCategoryRepository;

    @Test
    public void memberSaveTest() {

        Member member = Member.builder()
                .email("nicekkong@gmail.com")
                .password("12345")
                .mdn("01055557777")
                .name("공병일")
                .recentLoginDate(LocalDateTime.now())
                .agreeTermsVersion("T01")
                .memberStatus(MemberStatus.OK)
                .role(Role.USER)
                .build();

//        System.out.println(memberRepository.save(member).getId());

        System.out.println(memberService.createMember(member));

    }

    @Test
    public void selectMemberTest() {
        memberRepository.findById(1L).ifPresent(
                m -> System.out.println(m.getName())
        );

    }


    @Test
    public void selectContents() {
//        contentsRepository.findAll().forEach(System.out::println);

        System.out.println(contentsReviewRepository.findById(3L).get());
        //contentsReviewRepository.findAllByMemberIdOrderByCreateDateDesc(1L).forEach(System.out::println);
    }

//    @Test
//    public void makeFaqData() {
////        FaqCategory faqCategory = FaqCategory.builder()
////                .code("ETC")
////                .name("기타")
////                .build();
////        faqCategoryRepository.save(faqCategory);
//
//        for(int i = 1; i <= 10 ; i++ ) {
//            FaqInfo faqInfo = FaqInfo.builder()
//                    .faqCategory(faqCategoryRepository.findById(5L).get())
//                    .question(i +". 기타 문의 입니다??")
//                    .answer(i +".  기타 답변입니다.")
//                    .build();
//            faqInfoRepository.save(faqInfo);
//        }
//    }
//
//    @Autowired
//    private FaqService faqService;
//
//    @Test
//    public void testFaqList() {
//
//        List<FaqInfo> faqInfo = faqService.getFaqInfo(0, null);
//        System.out.println(faqInfo);
//    }

}
