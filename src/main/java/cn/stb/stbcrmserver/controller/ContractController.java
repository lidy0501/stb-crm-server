package cn.stb.stbcrmserver.controller;

import cn.stb.stbcrmserver.base.RespResult;
import cn.stb.stbcrmserver.domain.Contract;
import cn.stb.stbcrmserver.service.ContractService;
import cn.stb.stbcrmserver.vo.ListReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public List<Contract> queryAllContract(@RequestBody ListReq req){
        return contractService.queryAllContract(req);
    }

    /**
     * 添加/修改合同
     * @param contract
     * @return
     */
    @RequestMapping("addContract")
    public RespResult addContract(@RequestBody Contract contract){
        return contractService.addContract(contract);
    }

    /**
     * 删除合同(软删除)
     * @param contractId
     * @return
     */
    @RequestMapping("deleteContract/#{contractId}")
    public RespResult deleteContract(@PathVariable String contractId){
        return contractService.deleteContract(contractId);
    }

    /**
     * 查询单个合同信息
     * @param contractId
     * @return
     */
    @RequestMapping("selectContractById/#{contractId}")
    public Contract selectContractById(@PathVariable String contractId){
        return contractService.selectContractById(contractId);
    }
}
