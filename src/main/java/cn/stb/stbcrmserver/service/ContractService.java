package cn.stb.stbcrmserver.service;

import cn.stb.stbcrmserver.base.Page;
import cn.stb.stbcrmserver.base.RespResult;
import cn.stb.stbcrmserver.context.AcContext;
import cn.stb.stbcrmserver.dao.ContractDao;
import cn.stb.stbcrmserver.dao.OrderDao;
import cn.stb.stbcrmserver.domain.Contract;
import cn.stb.stbcrmserver.domain.Order;
import cn.stb.stbcrmserver.domain.Staff;
import cn.stb.stbcrmserver.utils.UUIDUtil;
import cn.stb.stbcrmserver.vo.ListReq;
import cn.stb.stbcrmserver.vo.Order4Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class ContractService {
    @Autowired
    private ContractDao contractDao;
    @Autowired
    private OrderDao orderDao;

    public List<Contract> queryAllContract(ListReq req) {
        int startIndex = req.getStartIndex();
        Page page = new Page(startIndex, 10);
        List<Contract> contractList =  contractDao.queryAllContract();
        page.setTotalRows(contractList.size());
        return contractList;
//        if (contractList.size() == 0){
//            return new ListVo<>(new ArrayList<Contract>(), page);
//        }
    }

    @Transactional
    public RespResult addContract(Contract contract) {
        //验证合同编号是否存在
        if (!StringUtils.isEmpty(contract.getContractCode())) {
            Contract contractA = contractDao.selectContractByCode(contract.getContractCode());
            if (contractA != null && contractA.getContractId() != contract.getContractId()) return RespResult.fail("该合同编号已经存在!");
        }
        //判断是否存在合同ID 空是新增否则是编辑
        if (StringUtils.isEmpty(contract.getContractId())) {
            contract.setOperatorId(AcContext.getStaffId());
            contract.setContractId(UUIDUtil.getNumId());
            int effectedNum = contractDao.addContract(contract);
            if (effectedNum > 0) return RespResult.ok("添加合同成功!");
            return RespResult.fail("添加合同失败!");
        } else {
            int effectNum = contractDao.updateContract(contract);
            if (effectNum > 0) return RespResult.ok("修改成功!");
            return RespResult.fail("修改失败!");
        }
    }


    public RespResult deleteContract(String contractId) {
        Contract contract = contractDao.findContractById(contractId);
        Order order  = orderDao.findOrderByCode(contract.getOrderCode());
        if (order.equals("") || order.equals(null)){
            return RespResult.fail("删除失败,该合同中的订单尚未被删除!");
        }else {
            int effectNum = contractDao.deleteContract(contractId);
            if(effectNum>0) return RespResult.ok("删除成功!");
            return RespResult.fail("删除失败");
        }
    }

    public Contract selectContractById(String contractId) {
        return contractDao.selectContractById(contractId);
    }

	public List<Order4Select> queryOrdersByLikeCode(String orderCode) {
        Staff staff = AcContext.getStaff();
        Map map = new HashMap();
        map.put("operatorId", staff.getStaffId());
        map.put("staffType", staff.getStaffType());
        map.put("orderCode", orderCode);
        List<Order> orders = orderDao.queryOrdersByLikeCode(map);
        List<Order4Select> order4SelectList = orders.stream().map(o -> Order4Select.builder().orderCode(o.getOrderCode()).orderPrice(o.getTotalFee()).build()).collect(Collectors.toList());
        return order4SelectList;
	}
}
