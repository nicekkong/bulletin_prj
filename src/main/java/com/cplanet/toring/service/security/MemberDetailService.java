package com.cplanet.toring.service.security;

import com.cplanet.toring.domain.Member;
import com.cplanet.toring.domain.security.MemberPrincipal;
import com.cplanet.toring.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class MemberDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public MemberDetailService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
}



    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member member =  memberRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("Member not found ::: email : " + email));

        return MemberPrincipal.create(member);
    }

    @Transactional
    public UserDetails loadUserById(Long memberId) {

//        Member member =  memberRepository.findById(memberId).orElseThrow(() ->
        Member member =  memberRepository.findByIdWithProfile(memberId).orElseThrow(() ->
                new UsernameNotFoundException("Member not found ::: MEMBER_ID : " + memberId));

        return MemberPrincipal.create(member);
    }


}
