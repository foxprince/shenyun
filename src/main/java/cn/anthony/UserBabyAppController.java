package cn.anthony;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.minganmed.toc.entity.po.UserBaby;
import com.minganmed.toc.entity.po.UserBabyExample;
import com.minganmed.toc.entity.po.UserInfo;
import com.minganmed.toc.operation.baby.service.UserBabyService;
import com.minganmed.toc.operation.common.BaseController;
import com.minganmed.toc.operation.common.ConfigSupport;
import com.minganmed.toc.operation.common.FileBucket;
import com.minganmed.toc.operation.common.FileValidator;
import com.minganmed.toc.operation.common.MAYLResult;

@Controller
@RequestMapping("/baby")
public class UserBabyAppController extends BaseController {
	@Resource
	UserBabyService service;
	private static Logger log = Logger.getLogger(UserBabyAppController.class);
	// UserBaby反序列化时的过滤器
	private static SimplePropertyPreFilter filter = new SimplePropertyPreFilter(UserBaby.class);
	// 文件上传路径，应该从配置文件读出
	// private static String BASE_URL =
	// ResourceBundle.getBundle("upload").getString("ma.baby.headimgdir");
	private static String BASE_URL = ConfigSupport.getConfigValue("ma.baby.headimgdir");
	private static String TMP_DIR = "/tmp/";
	private static String TMP_UPLOAD_LOCATION = BASE_URL + TMP_DIR;
	static Date date = new Date();
	static SimpleDateFormat sdf = new SimpleDateFormat("YYYYMM");
	static String dateStr = sdf.format(date);
	private static String UPLOAD_LOCATION = BASE_URL + "/babyicon/" + "/" + dateStr + "/";
	@Autowired
	FileValidator fileValidator;

	/**
	 * 设置文件上传绑定规则
	 * 
	 * @param binder
	 */
	@InitBinder("fileBucket")
	protected void initBinderFileBucket(WebDataBinder binder) {
		binder.setValidator(fileValidator);
	}

	static {// 添加不需要序列化为json的字段
		filter.getExcludes().add("userId");
		filter.getExcludes().add("name");
		filter.getExcludes().add("createTime");
	}

	private Long getUserId() {
		// return 123l;
		UserInfo u = getUserInfo();
		if (u == null) {
			throw new RuntimeException("登录状态已失效，请重新登录");
		}
		return u.getId();
	}

