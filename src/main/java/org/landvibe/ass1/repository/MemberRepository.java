package org.landvibe.ass1.repository;

import org.landvibe.ass1.Member;

public interface MemberRepository {

    Member findById(int id);

}
