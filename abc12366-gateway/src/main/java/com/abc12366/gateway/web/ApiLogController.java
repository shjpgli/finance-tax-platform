package com.abc12366.gateway.web;

import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.abc12366.gateway.model.ApiLog;
import com.abc12366.gateway.service.ApiLogService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-28 3:51 PM
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/log", headers = Constant.VERSION_HEAD + "=1")
public class ApiLogController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private ApiLogService apiLogService;

    @GetMapping
    public ResponseEntity selectList(){
        List<ApiLog> dataList = apiLogService.selectList();
        LOGGER.info("{}", dataList);
        return ResponseEntity.ok(dataList);
    }

    @GetMapping("/page")
    public ResponseEntity selectList(@RequestParam(value = "uri", required = false) String uri,
                                     @RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize) {
        ApiLog apiLog = new ApiLog.Builder()
                .uri(uri)
                .build();
        // 分页插件的用法：加入下面一行代码之后，插件会将最近的select语句分页；下面的代码可以放在Controller或Service中.
        // 当Service中有多条select语句时，建议放在Service中，这时需要将Page对象传递到Service实现方法，返回对象也是Page对象。
        // 将List对象强制转成Page可以获取Page的相关属性。如：((Page)dataList).getTotal()，总记录数统一使用total返回。
        // 代码解释：
        // count=true(第一个),默认值为false，是查询总记录数
        // pageSizeZero=true,默认值为 false，当该参数设置为 true 时，如果 pageSize=0 或者 pageNum = 0 就会查询出全部的结果（相当于没有执行分页查询，但是返回结果仍然是 Page 类型）
        // reasonable=true,分页合理化参数，默认值为false。当该参数设置为 true 时，pageNum<=0 时会查询第一页， pageNum>pages（超过总数时），会查询最后一页
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<ApiLog> dataList = apiLogService.selectList(apiLog);
        LOGGER.info("{}", dataList);
        return ResponseEntity.ok(Utils.kv("dataList", (Page)dataList, "total", ((Page)dataList).getTotal()));
    }
}
