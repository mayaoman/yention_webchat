package com.yention.tcm.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yention.tcm.api.entities.DoctorDiseaseEntity;

/** 
 * @Package com.yention.tcm.api.repositories
 * @ClassName: DoctorDiseaseRepository
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 孙刚
 * @date 2019年4月26日 下午9:00:27
 */
public interface DoctorDiseaseRepository extends JpaRepository<DoctorDiseaseEntity, String> {

}
