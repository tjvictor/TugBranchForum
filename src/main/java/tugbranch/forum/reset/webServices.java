package tugbranch.forum.reset;

import tugbranch.forum.dao.StaffDao;
import tugbranch.forum.dao.TopicCategoryDao;
import tugbranch.forum.dao.TopicDao;
import tugbranch.forum.model.FileUploadEntity;
import tugbranch.forum.model.ReplyTopic;
import tugbranch.forum.model.ResponseObject;
import tugbranch.forum.model.Staff;
import tugbranch.forum.model.Topic;
import tugbranch.forum.model.TopicCategory;
import tugbranch.forum.utils.CommonUtils;
import tugbranch.forum.utils.OpsLogTypeEnum;
import tugbranch.forum.utils.OpsLogUtils;
import tugbranch.forum.utils.TopicStatusEnum;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/websiteService")
public class webServices {
    //region private
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${file.MappingPath}")
    private String fileMappingPath;

    @Value("${file.MappingUrl}")
    private String fileMappingUrl;

    @Value("${image.MappingPath}")
    private String imageMappingPath;

    @Value("${image.MappingUrl}")
    private String imageMappingUrl;

    @Value("${banner.MappingPath}")
    private String bannerMappingPath;

    @Value("${banner.MappingUrl}")
    private String bannerMappingUrl;

    @Value("${audio.MappingPath}")
    private String audioMappingPath;

    @Value("${audio.MappingUrl}")
    private String audioMappingUrl;

    @Value("${video.MappingPath}")
    private String videoMappingPath;

    @Value("${video.MappingUrl}")
    private String videoMappingUrl;

    @Autowired
    private StaffDao staffDaoImp;

    @Autowired
    private OpsLogUtils opsLog;

    @Autowired
    private TopicCategoryDao topicCategoryDaoImp;

    @Autowired
    private TopicDao topicDaoImp;

