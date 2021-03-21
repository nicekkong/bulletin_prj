package com.cplanet.toring.domain.security;

import com.cplanet.toring.domain.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MemberPrincipal implements UserDetails {

    private final Member member;

    private List<? extends  GrantedAuthority> authorities;

    public MemberPrincipal(Member member) {
        this.member = member;
    }

    public static MemberPrincipal create(Member member) {
        return new MemberPrincipal(member);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Extract list of permissions (name)
        this.member.getPermissionList().forEach(p -> {
            GrantedAuthority authority = new SimpleGrantedAuthority(p);
            authorities.add(authority);
        });

        // Extract list of roles (ROLE_name)
        GrantedAuthority authority = new SimpleGrantedAuthority(this.member.getRole().name());
        authorities.add(authority);


        return authorities;
    }

    public Member getMember() {
        this.member.setPassword(null);
        return this.member;
    }

    public Long getId() {return this.member.getId();}

    @Override
    public String getPassword() {
        return this.member.getPassword();
    }

    @Override
    public String getUsername() {
        return this.member.getEmail();
    }

    public String getName() {return this.member.getName();}

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
