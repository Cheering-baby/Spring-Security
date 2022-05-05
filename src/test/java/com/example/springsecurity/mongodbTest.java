package com.example.springsecurity;

import cn.hutool.core.lang.Assert;
import com.example.springsecurity.nosql.mongodb.document.MemberReadDocument;
import com.example.springsecurity.nosql.mongodb.repository.MemberReadRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Author Ning Meng
 * @Date 2022/4/28 11:26
 **/

@SpringBootTest
public class mongodbTest {

    @Autowired
    private MemberReadRepository memberReadRepository;

    @Test
    void findMemberReadRepositoryByMemberId() {
        List<MemberReadDocument> memberReadRepositoryList = memberReadRepository.findByMemberIdOrderByCreateTimeDesc(2L);
        Assert.notEmpty(memberReadRepositoryList);
    }
}
