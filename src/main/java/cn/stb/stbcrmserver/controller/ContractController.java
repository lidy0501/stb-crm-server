package cn.stb.stbcrmserver.controller;

import cn.stb.stbcrmserver.base.RespResult;
import cn.stb.stbcrmserver.base.Right;
import cn.stb.stbcrmserver.base.RightType;
import cn.stb.stbcrmserver.domain.Contract;
import cn.stb.stbcrmserver.service.ContractService;
import cn.stb.stbcrmserver.vo.ListReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static cn.stb.stbcrmserver.base.RightType.CRM_合同管理;
import static cn.stb.stbcrmserver.base.RightType.CRM_商品管理;

@RestController
@RequestMapping("/ContractController")
public class ContractController {

    @Autowired
    private ContractService contractService;

    /**
     * 展示所有合同
     * @param req
     * @return
     */
    @RequestMapping("queryAllContract")
    @Right(CRM_合同管理)
    public List<Contract> queryAllContract(@RequestBody ListReq req){
        return contractService.queryAllContract(req);
    }

    /**
     * 添加/修改合同
     * @param contract
     * @return
     */
    @RequestMapping("addContract")
    @Right(CRM_合同管理)
    public RespResult addContract(@RequestBody Contract contract){
        return contractService.addContract(contract);
    }

    /**
     * 删除合同(软删除)
     * @param contractId
     * @return
     */
    @RequestMapping("deleteContract/{contractId}")
    @Right(CRM_合同管理)
    public RespResult deleteContract(@PathVariable String contractId){
        return contractService.deleteContract(contractId);
    }

    /**
     * 查询单个合同信息
     * @param contractId
     * @return
     */
    @RequestMapping("selectContractById/{contractId}")
    @Right(CRM_合同管理)
    public Contract selectContractById(@PathVariable String contractId){
        return contractService.selectContractById(contractId);
    }
}
