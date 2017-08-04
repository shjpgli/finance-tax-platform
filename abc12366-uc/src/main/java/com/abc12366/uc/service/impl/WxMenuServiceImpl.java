package com.abc12366.uc.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abc12366.uc.config.Scheduler;
import com.abc12366.uc.mapper.db1.WxMenuMapper;
import com.abc12366.uc.mapper.db2.WxMenuRoMapper;
import com.abc12366.uc.model.weixin.BaseWxRespon;
import com.abc12366.uc.model.weixin.bo.menu.Button;
import com.abc12366.uc.model.weixin.bo.menu.Menu;
import com.abc12366.uc.service.IWxMenuService;
import com.abc12366.uc.util.wx.WechatUrl;
import com.abc12366.uc.util.wx.WxConnectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Utils;

@Service
public class WxMenuServiceImpl implements IWxMenuService {
	@Autowired
	private WxMenuMapper wxMenuMapper;
	@Autowired
	private WxMenuRoMapper wxMenuRoMapper;
	private static final Logger LOGGER = LoggerFactory.getLogger(WxMenuServiceImpl.class);
	
	
	

	@Override
	public BaseWxRespon creatWxMenu(Menu menu) {
		 Map<String,String> tks=new HashMap<String, String>();
  	     tks.put("access_token", Scheduler.token.getAccess_token());
    	 BaseWxRespon respon= WxConnectFactory.post(WechatUrl.WXMENUCREATE, tks, menu, BaseWxRespon.class);
		 return respon;
	}

	@Override
	public Menu getWxMenu() {
		Map<String,String> tks=new HashMap<String, String>();
   	    tks.put("access_token", Scheduler.token.getAccess_token());
   	    return WxConnectFactory.get(WechatUrl.WXMENUQUERY,tks,null,Menu.class);
	}

	@Override
	public BaseWxRespon delWxMenu() {
		Map<String,String> tks1=new HashMap<String, String>();
   	    tks1.put("access_token", Scheduler.token.getAccess_token());
   	    BaseWxRespon respon=WxConnectFactory.get(WechatUrl.WXMENUDEL,tks1,null,BaseWxRespon.class);
   	    return respon;
	}

	@Override
	public Menu getWxMenuDb() {
		Menu menu=new Menu();
		List<Button> buttons=wxMenuRoMapper.seletFisrt();
		if(buttons!=null && buttons.size()>0){
			Button[] first=new  Button[buttons.size()];
			for(int i=0;i<buttons.size();i++){
				List<Button> secbuttons=wxMenuRoMapper.seletSec(buttons.get(i).getId());
				if(secbuttons!=null&&secbuttons.size()>0){
					Button[] sec=new  Button[secbuttons.size()];
					for(int j=0;j<secbuttons.size();j++){
						sec[j]=secbuttons.get(j);
					}
					buttons.get(i).setSub_button(sec);
				}
				first[i]=buttons.get(i);
			}
			menu.setButton(first);
		}
		
		return menu;
	}

	@Override
	public Button selectOne(String id) {
		Button button = new Button();
        try {
            LOGGER.info("查询单个微信菜单信息:{}", id);
            button = wxMenuRoMapper.selectOne(id);
            List<Button> secbuttons=wxMenuRoMapper.seletSec(button.getId());
            if(secbuttons!=null&&secbuttons.size()>0){
				Button[] sec=new  Button[secbuttons.size()];
				for(int j=0;j<secbuttons.size();j++){
					sec[j]=secbuttons.get(j);
				}
				button.setSub_button(sec);
			}
            
        } catch (Exception e) {
            LOGGER.error("查询单个微信菜单信息异常：{}", e);
            throw new ServiceException(4234);
        }
        return button;
	}

	@Override
	@Transactional("db1TxManager")
	public Button insert(Button button) {
		Timestamp now = new Timestamp(new Date().getTime());
		button.setId(Utils.uuid());
		button.setCreateTime(now);
		button.setLastUpdate(now);

		wxMenuMapper.insert(button);
        return button;
	}

	@Override
	@Transactional("db1TxManager")
	public Button update(Button button) {
		Timestamp now = new Timestamp(new Date().getTime());
		button.setLastUpdate(now);
        int update = wxMenuMapper.update(button);
        if(update != 1){
            if (update != 1){
                LOGGER.info("{修改微信菜单失败}", update);
                throw new ServiceException(4421);
            }
        }
        return wxMenuRoMapper.selectOne(button.getId());
	}

	@Override
	@Transactional("db1TxManager")
	public void delete(String id) {
		Button v = selectOne(id);
        if (v != null) {
            // 删除单个微信菜单
        	wxMenuMapper.deleteByPrimaryKey(id);
        } else {
            throw new ServiceException(4012);
        }
	}

}
