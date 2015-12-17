package ${root_package}.dao.interfaces;

import org.springframework.stereotype.Repository;

import ${root_package}.entity.${entity_name};
import com.ufo.core.dao.BaseDao;

@Repository
public interface ${entity_name}Dao extends BaseDao<${entity_name}, ${id_type}> {

}
