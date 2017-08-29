package com.abc12366.uc.service;

import com.abc12366.uc.model.weixin.bo.qrcode.Qrcode;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-08-29 10:51 AM
 * @since 1.0.0
 */
public interface IWxQrcodeService {
    List<Qrcode> selectList(Qrcode qrcode, int page, int size);

    Qrcode selectOne(String id);

    Qrcode insert(Qrcode qrcode) throws UnsupportedEncodingException;

    Qrcode update(Qrcode qrcode) throws UnsupportedEncodingException;

    void delete(String id);
}
