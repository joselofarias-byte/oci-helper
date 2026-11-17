package com.yohann.ocihelper.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yohann.ocihelper.bean.ResponseData;
import com.yohann.ocihelper.bean.dto.InstanceCfgDTO;
import com.yohann.ocihelper.bean.params.*;
import com.yohann.ocihelper.bean.params.oci.cfg.*;
import com.yohann.ocihelper.bean.params.oci.instance.*;
import com.yohann.ocihelper.bean.params.oci.securityrule.ReleaseSecurityRuleParams;
import com.yohann.ocihelper.bean.params.oci.task.CreateTaskPageParams;
import com.yohann.ocihelper.bean.params.oci.task.PauseCreateParams;
import com.yohann.ocihelper.bean.params.oci.task.StopChangeIpParams;
import com.yohann.ocihelper.bean.params.oci.task.StopCreateParams;
import com.yohann.ocihelper.bean.params.oci.task.UpdateCreateTaskBatchParams;
import com.yohann.ocihelper.bean.params.oci.task.UpdateCreateTaskParams;
import com.yohann.ocihelper.bean.params.oci.volume.UpdateBootVolumeCfgParams;
import com.yohann.ocihelper.bean.response.oci.task.CreateTaskRsp;
import com.yohann.ocihelper.bean.response.oci.cfg.OciCfgDetailsRsp;
import com.yohann.ocihelper.bean.response.oci.cfg.OciUserListRsp;
import com.yohann.ocihelper.service.IInstanceService;
import com.yohann.ocihelper.service.IOciService;
import com.yohann.ocihelper.utils.CommonUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

import java.util.concurrent.ExecutorService;

/**
 * <p>
 * OciController
 * </p >
 *
 * @author yohann
 * @since 2024/11/12 17:17
 */
@RestController
@RequestMapping(path = "/api/oci")
public class OciController {

    @Resource
    private IOciService ociService;
    @Resource
    private IInstanceService instanceService;
    @Resource
    private ExecutorService virtualExecutor;

    @PostMapping(path = "/userPage")
    public ResponseData<Page<OciUserListRsp>> userPage(@Validated @RequestBody GetOciUserListParams params) {
        return ResponseData.successData(ociService.userPage(params), "Lista de usuarios obtenida");
    }

    @PostMapping(path = "/addCfg")
    public ResponseData<Void> addCfg(@Validated AddCfgParams params) {
        ociService.addCfg(params);
        return ResponseData.successData("Configuración añadida");
    }

    @PostMapping(path = "/updateCfgName")
    public ResponseData<Void> updateCfgName(@Validated @RequestBody UpdateCfgNameParams params) {
        ociService.updateCfgName(params);
        return ResponseData.successData();
    }

    @PostMapping(path = "/updateCfgProxy")
    public ResponseData<Void> updateCfgProxy(@Validated @RequestBody UpdateCfgProxyParams params) {
        ociService.updateCfgProxy(params);
        return ResponseData.successData("Proxy actualizado");
    }

    @PostMapping(path = "/uploadCfg")
    public ResponseData<Void> uploadCfg(@Validated UploadCfgParams params) {
        String resultMsg = ociService.uploadCfg(params);
        return ResponseData.successData(resultMsg);
    }

    @PostMapping(path = "/removeCfg")
    public ResponseData<Void> removeCfg(@Validated @RequestBody IdListParams params) {
        ociService.removeCfg(params);
        return ResponseData.successData("Configuración eliminada");
    }

    @PostMapping(path = "/createInstance")
    public ResponseData<Void> createInstance(@Validated @RequestBody CreateInstanceParams params) {
        ociService.createInstance(params);
        return ResponseData.successData("Tarea de creación añadida");
    }

    @PostMapping(path = "/details")
    public ResponseData<OciCfgDetailsRsp> details(@Validated @RequestBody GetOciCfgDetailsParams params) {
        return ResponseData.successData(ociService.details(params), "Detalles de configuración obtenidos");
    }

    @PostMapping(path = "/changeIp")
    public ResponseData<Void> changeIp(@Validated @RequestBody ChangeIpParams params) {
        ociService.changeIp(params);
        return ResponseData.successData("Tarea de cambio de IP creada");
    }

