package com.cplanet.toring.repository;

import com.cplanet.toring.domain.*;
import com.cplanet.toring.domain.enums.ContentsStatus;
import com.cplanet.toring.dto.TestDataRequestDto;
import com.cplanet.toring.dto.request.DecisionMainRequestDto;
import com.cplanet.toring.dto.response.DecisionMainResponseDto;
import com.cplanet.toring.mapper.TestDataMapper;
import com.cplanet.toring.service.DecisionBoardService;
import com.cplanet.toring.service.TestDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoryServiceTest {

    @Autowired
    TestDataService testDataService;


    @Autowired
    private TestDataRepository testDataRepository;

    @Autowired
    private DecisionBoardService decisionBoardService;

    @Autowired
    private DecisionBoardRepository decisionBoardRepository;

    @Autowired
    private DecisionChoiceRepository decisionChoiceRepository;

    @Autowired
    private MemberRepository memberRepository;



    @Test
    public void testSave() {


        TestDataRequestDto dto = TestDataRequestDto.builder()
                .intData(99)
                .stringData("nicekkong@gmail.com")
                .build();
        System.out.println(testDataService.save(dto));
    }

    @Test
    public void testJpql() {
        testDataRepository.findBySearch("nicek").forEach(
                l -> System.out.println(l.getStringData())
        );
    }

    @Test
    public void testFindAll() {

        testDataRepository.findBySearch("nicekkong@gmail.com").forEach(
                l -> System.out.println(l.getCreateDate())
        );
    }

    @Autowired
    TestDataMapper testDataMapper;

    @Test
    public void testyMybatis() {

        TestData testData = testDataMapper.selectTestDataById(3l);
        System.out.println(testData.getStringData());


        List<String> testDatas = testDataMapper.selectByStrSearch("nice");
        testDatas.forEach(System.out::println);

    }

    @Test
    public void testDBoard() {
        DecisionMainResponseDto decisionBoardMain = decisionBoardService.getDecisionBoardMain(0);
        System.out.println("SIZE() " + decisionBoardMain.getDecisionMains().size());
        decisionBoardMain.getDecisionMains().forEach(d -> {
            System.out.println(d.getTitle());
            d.getOptionText().forEach(System.out::println);
        });


        System.out.println(decisionBoardMain.getHasNext());



    }

    @Test
    public void insertDB() {

        DecisionBoard db = DecisionBoard.builder()
                .memberId(1L)
                .title("TEST DCS BOARD5555")
                .contents("asdfasdfsad")
                .build();


        DecisionChoice decisionChoice1 = DecisionChoice.builder()
                .optionText("AAAAAA")
//                .decisionBoard(db)
                .build();
//        decisionChoice1.setDecisionBoard(db);

//        decisionChoiceRepository.save(decisionChoice1);

        DecisionChoice decisionChoice2 = DecisionChoice.builder()
                .optionText("BBBB")
//                .decisionBoard(db)
                .build();
//        decisionChoice2.setDecisionBoard(db);
//        decisionChoiceRepository.save(decisionChoice2);

        db.addDecisionChoice(decisionChoice1);
        db.addDecisionChoice(decisionChoice2);

        decisionBoardRepository.save(db);

    }

    @Test
    public void createDecisionBoardTest() {

        DecisionMainRequestDto dto = DecisionMainRequestDto.builder()
                .title("결정장애 정해주세요.")
                .contents("자세한 내용 입니다. ")
                .memberId(1L)
                .optionText(Arrays.asList("11111", "22222"))
                .build();

//        decisionBoardService.createDecisionBoard(dto);

    }

    @Test
    public void testBoardDetail() {
        DecisionBoard db = decisionBoardRepository.findByIdAndAndDisplayStatus_Ok(10l).orElse(null);
        System.out.println(db.getTitle());
        db.getDecisionChoices().forEach(System.out::println);

    }

    @Test
    public void testSaveReply() {

        Member member = memberRepository.findById(2L).orElse(null);

//        decisionBoardService.createDecisionReply(9L,"이건 뭐지??", member);
    }

    @Test
    public void testReplies() {

        decisionBoardService.getDecisionBoardReplies(9L).forEach(System.out::println);

    }

    @Test
    public void testCount() {


        int i = decisionBoardRepository.countByMemberIdAndDisplayStatus(1L, ContentsStatus.OK);
        decisionBoardRepository.countByMemberIdAndDisplayStatus(1L, ContentsStatus.OK);
        decisionBoardRepository.countByMemberIdAndDisplayStatus(1L, ContentsStatus.OK);
        System.out.println(i);

    }

    @Autowired
    private MyMentorRepository myMentorRepository;

    @Test
    public void testMentor() {



//         System.out.println(myMentorRepository.findByMemberIdOrderByCreateDateDesc(1L, PageRequest.of(0, 2)));

//        List<MyMentor> myMentor = myMentorRepository.findByMemberIdAndStatusOrderByCreateDateDesc(1L, PageRequest.of(0, 2)).getContent();
//        List<MyMentor> myMentor = myMentorRepository.findByMemberIdAndStatusOrderByCreateDateDesc(1L);
//        System.out.println("Query Done~!!!!!!!!!");

//
//        myMentor.forEach(m -> {
//            System.out.println(m.getProfile().getNickname());
//            m.getContents().forEach(c -> {
//                System.out.println(c.getThumbImage());
//            });
//        });



    }

    @Test
    public void testProfile() {

        Member member = memberRepository.findByIdWithProfile(1L).get();
        System.out.println(member);
    }

    @Test
    public void testMyMentor() {
//        myMentorRepository.findByMemberIdAndMemberOrderByCreateDateDesc(1L,  PageRequest.of(0 ,2));

//       List<MyMentor> profile =  myMentorRepository.findByMemberIdAndProfile_Member_MemberStatusOkOrderByCreateDateDesc(1L);

//        List<MyMentor> profile = myMentorRepository.findByMentorListByMemberId(1L);
        Page<MyMentor> profile = myMentorRepository.findByMentorByMemberId(1L, PageRequest.of(0, 2));

        System.out.println(profile);

    }

    @Autowired
    private ContentsRepository contentsRepository;

    @Test
    public void testContents() {

        System.out.println(contentsRepository.findTop3ByMemberIdOrderByCreateDateDesc(71L));

    }

    @Autowired
    private FaqCategoryRepository faqCategoryRepository;

    @Test
    public void testCategory() {

        Page<FaqCategory> all = faqCategoryRepository.findAll(PageRequest.of(0, 5));
        System.out.println(all);


    }

    @Test
//    @Transactional (LAZY Exception이 발생되지 않게 하려면 동일 Transaction으로 묶어 줘야 한다.)
    public void testRepo() {

        DecisionBoard board = decisionBoardRepository.findById(10L).get();

        board.getDecisionChoices().stream().map(
                c -> {
                    System.out.println(c.getOptionText());
                    return c;
                }
        ).collect(Collectors.toList());

    }



}
