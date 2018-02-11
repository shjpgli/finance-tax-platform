package com.abc12366.uc.service;

import java.text.ParseException;

import org.exolab.castor.xml.ValidationException;

import com.abc12366.uc.model.HsqjBo;

public interface IHsqjService {

	void register(HsqjBo hsqjBo)  throws ValidationException, ParseException;
     
}
