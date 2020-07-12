package com.bjtu.bookshop.controller;

import com.bjtu.bookshop.bean.request.PicRequests.*;
import com.bjtu.bookshop.bean.response.CarResponses;
import com.bjtu.bookshop.bean.response.PicResponses.*;
import com.bjtu.bookshop.service.PicService;
import com.bjtu.bookshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pic")
public class PicController {
    private final UserService userService;
    private final PicService picService;

    @Autowired
    public PicController(UserService userService, PicService picService) {
        this.userService = userService;
        this.picService = picService;
    }

    @RequestMapping(value = "/upload", method = {RequestMethod.POST})
    public PicUploadResponse picUpload(PicUploadRequest req, BindingResult br) {
        if (br.hasErrors()) return new PicUploadResponse() {{
            setState(-1);
        }};
        if (!userService.checkUserToken(req)) return new PicUploadResponse() {{
            setState(-10);
        }};

        return picService.picUpload(req.getUid(), req.getPic());
    }

}
