package com.abc12366.bangbang.service;



import com.abc12366.bangbang.model.question.QuestionLog;
import com.abc12366.bangbang.model.question.bo.QuestionBo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface QueLogService {

    QuestionLog insert(QuestionLog questionLog);

}