    //region file upload
    @PostMapping("/fileUpload/{requestFileName}/{requestFileType}")
    public ResponseObject uploadAvatar(@PathVariable String requestFileName, @PathVariable String requestFileType,
                                       HttpServletRequest request, HttpServletResponse response) {
        StandardMultipartHttpServletRequest fileRequest = (StandardMultipartHttpServletRequest) request;
        if (fileRequest == null) {
            return new ResponseObject("error", "系统错误，请联系系统管理员");
        }

        MultipartFile sourceFile = fileRequest.getFile(requestFileName);
        String savePath = imageMappingPath;
        String saveUrl = request.getContextPath() + imageMappingUrl;
        switch (requestFileType) {
            case "file":
                savePath = fileMappingPath;
                saveUrl = request.getContextPath() + fileMappingUrl;
                break;
            case "image":
                savePath = imageMappingPath;
                saveUrl = request.getContextPath() + imageMappingUrl;
                break;
            case "audio":
                savePath = audioMappingPath;
                saveUrl = request.getContextPath() + audioMappingUrl;
                break;
            case "video":
                savePath = videoMappingPath;
                saveUrl = request.getContextPath() + videoMappingUrl;
                break;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String originalFileName = sourceFile.getOriginalFilename();
        String randomString = String.format("%s-%s", sdf.format(new Date()), originalFileName);
        String randomFileName = savePath + randomString;
        String randomFileUrl = saveUrl + randomString;
        File targetFile = new File(randomFileName);
        try (OutputStream f = new FileOutputStream(targetFile)) {
            f.write(sourceFile.getBytes());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return new ResponseObject("error", "系统错误，请联系系统管理员");
        }

        FileUploadEntity fue = new FileUploadEntity();
        fue.setFileName(originalFileName);
        fue.setFileUrl(randomFileUrl);
        fue.setFileType(requestFileType);

        return new ResponseObject("ok", "上传成功", fue);
    }
    //endregion

    //region pagination

    //endregion

    //region login
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseObject login(@RequestParam(value = "sid") String sid,
                                @RequestParam(value = "password") String password) {

        try {
            Staff item = staffDaoImp.login(sid, password);
            if (StringUtils.isNotEmpty(item.getSid())) {
                opsLog.log(sid, OpsLogTypeEnum.Login_out, "登录");
                return new ResponseObject("ok", "登录成功", item);
            }
            else
                return new ResponseObject("error", "用户不存在或密码错误!", "");
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return new ResponseObject("error", "系统错误，请联系系统管理员");
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public void logout(@RequestParam("usid") String usid) {
        try {
            opsLog.log(usid, OpsLogTypeEnum.Login_out, "退出");
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/resetPwd", method = RequestMethod.GET)
    public ResponseObject resetPwd(@RequestParam(value = "id") String id,
                                   @RequestParam(value = "oldPwd") String oldPwd,
                                   @RequestParam(value = "newPwd") String newPwd,
                                   @RequestParam(value = "usid") String usid) {

        try {
            Staff item = staffDaoImp.getStaffById(id);
            if(oldPwd.equals(item.getPassword())){
                item.setPassword(newPwd);
                staffDaoImp.updateStaff(item);
                opsLog.log(usid, OpsLogTypeEnum.ResetPassword, String.format("旧密码: %s, 新密码: %s", oldPwd, newPwd));
                return new ResponseObject("ok", "密码修改成功", null);
            }else{
                return new ResponseObject("error", "原密码不正确", null);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return new ResponseObject("error", "系统错误，请联系系统管理员");
        }
    }
    //endregion

    //region User
    @RequestMapping(value = "/getUserById", method = RequestMethod.GET)
    public ResponseObject getUserById(@RequestParam(value = "userId") String id) {

        try {
            Staff item = staffDaoImp.getStaffById(id);
            return new ResponseObject("ok", "查询成功", item);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return new ResponseObject("error", "系统错误，请联系系统管理员");
        }
    }
    //endregion

    //region Topic
    @RequestMapping(value = "/getTopicCategory", method = RequestMethod.GET)
    public ResponseObject getTopicCategory() {
        try {
            List<TopicCategory> items = topicCategoryDaoImp.getTopicCategory();
            return new ResponseObject("ok", "查询成功", items);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return new ResponseObject("error", "系统错误，请联系系统管理员");
        }
    }

    @RequestMapping(value = "/addTopic", method = RequestMethod.POST)
    public ResponseObject addTopic(@RequestParam("title") String title, @RequestParam("content") String content,
                                   @RequestParam("staffId") String staffId, @RequestParam("categoryId") String categoryId) {
        try {

            Topic item = new Topic();
            item.setId(UUID.randomUUID().toString());
            item.setTitle(title);
            item.setContent(content);
            item.setStaffId(staffId);
            item.setViewCount(0);
            item.setReplyCount(0);
            item.setCategoryId(categoryId);
            item.setStatus(TopicStatusEnum.OPEN.getIndex());
            item.setPutTop(false);
            item.setResolved(false);
            item.setEssence(false);
            item.setCreateTime(CommonUtils.getCurrentDateTime());

            topicDaoImp.addTopic(item);
            return new ResponseObject("ok", "插入成功", item.getId());
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return new ResponseObject("error", "系统错误，请联系系统管理员");
        }
    }

    @RequestMapping(value = "/editTopic", method = RequestMethod.POST)
    public ResponseObject editTopic(@RequestParam("title") String title, @RequestParam("content") String content,
                                   @RequestParam("topicId") String topicId, @RequestParam("categoryId") String categoryId) {
        try {

            Topic item = topicDaoImp.getTopicById(topicId, false);
            item.setTitle(title);
            item.setContent(content);
            item.setCategoryId(categoryId);

            topicDaoImp.editTopic(item);
            return new ResponseObject("ok", "更新成功", item.getId());
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return new ResponseObject("error", "系统错误，请联系系统管理员");
        }
    }

    @RequestMapping(value = "/addReplyTopic", method = RequestMethod.POST)
    public ResponseObject addReplyTopic(@RequestParam("topicId") String topicId, @RequestParam("content") String content,
                                   @RequestParam("staffId") String staffId, @RequestParam("topicCreateTime") String topicCreateTime) {
        try {

            ReplyTopic item = new ReplyTopic();
            item.setId(UUID.randomUUID().toString());
            item.setTopicId(topicId);
            item.setContent(content);
            item.setStaffId(staffId);
            item.setResolved(false);
            item.setOrders(CommonUtils.getDateDifference(topicCreateTime));
            item.setCreateTime(CommonUtils.getCurrentDateTime());

            topicDaoImp.addReplyTopic(item);
            updateTopicReplyCountById(topicId);
            return new ResponseObject("ok", "插入成功", topicId);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return new ResponseObject("error", "系统错误，请联系系统管理员");
        }
    }

    @RequestMapping(value = "/getTopicById", method = RequestMethod.GET)
    public ResponseObject getTopicById(@RequestParam("topicId") String topicId) {
        try {

            Topic item = topicDaoImp.getTopicById(topicId, false);
            if(StringUtils.isNotEmpty(item.getId())){
                item.setStaff(staffDaoImp.getStaffById(item.getStaffId()));
                item.setCategory(topicCategoryDaoImp.getTopicCategoryById(item.getCategoryId()));
                return new ResponseObject("ok", "查询成功", item);
            }
            return new ResponseObject("ok", "查询成功", null);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return new ResponseObject("error", "系统错误，请联系系统管理员");
        }
    }

    @RequestMapping(value = "/getTopicListByCategory", method = RequestMethod.GET)
    public ResponseObject getTopicListByCategory(@RequestParam("title") String title,
                                                 @RequestParam("categoryId") String categoryId,
                                                 @RequestParam("pageNumber") int pageNumber,
                                                 @RequestParam("pageSize") int pageSize) {
        try {

            List<Topic> items = topicDaoImp.getTopicListByCategory(title, categoryId, pageNumber, pageSize);
            return new ResponseObject("ok", "查询成功", items);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return new ResponseObject("error", "系统错误，请联系系统管理员");
        }
    }

    @RequestMapping(value = "/getTopicCountByCategory", method = RequestMethod.GET)
    public ResponseObject getTopicCountByCategory(@RequestParam("title") String title,
                                                  @RequestParam("categoryId") String categoryId) {
        try {

            int count = topicDaoImp.getTopicCountByCategory(title, categoryId);
            return new ResponseObject("ok", "查询成功", count);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return new ResponseObject("error", "系统错误，请联系系统管理员");
        }
    }

    @RequestMapping(value = "/getReplyTopicsByTopicId", method = RequestMethod.GET)
    public ResponseObject getReplyTopicsByTopicId(@RequestParam("topicId") String topicId,
                                                 @RequestParam("pageNumber") int pageNumber,
                                                 @RequestParam("pageSize") int pageSize) {
        try {

            List<ReplyTopic> items = topicDaoImp.getReplyTopicsByTopicId(topicId, pageNumber, pageSize);
            return new ResponseObject("ok", "查询成功", items);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return new ResponseObject("error", "系统错误，请联系系统管理员");
        }
    }

    @RequestMapping(value = "/getReplyTopicCountByTopicId", method = RequestMethod.GET)
    public ResponseObject getReplyTopicCountByTopicId(@RequestParam("topicId") String topicId) {
        try {

            int count = topicDaoImp.getReplyTopicCountByTopicId(topicId);
            return new ResponseObject("ok", "查询成功", count);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return new ResponseObject("error", "系统错误，请联系系统管理员");
        }
    }


    @RequestMapping(value = "/updateTopicViewCountById", method = RequestMethod.GET)
    public ResponseObject updateTopicViewCountById(@RequestParam("topicId") String topicId) {
        try {

            topicDaoImp.updateTopicViewCountById(topicId);
            return new ResponseObject("ok", "更新成功", null);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return new ResponseObject("error", "系统错误，请联系系统管理员");
        }
    }

    @RequestMapping(value = "/updateTopicReplyCountById", method = RequestMethod.GET)
    public ResponseObject updateTopicReplyCountById(@RequestParam("topicId") String topicId) {
        try {

            topicDaoImp.updateTopicReplyCountById(topicId);
            return new ResponseObject("ok", "更新成功", null);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return new ResponseObject("error", "系统错误，请联系系统管理员");
        }
    }

    @RequestMapping(value = "/updateTopicStatusById", method = RequestMethod.GET)
    public ResponseObject updateTopicStatusById(@RequestParam("topicId") String topicId, @RequestParam("status") int status) {
        try {
            topicDaoImp.updateTopicPropertyById(topicId,"Status", status);
            return new ResponseObject("ok", "更新成功", null);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return new ResponseObject("error", "系统错误，请联系系统管理员");
        }
    }

    @RequestMapping(value = "/updateTopicPutTopById", method = RequestMethod.GET)
    public ResponseObject updateTopicPutTopById(@RequestParam("topicId") String topicId, @RequestParam("putTop") int putTop) {
        try {
            topicDaoImp.updateTopicPropertyById(topicId,"PutTop", putTop);
            return new ResponseObject("ok", "更新成功", null);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return new ResponseObject("error", "系统错误，请联系系统管理员");
        }
    }

    @RequestMapping(value = "/updateTopicResolvedById", method = RequestMethod.GET)
    public ResponseObject updateTopicResolvedById(@RequestParam("topicId") String topicId, @RequestParam("resolved") int resolved) {
        try {
            topicDaoImp.updateTopicPropertyById(topicId,"Resolved", resolved);
            return new ResponseObject("ok", "更新成功", null);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return new ResponseObject("error", "系统错误，请联系系统管理员");
        }
    }

    @RequestMapping(value = "/updateTopicEssenceById", method = RequestMethod.GET)
    public ResponseObject updateTopicEssenceById(@RequestParam("topicId") String topicId, @RequestParam("essence") int essence) {
        try {
            topicDaoImp.updateTopicPropertyById(topicId,"Essence", essence);
            return new ResponseObject("ok", "更新成功", null);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return new ResponseObject("error", "系统错误，请联系系统管理员");
        }
    }

    @RequestMapping(value = "/getPublicTopicsByUserId", method = RequestMethod.GET)
    public ResponseObject getPublicTopicsByUserId(@RequestParam("userId") String userId,
                                                  @RequestParam("pageNumber") int pageNumber,
                                                  @RequestParam("pageSize") int pageSize) {
        try {
            List<Topic> items =  topicDaoImp.getPublicTopicsByUserId(userId,pageNumber, pageSize);
            return new ResponseObject("ok", "查询成功", items);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return new ResponseObject("error", "系统错误，请联系系统管理员");
        }
    }

    @RequestMapping(value = "/getPublicTopicCountByUserId", method = RequestMethod.GET)
    public ResponseObject getPublicTopicCountByUserId(@RequestParam("userId") String userId) {
        try {
            int count =  topicDaoImp.getPublicTopicCountByUserId(userId);
            return new ResponseObject("ok", "查询成功", count);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return new ResponseObject("error", "系统错误，请联系系统管理员");
        }
    }

    @RequestMapping(value = "/getReplyTopicsByUserId", method = RequestMethod.GET)
    public ResponseObject getReplyTopicsByUserId(@RequestParam("userId") String userId,
                                                  @RequestParam("pageNumber") int pageNumber,
                                                  @RequestParam("pageSize") int pageSize) {
        try {
            List<Topic> items =  topicDaoImp.getReplyTopicsByUserId(userId,pageNumber, pageSize);
            return new ResponseObject("ok", "查询成功", items);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return new ResponseObject("error", "系统错误，请联系系统管理员");
        }
    }

    @RequestMapping(value = "/getReplyTopicCountByUserId", method = RequestMethod.GET)
    public ResponseObject getReplyTopicCountByUserId(@RequestParam("userId") String userId) {
        try {
            int count =  topicDaoImp.getReplyTopicCountByUserId(userId);
            return new ResponseObject("ok", "查询成功", count);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return new ResponseObject("error", "系统错误，请联系系统管理员");
        }
    }

    @RequestMapping(value = "/checkUserPermission", method = RequestMethod.GET)
    public ResponseObject checkUserPermission(@RequestParam("topicId") String topicId,
                                              @RequestParam("userId") String userId) {
        try {
            Topic item =  topicDaoImp.checkUserPermission(topicId, userId);
            return new ResponseObject("ok", "查询成功", item);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return new ResponseObject("error", "系统错误，请联系系统管理员");
        }
    }

    //endregion
}
