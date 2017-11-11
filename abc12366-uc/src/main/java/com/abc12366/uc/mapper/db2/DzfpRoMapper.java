package com.abc12366.uc.mapper.db2;

import java.util.List;

import com.abc12366.uc.model.dzfp.Einvocie;

public interface DzfpRoMapper {

	Einvocie selectOne(String fPQQLSH);

	List<Einvocie> selectList(Einvocie einvocie);

}