    @PostMapping(path = "/stopCreate")
    public ResponseData<Void> stopCreate(@Validated @RequestBody StopCreateParams params) {
        ociService.stopCreate(params);
        return ResponseData.successData("Tarea de creación detenida");
    }

    @PostMapping(path = "/stopChangeIp")
    public ResponseData<Void> stopChangeIp(@Validated @RequestBody StopChangeIpParams params) {
        ociService.stopChangeIp(params);
        return ResponseData.successData("Tarea de cambio de IP detenida");
    }

    @PostMapping(path = "/createTaskPage")
    public ResponseData<Page<CreateTaskRsp>> createTaskPage(@Validated @RequestBody CreateTaskPageParams params) {
        return ResponseData.successData(ociService.createTaskPage(params), "Tareas de creación obtenidas");
    }

        @PostMapping(path = "/stopCreateBatch")
    public ResponseData<Void> stopCreateBatch(@Validated @RequestBody IdListParams params) {
        ociService.stopCreateBatch(params);
        return ResponseData.successData("Tarea de creación detenida");
    }

        @PostMapping(path = "/updateCreateTask")
    public ResponseData<Void> updateCreateTask(@Validated @RequestBody UpdateCreateTaskParams params) {
        ociService.updateCreateTask(params);
        return ResponseData.successData("Tarea de creación actualizada");
    }

    @PostMapping(path = "/updateCreateTaskBatch")
    public ResponseData<Void> updateCreateTaskBatch(@Validated @RequestBody UpdateCreateTaskBatchParams params) {
        virtualExecutor.execute(() -> ociService.updateCreateTaskBatch(params));
        return ResponseData.successData("Cambios enviados; las tareas se reiniciarán de forma escalonada…");
    }

    @PostMapping(path = "/pauseCreateBatch")
    public ResponseData<Void> pauseCreateBatch(@Validated @RequestBody PauseCreateParams params) {
        ociService.pauseCreateBatch(params);
        return ResponseData.successData("Tarea de creación pausada");
    }

    @PostMapping(path = "/resumeCreateBatch")
    public ResponseData<Void> resumeCreateBatch(@Validated @RequestBody PauseCreateParams params) {
        ociService.resumeCreateBatch(params);
        return ResponseData.successData("Tarea de creación reanudada");
    }

    @PostMapping(path = "/createInstanceBatch")
    public ResponseData<Void> createInstanceBatch(@Validated @RequestBody CreateInstanceBatchParams params) {
        // Submit asynchronously so the client gets an immediate response
        // regardless of how many tasks are being scheduled.
        virtualExecutor.execute(() -> ociService.createInstanceBatch(params));
        return ResponseData.successData("Tareas enviadas; la creación se realizará de forma escalonada…");
    }

    @PostMapping(path = "/updateInstanceState")
    public ResponseData<Void> updateInstanceState(@Validated @RequestBody UpdateInstanceStateParams params) {
        ociService.updateInstanceState(params);
        return ResponseData.successData("Estado de instancia actualizado");
    }

    @PostMapping(path = "/sendCaptcha")
    public ResponseData<Void> sendCaptcha(@Validated @RequestBody SendCaptchaParams params) {
        ociService.sendCaptcha(params);
        return ResponseData.successData("Código enviado; consulta Telegram o DingTalk");
    }

    @PostMapping(path = "/terminateInstance")
    public ResponseData<Void> terminateInstance(@Validated @RequestBody TerminateInstanceParams params) {
        ociService.terminateInstance(params);
        return ResponseData.successData("Solicitud de terminación de instancia enviada");
    }

    @PostMapping(path = "/releaseSecurityRule")
    public ResponseData<Void> releaseSecurityRule(@Validated @RequestBody ReleaseSecurityRuleParams params) {
        ociService.releaseSecurityRule(params);
        return ResponseData.successData("Lista de seguridad abierta");
    }

    @PostMapping(path = "/getInstanceCfgInfo")
    public ResponseData<InstanceCfgDTO> getInstanceCfgInfo(@Validated @RequestBody GetInstanceCfgInfoParams params) {
        return ResponseData.successData(ociService.getInstanceCfgInfo(params), "Configuración de instancia obtenida");
    }

