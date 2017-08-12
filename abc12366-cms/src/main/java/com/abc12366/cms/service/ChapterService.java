package com.abc12366.cms.service;


import com.abc12366.cms.model.curriculum.bo.CurriculumBo;
import com.abc12366.cms.model.curriculum.bo.CurriculumChapterBo;
import com.abc12366.cms.model.curriculum.bo.CurriculumListBo;

import java.util.List;
import java.util.Map;

public interface ChapterService {

    List<CurriculumChapterBo> selectList(Map<String, Object> map);

    CurriculumChapterBo save(CurriculumChapterBo chapterBo);

    CurriculumChapterBo selectChapter(String chapterId);

    CurriculumChapterBo update(CurriculumChapterBo chapterBo);

    String updateStatus(String chapterId, String status);

    String delete(String chapterId);

}
