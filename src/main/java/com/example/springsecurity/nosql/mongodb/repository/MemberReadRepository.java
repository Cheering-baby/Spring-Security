package com.example.springsecurity.nosql.mongodb.repository;

import com.example.springsecurity.nosql.mongodb.document.MemberReadDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Ning Meng
 * @Date 2022/4/28 10:50
 **/

public interface MemberReadRepository extends MongoRepository<MemberReadDocument, String> {
    List<MemberReadDocument> findByMemberIdOrderByCreateTimeDesc(Long memberId);
}
