package com.metro.persistence.member;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metro.domain.member.Member;

public interface MemberRepository extends JpaRepository<Member, String> {

}
