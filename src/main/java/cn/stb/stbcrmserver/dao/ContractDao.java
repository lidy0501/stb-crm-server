package cn.stb.stbcrmserver.dao;

import cn.stb.stbcrmserver.domain.Contract;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ContractDao {

    List<Contract> queryAllContract();

    int addContract(Contract contract);

    Contract selectContractByCode(String contractCode);

    int updateContract(Contract contract);

    Contract selectContractById(String contractId);

    Contract findContractById(String contractId);

    int deleteContract(String contractId);
}
