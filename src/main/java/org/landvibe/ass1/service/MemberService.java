package org.landvibe.ass1.service;

import lombok.RequiredArgsConstructor;
import org.landvibe.ass1.Member;
import org.landvibe.ass1.annotation.CacheoutLandvibe;
import org.landvibe.ass1.annotation.CachingLandvibe;
import org.landvibe.ass1.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository jdbcMemberRepository;


    @CachingLandvibe(key = "member:{0}", cacheManager = "redisCacheManager")
    public String get(int id) {
        Member member = jdbcMemberRepository.findById(id);
        return member.getId() + " : " + member.getName();
    }

    @CacheoutLandvibe(key = "member:{0}", cacheManager = "redisCacheManager")
    public void deleteMemberCache(int id) {
    }
}