    @PostMapping(path = "/createIpv6")
    public ResponseData<Void> createIpv6(@Validated @RequestBody CreateIpv6Params params) {
        ociService.createIpv6(params);
        return ResponseData.successData("IPv6 añadido a la instancia");
    }

    @PostMapping(path = "/updateInstanceName")
    public ResponseData<Void> updateInstanceName(@Validated @RequestBody UpdateInstanceNameParams params) {
        ociService.updateInstanceName(params);
        return ResponseData.successData("Nombre de instancia actualizado");
    }

    @PostMapping(path = "/updateInstanceRootPassword")
    public ResponseData<Void> updateInstanceRootPassword(@Validated @RequestBody UpdateInstanceRootPasswordParams params) {
        ociService.updateInstanceRootPassword(params);
        String action = (params.getPassword() == null || params.getPassword().isBlank()) ? "Eliminar" : "Actualizar";
        return ResponseData.successData(action + " etiqueta de contraseña de root: operación completada");
    }

    @PostMapping(path = "/updateInstanceCfg")
    public ResponseData<Void> updateInstanceCfg(@Validated @RequestBody UpdateInstanceCfgParams params) {
        ociService.updateInstanceCfg(params);
        return ResponseData.successData("Configuración de instancia actualizada");
    }

    @PostMapping(path = "/updateBootVolumeCfg")
    public ResponseData<Void> updateBootVolumeCfg(@Validated @RequestBody UpdateBootVolumeCfgParams params) {
        ociService.updateBootVolumeCfg(params);
        return ResponseData.successData("Configuración del volumen de arranque actualizada");
    }

    @PostMapping(path = "/checkAlive")
    public ResponseData<Void> checkAlive() {
        return ResponseData.successData(ociService.checkAlive());
    }

    @PostMapping(path = "/checkAliveBatch")
    public ResponseData<Void> checkAliveBatch(@Validated @RequestBody IdListParams params) {
        ociService.checkAliveBatch(params);
        return ResponseData.successData("Comprobación iniciada en segundo plano; actualiza la lista más tarde…");
    }

    @PostMapping(path = "/refreshPlanTypeBatch")
    public ResponseData<Void> refreshPlanTypeBatch(@Validated @RequestBody IdListParams params) {
        ociService.refreshPlanTypeBatch(params);
        return ResponseData.successData("Tipo de plan actualizado");
    }

    @PostMapping(path = "/refreshCfgBatch")
    public ResponseData<Void> refreshCfgBatch(@Validated @RequestBody IdListParams params) {
        ociService.refreshCfgBatch(params);
        return ResponseData.successData("Actualización iniciada; el plan y el estado se consultarán en segundo plano…");
    }

    @PostMapping(path = "/startVnc")
    public ResponseData<String> startVnc(@Validated @RequestBody StartVncParams params) {
        return ResponseData.successData(ociService.startVnc(params));
    }

    @PostMapping(path = "/autoRescue")
    public ResponseData<Void> autoRescue(@Validated @RequestBody AutoRescueParams params) {
        ociService.autoRescue(params);
        return ResponseData.successData();
    }

    @PostMapping(path = "/oneClick500M")
    public ResponseData<Void> oneClick500M(@Validated @RequestBody CreateNetworkLoadBalancerParams params) {
        instanceService.oneClick500M(params);
        return ResponseData.successData("Tarea de activación de 500 Mbps enviada");
    }

    @PostMapping(path = "/oneClickClose500M")
    public ResponseData<Void> oneClickClose500M(@Validated @RequestBody Close500MParams params) {
        instanceService.oneClickClose500M(params);
        return ResponseData.successData("Tarea de desactivación de 500 Mbps enviada");
    }

    @PostMapping(path = "/updateInstanceShape")
    public ResponseData<Void> updateInstanceShape(@Validated @RequestBody UpdateShapeParams params) {
        instanceService.updateInstanceShape(params);
        return ResponseData.successData("Tipo de instancia actualizado");
    }
}
