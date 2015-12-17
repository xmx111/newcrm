package ${root_package}.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ${root_package}.dao.interfaces.${entity_name}Dao;
import ${root_package}.entity.${entity_name};
import ${root_package}.service.interfaces.I${entity_name}Service;
import com.ufo.core.dao.BaseDao;
import com.ufo.core.service.BaseSpringDataService;
import com.ufo.core.service.IBaseSpringDataService;

@Service
@Transactional
public class ${entity_name}Service extends BaseSpringDataService<${entity_name}, ${id_type}> implements I${entity_name}Service {
    
    @Autowired
    private ${entity_name}Dao ${entity_name_uncapitalize}Dao;

    @Override
    public BaseDao<${entity_name}, ${id_type}> getEntityDao() {
        return ${entity_name_uncapitalize}Dao;
    }
}
