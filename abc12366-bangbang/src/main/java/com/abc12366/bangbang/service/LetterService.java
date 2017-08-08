package com.abc12366.bangbang.service;

import com.abc12366.bangbang.model.bo.LetterBO;
import com.abc12366.bangbang.model.bo.LetterInsertBO;
import com.abc12366.bangbang.model.bo.LetterListBO;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-07-24
 * Time: 17:40
 */
public interface LetterService {
    LetterBO send(String fromId, String toId, LetterInsertBO letterInsertBO);

    LetterListBO selectList(HttpServletRequest request) throws IOException;

    void read(String id);

    void delete(String id);
}