	/**
	 * 宝宝列表
	 * 
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public String list() {
		try {
			// UserInfo userInfo = getUserInfo();
			if (getUserId() != null) {// 测试使用
				// if (getUserInfo() != null) {
				UserBabyExample example = new UserBabyExample();
				example.createCriteria().andUserIdEqualTo(getUserId());
				return JSON.toJSONString(MAYLResult.ok(service.selectByExample(example)), filter);
			} else {
				return JSON.toJSONString(MAYLResult.build(false, "未登录！"));
			}
		} catch (Exception e) {
			log.error("获得" + getUserId() + "的宝宝列表出错，异常为：" + e.getMessage());
			return JSON.toJSONString(MAYLResult.build(false, "不能获得宝宝列表数据"));
		}
	}

	/**
	 * 添加宝宝
	 * 
	 * @param po
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public String add(@RequestBody UserBaby po) {
		log.info("执行宝宝添加方法！");
		try {
			// 确保id为空，即使客户端传过来也要置空
			po.setId(null);
			po.setUserId(getUserId());
			if (service.insertSelective(po) < 1)
				throw new Exception("数据库添加失败");
			// 把宝宝头像从临时目录移到正式目录
			moveIcon(po.getIcon());
			return JSON.toJSONString(MAYLResult.ok(po), filter);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("添加用户" + getUserId() + "的宝宝出错，异常为：" + e.getMessage());
			return JSON.toJSONString(MAYLResult.build(false, "添加宝宝出错"));
		}
	}

	private void moveIcon(String iconPath) throws IOException {
		File src = new File(TMP_UPLOAD_LOCATION + iconPath);
		if (src.exists()) {
			FileUtils.moveFile(src, new File(UPLOAD_LOCATION + iconPath));
		}
	}

	/**
	 * 修改宝宝信息
	 * 
	 * @param po
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public String update(@RequestBody UserBaby po) {
		try {
			if (service.updateByPrimaryKeySelective(po) < 1)
				throw new Exception("修改数据库失败");
			// 如果用户重新上传了icon，则替换原有文件
			if (!StringUtils.isBlank(po.getIcon()))
				moveIcon(po.getIcon());
			return JSON.toJSONString(MAYLResult.ok(po), filter);
		} catch (Exception e) {
			log.error("修改" + getUserId() + "的宝宝出错，异常为：" + e.getMessage());
			return JSON.toJSONString(MAYLResult.build(false, "修改宝宝出错"));
		}
	}

	/**
	 * 上传宝宝头像， 上传后，头像暂存在临时目录。提交宝宝信息后，文件移到正式目录，文件地址入库。
	 * 
	 * @param fileBucket
	 * @param result
	 * @return 返回头像地址
	 * @throws IOException
	 */
	@RequestMapping(value = { "/uploadIcon", "/updateIcon" }, method = RequestMethod.POST)
	@ResponseBody
	public MAYLResult uploadIcon(@Valid FileBucket fileBucket, BindingResult result) throws IOException {
		if (result.hasErrors()) {
			log.error("文件上传验证失败");
			return MAYLResult.build(false, "添加宝宝头像失败，请检查文件格式或尺寸");
		} else {
			MultipartFile multipartFile = fileBucket.getFile();
			// 保存文件到临时目录
			// 文件需要重新取名
			String fileName = createFileName(multipartFile.getOriginalFilename());
			FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), new File(TMP_UPLOAD_LOCATION + fileName));
			Map<String, Object> map = new HashMap<>();
			map.put("iconTmpPath", TMP_DIR + fileName);
			return MAYLResult.ok(org.apache.commons.lang3.StringUtils.replace(TMP_DIR + fileName, "\\", "/"));

		}
	}

	/**
	 * 删除宝宝
	 * 
	 * @param fileBucket
	 * @param result
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public MAYLResult delete(Long id) {
		try {
			File icon = new File(UPLOAD_LOCATION + service.selectByPrimaryKey(id).getIcon());
			if (service.deleteByPrimaryKey(id) > 0) {
				// 删除头像
				try {
					FileUtils.forceDelete(icon);
				} catch (IOException e) {
					log.error("删除" + getUserId() + "宝宝的头像" + icon.getAbsolutePath() + "出错，异常为：" + e.getMessage());
				}
				return MAYLResult.ok();
			} else
				throw new Exception("删除成功纪录为0");
		} catch (Exception e) {
			log.error("删除" + getUserId() + "宝宝出错，异常为：" + e.getMessage());
			return MAYLResult.build(false, "删除宝宝出错");
		}
	}

	@RequestMapping(value = "find")
	@ResponseBody
	public String find(Long id) {
		try {
			UserBaby baby = service.selectByPrimaryKey(id);
			if (baby != null)
				return JSON.toJSONString(MAYLResult.ok(baby), filter);
			else
				throw new Exception("找不到宝宝信息，id：" + id);
		} catch (Exception e) {
			log.error("查找" + getUserId() + "宝宝出错，异常为：" + e.getMessage());
			return JSON.toJSONString(MAYLResult.build(false, "查找宝宝出错"));
		}
	}

	// yyyyMM/userid_{uuid}.jpg
	private String createFileName(String originalName) {
		return DateFormatUtils.format(Calendar.getInstance(), "yyyyMM") + System.getProperty("file.separator")
				+ getUserId() + "_" + UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(originalName);
	}
}
