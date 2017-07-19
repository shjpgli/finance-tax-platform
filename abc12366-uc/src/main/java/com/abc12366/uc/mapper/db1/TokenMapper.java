package com.abc12366.uc.mapper.db1;


import com.abc12366.uc.model.Token;

/**
 * Created by lgy on 2017-05-05.
 */
public interface TokenMapper {
    int insert(Token token);

    int update(Token token);

    int delete(String token);
}
