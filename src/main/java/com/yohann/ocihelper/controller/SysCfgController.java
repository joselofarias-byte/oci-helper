package com.yohann.ocihelper.controller;

import com.yohann.ocihelper.bean.ResponseData;
import com.yohann.ocihelper.bean.params.sys.*;
import com.yohann.ocihelper.bean.response.sys.GetGlanceRsp;
import com.yohann.ocihelper.bean.response.sys.GetSysCfgRsp;
import com.yohann.ocihelper.bean.response.sys.LoginRsp;
import com.yohann.ocihelper.service.ISysService;
import com.yohann.ocihelper.utils.CommonUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

/**
 * @projectName: oci-helper
 * @package: com.yohann.ocihelper.controller
 * @className: SysCfgController
 * @author: Yohann
 * @date: 2024/11/30 17:07
 */
@RestController
@RequestMapping(path = "/api/sys")
public class SysCfgController {

    @Resource
    private ISysService sysService;

    @PostMapping(path = "/login")
    public ResponseData<LoginRsp> addCfg(@Validated @RequestBody LoginParams params) {
        return ResponseData.successData(sysService.login(params), "Sesión iniciada");
    }

    @PostMapping(path = "/updateVersion")
    public ResponseData<Void> updateVersion() {
        sysService.updateVersion();
        return ResponseData.successData("Actualización solicitada; actualiza la página más tarde");
    }

    @PostMapping(path = "/getEnableMfa")
    public ResponseData<Boolean> getEnableMfa() {
        return ResponseData.successData(sysService.getEnableMfa(), "Estado de MFA obtenido");
    }

    @PostMapping(path = "/getSysCfg")
    public ResponseData<GetSysCfgRsp> getSysCfg() {
        return ResponseData.successData(sysService.getSysCfg(), "Ajustes obtenidos");
    }

    @PostMapping(path = "/updateSysCfg")
    public ResponseData<Void> updateSysCfg(@Validated @RequestBody UpdateSysCfgParams params) {
        sysService.updateSysCfg(params);
        return ResponseData.successData("Ajustes actualizados");
    }

    @PostMapping(path = "/sendMsg")
    public ResponseData<Void> sendMsg(@Validated @RequestBody SendMsgParams params) {
        sysService.sendMessage(params.getMessage());
        return ResponseData.successData("Mensaje enviado");
    }

    @PostMapping(path = "/checkMfaCode")
    public ResponseData<Void> checkMfaCode(@Validated @RequestBody CheckMfaCodeParams params) {
        sysService.checkMfaCode(params.getMfaCode());
        return ResponseData.successData("MFA verificado");
    }

    @PostMapping(path = "/backup")
    public void backup(@Validated @RequestBody BackupParams params) {
        sysService.backup(params);
    }

    @PostMapping(path = "/recover")
    public ResponseData<Void> recover(@Validated RecoverParams params) {
        sysService.recover(params);
        return ResponseData.successData("Datos restaurados");
    }

    @GetMapping(path = "/glance")
    public ResponseData<GetGlanceRsp> glance() {
        return ResponseData.successData(sysService.glance(), "Datos del panel obtenidos");
    }

    @PostMapping(path = "/googleLogin")
    public ResponseData<LoginRsp> googleLogin(@Validated @RequestBody GoogleLoginParams params) {
        return ResponseData.successData(sysService.googleLogin(params), "Sesión con Google iniciada");
    }

    @PostMapping(path = "/getGoogleClientId")
    public ResponseData<String> getGoogleClientId() {
        return ResponseData.successData(sysService.getGoogleClientId(), "ID de cliente Google obtenido");
    }
}
