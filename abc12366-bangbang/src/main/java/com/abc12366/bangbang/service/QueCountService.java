package com.abc12366.bangbang.service;



import com.abc12366.bangbang.model.question.bo.QuestionCountBo;
import com.abc12366.bangbang.model.question.bo.QuestionLikeBo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface QueCountService {
    List<QuestionCountBo> selectLike();
    List<QuestionCountBo> selectAttention();

    List<QuestionCountBo> selectAccept();

}
